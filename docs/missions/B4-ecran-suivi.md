# Mission B4 — Écran Suivi

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | B4 |
| Titre | Écran Suivi |
| Type | Mission planifiée |
| Sprint | Sprint 7 |
| FR/NFR concernés | FR-20 à FR-23 |
| ADR concerné(s) | ADR-019 (Proposed) |
| **Statut global** | `Implémentation` — en cours |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | `../journal/2026-09-05.md` |

> Fiche créée rétroactivement. Le correctif B-28 a rendu les données réelles ; le reste dépend largement d'ADR-019, encore au statut `Proposed`.

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] Correctif B-28 appliqué (`progression` réellement écrite)
- [ ] **ADR-019 tranché** (suivi de la durée de session) — condition du volet « Temps » de FR-23

### Notes de conception — écarts constatés
- Le calcul par niveau (`competencesParNiveau`) dans `ProgressionRepositoryImpl.getProgressionStats` reste une **heuristique approximative** (`"A1" to if (unitesTerminees >= 2) 0.8f else unitesTerminees * 0.4f`), pas une vraie mesure par compétence — à noter comme limite si le mémoire s'appuie sur cette donnée.
- Le filtre temporel (« 7 jours / 30 jours / Tout ») de `SuiviScreen` a un `TODO` explicite (`onClick = { /* TODO: Filtrer dans le ViewModel */ }`) : les boutons existent visuellement mais ne filtrent rien.
- Aucune UI de révision espacée (FSRS) n'existe, bien que `VocabEntity` (`prochainRappel`, `facteurDifficulte`) et `ApprentissageDao.getFlashcardsDues` soient déjà en place côté données.

### Sortie de phase
- [ ] Décider si B4 attend ADR-019 ou livre d'abord sans le volet « Temps »

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### État réel constaté dans le code

| Exigence | État | Constat |
|---|---|---|
| FR-20 (progression par niveau GeR) | ✅ Fait (avec réserve) | Données réelles depuis B-28 ; calcul par niveau reste heuristique |
| FR-21 (historique des scores) | ✅ Fait | `tauxReussite` réel, `streakJours` réel (`calculerStreak`) |
| FR-22 (planification FSRS) | ❌ À faire | Modèle de données prêt (`VocabEntity`), aucune UI ni intégration dans Suivi |
| FR-23 (temps, unités, régularité) | 🟡 Partiel | Unités et régularité (streak) réels ; « Temps » affiché `"—"` (placeholder honnête, `AN-F3-01`), en attente d'ADR-019 |

### Découpage en sous-tâches restantes
- [ ] Câbler le filtre temporel (7j/30j/Tout) dans `SuiviViewModel`
- [ ] Concevoir et livrer l'algorithme de planification des révisions (FSRS simplifié), avec une UI dans Suivi ou Apprentissage
- [ ] Si ADR-019 est accepté avant cette mission : afficher un vrai temps d'étude ; sinon, documenter explicitement l'absence comme limite assumée

### Points de vigilance obligatoires
- [ ] Ne jamais réintroduire de valeur fictive pour « Temps » tant qu'ADR-019 n'est pas implémenté (cohérent avec le choix déjà fait en Sprint 3)

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 3 — Test

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire | `calculerStreak` (cas limites : aucune activité, activité hier, activité aujourd'hui) | ☐ À faire |
| Unitaire | Algorithme de planification des révisions | ☐ À faire (dépend de la conception FSRS) |
| Test manuel | Cohérence des données entre Accueil, Apprentissage et Suivi | ☐ À faire |

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise du backlog, `../04-missions-et-sprints.md`)
- [x] FR-20 à FR-21 implémentés avec données réelles (B-28)
- [ ] FR-22 implémenté (FSRS)
- [ ] Cohérence des données vérifiée avec le module Accueil et Apprentissage
- [ ] Algorithme de planification des révisions testé unitairement

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [x] Cette fiche (création rétroactive)
- [ ] `06-architecture-technique.md` (statut d'ADR-019 à mettre à jour si tranché)

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non clôturée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | En cours — le socle de données réelles est solide ; FSRS et le filtre temporel restent à construire, le temps d'étude dépend d'ADR-019 |
