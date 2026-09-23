# Mission F7 — Club de classe et concours de classe

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F7 |
| Titre | Club de lecture de classe, concours de classe/établissement |
| Type | Mission planifiée |
| Sprint | À planifier (Cycle 2, horizon H2) |
| FR/NFR concernés | FR-44, FR-45 |
| ADR concerné(s) | ADR-020, ADR-025 |
| **Statut global** | `Conception` — non démarrée |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [ ] Mission F3 terminée (le concours réutilise le mécanisme d'export/dépôt de production)
- [ ] Contenu du club (questions guidées, quiz) disponible via Mission A0

### Notes de conception
Club et concours restent à l'échelle de la classe ou de l'établissement, sans forum ni serveur : diffusion par fichier (défis mensuels, quiz, annonces via FR-08), dépôt de concours par export, jury tenu par l'enseignant, résultats diffusés par fichier.

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Diffusion de questions/défis de club par pack de contenu ou annonce (`CLASS_PACKET`)
- [ ] Ouverture/fermeture d'un concours par l'enseignant, dépôt via export existant
- [ ] Diffusion des résultats par fichier

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

| Type de test | Portée | Résultat |
|---|---|---|
| Test manuel | Cycle complet (ouverture, dépôt, résultats) sans connexion | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] Fonctionne sans connexion ; annonces via FR-08

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation
- [ ] `01-exigences-fonctionnelles.md` (FR-44, FR-45 → cochées)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Statut final | Non démarrée — dépend de F3 |
