# Mission F5 — Dictionnaire hors ligne

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F5 |
| Titre | Dictionnaire hors ligne allemand ↔ français (puis anglais, espagnol) |
| Type | Mission planifiée |
| Sprint | À planifier (Cycle 2, horizon H2) |
| FR/NFR concernés | FR-42 |
| ADR concerné(s) | ADR-025, ADR-028 |
| **Statut global** | `Conception` — non démarrée |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [ ] **Mission F1c terminée** (mécanisme de pack `reference` disponible)
- [ ] Audit de licence des sources candidates effectué (`19-registre-licences-contenus-tiers.md`, LIC-004, LIC-007, LIC-008)
- [ ] Encadrant consulté sur les sources sous licence à partage à l'identique

### Notes de conception
Pack `dict-de-fr` en priorité, puis `dict-de-en`, `dict-de-es` ; l'arabe en dernier, selon les retours du pilote (police à tester d'abord via la police système). Base SQLite séparée, en lecture seule (`reference.db`), clés de recherche normalisées (casse, ä/ae, ö/oe, ü/ue, ß/ss). Le schéma exact de `reference.db` et le choix technique (Room FTS3/FTS4 ou SQLite brut) sont à fixer à l'ouverture de cette mission.

### Sortie de phase
- [ ] Source de données choisie et son statut de licence fixé dans le registre (`Approuvé` ou `Refusé`)
- [ ] Schéma de `reference.db` arrêté

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Script de conversion de la source choisie vers `reference.db`
- [ ] Ouverture et recherche en lecture seule, séparée de la base principale
- [ ] Écran de recherche dans le dictionnaire, accessible depuis l'atelier d'écriture
- [ ] Notice de licence et attribution dans l'écran « Crédits »

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire | Normalisation des clés de recherche (ä/ae, ß/ss…) | ☐ Passant ☐ Échec |
| Test manuel offline | Recherche fonctionnelle sans réseau | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] Pack optionnel ; recherche hors ligne ; licence conforme à ADR-025, référence `LIC-xxx` renseignée

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation
- [ ] `19-registre-licences-contenus-tiers.md` (statut `Intégré`)
- [ ] `01-exigences-fonctionnelles.md` (FR-42 → coché)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Statut final | Non démarrée — dépend de F1c et de l'audit de licence |
