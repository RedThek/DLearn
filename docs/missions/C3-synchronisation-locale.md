# Mission C3 — Synchronisation locale (BYOD)

| Champ | Valeur |
|---|---|
| ID | C3 |
| Titre | Synchronisation locale (BYOD) |
| Type | Mission planifiée |
| Sprint | Sprint 3 (groundwork) |
| FR/NFR concernés | FR-29 à FR-31, NFR-01 |
| ADR concerné(s) | ADR-004 |
| **Statut global** | `Validation` |
| Date de création | 2026-09-05 |
| Date de mise à jour | 2026-09-05 |
| Rapport lié | [2026-09-05](../journal/2026-09-05.md) |

---

## Phase 1 — Conception
Terminée (ADR-004, ADR-017, ADR-018). Format d'échange v1 défini dans `14-charte-versionnage-contenu.md`.

## Phase 2 — Implémentation
- [x] (Backend) `SyncLogDao` et `SyncLogEntity` (B-22 corrigé)
- [x] (Backend) `SyncRepository` et `ExportDataUseCase` (Export JSON)
- [x] (Frontend) Déclenchement du partage depuis l'écran Profil
- [x] (Backend) Mécanisme d'import (ADR-018, Sprint 4)
- [x] (Frontend) Sélecteur de fichier et UI d'import

## Phase 3 — Test
- [x] Build backend OK
- [x] Build frontend OK
- [ ] Test export fichier sur device réel
- [x] Test unitaire fusion timestamp (ADR-018)
