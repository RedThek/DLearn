# Mission F4 — Gamification locale

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F4 |
| Titre | Points, niveaux, badges dérivés ; certificats PDF |
| Type | Mission planifiée |
| Sprint | À planifier (Cycle 2, horizon H2) |
| FR/NFR concernés | FR-41 |
| ADR concerné(s) | ADR-023 |
| **Statut global** | `Conception` — non démarrée |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] ADR-023 accepté (gamification dérivée, pas de compteur persisté, pas de classement public)
- [ ] ADR-019 (suivi de session) tranché, si le temps d'étude doit entrer dans le calcul des badges

### Notes de conception
Points, niveaux et badges sont des **vues dérivées** de `progression`, `reponse_eleve`, `production_ecrite` et, le cas échéant, `session_etude` — aucun compteur mutable n'est ajouté. Un classement de classe optionnel, désactivé par défaut, n'est visible que si l'enseignant l'active explicitement.

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Fonction de calcul des badges à partir des données existantes (pas de nouvelle table d'état)
- [ ] Écran de badges dans le Profil
- [ ] Classement de classe optionnel, commande enseignant, désactivé par défaut
- [ ] Génération de certificat de participation en PDF local

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire | Calcul des badges à partir de jeux de données connus | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] Aucun compteur persisté ; badges recalculés à la demande
- [ ] Classement de classe optionnel et désactivé par défaut

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation
- [ ] `01-exigences-fonctionnelles.md` (FR-41 → coché)
- [ ] `10-protocole-ethique-consentement.md` (mention : la gamification est un facteur d'engagement, pas une mesure de compétence)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Statut final | Non démarrée |
