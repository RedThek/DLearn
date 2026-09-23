# Mission F0 — Gel de périmètre et gouvernance de la vision

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F0 |
| Titre | Gel de périmètre et gouvernance de la vision |
| Type | Mission planifiée (documentaire, parallèle au Sprint 5) |
| Sprint | Sprint 5 (parallèle) |
| FR/NFR concernés | FR-35 à FR-46, NFR-30 à NFR-32 |
| ADR concerné(s) | ADR-020 à ADR-025 |
| **Statut global** | `Documentation` |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | `../journal/2026-09-21.md` |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] Une proposition d'extension du produit a été soumise (`DLearn-New-Fonctionnalités.md`)
- [x] Analyse d'impact réalisée : la proposition mêle un noyau pédagogique hors ligne (compatible ADR-002/ADR-008) et une plateforme sociale/éditoriale en ligne (incompatible ADR-002)

### Notes de conception
Douze capacités dédupliquées à partir des sections redondantes du document source, classées selon six critères (K1 hors ligne, K2 alignement DBR, K3 niveau CECR, K4 charge de validation, K5 droits/mineurs, K6 coût solo), puis réparties en trois horizons (H1 Cycle 1, H2 Cycle 2, H3 après soutenance). Détail complet : `../18-vision-produit-et-horizons.md`.

### Sortie de phase
- [x] Classement des 12 capacités arrêté
- [x] Six ADR rédigés et validés par le porteur (ADR-020 à ADR-025)

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 2 — Implémentation

> Mission documentaire : « implémentation » désigne ici l'intégration des textes dans le dépôt.

### Découpage en sous-tâches
- [ ] Intégrer `18-vision-produit-et-horizons.md` et `19-registre-licences-contenus-tiers.md` dans `docs/`
- [ ] Coller ADR-020 à ADR-025 dans `06-architecture-technique.md` (voir `INTEGRATION-DOCS-VISION.md`)
- [ ] Inscrire R-22 à R-27 dans `08-registre-des-risques.md`
- [ ] Inscrire FR-35 à FR-46 et NFR-30 à NFR-32
- [ ] Créer le Bloc F dans `04-missions-et-sprints.md`
- [ ] Petits ajouts (`03-…`, `05-…`, `07-…`, `09-…`, `README.md`, `ETAT_ACTUEL.md`)

**Statut de la phase :** ☐ À faire ☒ En cours ☒ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Cohérence documentaire (`grep`) | Numérotation ADR/FR/NFR/risques, aucun fichier hors `docs/` modifié — `INTEGRATION-DOCS-VISION.md` §8 | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] ADR-020 à ADR-025 intégrés à `06-architecture-technique.md`
- [ ] `18-…` et `19-…` ajoutés ; `README.md` et `ETAT_ACTUEL.md` mis à jour
- [ ] Risques R-22 à R-27 inscrits ; FR-35 à FR-46 et NFR-30 à NFR-32 inscrits

**Statut de la phase :** ☐ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [x] `18-vision-produit-et-horizons.md` (créé)
- [x] `19-registre-licences-contenus-tiers.md` (créé)
- [ ] `06-…`, `08-…`, `01-…`, `02-…`, `04-…`, `03-…`, `09-…`, `05-…`, `07-…`, `README.md`, `ETAT_ACTUEL.md`

### Journal de bord DBR
- [x] Entrée créée dans `../journal/2026-09-21.md`

**Statut de la phase :** ☐ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non clôturée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | En cours — décisions tranchées, intégration documentaire restant à réaliser |
