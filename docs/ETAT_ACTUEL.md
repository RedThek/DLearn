# État actuel du projet — Liteschreib IKII

**Dernière mise à jour :** 2026-09-05 (Backend Agent — Sprint 3 Backend terminés)

---

## 1. Où en est le projet, en une phrase

Le backend du Sprint 3 est prêt ✅ : la migration 4→5 est en place, l'assignation et la soumission sont persistées, et le groundwork de l'export JSON pour la synchronisation est terminé. Le Frontend Agent peut maintenant câbler les UIs correspondantes.

---

## 2. Missions actives

| Priorité | Fiche | Statut réel (code) | Bloquée par |
|---|---|---|---|
| 1 | `missions/C2-dashboard-enseignant-implementation.md` | 🔄 Implémentation (Backend ✅, Frontend ❌) | — |
| 2 | `missions/C3-synchronisation-locale.md` | 🔄 Implémentation (Export Backend ✅, Import/Share ❌) | — |
| 3 | `missions/A0-cartographie-contenu-pedagogique.md` | 🔄 En cours — relecture humaine toujours requise | Disponibilité relecteur natif |
| 4 | `missions/A4-entites-room-prepopulation.md` | ✅ Validée (Migration 4→5 en place) | — |

---

## 3. Audit de reconciliation (B-21 à B-27) — Statut

| ID | Sévérité | Description | Statut |
|---|---|---|---|
| B-21 | 🔴 CRITIQUE | `soumettre()` est un no-op | ✅ Corrigé (Backend) |
| B-22 | 🟠 ÉLEVÉ | `SyncLogDao` manquant | ✅ Corrigé |
| B-23 | 🟠 ÉLEVÉ | Bouton "Assigner" ne fait rien | [ ] Frontend à câbler |
| B-24 | 🟠 ÉLEVÉ | Onglet "Corrections" statique | [ ] Frontend à câbler |
| B-25 | 🟡 MOYEN | `SuiviScreen` : données codées en dur | ✅ Streak réel (Backend) |
| B-26 | 🟡 MOYEN | Divergence schéma/docs | ✅ ADR-016 intégré |
| B-27 | 🟡 MOYEN | Politique migration Room | ✅ ADR-017 intégré |

---

## 5. Ce qui est déjà fonctionnel (ne pas refaire)

- **Migration Room 4→5 testée** (Assignation, statut Soumis, SyncLog) ✅
- **Persistance réelle des soumissions** (B-21 corrigé) ✅
- **Repository d'assignation** et use cases associés ✅
- **Calcul du streak** basé sur l'activité réelle ✅
- **Groundwork export JSON** (SyncRepository) ✅
- ... (reste inchangé)

---

## 6. Sprint et cycle en cours

- **Sprint courant :** Sprint 3 — Intégration Dashboard Enseignant (Frontend) + Partage Mission C3.
