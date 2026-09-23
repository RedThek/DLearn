# Mission F2 — Atelier d'écriture guidé

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F2 |
| Titre | Atelier d'écriture guidé (fiches-méthode, structuration, défis courts) |
| Type | Mission planifiée |
| Sprint | À planifier (cohérent avec Mission B3, horizon H1) |
| FR/NFR concernés | FR-35, FR-36, FR-37 |
| ADR concerné(s) | ADR-020, ADR-022 |
| **Statut global** | `Conception` — non démarrée |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] Mission F0 admise (capacité C2 classée H1, `18-vision-produit-et-horizons.md`)
- [ ] Gabarit `16-gabarit-auteur-exercice.md` étendu pour couvrir les fiches-méthode et les défis d'écriture
- [ ] Mission B3 (Écran Écriture) suffisamment avancée pour accueillir ces contenus dans le même écran

### Notes de conception
Trois éléments : fiches-méthode par type de production (dialogue, description, lettre, e-mail, publicité, discours, argumentation), un outil de structuration « idée – argument – exemple » réutilisable dans l'éditeur d'écriture, des défis courts avec longueur indicative non bloquante. Convention de code : package-by-feature (ADR-022), nouveau package `presentation/atelier` ou extension de `presentation/ecriture` selon la charge.

### Sortie de phase
- [ ] Choix arrêté : extension de `presentation/ecriture` ou nouveau package dédié

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Extension du gabarit de contenu pour les fiches-méthode et les défis
- [ ] Modèle de domaine et DAO pour les fiches-méthode (upsert via pack, ADR-028)
- [ ] UI de consultation des fiches-méthode, calibrées par niveau GeR
- [ ] Outil de structuration idée–argument–exemple, réutilisable dans l'éditeur d'écriture
- [ ] Défis d'écriture (thème, longueur indicative, minuteur optionnel)

### Points de vigilance obligatoires
- [ ] Calibrage A1–A2 respecté (`16-…` section 5) ; pas de contenu hors niveau
- [ ] Contenu relu par un locuteur natif avant tout statut `Validé` (Mission A0)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | Cas d'usage des fiches-méthode et des défis | ☐ Passant ☐ Échec |
| Instrumentation (UI Compose) | Parcours fiche-méthode → structuration → écriture | ☐ Passant ☐ Échec |
| Test manuel offline | Fonctionnement intégral hors ligne | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] Fiches-méthode, structuration idée–argument–exemple et défis courts implémentés
- [ ] Contenu relu par un locuteur natif ou l'encadrant (Mission A0)
- [ ] Tests UI et vérification hors ligne

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `16-gabarit-auteur-exercice.md`, `09-cartographie-contenu-pedagogique.md` (section 3.5)
- [ ] `01-exigences-fonctionnelles.md` (FR-35 à FR-37 → cochées)
- [ ] `04-missions-et-sprints.md`

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Statut final | Non démarrée |
