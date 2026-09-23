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
- [x] (Frontend) Dialog Assigner fonctionnel (FR-26)
- [x] (Frontend) Onglet Corrections réel (FR-27)

## Phase 3 — Test
- [x] Migration Room 4→5 testée via `MigrationTest.kt`
- [x] Build backend OK
- [x] Build frontend OK
- [ ] Tests UI (Frontend) — *À faire*

## Phase 4 — Validation
- [x] Auto-revue effectuée
- [ ] DoD complète (attend validation finale/screenshots)

## Point de vigilance ajouté le 2026-09-22 (ADR-026)

`EnseignantViewModel.charger()` construit `productionsResume` avec :

    val eleve = elevesParId[p.eleveId] ?: return@mapNotNull null

Une production dont l'`eleveId` ne correspond à aucun élève connu de l'enseignant est **écartée silencieusement** de l'onglet Corrections — aucun message, aucun compteur. C'est exactement le mécanisme que R-26 (`08-registre-des-risques.md`) documente comme risque de collision d'identité entre appareils, observable dans le module que possède cette mission.

**Action** : à corriger dans le cadre de la Mission F1b (import atomique avec résumé, `20-specification-formats-echange-et-packs.md` §6 — un enregistrement d'élève inconnu doit être **compté et signalé**, jamais silencieusement ignoré). Aucune action requise dans C2 elle-même avant F1b.
