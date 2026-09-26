# Mission B3 — Écran Écriture

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | B3 |
| Titre | Écran Écriture |
| Type | Mission planifiée |
| Sprint | Sprint 6 |
| FR/NFR concernés | FR-15 à FR-18, FR-34 |
| ADR concerné(s) | ADR-011 |
| **Statut global** | `Implémentation` — en cours |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | `../journal/2026-09-05.md` |

> Fiche créée rétroactivement. La majorité du code existe déjà (`EcritureScreen`, `EcritureViewModel`, `ClavierAllemand`) ; cette fiche fixe l'écart réel entre ce qui est fait et le backlog.

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] Mission A4 (Room), Mission A5 (Hilt) terminées
- [x] Correctif B-21 appliqué (soumission réellement persistée)
- [x] Correctif B-29 appliqué (résolution de l'unité par argument de navigation ou niveau réel de l'élève, plus de première unité codée en dur)

### Notes de conception — anomalie constatée
**Constat de l'Architecte, à confirmer** : `seed_v1.json` contient déjà, pour chaque unité, un exercice de type `PRODUCTION_GUIDEE` avec une vraie consigne rédigée pour l'écriture (par exemple `EX-6E-01-002`, « Stelle dich vor: Wie heißt du?… »). Or `EcritureScreen` affiche comme consigne `unite.objectifsApprentissage`, un texte d'objectifs pédagogiques généraux — **pas** la consigne d'écriture réelle déjà présente dans le contenu. FR-15 exige explicitement une « consigne d'écriture liée à l'œuvre étudiée » : les deux pièces existent séparément dans le code sans être reliées. À signaler à l'équipe avec le prochain code `AN-*` disponible (numérotation à vérifier, la dernière connue étant `AN-F3-04`).

### Sortie de phase
- [ ] Décider comment relier `EcritureViewModel` à l'exercice `PRODUCTION_GUIDEE` de l'unité plutôt qu'à `objectifsApprentissage`

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### État réel constaté dans le code

| Exigence | État | Constat |
|---|---|---|
| FR-15 (rédiger en réponse à une consigne liée à l'œuvre) | 🟡 Partiel | Éditeur fonctionnel, mais la consigne affichée est `objectifsApprentissage`, pas la vraie consigne `PRODUCTION_GUIDEE` (voir anomalie ci-dessus) |
| FR-16 (consulter des exemples de production modèles) | ❌ À faire | Aucun exemple modèle dans le code |
| FR-17 (auto-évaluation par grille simplifiée) | ✅ Fait | `GrilleAutoEvaluation`, trois critères, persistée en JSON |
| FR-18 (exporter/partager sa production) | ❌ À faire | Aucun bouton de partage dans `EcritureScreen` (le partage existant dans `ProfilScreen` concerne l'export de synchronisation, pas une production individuelle) |
| FR-34 (clavier allemand) | ✅ Fait | `ClavierAllemand.kt`, non listé explicitement dans la DoD actuelle de B3 — à ajouter |
| Sauvegarde automatique | ✅ Fait | `onTexteChange` avec `debounce` de 1500 ms |

### Découpage en sous-tâches restantes
- [ ] Relier l'éditeur à l'exercice `PRODUCTION_GUIDEE` réel de l'unité comme consigne (au lieu de `objectifsApprentissage`)
- [ ] Ajouter des exemples de production modèles par niveau GeR, consultables hors ligne (FR-16)
- [ ] Ajouter un export/partage de production individuelle (FR-18), par exemple via `ACTION_SEND` comme pour la synchronisation
- [ ] Ajouter FR-34 à la Definition of Done officielle de B3 (déjà satisfaite)

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 3 — Test

| Type de test | Portée | Résultat |
|---|---|---|
| Test manuel | Perte de données (rotation d'écran, mise en arrière-plan) | ✅ Vérifié (backlog coché) |
| Test manuel | Grille d'auto-évaluation | ✅ Vérifié (backlog coché) |
| Instrumentation (UI Compose) | Consigne réelle affichée après correction de l'anomalie | ☐ À faire |
| Test manuel | Export/partage d'une production individuelle | ☐ À faire |

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise du backlog, `../04-missions-et-sprints.md`, complétée)
- [ ] FR-15 à FR-17 implémentés *(FR-15 partiel, FR-16 à faire, FR-17 fait)*
- [x] Sauvegarde automatique vérifiée
- [x] Grille d'auto-évaluation fonctionnelle
- [ ] FR-18 implémenté (à ajouter formellement à la DoD)
- [ ] FR-34 vérifié et coché (déjà fait, jamais formalisé)

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [x] Cette fiche (création rétroactive)
- [ ] `04-missions-et-sprints.md` (ajouter FR-18 et FR-34 explicitement à la DoD de B3)
- [ ] Registre des anomalies (attribuer un code `AN-*` à l'écart consigne/`PRODUCTION_GUIDEE`)

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non clôturée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | En cours — l'essentiel du module fonctionne ; reste à relier la vraie consigne d'écriture, ajouter les exemples modèles et l'export individuel |
