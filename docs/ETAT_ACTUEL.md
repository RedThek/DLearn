# État actuel du projet — Liteschreib IKII

**Dernière mise à jour :** 2026-09-05 (Session Sprint 3 terminée)

---

## 1. Où en est le projet, en une phrase

Le Sprint 3 est terminé ✅ : le dashboard enseignant est fonctionnel (assignation, corrections), les productions élèves sont réellement persistées lors de la soumission, le suivi affiche des données réelles, et l'export JSON pour la synchronisation est opérationnel.

---

## 2. Missions actives

| Priorité | Fiche | Statut réel (code) | Bloquée par |
|---|---|---|---|
| 1 | `missions/C2-dashboard-enseignant-implementation.md` | ✅ Terminé (Backend + Frontend) | — |
| 2 | `missions/C3-synchronisation-locale.md` | 🔄 Implémentation (Export ✅, Import ❌) | — |
| 3 | `missions/A0-cartographie-contenu-pedagogique.md` | 🔄 En cours — relecture humaine toujours requise | Disponibilité relecteur natif |
| 4 | `missions/A4-entites-room-prepopulation.md` | ✅ Validée | — |

---

## 3. Audit de reconciliation (B-21 à B-27) — Statut

| ID | Sévérité | Description | Statut |
|---|---|---|---|
| B-21 | 🔴 CRITIQUE | `soumettre()` est un no-op | ✅ Corrigé |
| B-22 | 🟠 ÉLEVÉ | `SyncLogDao` manquant | ✅ Corrigé |
| B-23 | 🟠 ÉLEVÉ | Bouton "Assigner" ne fait rien | ✅ Corrigé |
| B-24 | 🟠 ÉLEVÉ | Onglet "Corrections" statique | ✅ Corrigé |
| B-25 | 🟡 MOYEN | `SuiviScreen` : données codées en dur | ✅ Données réelles connectées |
| B-26 | 🟡 MOYEN | Divergence schéma/docs | ✅ ADR-016 formalisé |
| B-27 | 🟡 MOYEN | Politique migration Room | ✅ ADR-017 formalisé |

---

## 5. Ce qui est déjà fonctionnel (ne pas refaire)

- **Assignation de contenus** (par classe ou par élève) depuis le dashboard enseignant ✅
- **Visualisation des productions soumises** par l'enseignant ✅
- **Persistance réelle des soumissions** dans la base Room (statut SOUMIS) ✅
- **Calcul du streak réel** et progression réelle dans l'onglet Suivi ✅
- **Export JSON et partage Android** (Nearby Share/Bluetooth) depuis le profil ✅
- **Migration Room 4→5** (explicite et testée) ✅

---

## 6. Sprint et cycle en cours

- **Sprint courant :** Sprint 3 terminé ✅
- **Prochain Sprint :** Sprint 4 — Mission B1 (Accueil) + suite Mission C3 (Import enseignant).
