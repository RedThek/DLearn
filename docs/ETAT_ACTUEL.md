# État actuel du projet — Liteschreib IKII

**Dernière mise à jour :** 2026-09-05 (Session Sprint 4 terminée)

---

## 1. Où en est le projet, en une phrase

Le Sprint 4 est terminé ✅ : la progression est désormais réelle et persistée, le module Écriture est lié au niveau de l'élève, les assignations enseignant sont visibles sur l'accueil élève, et l'import de données BYOD est opérationnel avec interface de sélection.

---

## 2. Missions actives

| Priorité | Fiche | Statut réel (code) | Bloquée par |
|---|---|---|---|
| 1 | `missions/B2-ecran-apprentissage.md` | 🔄 À faire | — |
| 2 | `missions/C3-synchronisation-locale.md` | ✅ Terminé | — |
| 3 | `missions/A0-cartographie-contenu-pedagogique.md` | 🔄 En cours — relecture humaine toujours requise | Disponibilité relecteur natif |

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
| B-28 | 🟠 ÉLEVÉ | `unitesTerminees` à zéro (Bug silencieux) | ✅ Corrigé (Wiring ViewModel OK) |
| B-29 | 🟡 MOYEN | `EcritureViewModel` unité fixe (Bug silencieux) | ✅ Corrigé (Route + SavedStateHandle OK) |

---

## 5. Ce qui est déjà fonctionnel (ne pas refaire)

- **Assignation de contenus** (par classe ou par élève) depuis le dashboard enseignant ✅
- **Visualisation des productions soumises** par l'enseignant ✅
- **Persistance réelle des soumissions** dans la base Room (statut SOUMIS) ✅
- **Calcul du streak réel** et progression réelle dans l'onglet Suivi ✅
- **Import de données BYOD** (ADR-018) avec sélecteur de fichier et fusion timestamp ✅
- **Assignations enseignant** visibles sur l'accueil élève (AN-B3-01) ✅
- **Navigation contextuelle** vers le module Écriture ✅
- **Export JSON et partage Android** (Nearby Share/Bluetooth) depuis le profil ✅
- **Migration Room 4→5** (explicite et testée) ✅

---

## 6. Sprint et cycle en cours

- **Sprint courant :** Sprint 4 terminé ✅
- **Prochain Sprint :** Sprint 5 — Mission B2 (Apprentissage) + Mission B1 itération 2.
