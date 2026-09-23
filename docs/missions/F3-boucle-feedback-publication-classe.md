# Mission F3 — Boucle de feedback et publication de classe

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F3 |
| Titre | Commentaires enseignant, historique de versions, publication de classe |
| Type | Mission planifiée |
| Sprint | À planifier (fin Cycle 1, horizon H1) |
| FR/NFR concernés | FR-38, FR-39, FR-40 |
| ADR concerné(s) | ADR-023, ADR-027 |
| **Statut global** | `Conception` — non démarrée |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [ ] **Mission F1b terminée** (le commentaire est un enregistrement `commentaire` d'un bundle `FEEDBACK`, ADR-027)
- [ ] Mission C3 close pour le format v2
- [x] Cycle d'écriture de classe défini (`18-vision-produit-et-horizons.md` §8)

### Notes de conception
Le statut d'une production reste la propriété de l'élève ; « commenté » se déduit de l'existence d'un `commentaire` dont `productionRev` est à jour, sans écriture de statut par l'enseignant dans la ligne de l'élève (ADR-027). La publication de classe est un enregistrement distinct, propriété de l'enseignant, généré en PDF localement (aucune donnée transmise hors de l'appareil), avec consentement explicite de l'élève avant toute inclusion.

### Sortie de phase
- [ ] Statuts `COMMENTE`/`REVISE`/`PUBLIE_CLASSE` du diagramme de `18-…` §8 traduits en règles précises (déduites, pas stockées)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Écran de consultation des commentaires côté élève
- [ ] Historique de versions d'une production (table `productionVersion`, réservée dans `20-…` §4.3)
- [ ] Sélection des productions à publier, écran de consentement de l'élève
- [ ] Génération PDF locale du recueil de classe (`android.graphics.pdf.PdfDocument`)

### Points de vigilance obligatoires
- [ ] Aucune donnée ne quitte l'appareil lors de la génération du PDF
- [ ] Le consentement de l'élève est explicite et révocable avant la génération finale

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

| Type de test | Portée | Résultat |
|---|---|---|
| Test manuel à deux appareils | Commentaire émis par l'enseignant, reçu et affiché côté élève | ☐ Passant ☐ Échec |
| Test manuel | Génération d'un PDF de classe avec consentement partiel (certains élèves exclus) | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] Commentaires enseignant renvoyés à l'élève
- [ ] Historique de versions et recueil PDF de classe (consentement de l'élève)
- [ ] Test à deux appareils physiques

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `01-exigences-fonctionnelles.md` (FR-38 à FR-40 → cochées)
- [ ] `10-protocole-ethique-consentement.md` (consentement de publication)
- [ ] `04-missions-et-sprints.md`

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Statut final | Non démarrée — dépend de F1b |
