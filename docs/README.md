# Documentation technique — Liteschreib IKII

Application Android offline-first d'enseignement de l'allemand langue étrangère (DaF) par la littérature, destinée aux élèves du secondaire francophone camerounais (Yaoundé), alignée sur le curriculum *Ihr und Wir Plus* et les niveaux GeR/CECR.

Ce dossier constitue la documentation de référence pour le développement du projet, à double redevabilité **académique** (mémoire de Master, soutenance) et **ingénierie** (logiciel fonctionnel, maintenable). Il est destiné à être versionné dans le dépôt Git (`/docs`) et tenu à jour à chaque sprint.

> ⭐ **Pour reprendre le développement** (après une pause, courte ou longue), commencer systématiquement par [`ETAT_ACTUEL.md`](ETAT_ACTUEL.md) — point d'entrée unique qui renvoie vers la mission active et le dernier rapport de session.

## Sommaire — Documents de référence

Ces documents sont relativement figés : ils décrivent le projet dans son ensemble et ne changent qu'à des jalons précis (nouvelle décision, nouvelle exigence).

| # | Document | Contenu |
|---|---|---|
| 1 | [01-exigences-fonctionnelles.md](01-exigences-fonctionnelles.md) | Acteurs, cas d'usage, exigences fonctionnelles (FR) par module |
| 2 | [02-exigences-non-fonctionnelles.md](02-exigences-non-fonctionnelles.md) | Exigences non fonctionnelles (NFR) : performance, offline, sécurité, accessibilité, etc. |
| 3 | [03-roadmap-developpement.md](03-roadmap-developpement.md) | Roadmap détaillée (cycles DBR + sprints Scrum), calendrier, outils par étape |
| 4 | [04-missions-et-sprints.md](04-missions-et-sprints.md) | Backlog de missions à réaliser et valider, avec critères de Definition of Done |
| 5 | [05-checklist-quotidienne.md](05-checklist-quotidienne.md) | Checklists quotidiennes / hebdomadaires de développement |
| 6 | [06-architecture-technique.md](06-architecture-technique.md) | Clean Architecture, structure de packages, Git workflow, ADR, stratégie de tests |
| 7 | [07-glossaire.md](07-glossaire.md) | Glossaire des termes techniques et didactiques (DaF, MALL, GeR/CECR, FSRS, etc.) |
| 8 | [08-registre-des-risques.md](08-registre-des-risques.md) | Registre des risques techniques, pédagogiques, éthiques et organisationnels |
| 9 | [09-cartographie-contenu-pedagogique.md](09-cartographie-contenu-pedagogique.md) | Gabarit de correspondance niveau GeR ↔ curriculum ↔ contenu applicatif |
| 10 | [10-protocole-ethique-consentement.md](10-protocole-ethique-consentement.md) | Protocole éthique et gabarits de consentement (public mineur) |
| 11 | [11-schema-donnees-room.md](11-schema-donnees-room.md) | Schéma de données Room détaillé (entités, relations, migrations) |
| 12 | [12-politique-confidentialite-notice-information.md](12-politique-confidentialite-notice-information.md) | Notice de confidentialité destinée aux utilisateurs finaux (élèves, parents, enseignants) |
| 13 | [13-plan-gestion-donnees-recherche.md](13-plan-gestion-donnees-recherche.md) | Plan de gestion des données de recherche collectées lors des pilotes DBR |
| 14 | [14-charte-versionnage-contenu.md](14-charte-versionnage-contenu.md) | Schémas de version (application, contenu, Room, format d'échange) et changelog |
| 15 | [15-guide-enseignant-onboarding.md](15-guide-enseignant-onboarding.md) | Guide d'installation et de prise en main pour l'enseignant pilote |
| 16 | [16-gabarit-auteur-exercice.md](16-gabarit-auteur-exercice.md) | Gabarit standardisé de création d'une unité de contenu pédagogique |
| 17 | [17-diagrammes-uml.md](17-diagrammes-uml.md) | Diagrammes UML formalisés en PlantUML (cas d'usage, classes, séquences) |

## Suivi opérationnel en direct (vivant, mis à jour en continu)

Contrairement aux documents ci-dessus, ces éléments évoluent à chaque session de travail. Ils orchestrent le cycle de réalisation (Conception → Implémentation → Test → Validation → Documentation) de chaque mission, cas d'utilisation ou changement, et permettent une reprise de contexte immédiate en cas d'interruption.

| Emplacement | Rôle |
|---|---|
| [`ETAT_ACTUEL.md`](ETAT_ACTUEL.md) | ⭐ Point d'entrée unique pour reprendre le travail |
| [`processus/gabarit-cycle-iteration.md`](processus/gabarit-cycle-iteration.md) | Gabarit maître des 5 phases, à dupliquer pour chaque mission |
| [`processus/guide-orchestration.md`](processus/guide-orchestration.md) | Mode d'emploi complet du système et procédure de reprise |
| [`missions/`](missions/README.md) | Une fiche vivante par mission/cas d'usage, suivie phase par phase |
| [`journal/`](journal/README.md) | Un rapport par session de travail (journal de bord DBR) |

Voir `processus/guide-orchestration.md` pour le détail du fonctionnement de ce système à trois niveaux de granularité.

## Principes directeurs (rappel)

Ces principes s'appliquent à **tout** le développement et doivent être vérifiés à chaque revue de code ou de sprint :

1. **Offline-first non négociable** — aucune fonctionnalité cœur ne doit dépendre d'une connexion réseau. Pas de SDK d'analytics cloud.
2. **IA différée (Phase 3)** — TFLite, Gemini Nano, ASR/AWE ne sont introduits qu'après stabilisation du cœur pédagogique, pour découpler le risque IA du socle architectural.
3. **Clean Architecture dès l'origine** — séparation stricte domain → data → presentation, pour éviter tout refactoring coûteux lors de l'intégration IA.
4. **Fidélité Figma 1:1** — le design system doit correspondre exactement aux exports Dev Mode ; aucun token approximé.
5. **Double redevabilité** — chaque mission de développement doit pouvoir être documentée pour la soutenance (traçabilité DBR : journal de bord, décisions, itérations).

## État du projet (dernière mise à jour du dossier)

- Design system partiellement scaffoldé (`Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt`) — Material Design 3, couleur dynamique désactivée.
- Maquettes Figma disponibles : Apprentissage, Suivi, Profil élève ; dashboard enseignant en cours.
- Navigation, écrans, ViewModels, entités Room, modules Hilt : **à faire** (voir roadmap et missions).
- **Toutes les décisions d'architecture initialement en attente sont désormais tranchées** (ADR-004 à ADR-010, voir `06-architecture-technique.md`) : synchronisation par fichier, `minSdkVersion` 28, contenu hybride, TTS avec téléchargement ponctuel, contenu MVP large, application unique, distribution locale.
- Risque résiduel à surveiller en priorité : **R-07** (couverture large mais superficielle du contenu, voir `08-registre-des-risques.md` et `09-cartographie-contenu-pedagogique.md`).
