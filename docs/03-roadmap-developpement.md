# Roadmap de développement — Liteschreib IKII

## 1. Cadre méthodologique

Méthodologie hybride **DBR (Design-Based Research) + Agile** :

- Des **cycles macro DBR** structurent la recherche : `Analyse → Conception → Développement → Évaluation`, répétés autant de fois que nécessaire (typiquement 2 cycles sur 12 mois).
- Chaque phase de **Développement** est elle-même découpée en **sprints Scrum de 2 semaines**, avec objectif de sprint, backlog et revue.

Cette structure permet de satisfaire simultanément l'exigence académique (itérations validées, traces d'évaluation) et l'exigence d'ingénierie (livraisons incrémentales testables).

## 2. Calendrier macro (12 mois, jalons académiques)

```
Mois :     1    2    3    4    5    6    7    8    9    10   11   12
Cycle 1  [Analyse|Conception|-------Développement-------|Évaluation]
                                                          ▲ Revue mi-parcours (mois 6)
Cycle 2                      [Analyse/Conception|----Développement----|Évaluation]
                                                                         ▲ Soutenance (mois 12)
```

- **Mois 6** : revue de mi-parcours académique — nécessite un livrable fonctionnel du socle pédagogique (Cycle 1) + résultats de la première évaluation heuristique/pilote.
- **Mois 12** : soutenance finale — nécessite l'intégration Phase 3 (IA) stabilisée + évaluation terrain du Cycle 2 + rédaction complète du mémoire.

## 3. Cycle DBR 1 — Socle pédagogique (mois 1 à 7 environ)

### 3.1 Analyse (réalisé)
- Étude des besoins (curriculum *Ihr und Wir Plus*, niveaux GeR/CECR, contraintes BYOD/offline)
- Revue de littérature initiale (MALL, DaF, ICALL)

### 3.2 Conception (réalisé / en finalisation)
- Modélisation UML (PlantUML) des acteurs et cas d'usage
- Architecture Clean Architecture + MVVM définie
- Wireframes low-fi → design system (Figma, kit Material 3) → maquettes high-fi (Apprentissage, Suivi, Profil élève ; dashboard enseignant en cours)
- Prototype interactif + évaluation heuristique interne
- Handoff Dev Mode Figma

### 3.3 Développement (phase courante) — découpage en sprints

| Sprint | Objectif principal | Livrables |
|---|---|---|
| **Sprint 0** *(en cours)* | Finaliser le design system et le scaffolding de base | `Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt` avec tokens Figma définitifs (plus de `TODO`) ; structure de packages Clean Architecture créée |
| **Sprint 1** | Navigation & squelette d'écrans | Navigation Compose (5 routes + dashboard enseignant), Scaffold + BottomNavigation, écrans vides fonctionnels |
| **Sprint 2** | Couche données — Room | Entités Room (Profil, Unité, Exercice, Production écrite, Progression), DAO, stratégie de pré-population (seed depuis assets) couvrant plusieurs niveaux GeR dès le MVP (ADR-008) — voir cartographie de contenu pour le détail de la portée |
| **Sprint 3** | Injection de dépendances & couche domaine | Modules Hilt (App, Data, Domain), cas d'usage (`UseCase`) pour chaque module fonctionnel du FR |
| **Sprint 4** | Écran Accueil + Profil (bout en bout) | ViewModel + UI reliés à Room, tests unitaires domaine, tests UI de base |
| **Sprint 5** | Écran Apprentissage (lecture + TTS + exercices) | Intégration Android TTS API avec vérification/téléchargement ponctuel de la voix allemande (ADR-007, FR-32), glossaire contextuel, correction offline des exercices |
| **Sprint 6** | Écran Écriture | Éditeur de texte, sauvegarde auto, grille d'auto-évaluation |
| **Sprint 7** | Écran Suivi | Visualisation progression GeR/CECR, planification de révision (algorithme simplifié type FSRS) |
| **Sprint 8** | Dashboard enseignant | Vue classes/élèves, assignation de contenu, consultation productions écrites |
| **Sprint 9** | Synchronisation locale (BYOD) | Export/import de fichier via partage système (Nearby Share / Bluetooth / carte SD en repli, ADR-004), tests d'intégration bout en bout sur au moins deux appareils physiques |
| **Sprint 10** | Durcissement & tests | Couverture de tests domaine ≥ 70 %, tests instrumentés écrans critiques, correctifs accessibilité (Stark) |

### 3.4 Évaluation (fin de cycle)
- Distribution de l'APK par transfert local (USB, Bluetooth, carte SD — ADR-010), accompagnée du guide d'installation (`15-guide-enseignant-onboarding.md`)
- Test pilote avec un échantillon d'élèves/enseignants (contexte Yaoundé), sur des appareils Android 9.0+ (ADR-005)
- Évaluation heuristique + collecte de données via Google Forms hors ligne
- Synthèse pour la revue de mi-parcours (mois 6)

## 4. Cycle DBR 2 — Intégration IA/NLP différée (mois 7 à 12 environ)

### 4.1 Analyse/Conception révisée
- Intégration des retours du pilote Cycle 1
- Spécification des interfaces de domaine pour les fonctionnalités IA (ports/adapters anticipés dès NFR-18)

### 4.2 Développement — sprints indicatifs

| Sprint | Objectif principal | Livrables |
|---|---|---|
| **Sprint 11** | Intégration TFLite (pipeline modèle embarqué) | Module `data/ai` isolé, interface `PronunciationEvaluator` implémentée |
| **Sprint 12** | Intégration Gemini Nano (si disponible sur device cible) | Fallback gracieux si device incompatible (contrainte offline/on-device stricte) |
| **Sprint 13** | Fonctions ASR/AWE de base | Reconnaissance vocale simple, feedback de prononciation |
| **Sprint 14** | Stabilisation & tests de non-régression | Vérification que le socle Cycle 1 n'est pas dégradé (NFR-18) |

### 4.3 Évaluation finale
- Test terrain élargi
- Rédaction des chapitres de résultats du mémoire
- Préparation de la soutenance (mois 12)

## 5. Outils par étape du pipeline

| Étape | Outils |
|---|---|
| Conception UML | PlantUML (plugin Android Studio) |
| Design | Figma (kit Material 3), Stark (contraste/accessibilité), Penpot (auto-hébergé Docker, secours) |
| Développement | Android Studio, Kotlin, Jetpack Compose, Navigation Compose, KSP, Gradle |
| Architecture | Clean Architecture + MVVM, Hilt (DI), Room + Flow |
| IA embarquée (Cycle 2) | TensorFlow Lite, Gemini Nano, ML Kit (OCR), Android TTS API |
| Tests | JUnit, Turbine (Flow), Espresso/Compose Test, tests de migration Room |
| CI/CD | GitHub Actions, GitHub (revue de code, Pull Requests) |
| Gestion de projet | Backlog de sprint (voir `04-missions-et-sprints.md`), tableau Kanban/Scrum |
| Recherche & littérature | Zotero, sources prioritairement en allemand, puis français/anglais |
| Collecte de données terrain | Google Forms (export hors ligne) |

## 6. Dépendances critiques entre sprints

- La navigation (Sprint 1) et Room (Sprint 2) sont **bloquantes** pour tous les écrans fonctionnels suivants.
- Hilt (Sprint 3) doit être en place avant tout ViewModel connecté à un cas d'usage.
- La synchronisation locale (Sprint 9) dépend du choix technique arbitré en ADR (voir `06-architecture-technique.md`) — à trancher au plus tard avant le Sprint 8.
- Le Cycle 2 (IA) ne démarre qu'après l'évaluation de fin de Cycle 1 : ne pas anticiper de dépendance IA dans le code du socle avant cette validation.
