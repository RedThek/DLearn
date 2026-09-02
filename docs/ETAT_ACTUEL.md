# État actuel du projet — Liteschreib IKII

> ⭐ **Ce fichier est le point d'entrée unique pour reprendre le travail**, que ce soit après une interruption brève ou une longue pause (académique, personnelle, imprévue). Il doit être mis à jour à la fin de chaque session ayant fait évoluer une mission. Voir la procédure complète de reprise dans `processus/guide-orchestration.md`, section 5.

**Dernière mise à jour :** 2026-09-02

---

## 1. Où en est le projet, en une phrase

Build propre. A2 ✅, A1 ✅ (ADR-014), A3 ✅. Sprint 1 terminé. Sprint 2 en attente de A0 (contenu pédagogique validé).

## 2. Missions actives

- **Front contenu** : [`missions/A0-cartographie-contenu-pedagogique.md`](missions/A0-cartographie-contenu-pedagogique.md) — En cours
- **Sprint 2 préparation** : [`missions/A4-entites-room-prepopulation.md`](missions/A4-entites-room-prepopulation.md) — Bloquée par A0-T23

## 2bis. Missions préparées (fiches créées, non démarrées)

Ces fiches sont pré-instanciées pour anticiper les prochains sprints, mais aucune n'a encore démarré — leurs dépendances doivent d'abord être clôturées, dans cet ordre :

| Ordre | Fiche | Sprint | Dépend de | Point de vigilance |
|---|---|---|---|---|
| 3 | [`missions/A4-entites-room-prepopulation.md`](missions/A4-entites-room-prepopulation.md) | Sprint 2 | A2, A0 | ⚠️ Bloquée en pratique tant que `09-cartographie-contenu-pedagogique.md` n'est pas rempli avec du contenu réel validé (risque R-07) |
| 4 | [`missions/A5-modules-hilt.md`](missions/A5-modules-hilt.md) | Sprint 3 | A2, A4 | — |

## 3. Dernier rapport de session

- `EXEC-SPRINT1-AGENT.md` exécuté le 2026-09-02.

## 4. Sprint et cycle en cours

- **Cycle DBR** : Cycle 1 (Analyse/Conception terminées, Développement en cours)
- **Sprint** : Sprint 1 terminé.
- **Prochain sprint** : Sprint 2 — Entités Room & Pré-population (A4) + Modules Hilt (A5)

## 5. Décisions et risques à garder en tête

- Toutes les décisions d'architecture sont tranchées — voir le registre complet dans `06-architecture-technique.md` (ADR-001 à ADR-014)
- **ADR-014** : Abandon du workflow Figma au profit d'une génération UI par agent de codage.
- **Risque prioritaire à surveiller** : R-07 (contenu MVP, ADR-008) — portée resserrée au collège complet (6e, 5e, 4e, 3e, bande A1→A2) ; seuil de 5 unités validées par niveau (20 au total), **aucune unité encore validée**.
- **Risque prioritaire suivant** : R-11 (délai d'approbation éthique/consentement).

## 6. Checklist de reprise rapide

1. Lire ce fichier en entier
2. Consulter EXEC-SPRINT1-AGENT.md pour l'historique récent.
3. Vérifier l'état réel du dépôt (`git log`, `git status`, build)
4. Appliquer la checklist de début de journée (`05-checklist-quotidienne.md`)
