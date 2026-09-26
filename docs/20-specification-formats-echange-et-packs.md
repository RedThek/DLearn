# Spécification des formats d'échange (v2) et des packs de contenu — Liteschreib IKII

> **Statut :** Spécification de référence v2.0 — 2026-09-21. Issue d'ADR-026, ADR-027 et ADR-028 (acceptés).
> **Rôle :** contrat précis entre l'application et tout outil qui lit ou écrit ces fichiers (agents de code, outil de fabrication de packs, futur « DLearn Hub »). Aucune implémentation n'existe encore : ce document est la cible des Missions F1a, F1b, F1c et C3.
> **À faire à l'ouverture de F1b :** relire ce document avec le code réel, corriger les écarts, puis figer la version.

## 1. Conventions générales

| Sujet | Règle |
|---|---|
| Encodage | JSON UTF-8, sans BOM |
| Fichier bundle | Extension `.ikii.json`, type MIME `application/json` |
| Fichier pack | Extension `.ikiipack`, conteneur ZIP standard, type MIME `application/zip` |
| Identifiants | UUID v4 en minuscules avec tirets (`uid`, `bundleId`, `instanceId`, identifiants d'enregistrements additifs) |
| Dates | Entiers en millisecondes depuis l'époque Unix. **Informatives** : elles ne servent jamais d'autorité pour arbitrer un conflit |
| Nombres non entiers | Au plus 4 décimales, sans exposant, sans zéros de fin (`0.75`, jamais `0.750` ni `7.5e-1`). L'écrivain arrondit avant sérialisation |
| Champs inconnus | Ignorés en lecture (compatibilité ascendante) |
| Enregistrements de type inconnu | Ignorés et comptés dans le résumé d'import ; ils ne font pas échouer le bundle |
| Noms | Aucun bundle `STUDENT_REPORT` ne contient de nom d'élève : seul le `uid` transite |

## 2. Enveloppe d'un bundle

| Champ | Type | Obligatoire | Description |
|---|---|---|---|
| `format` | chaîne | oui | Toujours `ikii-bundle` |
| `formatVersion` | entier | oui | `2` pour cette spécification |
| `minReader` | entier | oui | Plus petite `formatVersion` capable de lire ce fichier |
| `bundleId` | UUID | oui | Identifiant unique du fichier ; sert à détecter un rejeu |
| `type` | chaîne | oui | `STUDENT_REPORT`, `PROVISION`, `FEEDBACK` ou `CLASS_PACKET` |
| `origin` | objet | oui | `instanceId` (UUID de l'installation), `role` (`ELEVE` ou `ENSEIGNANT`), `uid` (utilisateur émetteur) |
| `audience` | objet | oui | `uid` (destinataire unique) **ou** `classe` (destinataires d'une classe) |
| `createdAt` | entier | oui | Date de création (informative) |
| `appVersion` | chaîne | non | Version de l'application émettrice, pour le support |
| `records` | tableau | oui | Enregistrements (voir §4) |
| `checksum` | chaîne | oui | SHA-256 hexadécimal en minuscules de la forme canonique de `records` (§6) |
| `signature` | chaîne ou `null` | oui | **Réservée**, toujours `null` en v2.0 (ADR-027) |

## 3. Types de bundles

| Type | Sens | Adressage | Contenu typique | Sensibilité |
|---|---|---|---|---|
| `STUDENT_REPORT` | élève → enseignant | Émis par un appareil pour un enseignant ou une classe | `progression`, `production`, `session` d'un ou plusieurs élèves du même appareil | Moyenne (productions écrites de mineurs) |
| `PROVISION` | enseignant → élève | **Un fichier par élève** (`audience.uid`) | `account` | **Élevée** (contient un identifiant de connexion et un hash) |
| `FEEDBACK` | enseignant → élève | **Un fichier par élève** (`audience.uid`) | `commentaire` | Élevée (un commentaire ne doit jamais atteindre un autre élève) |
| `CLASS_PACKET` | enseignant → classe | Une classe (`audience.classe`) | `assignation`, `annonce` | Faible (informations communes) |

Règle d'adressage : tout enregistrement propre à un élève voyage dans un fichier adressé à cet élève seul. Un `CLASS_PACKET` ne contient jamais de donnée individuelle.

## 4. Enregistrements

Chaque enregistrement a la forme `{ "kind", "key", "rev", "data" }`. `rev` est un entier croissant, incrémenté **par le propriétaire** de l'enregistrement à chaque modification (voir §5). Pour les enregistrements additifs, `rev` vaut toujours `1`.

### 4.1 Enregistrements définis en v2.0

| `kind` | Propriétaire | Nature | `key` | `data` |
|---|---|---|---|---|
| `progression` | Élève | État | `studentUid`, `uniteId` | `statut` (`NON_COMMENCE`, `EN_COURS`, `TERMINE`), `scoreMoyen` (nombre ou `null`), `updatedAt` |
| `production` | Élève | État | `studentUid`, `uniteId` | `statut` (`BROUILLON` ou `SOUMIS`), `contenuTexte`, `autoEvaluation` (objet ou `null`), `updatedAt`. Seules les productions `SOUMIS` sont exportées |
| `session` | Élève | Additif | `id` (UUID) | `studentUid`, `dateDebut`, `dateFin` |
| `account` | Enseignant | État | `uid` | `uid`, `identifiant`, `nomAffiche`, `role` (`ELEVE`), `classe`, `niveau`, `credential` (voir §4.2) |
| `assignation` | Enseignant | Additif | `id` (UUID) | `enseignantUid`, `cibleType` (`ELEVE` ou `CLASSE`), `cibleId` (`uid` de l'élève **ou** nom de classe), `uniteId`, `dateAssignation` |
| `commentaire` | Enseignant | Additif | `id` (UUID) | `studentUid`, `uniteId`, `productionRev` (le `rev` de la production commentée), `texte`, `dateCommentaire` |
| `annonce` | Enseignant | Additif | `id` (UUID) | `titre`, `texte`, `date`, `expireLe` (facultatif) |

### 4.2 Le bloc `credential`

| Champ | Description |
|---|---|
| `alg` | `PBKDF2WithHmacSHA256` (cible) ou `SHA-256-LEGACY` (hash sans sel hérité, à rehasher à la prochaine connexion réussie) |
| `salt` | Sel aléatoire encodé en Base64 (absent si `SHA-256-LEGACY`) |
| `iterations` | Nombre d'itérations (absent si `SHA-256-LEGACY`), à calibrer sur les appareils de référence (ADR-012) |
| `hash` | Empreinte du mot de passe, encodée en hexadécimal ou Base64 selon `alg` (à fixer à l'implémentation) |

Un mot de passe en clair ne figure **jamais** dans un bundle.

### 4.3 Enregistrements réservés (non définis en v2.0)

| `kind` | Usage prévu | Mission |
|---|---|---|
| `reponse` | Réponses aux exercices (additif) | À décider |
| `productionVersion` | Historique de versions (`studentUid`, `uniteId`, `n`) — FR-39 | F3 |
| `publication` | Publication de classe : sélection et consentement (propriétaire enseignant) — FR-40 | F3 |

## 5. Règles de propriété, de fusion et de statut

1. **Un seul propriétaire par enregistrement.** Le propriétaire est celui du tableau §4.1. Un récepteur n'émet jamais de modification d'un enregistrement dont il n'est pas propriétaire.
2. **Enregistrements additifs :** insertion si l'`id` n'existe pas, sinon rien (opération idempotente).
3. **Enregistrements d'état :** le récepteur applique l'enregistrement entrant si `rev` entrant > `rev` local, sinon l'ignore. En cas d'égalité, il conserve la version locale.
4. **Cas résiduel — un élève sur deux appareils :** deux séries de `rev` divergent. On applique « `rev` le plus élevé gagne ; à égalité de `rev`, `updatedAt` le plus récent ». Cas rare, documenté et accepté.
5. **Statuts dérivés :** le statut d'une production appartient à l'élève. « Commenté » n'est jamais écrit dans la ligne de l'élève : il se déduit de l'existence d'un `commentaire` dont `productionRev` est supérieur ou égal au `rev` courant de la production. La publication de classe est un enregistrement distinct (`publication`, propriétaire enseignant).
6. **`sync_log` n'est jamais transmis.**

## 6. Réception d'un bundle

Ordre normatif :

1. Lire l'enveloppe ; refuser si `format` est inconnu, ou si `minReader` est supérieur à la version de lecture de l'application (message explicite).
2. Refuser si `formatVersion` vaut `1` : les fichiers v1 ne sont plus acceptés (§9).
3. Vérifier le `checksum` (voir ci-dessous) ; refuser en cas d'écart (fichier tronqué ou corrompu).
4. Vérifier le destinataire : `audience.uid` doit être l'utilisateur d'un profil de l'appareil, ou `audience.classe` correspondre à une classe présente.
5. Vérifier le rejeu : si `bundleId` est déjà consigné, informer « déjà importé » sans rien modifier.
6. Appliquer **tous** les enregistrements dans **une seule transaction**, selon §5. En cas d'erreur, rien n'est modifié.
7. Enregistrer le `bundleId` dans le journal de synchronisation (uniquement si la transaction a réussi).
8. Renvoyer un **résumé** à l'utilisateur : nombre d'enregistrements appliqués, ignorés (déjà à jour), de type inconnu, et **inconnus** (voir ci-dessous).

**Enregistrement d'un élève inconnu (côté enseignant) :** un enregistrement dont le `studentUid` ne correspond à aucun élève du dispositif n'est pas écarté silencieusement. Il est compté et signalé dans le résumé (« N enregistrement(s) d'élèves inconnus »), sans être appliqué.

### Forme canonique et somme de contrôle

Le `checksum` est le SHA-256 de la forme canonique du tableau `records` :

- sérialisation JSON compacte, sans espace ni retour à la ligne ;
- clés d'objet triées par ordre lexicographique (points de code Unicode) à tous les niveaux ;
- caractères non ASCII écrits tels quels en UTF-8 (pas de séquences `\uXXXX` sauf pour les caractères de contrôle) ;
- ordre des éléments de tableau conservé ;
- nombres selon la convention du §1.

**Vecteur de test** (à reproduire tel quel dans le test unitaire) :

Enregistrements d'entrée :

```json
[
  {"kind":"progression","key":{"studentUid":"11111111-1111-4111-8111-111111111111","uniteId":"U-6E-01"},"rev":3,"data":{"statut":"TERMINE","scoreMoyen":0.75}},
  {"kind":"production","key":{"studentUid":"11111111-1111-4111-8111-111111111111","uniteId":"U-6E-01"},"rev":2,"data":{"statut":"SOUMIS","contenuTexte":"Ich heiße Aïcha.","autoEvaluation":null}}
]
```

Forme canonique attendue (une seule ligne) :

```
[{"data":{"scoreMoyen":0.75,"statut":"TERMINE"},"key":{"studentUid":"11111111-1111-4111-8111-111111111111","uniteId":"U-6E-01"},"kind":"progression","rev":3},{"data":{"autoEvaluation":null,"contenuTexte":"Ich heiße Aïcha.","statut":"SOUMIS"},"key":{"studentUid":"11111111-1111-4111-8111-111111111111","uniteId":"U-6E-01"},"kind":"production","rev":2}]
```

SHA-256 attendu :

```
09c55b939d0e560616661f63d3ced73b1906075d7c0a33a505095f0f378ec1ef
```

## 7. Exemple de bundle `STUDENT_REPORT`

Valeurs illustratives ; le `checksum` correspond au vecteur de test ci-dessus.

```json
{
  "format": "ikii-bundle",
  "formatVersion": 2,
  "minReader": 2,
  "bundleId": "5b1f0c2e-3a9d-4c58-9f3e-6a0d2b7c1e44",
  "type": "STUDENT_REPORT",
  "origin": {
    "instanceId": "a3c7f0d1-92b4-4e6a-8d15-0b7e4c9a2f31",
    "role": "ELEVE",
    "uid": "11111111-1111-4111-8111-111111111111"
  },
  "audience": { "classe": "6ème" },
  "createdAt": 1790000000000,
  "appVersion": "1.0.0",
  "records": [
    {"kind":"progression","key":{"studentUid":"11111111-1111-4111-8111-111111111111","uniteId":"U-6E-01"},"rev":3,"data":{"statut":"TERMINE","scoreMoyen":0.75}},
    {"kind":"production","key":{"studentUid":"11111111-1111-4111-8111-111111111111","uniteId":"U-6E-01"},"rev":2,"data":{"statut":"SOUMIS","contenuTexte":"Ich heiße Aïcha.","autoEvaluation":null}}
  ],
  "checksum": "09c55b939d0e560616661f63d3ced73b1906075d7c0a33a505095f0f378ec1ef",
  "signature": null
}
```

## 8. Packs de contenu (`.ikiipack`)

### 8.1 Structure du conteneur

| Fichier | Obligatoire | Rôle |
|---|---|---|
| `manifest.json` | oui | Métadonnées du pack (voir §8.2) |
| `content.json` | pour un pack de type `content` | Contenu pédagogique relationnel (même structure que `seed_v1.json`, étendue en §8.3) |
| `reference.db` | pour un pack de type `reference` | Base SQLite en lecture seule (par exemple un dictionnaire) |
| `LICENSE.txt` ou `NOTICE.txt` | oui si le pack contient un élément tiers | Licence et attribution (ADR-025) |

### 8.2 Manifeste

| Champ | Type | Obligatoire | Description |
|---|---|---|---|
| `format` | chaîne | oui | Toujours `ikii-pack` |
| `schemaVersion` | entier | oui | Version de cette spécification pour les packs (`1`) |
| `packId` | chaîne | oui | Identifiant stable (`core`, `dict-de-fr`, …) |
| `packVersion` | chaîne | oui | SemVer du pack |
| `type` | chaîne | oui | `content` ou `reference` |
| `contentVersion` | chaîne | pour `content` | Étiquette `CONTENU-vX.Y` (`14-charte-versionnage-contenu.md`) |
| `minAppVersion` | chaîne | oui | Plus petite version d'application compatible |
| `dependsOn` | tableau | non | Packs requis avec plage de versions |
| `checksum` | chaîne | oui | SHA-256 hexadécimal de la charge utile (`content.json` ou `reference.db`, octets exacts) |
| `license` | chaîne | oui | Identifiant de la licence (par exemple `CC0-1.0`, `CC-BY-SA-4.0`) |
| `attribution` | chaîne | si la licence l'exige | Texte de crédit affiché dans l'écran « Crédits » |
| `registryId` | chaîne | oui | Référence `LIC-xxx` dans `19-registre-licences-contenus-tiers.md` |
| `normalization` | entier | pour `reference` | Version de l'algorithme de normalisation des clés de recherche |
| `signature` | chaîne ou `null` | oui | **Réservée**, `null` en v1 |

### 8.3 Règles pour les packs `content`

1. La structure de `content.json` reprend celle de `seed_v1.json` (`unites`, `extraits`, `glossaire`, `exercices`, `options`). Chaque objet peut porter `revision` (entier, défaut `1`) et `retire` (booléen, défaut `false`).
2. **Identifiants stables et immuables.** Une correction de forme (faute, ponctuation) conserve l'`id` et incrémente `revision`. Un changement de sens (réponse attendue différente, exercice différent) reçoit un **nouvel `id`**.
3. **Upsert transactionnel :** un enregistrement est inséré s'il n'existe pas, remplacé si sa `revision` entrante est supérieure, ignoré sinon.
4. **Retrait :** un contenu retiré est marqué `retire: true` et masqué de l'interface ; il n'est **jamais supprimé** (les progressions et réponses le référencent).
5. **Aucune donnée d'élève n'est modifiée** par l'installation d'un pack.
6. Le pack `core` est le seed embarqué dans l'APK ; il est installé **par le même importeur** que tout autre pack.
7. Suivi des installations : table `pack_installe` (`packId`, `packVersion`, `checksum`, `dateInstallation`) ; installer deux fois le même `packVersion` avec le même `checksum` est sans effet.

### 8.4 Règles pour les packs `reference` (dictionnaire)

1. Un pack par paire de langues (`dict-de-fr`, puis `dict-de-en`, `dict-de-es` ; l'arabe en dernier selon les données du pilote).
2. Base en lecture seule, ouverte séparément de la base principale ; jamais migrée avec elle.
3. Les clés de recherche sont normalisées (casse, ä/ae, ö/oe, ü/ue, ß/ss) selon l'algorithme désigné par `normalization` ; le manifeste et l'application doivent s'accorder sur cette version.
4. Le schéma exact de `reference.db` est fixé à l'ouverture de la Mission F5.
5. Un pack sous licence à partage à l'identique ou copyleft reste **hors du dépôt** et embarque sa propre notice (ADR-025).

## 9. Compatibilité et évolution

| Règle | Détail |
|---|---|
| Version majeure | `formatVersion` change seulement pour une rupture ; toute rupture est consignée dans `14-charte-versionnage-contenu.md` |
| Lecture | Un lecteur de version `N` lit tout fichier dont `minReader` ≤ `N` |
| Ajouts | Nouveaux champs et nouveaux types d'enregistrement sont des ajouts compatibles |
| **Rupture avec v1** | Les fichiers v1 (`versionFichierEchange: 1`, `eleveId` local) sont **refusés** avec un message explicite. Justification : v1 n'a jamais servi sur le terrain et ne porte pas d'identité globale. ADR-018 reste valide pour v1 et est remplacé par ADR-027 pour v2 |

## 10. Modèle de menace (résumé)

| Menace | Réponse en v2.0 |
|---|---|
| Fichier tronqué ou corrompu (Bluetooth, carte SD) | `checksum` obligatoire, refus de l'import |
| Rejeu d'un même fichier | `bundleId` consigné |
| Import partiel après un arrêt | Transaction unique |
| Divulgation entre élèves | Adressage par destinataire (§3), aucun nom dans les rapports d'élèves |
| Fuite d'un mot de passe | Jamais en clair ; hash étiqueté, PBKDF2 avec sel (cible) |
| Un élève modifie ses propres données avant export | **Non traité**, accepté : l'enjeu du pilote est la recherche, pas la notation (ADR-023) |
| Falsification d'un bundle enseignant ou d'un pack | **Non traité en v2.0** ; champ `signature` réservé pour l'activer sans rupture si un enjeu apparaît (concours, H3) |

## 11. Liens

- `06-architecture-technique.md` — ADR-004, ADR-018, ADR-025, ADR-026, ADR-027, ADR-028
- `11-schema-donnees-room.md` — évolutions de schéma décidées
- `14-charte-versionnage-contenu.md` — versions du format d'échange et des packs
- `19-registre-licences-contenus-tiers.md` — champ `registryId` des packs
- `04-missions-et-sprints.md` — Missions C3, F1a, F1b, F1c, F5

## 12. Historique

| Version | Date | Modification |
|---|---|---|
| 2.0 | 2026-09-21 | Création (bundles v2, packs v1, vecteur de test) |
