# Mission C2 — Dashboard enseignant (implémentation)

| Champ | Valeur |
|---|---|
| ID | C2 |
| Titre | Dashboard enseignant (implémentation) |
| Type | Mission planifiée |
| Sprint | Sprint 3 |
| FR/NFR concernés | FR-24 à FR-27, FR-33 |
| ADR concerné(s) | ADR-016 |
| **Statut global** | `Implémentation` |
| Date de création | 2026-09-05 |
| Date de mise à jour | 2026-09-05 |
| Rapport lié | [2026-09-05](../journal/2026-09-05.md) |

---

## Phase 1 — Conception
Terminée (voir `RECONCILIATION-SPRINT3.md`). Simplification via ADR-016 validée.

## Phase 2 — Implémentation
- [x] (Backend) Migration 4→5 : ajout table `assignation` et `statut` sur `production_ecrite`
- [x] (Backend) `AssignationEntity`, `AssignationDao`, `AssignationRepository`
- [x] (Backend) `SoumettreProductionUseCase` (B-21 corrigé)
- [x] (Backend) `GetProductionsSoumisesUseCase`
- [ ] (Frontend) Dialog Assigner fonctionnel (FR-26) — *À faire par Frontend Agent*
- [ ] (Frontend) Onglet Corrections réel (FR-27) — *À faire par Frontend Agent*

## Phase 3 — Test
- [x] Migration Room 4→5 testée via `MigrationTest.kt`
- [x] Build backend OK
- [ ] Tests UI (Frontend)

## Phase 4 — Validation
- [ ] DoD complète (attend le frontend)
