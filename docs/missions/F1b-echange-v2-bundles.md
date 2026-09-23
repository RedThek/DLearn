# Mission F1b — Échange v2 : bundles, `rev`, import atomique

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F1b |
| Titre | Format d'échange v2 (bundles par propriétaire) |
| Type | Mission planifiée |
| Sprint | À planifier — préalable à la clôture de la Mission C3 et à la Mission D0 |
| FR/NFR concernés | FR-29, FR-47, NFR-34 |
| ADR concerné(s) | ADR-027 |
| **Statut global** | `Conception` — non démarrée |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — *(à créer au démarrage réel)* |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] ADR-027 accepté ; spécification `../20-specification-formats-echange-et-packs.md` rédigée avec vecteur de test
- [ ] **Mission F1a terminée** (le `uid` est le pivot de tous les bundles)
- [ ] Test à deux appareils confirmant R-27 (Mission F1)

### Notes de conception
Quatre types de bundles (`STUDENT_REPORT`, `PROVISION`, `FEEDBACK`, `CLASS_PACKET`), partitionnés par propriétaire des données, arbitrés par un compteur `rev` plutôt que par l'horloge. Import atomique, somme de contrôle SHA-256 obligatoire, détection de rejeu par `bundleId`. Hash de mot de passe étiqueté (PBKDF2 avec sel), les hash SHA-256 existants étant marqués `SHA-256-LEGACY` et rehashés à la prochaine connexion réussie. Signature réservée, non activée en v1 du format. Détail intégral, y compris le vecteur de test et sa somme SHA-256 : `20-specification-formats-echange-et-packs.md`.

**Point à mesurer avant de coder** : le nombre d'itérations PBKDF2 doit être calibré sur les appareils de référence (Tecno, Itel — ADR-012) pour rester rapide sur un appareil d'entrée de gamme sans affaiblir la protection.

### Sortie de phase
- [ ] Nombre d'itérations PBKDF2 mesuré et fixé
- [ ] Schéma exact des nouvelles colonnes/tables arrêté (voir `11-schema-donnees-room.md` section 6)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Migration Room : `progression.rev`, `production_ecrite.rev` (défaut 1) ; colonnes de hash étiqueté sur `utilisateur` ; `sync_log.bundleId`
- [ ] Nouvelle table `commentaire`
- [ ] Sérialiseur/désérialiseur des bundles conforme à l'enveloppe de `20-…` (forme canonique, somme de contrôle)
- [ ] Émission : `STUDENT_REPORT` (élève → enseignant), `PROVISION`, `FEEDBACK`, `CLASS_PACKET` (enseignant → élève)
- [ ] Réception : ordre normatif de `20-…` §6 (refus v1, vérification de somme, détection de rejeu, transaction unique, résumé d'import)
- [ ] Statuts déduits : « commenté » à partir de `commentaire.productionRev` ; aucune écriture de statut dans la ligne de l'élève par l'enseignant
- [ ] Migration du hash SHA-256 existant vers PBKDF2 (rehash à la connexion réussie)
- [ ] Refus explicite des fichiers `formatVersion: 1`, avec message clair
- [ ] Mise à jour de l'UI de synchronisation (Profil, Dashboard enseignant) pour les nouveaux types de bundles

### Points de vigilance obligatoires
- [ ] Reproduire exactement le vecteur de test de `20-…` §6 dans un test unitaire
- [ ] Import interrompu (simulation) : aucun état partiel
- [ ] Aucun mot de passe en clair, à aucune étape, dans un fichier

### Notes d'implémentation
*(à compléter au démarrage)*

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire | Vecteur de test de `20-…` (somme de contrôle exacte) | ☐ Passant ☐ Échec |
| Unitaire | Fichier tronqué, fichier rejoué, import interrompu | ☐ Passant ☐ Échec |
| Migration Room | Nouvelles colonnes et table `commentaire` | ☐ Passant ☐ Échec |
| Test manuel à deux appareils | Synchronisation dans les deux sens (`STUDENT_REPORT` puis `CLASS_PACKET`/`FEEDBACK`) — reprise de `TEST-PROTOCOLE-F1-DEUX-APPAREILS.md`, adapté au format v2 | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] Bundles conformes à `20-…`, vecteur de test reproduit
- [ ] Import atomique, résumé complet (appliqués/ignorés/inconnus/élèves inconnus signalés)
- [ ] Test à deux appareils physiques réussi dans les deux sens (clôture C3-T11 pour le format v2)
- [ ] `14-charte-versionnage-contenu.md` mis à jour (rupture v1 → v2 documentée)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `11-schema-donnees-room.md`, `14-charte-versionnage-contenu.md`
- [ ] `08-registre-des-risques.md` (R-27, R-30 → `Clos`)
- [ ] `docs/missions/C3-synchronisation-locale.md` (clôture définitive)
- [ ] `04-missions-et-sprints.md` (statut de F1b)

### Journal de bord DBR
- [ ] Entrée à créer au démarrage réel

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | Non démarrée — dépend de F1a et du résultat du test à deux appareils (Mission F1) |
