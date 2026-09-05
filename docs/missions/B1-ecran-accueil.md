# Mission B1 — Écran Accueil

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | B1 |
| Titre | Écran Accueil |
| Type | Mission planifiée |
| Sprint | Sprint 4 |
| FR/NFR concernés | FR-05, FR-06 |
| ADR concerné(s) | ADR-009, ADR-014 |
| **Statut global** | `Implémentation` |
| Date de création de ce fichier | 2026-09-05 |
| Date de dernière mise à jour | 2026-09-05 |
| Dernier rapport journalier lié | [2026-09-05](../journal/2026-09-05.md) |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] La mission est décrite dans le backlog (`../04-missions-et-sprints.md`)
- [x] Les exigences concernées (FR-05, FR-06) sont identifiées
- [x] Description UX structurée rédigée (ADR-014)

### Notes de conception
L'écran d'accueil doit afficher les statistiques de progression, les activités en cours et les assignations de l'enseignant.
Correctif AN-B3-01 : Ajout d'une section pour les unités assignées par l'enseignant.

### Sortie de phase
- [x] Approche technique arrêtée
- [x] Aucune question bloquante

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [x] Extension de `AccueilUiState` pour inclure les assignations
- [x] Câblage de `AccueilViewModel` avec `GetAssignationsPourEleveUseCase`
- [x] Affichage de la section « Assigné par ton enseignant » dans `AccueilScreen`
- [ ] Remplacement des données mockées (progression, streak) par les vraies données Room (GetProgressionStatsUseCase)

### Points de vigilance obligatoires
- [x] Respect de la séparation Clean Architecture
- [x] Injection via Hilt
- [x] Fidélité au design system

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Instrumentation (UI Compose) | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Test manuel offline (mode avion) | Fonctionnement des assignations | ☒ Passant ☐ Échec |

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] FR-05, FR-06 implémentés et testés manuellement
- [ ] ViewModel couvert par tests unitaires
- [ ] Revue de fidélité Figma effectuée (ou screenshots validés)

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [x] `04-missions-et-sprints.md`
- [x] `docs/missions/B1-ecran-accueil.md` (ce fichier)
- [x] `docs/journal/2026-09-05.md`

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée
