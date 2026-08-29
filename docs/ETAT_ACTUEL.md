# État actuel du projet — Liteschreib IKII

> ⭐ **Ce fichier est le point d'entrée unique pour reprendre le travail**, que ce soit après une interruption brève ou une longue pause (académique, personnelle, imprévue). Il doit être mis à jour à la fin de chaque session ayant fait évoluer une mission. Voir la procédure complète de reprise dans `processus/guide-orchestration.md`, section 5.

**Dernière mise à jour :** 2026-08-29

---

## 1. Où en est le projet, en une phrase

Le socle documentaire est complet et le build a été rétabli (correctif d'imports dans la couche présentation). Le développement Android en est toujours au **Sprint 0** : finalisation du design system et scaffolding Clean Architecture.

## 2. Missions actives

- **Front technique** : [`missions/A1-finaliser-design-system.md`](missions/A1-finaliser-design-system.md) — Phase Implémentation — build rétabli, TODO tokens Figma à traiter.
- **Front contenu (parallèle, non bloquant sur A1)** : [`missions/A0-cartographie-contenu-pedagogique.md`](missions/A0-cartographie-contenu-pedagogique.md) — Phase Implémentation — portée MVP confirmée (collège complet 6e-3e).

## 2bis. Missions préparées (fiches créées, non démarrées)

Ces fiches sont pré-instanciées pour anticiper les prochains sprints, mais aucune n'a encore démarré — leurs dépendances doivent d'abord être clôturées, dans cet ordre :

| Ordre | Fiche | Sprint | Dépend de | Point de vigilance |
|---|---|---|---|---|
| 1 | [`missions/A2-structure-packages-clean-architecture.md`](missions/A2-structure-packages-clean-architecture.md) | Sprint 0 | Aucune (parallèle/suite de A1) | — |
| 2 | [`missions/A3-navigation-compose.md`](missions/A3-navigation-compose.md) | Sprint 1 | A2 | — |
| 3 | [`missions/A4-entites-room-prepopulation.md`](missions/A4-entites-room-prepopulation.md) | Sprint 2 | A2 | ⚠️ Bloquée en pratique tant que `09-cartographie-contenu-pedagogique.md` n'est pas rempli avec du contenu réel validé (risque R-07) |
| 4 | [`missions/A5-modules-hilt.md`](missions/A5-modules-hilt.md) | Sprint 3 | A2, A4 | — |

## 3. Dernier rapport de session

- [`journal/2026-08-29.md`](journal/2026-08-29.md) — Rétablissement du build (correctif d'imports Compose).

## 4. Sprint et cycle en cours

- **Cycle DBR** : Cycle 1 (Analyse/Conception terminées, Développement en cours)
- **Sprint** : Sprint 0 — finalisation du design system et du scaffolding Clean Architecture (voir `03-roadmap-developpement.md`)
- **Prochain sprint** : Sprint 1 — Navigation Compose avec sélecteur de profil (ADR-009)

## 5. Décisions et risques à garder en tête

- Toutes les décisions d'architecture sont tranchées — voir le registre complet dans `06-architecture-technique.md` (ADR-001 à ADR-013)
- **Risque prioritaire à surveiller** : R-07 (contenu MVP, ADR-008) — portée resserrée au collège complet (6e, 5e, 4e, 3e, bande A1→A2) ; seuil de 5 unités validées par niveau (20 au total), **aucune unité encore validée** (4 brouillons rédigés, 1 par niveau avec personnages diversifiés, en attente de relecture humaine, voir Mission A0)
- **Nouveau risque** : R-17 (dépôt GitHub public, ADR-013) — vigilance permanente pour ne jamais committer de données réelles sensibles
- **Risque prioritaire suivant** : R-11 (délai d'approbation éthique/consentement) — la démarche décrite dans `10-protocole-ethique-consentement.md` doit être engagée dès que possible, en parallèle du développement

## 6. Checklist de reprise rapide

1. Lire ce fichier en entier
2. Ouvrir le dernier rapport dans `journal/` (lien section 3)
3. Ouvrir la fiche de mission active dans `missions/` (lien section 2)
4. Vérifier l'état réel du dépôt (`git log`, `git status`, build)
5. Appliquer la checklist de début de journée (`05-checklist-quotidienne.md`)
6. Reprendre à la phase indiquée « En cours » dans la fiche de mission

## 7. Documents de référence à consulter selon le besoin

| Besoin | Document |
|---|---|
| Comprendre une exigence | `01-exigences-fonctionnelles.md`, `02-exigences-non-fonctionnelles.md` |
| Comprendre une décision d'architecture | `06-architecture-technique.md` (ADR) |
| Démarrer une nouvelle mission | `processus/gabarit-cycle-iteration.md` |
| Contribuer du contenu pédagogique | `16-gabarit-auteur-exercice.md`, `09-cartographie-contenu-pedagogique.md` |
| Préparer le déploiement pilote | `15-guide-enseignant-onboarding.md`, `10-protocole-ethique-consentement.md` |
