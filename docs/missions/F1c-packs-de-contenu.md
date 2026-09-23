# Mission F1c — Packs de contenu et dictionnaire hors application

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F1c |
| Titre | Packs de contenu (`.ikiipack`), pack `core`, dictionnaire séparé |
| Type | Mission planifiée |
| Sprint | À planifier — préalable à la Mission F5 (dictionnaire) |
| FR/NFR concernés | FR-31 |
| ADR concerné(s) | ADR-028 |
| **Statut global** | `Conception` — non démarrée |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — *(à créer au démarrage réel)* |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] ADR-028 accepté ; spécification `../20-specification-formats-echange-et-packs.md` §8 rédigée (manifeste, règles d'upsert)
- [ ] Mission F1a terminée (le suivi des packs n'a pas de dépendance forte à l'identité, mais partage ses conventions de somme de contrôle et de champ `signature` réservé)

### Notes de conception
Le seed embarqué (`seed_v1.json`) devient le **pack `core`**, installé par le même importeur que tout pack ultérieur. Un pack est un ZIP `.ikiipack` avec un manifeste (`packId`, `packVersion`, `checksum`, licence, référence au registre `19-…`). Les identifiants de contenu restent stables et immuables ; une correction de forme incrémente `revision`, un changement de sens crée un nouvel identifiant ; un contenu retiré est marqué `retire`, jamais supprimé (pour préserver les progressions déjà enregistrées). Le dictionnaire est une base séparée, en lecture seule, jamais migrée avec la base principale. Détail intégral : `20-specification-formats-echange-et-packs.md` §8.

### Sortie de phase
- [ ] Structure exacte de `pack_installe` et des colonnes `revision`/`retire` arrêtée (voir `11-schema-donnees-room.md` section 6)
- [ ] Outil de fabrication de packs (hors application) esquissé, au moins en ligne de commande

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Migration Room : colonnes `revision` (défaut 1) et `retire` (défaut 0) sur `unite_apprentissage`, `extrait_litteraire`, `glossaire_entree`, `exercice`, `option_exercice`
- [ ] Nouvelle table `pack_installe` (`packId`, `packVersion`, `checksum`, `dateInstallation`)
- [ ] Importeur de pack unique : lecture du ZIP, vérification du manifeste et de la somme de contrôle, upsert transactionnel selon les règles de `20-…` §8.3
- [ ] Migrer `ContentDataSource.peupler()` vers cet importeur unique (le seed devient le pack `core` embarqué dans les assets)
- [ ] Écran « Crédits » alimenté par les manifestes des packs installés
- [ ] Interface d'installation d'un pack externe (sélecteur de fichier, comme pour l'import de synchronisation)

### Points de vigilance obligatoires
- [ ] Une correction de contenu (nouvelle `revision`) ne doit jamais faire perdre une progression existante
- [ ] Installer deux fois le même pack (même `packVersion`, même `checksum`) est sans effet
- [ ] Aucune donnée d'élève n'est modifiée par l'installation d'un pack

### Notes d'implémentation
*(à compléter au démarrage)*

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Migration Room | `pack_installe`, colonnes `revision`/`retire` | ☐ Passant ☐ Échec |
| Unitaire | Upsert (insertion, mise à jour de révision, retrait) | ☐ Passant ☐ Échec |
| Unitaire | Installation idempotente (même pack deux fois) | ☐ Passant ☐ Échec |
| Test manuel | Une correction de contenu appliquée sans perte de progression (scénario reproduisant le bug B-09) | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] Une correction de contenu se propage sans perte de progression (R-29 clos)
- [ ] Installation idempotente d'un même pack
- [ ] Migration explicite testée (ADR-017)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `11-schema-donnees-room.md`, `14-charte-versionnage-contenu.md`
- [ ] `08-registre-des-risques.md` (R-29 → `Clos`)
- [ ] `04-missions-et-sprints.md` (statut de F1c)

### Journal de bord DBR
- [ ] Entrée à créer au démarrage réel

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | Non démarrée — dépend de F1a |
