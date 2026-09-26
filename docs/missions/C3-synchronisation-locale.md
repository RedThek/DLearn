# Mission C3 — Synchronisation locale (BYOD)

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | C3 |
| Titre | Synchronisation locale (BYOD) |
| Type | Mission planifiée |
| Sprint | Sprint 3 (groundwork), complétée Sprint 4 pour le format v1 ; **redirection vers le format v2 par la Mission F1b** |
| FR/NFR concernés | FR-29 à FR-31, NFR-01 |
| ADR concerné(s) | ADR-004, ADR-018 (v1), **ADR-027 (v2, remplace ADR-018)** |
| **Statut global** | `Validation — incomplète` |
| Date de création | 2026-09-05 |
| Date de mise à jour | 2026-09-22 |
| Rapport lié | [2026-09-05](../journal/2026-09-05.md), [2026-09-21_2](../journal/2026-09-21_2.md) |

> **Correction apportée le 2026-09-22.** Cette fiche affichait `Validation` avec toutes les cases de la Phase 3 cochées sauf une (« Test export fichier sur device réel »). En réalité, **le test bout en bout entre deux appareils physiques n'a jamais été exécuté**, ni en Sprint 3 ni en Sprint 4 (`bloc-C-taches.md`, tâche C3-T11, reportée puis jamais reprise). C'est ce test précis, resté ouvert depuis le Sprint 3, que reprend `TEST-PROTOCOLE-F1-DEUX-APPAREILS.md` (Mission F1), à la fois pour le clore et pour confirmer les risques R-26/R-27.

---

## Phase 1 — Conception
Terminée pour le format v1 (ADR-004, ADR-017, ADR-018). **Remplacée pour le format v2** par ADR-026 et ADR-027 (`06-architecture-technique.md`), spécifiés dans `20-specification-formats-echange-et-packs.md`. Le format v1 est amené à être refusé à l'import une fois le format v2 en service (rupture assumée et documentée, `14-charte-versionnage-contenu.md`).

## Phase 2 — Implémentation
- [x] (Backend) `SyncLogDao` et `SyncLogEntity` (B-22 corrigé)
- [x] (Backend) `SyncRepository` et `ExportDataUseCase` (export JSON, format v1)
- [x] (Frontend) Déclenchement du partage depuis l'écran Profil
- [x] (Backend) Mécanisme d'import (ADR-018, format v1)
- [x] (Frontend) Sélecteur de fichier et UI d'import
- [ ] **(Bloc F, Mission F1b)** Bundles v2 par propriétaire, `rev`, import atomique, hash étiqueté — remplace le mécanisme v1 ci-dessus

## Phase 3 — Test
- [x] Build backend OK
- [x] Build frontend OK
- [x] Test unitaire fusion timestamp (ADR-018, format v1 — `ImportFusionTest.kt`)
- [ ] **Test bout en bout entre deux appareils physiques (format v1)** — protocole prêt : `../planification/TEST-PROTOCOLE-F1-DEUX-APPAREILS.md`, jamais exécuté à ce jour
- [ ] Test bout en bout entre deux appareils physiques (format v2) — dépend de la Mission F1b

## Phase 4 — Validation

### Definition of Done (reprise et actualisée du backlog, `../04-missions-et-sprints.md`)
- [x] Groundwork export JSON + `SyncLogDao`
- [x] FR-29 à FR-31 implémentés partiellement via export JSON + partage Android (format v1)
- [ ] **Test bout en bout entre deux appareils physiques (Android 9.0+) sans réseau internet, dans les deux sens (format v2, ADR-027), sur chacun des canaux de repli**
- [x] Gestion des conflits de synchronisation documentée — ADR-018 pour v1, **remplacé par ADR-027 pour v2**
- [x] Format de fichier d'échange versionné conforme à `14-charte-versionnage-contenu.md`

> Cette mission ne peut être définitivement close qu'après le test à deux appareils en format v2 (Mission F1b). Le test en format v1 (§ ci-dessus) reste utile immédiatement : il confirme ou infirme R-26/R-27 avant même que F1a/F1b ne soient codées.

## Phase 5 — Documentation
- [x] `04-missions-et-sprints.md`, `01-exigences-fonctionnelles.md` référencent cette mission
- [ ] `14-charte-versionnage-contenu.md` à compléter lors de la clôture v2 (Mission F1b)

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non clôturée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | En cours — le format v1 est implémenté mais jamais validé de bout en bout entre deux appareils réels ; la clôture définitive attend le format v2 (Mission F1b) |
