# Mission F6 — Aides à l'écriture à règles

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F6 |
| Titre | Correcteur, synonymes, règles grammaticales élémentaires (niveau N0) |
| Type | Mission planifiée |
| Sprint | À planifier (Cycle 2, horizon H2) |
| FR/NFR concernés | FR-43 |
| ADR concerné(s) | ADR-003, ADR-024, ADR-025 |
| **Statut global** | `Conception` — non démarrée |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [ ] Mission E1 (interfaces de domaine anticipées, `PronunciationEvaluator` et pairs) suffisamment avancée pour y adosser `SpellChecker`, `SynonymProvider`, `GrammarRuleChecker`
- [ ] Audit de licence des moteurs candidats (Hunspell, OpenThesaurus — `19-…`, LIC-007, LIC-008)

### Notes de conception
Niveau **N0** d'ADR-024 uniquement (règles et dictionnaires hors ligne). Les ports de domaine sont distincts de l'assistant génératif (`WritingAssistant`, réservé au Cycle 2 tardif, niveaux N1+). Les moteurs restent dans `data/`, jamais dans `domain` (NFR-16).

### Sortie de phase
- [ ] Moteur retenu et son statut de licence fixé

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Interfaces `SpellChecker`, `SynonymProvider`, `GrammarRuleChecker` dans `domain`
- [ ] Implémentation à base de règles/dictionnaires dans `data`
- [ ] Intégration dans l'éditeur d'écriture (soulignement, suggestions)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | Chaque port testé avec une implémentation factice | ☐ Passant ☐ Échec |
| Test manuel offline | Suggestions disponibles sans réseau | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] Niveau N0 d'ADR-024 respecté ; aucune donnée transmise hors de l'appareil

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation
- [ ] `19-registre-licences-contenus-tiers.md`
- [ ] `01-exigences-fonctionnelles.md` (FR-43 → coché)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Statut final | Non démarrée — dépend de la Mission E1 |
