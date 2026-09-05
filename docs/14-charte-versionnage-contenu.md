# Charte de versionnage — Liteschreib IKII

Ce document définit les schémas de version utilisés pour l'application, le contenu pédagogique, la base de données Room et le format d'échange de la synchronisation locale. Il découle directement des décisions ADR-004 (synchronisation), ADR-008 (portée du contenu) et ADR-010 (distribution sans store).

## 1. Version de l'application

Schéma **SemVer** (`MAJOR.MINOR.PATCH`), ex. `1.2.0` :
- **MAJOR** : changement de schéma Room non rétrocompatible, ou changement structurel majeur (ex. passage au Cycle 2 avec fonctionnalités IA)
- **MINOR** : ajout de fonctionnalité (nouvel écran, nouvelle mission validée) sans rupture de compatibilité
- **PATCH** : correctif de bug, ajustement mineur

Le numéro de version est affiché dans l'écran Profil (ou équivalent) pour faciliter le support lors du pilote.

## 2. Version du contenu pédagogique

Indépendante de la version de l'application, car le contenu (unités, extraits, exercices) évolue à un rythme différent (voir cartographie de contenu, `09-cartographie-contenu-pedagogique.md`).

Format : `CONTENU-vX.Y` où :
- **X** s'incrémente à chaque ajout d'un niveau GeR complet ou changement structurel de la cartographie
- **Y** s'incrémente à chaque ajout/modification d'unités sans changement structurel

Chaque unité de contenu (`UniteApprentissage`) porte elle-même un champ `versionUnite` (à ajouter au schéma Room si nécessaire) permettant de savoir depuis quelle version de contenu elle est disponible — utile pour distinguer, lors du pilote, ce qui a pu changer entre deux appareils désynchronisés (cf. R-15, R-03).

## 3. Version du schéma Room

Suit le mécanisme natif Room (`@Database(version = n)`), avec une migration explicite testée à chaque incrément (voir NFR-22 et `11-schema-donnees-room.md`, section 4). Le tableau de correspondance ci-dessous doit être tenu à jour :

| Version Room | Application associée (MINOR) | Changement principal |
|---|---|---|
| 1 | 1.0.0 | Schéma initial (Mission A4) |
| 4 | 1.x.x | Migration 3→4 (isValidated, ADR-015) |
| 5 | 1.x.x | Migration 4→5 (Assignation, statut Soumis, SyncLogDao, ADR-017) |

## 4. Version du format d'échange (synchronisation locale, ADR-004)

Le fichier transféré entre appareils élève/enseignant (`SyncLog.versionFichierEchange`, voir `11-schema-donnees-room.md`) porte un numéro de version propre, car deux appareils avec des versions d'application différentes doivent pouvoir se synchroniser sans perte de données pendant la période de transition d'un pilote.

Règle : le format d'échange privilégie la **rétrocompatibilité en lecture** — un appareil en version `N` doit pouvoir lire un fichier produit par un appareil en version `N-1`. Toute rupture de compatibilité doit être un événement rare et documenté ici.

| Version format d'échange | Application minimale requise | Changement |
|---|---|---|
| 1 | 1.0.0 | Format initial — export élève, **import fonctionnel côté enseignant depuis Sprint 4** (fusion par timestamp au niveau enregistrement, ADR-018) | Correctifs B-22, ADR-018 |

## 5. Processus de mise à jour manuelle (sans store, ADR-010)

Puisque l'application est distribuée par APK partagé localement, il n'existe pas de mécanisme de mise à jour automatique. Le processus de mise à jour suit ces étapes :

1. Le nouveau build (application et/ou package de contenu) est préparé et testé (voir checklist quotidienne et Definition of Done).
2. Le changelog est rédigé (section 6).
3. Le fichier est transféré à l'enseignant référent (via le même canal que la distribution initiale, ADR-010).
4. L'enseignant redistribue aux élèves lors d'une séance dédiée, en réutilisant la procédure du guide enseignant (`15-guide-enseignant-onboarding.md`).
5. La progression et les productions écrites existantes des élèves doivent être préservées lors de la mise à jour (non-régression testée avant diffusion, voir migrations Room).

## 6. Changelog (journal des versions)

| Version app | Date | Contenu | Notes |
|---|---|---|---|
| 1.0.0 | *à compléter* | Socle initial (Sprint 0 à Sprint 10) | Première version distribuée au pilote |
| *à compléter* | *à compléter* | *à compléter* | *à compléter* |

## 7. Lien avec le reste du projet

- Référencé par ADR-004 (format d'échange) et ADR-010 (distribution manuelle) dans `06-architecture-technique.md`.
- Requis comme critère de Definition of Done pour la Mission C3 (synchronisation) et la Mission D0 (préparation de la distribution) dans `04-missions-et-sprints.md`.
