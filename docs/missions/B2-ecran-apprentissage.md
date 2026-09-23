# Mission B2 — Écran Apprentissage

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | B2 |
| Titre | Écran Apprentissage |
| Type | Mission planifiée |
| Sprint | Sprint 5 |
| FR/NFR concernés | FR-09 à FR-14, FR-32 |
| ADR concerné(s) | ADR-007, ADR-014 |
| **Statut global** | `Implémentation` — en cours, priorité 1 du projet (`ETAT_ACTUEL.md`) |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | `../journal/2026-09-05.md` |

> Cette fiche est créée rétroactivement : une part importante du travail existe déjà en code. Elle sert désormais de référence unique pour ce qui reste réellement à faire, établie par relecture du code plutôt que par supposition.

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] Mission A2 (structure de packages) terminée
- [x] Mission A4 (entités Room) livrée en infrastructure
- [x] ADR-014 (agent de codage sans Figma) en vigueur

### Notes de conception
Écran en deux vues : bibliothèque (liste des unités) et lecture (extrait avec glossaire interactif, cliquable). Module Exercice séparé (`presentation/exercice`), couvrant QCM, texte à trous, vrai/faux et production guidée. La lecture audio (TTS, FR-11/FR-32) n'a fait l'objet d'aucun travail de conception ni d'aucun code à ce jour — c'est le principal reste de cette mission.

### Sortie de phase
- [x] Approche de la bibliothèque et de la lecture arrêtée et codée
- [ ] Approche de la vérification/installation de la voix TTS (FR-32) à concevoir

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée *(pour la partie déjà codée)*

---

## Phase 2 — Implémentation

### État réel constaté dans le code

| Exigence | État | Constat |
|---|---|---|
| FR-09 (parcourir par niveau/thème, **filtrable**) | 🟡 Partiel | `ApprentissageViewModel.chargerBibliotheque()` liste toutes les unités (`getAllUnites()`) ; aucun filtre par niveau dans l'UI (`BibliothequeLectures`), alors que `GetUnitesParNiveauUseCase` existe déjà et est utilisé ailleurs (`EcritureViewModel`) |
| FR-10 (glossaire contextuel) | ✅ Fait | `TexteAvecGlossaire`, annotation cliquable, dialog de traduction |
| FR-11 (lecture audio TTS) | ❌ À faire | Aucune classe `TtsManager` ni usage de l'API Android TTS trouvés dans le code fourni |
| FR-12 (exercices, correction offline) | ✅ Fait | Module Exercice complet (QCM, texte à trous, vrai/faux, production guidée), correction locale, réponses enregistrées |
| FR-13 (niveau GeR et objectifs affichés) | ✅ Fait | `LectureUniteScreen` affiche le badge de niveau et `objectifsApprentissage` |
| FR-14 (marquer en cours/terminé, reprendre) | 🟡 Partiel | `marquerUniteEnCours` (à l'ouverture) et `marquerUniteTerminee` (fin d'exercices) sont **automatiques** (correctif B-28) ; aucun contrôle manuel, aucun badge de statut affiché dans la liste des unités |
| FR-32 (vérification/téléchargement voix TTS) | ❌ À faire | Aucun code trouvé |

### Découpage en sous-tâches restantes
- [ ] Ajouter un filtre par niveau GeR dans `BibliothequeLectures` (réutiliser `GetUnitesParNiveauUseCase`)
- [ ] Afficher un badge de statut (non commencé / en cours / terminé) sur chaque carte d'unité dans la bibliothèque
- [ ] Créer `TtsManager` (vérification de la voix installée, proposition de téléchargement si absente, lecture avec contrôle play/pause/vitesse)
- [ ] Écran/dialogue de vérification affiché une seule fois au premier accès (FR-32)
- [ ] Vérifier que l'usage ultérieur du module reste strictement hors ligne une fois la voix installée (NFR-01-bis)

### Points de vigilance obligatoires
- [ ] Aucune régression sur le module Exercice déjà fonctionnel
- [ ] L'exception réseau du TTS ne doit jamais s'étendre à une autre fonctionnalité (ADR-007)

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 3 — Test

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | Cas d'usage du module Exercice | ✅ Couvert (voir code) |
| Instrumentation (UI Compose) | Parcours de lecture, glossaire | ☐ À faire |
| Instrumentation (UI Compose) | Scénario « voix non installée » et « voix installée » (FR-32) | ☐ À faire |
| Test manuel offline | Mode avion sur le module Exercice | ✅ Vérifié (FR-12 coché au backlog) |
| Test manuel offline | Mode avion après installation de la voix TTS | ☐ À faire |

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise du backlog, `../04-missions-et-sprints.md`)
- [x] Module Exercice complet (QCM/texte à trous/vrai-faux) implémenté
- [ ] FR-09 à FR-11 implémentés *(FR-09 partiel, FR-11 à faire)*
- [x] FR-12 implémenté : correction offline vérifiée (mode avion)
- [ ] FR-13, FR-14 implémentés *(FR-13 fait, FR-14 partiel — automatique seulement)*
- [ ] FR-32 implémenté : écran/dialogue de vérification de la voix TTS allemande au premier accès, avec proposition de téléchargement si connexion disponible (ADR-007)
- [ ] Intégration TTS fonctionnelle et strictement hors ligne une fois la voix installée
- [ ] Tests UI Compose sur le parcours de lecture, incluant le scénario « voix non installée » et « voix installée »

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [x] Cette fiche (création rétroactive)
- [ ] `04-missions-et-sprints.md` (préciser l'état partiel de FR-09/FR-14 plutôt qu'une case à cocher globale)
- [ ] `ETAT_ACTUEL.md` (détailler « reste TTS » avec le détail ci-dessus)

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non clôturée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | En cours — la charge restante est concentrée sur le TTS (FR-11, FR-32) et deux affinages (filtre FR-09, statut visible FR-14) |
