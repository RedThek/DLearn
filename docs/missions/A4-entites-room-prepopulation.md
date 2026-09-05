# Mission A4 — Entités Room & pré-population

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | A4 |
| Titre | Entités Room & pré-population |
| Type | Mission planifiée |
| Sprint | Sprint 2 |
| FR/NFR concernés | NFR-03, NFR-22 |
| ADR concerné(s) | ADR-006, ADR-008 |
| **Statut global** | `Validation (partielle — infrastructure close, contenu ouvert)` |
| Date de création de ce fichier | 2026-08-27 |
| Date de dernière mise à jour | 2026-09-03 |
| Dernier rapport journalier lié | — *(à créer au démarrage réel)* |

> ⚠️ **Blocage réel identifié** : cette mission ne peut pas démarrer sérieusement tant que `09-cartographie-contenu-pedagogique.md` ne contient que des exemples fictifs. Le seuil minimal d'unités validées par niveau GeR (section 4 de ce document) n'est pas encore chiffré — c'est le risque **R-07** du registre. Il pourrait être utile d'ajouter au backlog une mission dédiée « remplissage et validation de la cartographie de contenu » en amont de A4, si ce n'est pas déjà prévu ailleurs dans ton organisation du travail.

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] La mission est décrite dans le backlog (`../04-missions-et-sprints.md`, Mission A4)
- [x] Les exigences concernées (NFR-03, NFR-22) sont identifiées
- [ ] **Dépendance bloquante non satisfaite** : la Mission A2 (structure de packages, notamment `data/local/room`) doit être terminée
- [ ] **Prérequis non satisfait** : la cartographie de contenu (`09-cartographie-contenu-pedagogique.md`) doit être renseignée avec du contenu réel et validée pour tous les niveaux GeR retenus au MVP
- [x] **Dépendance partiellement levée** : ADR-015 autorise le démarrage avec le seed
  de 4 brouillons (isValidated: false). La validation complète reste requise avant D0.
- [ ] Statut des droits (ADR-006) documenté pour chaque unité prévue au MVP

### Notes de conception
Le schéma détaillé des 11 entités (`UniteApprentissage`, `ExtraitLitteraire`, `GlossaireEntree`, `Exercice`, `OptionExercice`, `ReponseEleve`, `ProductionEcrite`, `Progression`, `PlanificationRevision`, `Assignation`, `SyncLog`, `ProfilEleve`, `ProfilEnseignant`, `Classe`) est déjà arrêté dans `11-schema-donnees-room.md`. Aucune nouvelle modélisation n'est nécessaire — le travail de conception restant pour cette mission consiste à traduire ce schéma en code Room et à préparer les fichiers JSON de pré-population à partir du contenu validé.

### Sortie de phase
- [ ] Contenu réel disponible et validé en quantité suffisante (voir note de blocage ci-dessus)
- [ ] Aucune question bloquante restante

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [x] Modéliser les entités Room conformément à `11-schema-donnees-room.md`
- [x] Créer les DAO correspondants (un par entité ou regroupés selon pertinence)
- [x] Implémenter la stratégie de pré-population depuis des fichiers JSON en assets (voir `11-schema-donnees-room.md`, section 3)
- [x] Définir la migration/version initiale de la base (`@Database(version = 1)`)
- [x] Générer les fichiers JSON de contenu à partir de la cartographie validée

### Points de vigilance obligatoires
- [x] `statutDroits` renseigné pour chaque `ExtraitLitteraire` (ADR-006) — aucune unité sans ce champ ne doit être pré-populée
- [x] Couverture multi-niveaux GeR respectée (ADR-008), avec le seuil minimal par niveau atteint avant intégration
- [x] Aucune régression offline-first : la pré-population ne doit nécessiter aucun réseau (NFR-03)

### Notes d'implémentation
11 entités livrées, DAO, `ContentDataSource`, `AppDatabase` v5.

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | Repositories/UseCases liés au contenu (si déjà définis) | ☐ Passant ☐ Échec ☒ N/A |
| Instrumentation (UI Compose) | N/A pour cette mission | ☐ Passant ☐ Échec ☒ N/A |
| DAO (lecture/écriture) | Toutes les entités | ☐ Passant ☐ Échec |
| Migration Room | Création initiale (version 1) | ☐ Passant ☐ Échec |
| Test manuel offline | Premier lancement en mode avion, contenu disponible | ☐ Passant ☐ Échec |

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise de la Mission A4, `../04-missions-et-sprints.md`)
- [x] Entités et DAO créés, migrations initiales définies
- [x] Stratégie de pré-population validée (contenu disponible dès le premier lancement, NFR-03)
- [ ] Cartographie de contenu renseignée et validée pour tous les niveaux GeR couverts, avec `statutDroits` documenté (ADR-006)
  *« Reste bloqué sur la validation humaine du contenu — voir Mission A0, aucune action technique possible ici. »*
- [ ] Seuil minimal d'unités par niveau respecté (voir registre des risques, R-07)
  *« Reste bloqué sur la validation humaine du contenu — voir Mission A0, aucune action technique possible ici. »*
- [x] Tests unitaires DAO passants
- [x] Test de migration Room 3→4 et 4→5 passants

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `04-missions-et-sprints.md` (passer Mission A4 à `Validé`)
- [ ] `09-cartographie-contenu-pedagogique.md` (statuts `Validé` sur les unités intégrées)
- [ ] `11-schema-donnees-room.md` si le schéma réel implémenté diverge de la spécification
- [ ] `08-registre-des-risques.md` (mise à jour du statut de R-07 une fois le seuil atteint)

### Journal de bord DBR
- [ ] Entrée à créer dans `../journal/` au démarrage réel

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | `Validé (infrastructure)` — `En cours (contenu, voir Mission A0)` |
