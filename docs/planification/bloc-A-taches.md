# Plan de tâches détaillé — Bloc A (Fondations techniques)

## Objectif de ce document

Ce document décompose chaque mission du Bloc A (`../04-missions-et-sprints.md`) en tâches suffisamment petites pour être réalisées en une session de travail, ordonnées chronologiquement, avec leurs dépendances explicites. Il sert de guide d'exécution jour par jour, en complément :
- des fiches de mission (`../missions/`), qui suivent l'avancement par phase (Conception → Implémentation → Test → Validation → Documentation) ;
- de la checklist quotidienne (`../05-checklist-quotidienne.md`), qui rythme chaque session.

> ⚠️ **Base de ce plan** : j'ai relu le code réel du dépôt (état fourni en tout début de conversation) pour que les tâches ci-dessous reflètent ce qui existe déjà plutôt qu'une planification abstraite. Voir la section 1 pour les constats importants qui en découlent — certains touchent directement l'ordre des tâches.

## Comment lire ce document

- Chaque tâche a un ID unique (`A1-T03`, `A4-T12`, etc.), repris tel quel dans les commits et les fiches de mission si besoin.
- **Dépend de** : tâches devant être terminées avant de démarrer celle-ci. Vide = peut démarrer dès que la mission est ouverte.
- **Statut** : ☐ À faire · 🔄 En cours · ✅ Fait *(déjà vérifié dans le code actuel)*
- **[BLOQUANT]** : tâche dont la non-réalisation empêche la mission suivante de démarrer sérieusement.

---

## 1. Constats sur l'état réel du code (à traiter en priorité)

Trois incohérences concrètes ont été repérées en relisant le code déjà généré. Elles ne bloquent rien immédiatement, mais mieux vaut les trancher tôt pour éviter qu'elles ne se propagent :

| # | Constat | Où | Impact |
|---|---|---|---|
| 1 | **Deux systèmes de thème coexistent.** `ui.theme.*` (template par défaut Android Studio : couleurs Purple/Pink, `DLearnTheme`, `dynamicColor = true`) est celui **réellement utilisé** par `MainActivity.kt`. `presentation.theme.*` (le vrai design system : `LiteschreibIkiiTheme`, `dynamicColor = false`) existe mais n'est **pas branché** — et son `Theme.kt` importe même encore `Typography` depuis l'ancien `ui.theme.Type.kt`. | `MainActivity.kt`, `presentation/theme/`, `ui/theme/` | Sans correction, la Mission A1 peut sembler « terminée » alors que l'app affiche toujours le thème par défaut |
| 2 | **Deux fichiers de navigation coexistent**, avec des références de package différentes. `DLearnNavGraph.kt` (actif, minimal, `edu.project.dlearn.*`) et `NavGraph.kt` (commenté, `com.ikii.liteschreib.features.*` — un nom de package qui n'a jamais été le vrai namespace du projet). | `presentation/navigation/` | Confusion garantie si les deux fichiers sont complétés en parallèle |
| 3 | **Trois noms de package flottent** dans le projet : `edu.project.dlearn` (le vrai, celui de `build.gradle.kts` et `AndroidManifest.xml`), `com.ikii.liteschreib` (dans les commentaires de `AppModule.kt`/`AppDatabase.kt`), et `com.liteschreib.ikii` (celui que j'avais documenté dans `06-architecture-technique.md`, section 2). | Partout | Risque d'erreurs de compilation si le code commenté est réactivé tel quel |

**Décision que je prends pour ce plan** (à confirmer ou corriger par toi) : conserver `edu.project.dlearn` comme unique nom de package, puisque c'est le seul réellement configuré dans Gradle. J'ai corrigé `06-architecture-technique.md` en conséquence. Les tâches ci-dessous intègrent le nettoyage nécessaire.

---

## 2. Vue d'ensemble de l'enchaînement du Bloc A

```
A0 (contenu)  ──────────────────────────────────┐
                                                  │ (validation requise)
A1 (design system) ──┐                           ▼
                      │                    A4 (Room & pré-population)
A2 (packages) ────────┼──► A3 (navigation)        ▲
                      │                           │
                      └───────────────────────────┴──► A5 (Hilt)
```

- **A0** et **A1** peuvent avancer en parallèle, sans dépendance technique entre elles.
- **A2** bloque **A3**, **A4** et **A5** (structure de packages nécessaire).
- **A4** nécessite en plus que **A0** ait atteint son seuil de validation (au moins partiellement).
- **A5** nécessite **A2** et **A4** (les bindings Hilt référencent les repositories créés en A4).

---

## 3. Mission A0 — Cartographie de contenu pédagogique

*(Contexte complet : `../missions/A0-cartographie-contenu-pedagogique.md`)*

| ID | Tâche | Dépend de | Statut |
|---|---|---|---|
| A0-T01 | Confirmer la population cible et proposer la correspondance niveau scolaire ↔ GeR | — | ✅ Fait |
| A0-T02 | Trancher la portée MVP (collège complet 6e-3e) | T01 | ✅ Fait |
| A0-T03 | Rédiger `U-6E-01` (brouillon, 6e, A1) | T02 | ✅ Fait |
| A0-T04 | Rédiger `U-5E-01` (brouillon, 5e, A1 consolidation) | T02 | ✅ Fait |
| A0-T05 | Rédiger `U-4E-01` (brouillon, 4e, A2) | T02 | ✅ Fait |
| A0-T06 | Rédiger `U-3E-01` (brouillon, 3e, A2 consolidation) | T02 | ✅ Fait |
| A0-T07 | Se procurer un extrait/exemplaire du manuel *Ihr und Wir Plus* | — | ☐ À faire |
| A0-T08 | Renseigner la référence de chapitre pour `U-6E-01` | T07 | ☐ À faire |
| A0-T09 | Renseigner la référence de chapitre pour `U-5E-01` | T07 | ☐ À faire |
| A0-T10 | Renseigner la référence de chapitre pour `U-4E-01` | T07 | ☐ À faire |
| A0-T11 | Renseigner la référence de chapitre pour `U-3E-01` | T07 | ☐ À faire |
| A0-T12 | Identifier un locuteur natif ou l'encadrant académique pour la relecture | — | ☐ À faire |
| A0-T13 | Faire relire `U-6E-01`, corriger si besoin → statut `Relu` | T12 | ☐ À faire |
| A0-T14 | Faire relire `U-5E-01` → `Relu` | T12 | ☐ À faire |
| A0-T15 | Faire relire `U-4E-01` → `Relu` | T12 | ☐ À faire |
| A0-T16 | Faire relire `U-3E-01` → `Relu` | T12 | ☐ À faire |
| A0-T17 | Faire valider chaque unité relue par l'encadrant → statut `Validé` | T08-T16 (par unité) | ☐ À faire |
| A0-T18 | Rédiger 4 unités supplémentaires niveau 6e (vers le seuil de 5) | T02 | ☐ À faire |
| A0-T19 | Rédiger 4 unités supplémentaires niveau 5e | T02 | ☐ À faire |
| A0-T20 | Rédiger 4 unités supplémentaires niveau 4e | T02 | ☐ À faire |
| A0-T21 | Rédiger 4 unités supplémentaires niveau 3e | T02 | ☐ À faire |
| A0-T22 | Répéter le cycle relecture/validation (T07-T17) pour les nouvelles unités | T18-T21 | ☐ À faire |
| A0-T23 | **[BLOQUANT pour A4]** Vérifier que le seuil de 5 unités `Validé` est atteint sur les 4 niveaux | T22 | ☐ À faire |
| A0-T24 | Mettre à jour `04-missions-et-sprints.md` : Mission A0 → `Validé` | T23 | ☐ À faire |

---

## 4. Mission A1 — Finaliser le design system

*(Contexte complet : `../missions/A1-finaliser-design-system.md`)*

| ID | Tâche | Dépend de | Statut |
|---|---|---|---|
| A1-T01 | Scaffolding initial de `presentation/theme/{Color,Type,Shape,Theme,LiteschreibIkiiTheme}.kt` | — | ✅ Fait (certains fichiers encore vides) |
| A1-T02 | **[Constat #1]** Nettoyer la confusion entre `ui.theme.*` (actif, template par défaut) et `presentation.theme.*` (nouveau, pas branché) — voir section 1 | — | ☐ À faire |
| A1-T03 | **[BLOQUANT]** Exporter les couleurs exactes depuis Figma Dev Mode (primary/secondary/tertiary/error/background/surface, light + dark) | — | ☐ À faire |
| A1-T04 | Écrire `presentation/theme/Color.kt` avec les valeurs exactes (fichier actuellement vide) | T03 | ☐ À faire |
| A1-T05 | Construire `lightColorScheme(...)`/`darkColorScheme(...)` dans `Theme.kt` à partir de `Color.kt` (actuellement appelés sans paramètres) | T04 | ☐ À faire |
| A1-T06 | **[BLOQUANT]** Exporter l'échelle typographique depuis Figma (tailles, graisses, line-height) | — | ☐ À faire |
| A1-T07 | Écrire `presentation/theme/Type.kt` avec l'objet `Typography` réel (fichier actuellement vide) | T06 | ☐ À faire |
| A1-T08 | Mettre à jour l'import dans `Theme.kt` : utiliser `presentation.theme.Type.Typography` au lieu de `ui.theme.Typography` | T07 | ☐ À faire |
| A1-T09 | Vérifier `Shape.kt` (déjà rempli : 4/8/12/16/28dp) contre les valeurs réelles de Figma | — | ☐ À faire *(fichier non vide mais non confirmé)* |
| A1-T10 | Écrire le contenu de `LiteschreibIkiiTheme.kt` (vide) ou fusionner sa responsabilité dans `Theme.kt` — clarifier quel fichier est la source de vérité | T05, T08 | ☐ À faire |
| A1-T11 | **[BLOQUANT]** Mettre à jour `MainActivity.kt` : utiliser `LiteschreibIkiiTheme` au lieu de `DLearnTheme` | T10 | ☐ À faire |
| A1-T12 | Supprimer les fichiers du template par défaut `ui/theme/{Color,Type,Theme}.kt` une fois la bascule confirmée sans régression visuelle | T11 | ☐ À faire |
| A1-T13 | Nettoyer `MainActivity.kt` : retirer l'import dupliqué de `DLearnTheme` et le bloc `Greeting`/`GreetingPreview` de démonstration | T12 | ☐ À faire |
| A1-T14 | Comparer `AccueilScreen` (Compose Preview) à la maquette Figma correspondante | T11 | ☐ À faire |
| A1-T15 | Vérifier le contraste de chaque token de couleur avec Stark (NFR-13) | T04 | ☐ À faire |
| A1-T16 | Corriger les écarts de contraste identifiés | T15 | ☐ À faire |
| A1-T17 | Commit dédié : `feat(design-system): tokens Figma définitifs` | T13, T16 | ☐ À faire |
| A1-T18 | Mettre à jour `../missions/A1-finaliser-design-system.md` (phases restantes) et `04-missions-et-sprints.md` (Mission A1 → `Validé`) | T17 | ☐ À faire |

---

## 5. Mission A2 — Structure de packages Clean Architecture

*(Contexte complet : `../missions/A2-structure-packages-clean-architecture.md`)*

| ID | Tâche | Dépend de | Statut |
|---|---|---|---|
| A2-T00 | **[Décision]** Aligner la convention de nommage sur l'existant : garder `presentation.theme` (plutôt que `presentation.designsystem`) et `core.di` (plutôt que `di`) — mettre à jour `06-architecture-technique.md` en conséquence | — | ✅ Fait *(pris en compte dans ce plan)* |
| A2-T01 | Créer `domain/model/` | — | ☐ À faire |
| A2-T02 | Créer `domain/usecase/` | — | ☐ À faire |
| A2-T03 | Créer `domain/repository/` | — | ☐ À faire |
| A2-T04 | Organiser `data/local/room/` (actuellement `data/local/` contient directement `AppDatabase.kt` commenté) | — | ☐ À faire |
| A2-T05 | Créer `data/local/datasource/` | — | ☐ À faire |
| A2-T06 | Créer `data/repository/` | — | ☐ À faire |
| A2-T07 | Créer `data/ai/` (vide, réservé Cycle 2 — ADR-003) | — | ☐ À faire |
| A2-T08 | Créer `presentation/apprentissage/`, `presentation/ecriture/`, `presentation/suivi/`, `presentation/profil/`, `presentation/enseignant/` (`presentation/accueil/` existe déjà) | — | ☐ À faire |
| A2-T09 | Vérifier qu'aucune classe placeholder ne viole la règle de dépendance | T01-T08 | ☐ À faire |
| A2-T10 | `./gradlew build` pour confirmer que l'arborescence vide compile | T09 | ☐ À faire |
| A2-T11 | Mettre à jour `06-architecture-technique.md` section 2 avec l'arborescence réelle (`edu.project.dlearn.*`) | T10 | ☐ À faire |
| A2-T12 | Commit : `feat(architecture): scaffolding structure Clean Architecture` | T10 | ☐ À faire |
| A2-T13 | Mettre à jour `../missions/A2-...md` et `04-missions-et-sprints.md` (Mission A2 → `Validé`) | T12 | ☐ À faire |

---

## 6. Mission A3 — Navigation Compose

*(Contexte complet : `../missions/A3-navigation-compose.md` — dépend de A2)*

| ID | Tâche | Dépend de | Statut |
|---|---|---|---|
| A3-T01 | **[Constat #2, Décision]** Choisir entre `NavGraph.kt` (commenté, package `com.ikii.liteschreib.*` obsolète) et `DLearnNavGraph.kt` (actif, `edu.project.dlearn.*`) comme fichier unique — supprimer l'autre | A2 | ☐ À faire |
| A3-T02 | Clarifier si `AccueilScreen` actuel (boutons Élève/Enseignant déjà présents) joue le rôle de l'écran de sélection de profil (`Routes.CONNEXION`), ou si c'est un doublon à réorganiser | T01 | ☐ À faire |
| A3-T03 | Décommenter et adapter `BottomNavItem.kt` (déjà rédigé en commentaire) | A2-T08 | ☐ À faire |
| A3-T04 | Créer les écrans placeholder restants du sous-graphe élève (`ApprentissageScreen`, `EcritureScreen`, `SuiviScreen`, `ProfilScreen`) | A2-T08 | ☐ À faire |
| A3-T05 | Construire le `NavHost` racine avec sous-graphe élève (5 routes) et sous-graphe enseignant | T02, T04 | ☐ À faire |
| A3-T06 | Implémenter la `BottomNavigationBar` en réutilisant `presentation.theme` | T03, T05 | ☐ À faire |
| A3-T07 | Implémenter la route dashboard enseignant (placeholder en attendant Mission C2) | T05 | ☐ À faire |
| A3-T08 | Implémenter la bascule de profil sur device partagé (`codeAcces` optionnel) | T05 | ☐ À faire |
| A3-T09 | Test manuel : navigation entre les 5 onglets, retour arrière, bascule de profil | T06, T07, T08 | ☐ À faire |
| A3-T10 | Écrire un test d'instrumentation basique de navigation | T09 | ☐ À faire |
| A3-T11 | Commit : `feat(navigation): graphe de navigation complet` | T10 | ☐ À faire |
| A3-T12 | Mettre à jour `../missions/A3-navigation-compose.md` et `04-missions-et-sprints.md` (Mission A3 → `Validé`) | T11 | ☐ À faire |

---

## 7. Mission A4 — Entités Room & pré-population

*(Contexte complet : `../missions/A4-entites-room-prepopulation.md` — dépend de A2 et A0)*

| ID | Tâche | Dépend de | Statut |
|---|---|---|---|
| A4-T01 | **[BLOQUANT]** Attendre que le seuil de validation A0-T23 soit atteint (ou démarrer avec les unités `Rédigé` en connaissance de cause, décision à ton appréciation) | A0-T23 | ☐ À faire |
| A4-T02 | **[Constat #3]** Corriger les références de package erronées dans le squelette existant de `AppDatabase.kt` et `AppModule.kt` (`com.ikii.liteschreib.*` → `edu.project.dlearn.*`) | A2 | ☐ À faire |
| A4-T03 | Modéliser l'entité `ProfilEleve` | A2-T04 | ☐ À faire |
| A4-T04 | Modéliser l'entité `ProfilEnseignant` | A2-T04 | ☐ À faire |
| A4-T05 | Modéliser l'entité `Classe` | A2-T04 | ☐ À faire |
| A4-T06 | Modéliser l'entité `UniteApprentissage` | A2-T04 | ☐ À faire |
| A4-T07 | Modéliser l'entité `ExtraitLitteraire` | A2-T04 | ☐ À faire |
| A4-T08 | Modéliser l'entité `GlossaireEntree` | A2-T04 | ☐ À faire |
| A4-T09 | Modéliser l'entité `Exercice` | A2-T04 | ☐ À faire |
| A4-T10 | Modéliser l'entité `OptionExercice` | A2-T04 | ☐ À faire |
| A4-T11 | Modéliser l'entité `ReponseEleve` | A2-T04 | ☐ À faire |
| A4-T12 | Modéliser l'entité `ProductionEcrite` | A2-T04 | ☐ À faire |
| A4-T13 | Modéliser l'entité `Progression` | A2-T04 | ☐ À faire |
| A4-T14 | Modéliser l'entité `PlanificationRevision` | A2-T04 | ☐ À faire |
| A4-T15 | Modéliser l'entité `Assignation` | A2-T04 | ☐ À faire |
| A4-T16 | Modéliser l'entité `SyncLog` | A2-T04 | ☐ À faire |
| A4-T17 | Créer les DAO correspondants | T03-T16 | ☐ À faire |
| A4-T18 | Activer `AppDatabase.kt` avec la liste complète des entités, `version = 1` | T02, T17 | ☐ À faire |
| A4-T19 | Générer les fichiers JSON de contenu à partir des unités `Validé` | A4-T01 | ☐ À faire |
| A4-T20 | Implémenter le callback de pré-population au premier lancement | T18, T19 | ☐ À faire |
| A4-T21 | Écrire les tests unitaires DAO | T17 | ☐ À faire |
| A4-T22 | Tester le premier lancement en mode avion (contenu disponible, NFR-03) | T20 | ☐ À faire |
| A4-T23 | Documenter tout écart entre le schéma implémenté et `11-schema-donnees-room.md` | T18 | ☐ À faire |
| A4-T24 | Commit : `feat(data): entités Room et pré-population` | T21, T22 | ☐ À faire |
| A4-T25 | Mettre à jour `../missions/A4-entites-room-prepopulation.md` et `04-missions-et-sprints.md` (Mission A4 → `Validé`) | T24 | ☐ À faire |

---

## 8. Mission A5 — Modules Hilt

*(Contexte complet : `../missions/A5-modules-hilt.md` — dépend de A2 et A4)*

| ID | Tâche | Dépend de | Statut |
|---|---|---|---|
| A5-T01 | Annoter `DLearnApplication` avec `@HiltAndroidApp` | — | ✅ Fait |
| A5-T02 | Annoter `MainActivity` avec `@AndroidEntryPoint` | — | ✅ Fait |
| A5-T03 | Corriger les références de package dans le squelette de `AppModule.kt` (voir A4-T02, même correction) | A4-T02 | ☐ À faire |
| A5-T04 | Activer/compléter `DatabaseModule` (fourniture de `AppDatabase`) — noter `fallbackToDestructiveMigration()` comme dette technique explicite à corriger avant production | A4-T18 | ☐ À faire |
| A5-T05 | Activer/compléter `RepositoryModule` (bindings `Repository` → implémentations, un seul binding esquissé actuellement pour `ApprentissageRepository`) | A4-T17 | ☐ À faire |
| A5-T06 | Ajouter les bindings manquants au fur et à mesure des repositories des autres modules (Suivi, Profil, Écriture) | T05 | ☐ À faire |
| A5-T07 | Vérifier qu'aucun ViewModel ne recourt à une instanciation manuelle de dépendance (vigilance continue, dès leur création) | T05 | ☐ À faire |
| A5-T08 | `./gradlew build` complet après activation des modules | T04, T05 | ☐ À faire |
| A5-T09 | Documenter les scopes retenus dans `06-architecture-technique.md` | T08 | ☐ À faire |
| A5-T10 | Commit : `feat(di): modules Hilt activés` | T08 | ☐ À faire |
| A5-T11 | Mettre à jour `../missions/A5-modules-hilt.md` et `04-missions-et-sprints.md` (Mission A5 → `Validé`) | T10 | ☐ À faire |

---

## 9. Suivi

Ce document doit être mis à jour à chaque session touchant une tâche du Bloc A (cocher `✅ Fait`), en cohérence avec la fiche de mission correspondante et `docs/ETAT_ACTUEL.md`. Il ne remplace pas le rapport journalier (`docs/journal/`) qui reste la trace narrative de chaque session — ce document-ci est la trace structurelle de l'avancement tâche par tâche.
