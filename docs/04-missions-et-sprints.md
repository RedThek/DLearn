# Missions à réaliser et valider — Liteschreib IKII

> **Lien avec le suivi opérationnel en direct** : ce document est le **backlog** (quoi faire, dans quel ordre, avec quels critères). Dès qu'une mission démarre réellement, elle est instanciée en une **fiche vivante** dans `docs/missions/<ID>-<slug>.md` (dupliquée depuis `docs/processus/gabarit-cycle-iteration.md`), suivie phase par phase. Voir `docs/processus/guide-orchestration.md` pour le fonctionnement complet, et `docs/ETAT_ACTUEL.md` pour savoir quelle mission est active en ce moment.

Chaque mission correspond à une unité de travail assignable à un sprint. Une mission n'est **validée** que si tous les critères de sa colonne « Definition of Done » sont satisfaits — cocher au fur et à mesure dans le tracker de projet (GitHub Projects/Issues recommandé) **et** dans la fiche vivante correspondante.

## Légende des statuts
`À faire` · `En cours` · `En revue` · `Validé`

---

## Bloc A — Fondations techniques

### Mission A0 — Remplissage et validation de la cartographie de contenu pédagogique
- **Sprint** : Sprint 0 à 2 (en parallèle des missions techniques A1-A3, doit être validée avant A4)
- **Description** : Établir la correspondance niveau scolaire (6ème à Terminale) ↔ niveau GeR ↔ curriculum *Ihr und Wir Plus*, et rédiger/valider un premier lot d'unités de contenu dans `09-cartographie-contenu-pedagogique.md`, pour lever le blocage identifié sur la Mission A4.
- **Definition of Done** :
  - [ ] Correspondance niveau scolaire ↔ niveau GeR proposée et validée par toi et/ou l'encadrant académique (section 2 de `09-cartographie-contenu-pedagogique.md`)
  - [x] Portée MVP tranchée : collège complet (6ème, 5ème, 4ème, 3ème) ; lycée (2nde à Terminale) reporté à un développement ultérieur
  - [ ] Références aux chapitres *Ihr und Wir Plus* complétées pour les 4 niveaux du MVP (actuellement en attente, je n'ai pas accès au manuel)
  - [ ] Les 4 unités du MVP (U-6E-01, U-5E-01, U-4E-01, U-3E-01) relues par un locuteur natif ou l'encadrant académique et passées au statut `Validé`
  - [ ] Seuil minimal d'unités par niveau retenu au MVP atteint (5 unités par niveau, 20 au total — 4/20 rédigées à ce stade)
  - [ ] Statut des droits (ADR-006) documenté pour chaque unité

### Mission A1 — Finaliser le design system
- **Sprint** : Sprint 0
- **Description** : Remplacer tous les `TODO` de `Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt` par les valeurs exactes (ADR-014).
- **Definition of Done** :
  - [x] Aucun `TODO` restant dans les fichiers du design system
  - [x] Screenshot validé sur device/émulateur, archivé dans `docs/screenshots/A1/` (ADR-014)
  - [x] Contraste vérifié par test AccessibilityChecks
  - [x] Commit dédié avec message clair (`feat(design-system): tokens canonisés`)
- **Statut** : `Validation`

### Mission A2 — Structure de packages Clean Architecture
- **Sprint** : Sprint 0
- **Description** : Créer l'arborescence `domain/`, `data/`, `presentation/` (voir `06-architecture-technique.md`) avec modules Gradle ou packages selon la granularité retenue.
- **Definition of Done** :
  - [x] Arborescence créée et documentée dans `06-architecture-technique.md`
  - [x] Règle de dépendance vérifiée (aucun import `presentation` dans `domain`)
  - [x] Build Gradle propre (`./gradlew build` sans erreur)
- **Statut** : `Validé`

### Mission A3 — Navigation Compose
- **Sprint** : Sprint 1
- **Description** : Implémenter la navigation à 5 onglets + route dashboard enseignant, avec un écran de sélection de rôle/profil en point d'entrée (ADR-009, FR-33).
- **Definition of Done** :
  - [x] Écran de sélection de profil (Élève/Enseignant) fonctionnel si plusieurs profils existent
  - [x] `NavHost` fonctionnel avec les 5 routes élève + route enseignant
  - [x] `BottomNavigationBar` fonctionnelle
  - [x] `NavViewModel` câblé dans `NavGraph.kt`
  - [x] Navigation testée manuellement
  - [x] Test d'instrumentation basique de navigation (NavigationTest.kt)
- **Statut** : `Validé`

### Mission A4 — Entités Room & pré-population
- **Sprint** : Sprint 2 (infrastructure) — contenu validé toujours dépendant de la Mission A0
- **Prérequis** : Mission A0 validée (cartographie de contenu remplie et unités du MVP validées) —
  **débloquée techniquement par ADR-015** (seed brouillon + `isValidated`), sans que cela ne substitue à la
  validation humaine du contenu
- **Description** : Modéliser les entités (Profil, Unité, Exercice, ProductionEcrite, Progression) et la stratégie de seed depuis les assets, en couvrant plusieurs niveaux GeR dès le MVP (ADR-008).
- **Definition of Done — Infrastructure technique (close)** :
  - [x] Entités et DAO créés, migrations initiales définies (AppDatabase v5, ADR-015/017)
  - [x] Stratégie de pré-population validée (contenu disponible dès le premier lancement, NFR-03, ADR-015)
  - [x] Tests unitaires DAO passants
  - [x] Tests de migration Room 3→4 et 4→5 passants
- **Definition of Done — Contenu pédagogique (reste ouvert, suivi sous Mission A0)** :
  - [ ] Cartographie de contenu (`09-cartographie-contenu-pedagogique.md`) renseignée et validée pour tous les niveaux GeR couverts, avec `statutDroits` documenté (ADR-006)
  - [ ] Seuil minimal d'unités par niveau respecté (voir registre des risques, R-07 mis à jour à 5 unités)
- **Statut** : `Validé (infrastructure)` — `En cours (contenu, voir Mission A0)`

### Mission A5 — Modules Hilt
- **Sprint** : Sprint 2 (clôturé Sprint 3)
- **Description** : Définir les modules d'injection de dépendances (App, Data, Domain) et les scopes appropriés.
- **Definition of Done** :
  - [x] Hilt entièrement câblé, DataStore session, tous les bindings repository/use case
  - [x] Aucune instanciation manuelle de repository/use case dans les ViewModels
  - [x] Build + tests passants après intégration Hilt
  - [x] Documentation des scopes dans `06-architecture-technique.md`
- **Statut** : `Validé`

---

## Bloc B — Écrans fonctionnels (élève)

### Mission B1 — Écran Accueil
- **Sprint** : Sprint 4
- **Definition of Done** :
  - [ ] FR-05, FR-06 implémentés et testés manuellement
  - [ ] ViewModel couvert par tests unitaires
  - [ ] Revue de fidélité Figma effectuée

### Mission B2 — Écran Apprentissage
- **Sprint** : Sprint 5
- **Definition of Done** :
  - [x] Module Exercice complet (QCM/texte à trous/vrai-faux) implémenté
  - [ ] FR-09 à FR-11 implémentés
  - [x] FR-12 implémenté : correction offline vérifiée (mode avion)
  - [ ] FR-13, FR-14 implémentés
  - [ ] FR-32 implémenté : écran/dialogue de vérification de la voix TTS allemande au premier accès, avec proposition de téléchargement si connexion disponible (ADR-007)
  - [ ] Intégration TTS fonctionnelle et strictement hors ligne une fois la voix installée
  - [ ] Tests UI Compose sur le parcours de lecture, incluant le scénario « voix non installée » et « voix installée »

### Mission B3 — Écran Écriture
- **Sprint** : Sprint 6
- **Definition of Done** :
  - [ ] FR-15 à FR-17 implémentés
  - [x] Sauvegarde automatique vérifiée (perte de données testée : rotation d'écran, mise en arrière-plan)
  - [x] Grille d'auto-évaluation fonctionnelle

### Mission B4 — Écran Suivi
- **Sprint** : Sprint 7
- **Definition of Done** :
  - [x] FR-20 à FR-21 implémentés avec données réelles (B-28)
  - [ ] FR-22 implémenté (FSRS)
  - [ ] Cohérence des données vérifiée avec le module Accueil et Apprentissage
  - [ ] Algorithme de planification des révisions testé unitairement

---

## Bloc C — Enseignant & synchronisation

### Mission C1 — Dashboard enseignant (mockup)
- **Sprint** : Supprimée (ADR-014)
- **Statut** : `Validé`

### Mission C2 — Dashboard enseignant (implémentation)
- **Sprint** : Sprint 3 (ex-Sprint 8)
- **Definition of Done** :
  - [x] Vue liste classes/élèves fonctionnelle
  - [x] Création de compte élève par l'enseignant (identifiant/mot de passe) fonctionnelle
  - [x] FR-24 à FR-27 implémentés (Assigner/Corrections réels)
  - [x] Tests UI de base (ADR-014)
- **Statut** : `Validation`

### Mission C3 — Synchronisation locale (BYOD)
- **Sprint** : Sprint 3 (groundwork)
- **Prérequis** : ADR-004 tranché — export/import de fichier via partage système
- **Definition of Done** :
  - [x] Groundwork export JSON + SyncLogDao
  - [x] FR-29 à FR-31 implémentés partiellement via export JSON + Partage Android
  - [ ] Test bout en bout entre deux appareils physiques (Android 9.0+) sans réseau internet, **dans les deux sens** (format v2, ADR-027), sur chacun des canaux de repli
  - [x] Gestion des conflits documentée (ADR-018 pour v1, remplacé par ADR-027 pour v2)
  - [x] Format de fichier d'échange versionné conforme à `14-charte-versionnage-contenu.md`
- **Statut** : `En cours`

> C3-T10 et C3-T11 sont redirigées vers le format v2 (Mission F1b). Le format v1 n'est plus accepté à l'import (rupture documentée dans 14-charte-versionnage-contenu.md).

---

## Bloc D — Qualité & durcissement

### Mission D0 — Préparation de la distribution locale (APK)
- **Sprint** : avant l'évaluation pilote (fin Cycle 1)
- **Description** : Préparer le build de release et le processus de distribution par transfert local (ADR-010).
- **Definition of Done** :
  - [ ] Build de release signé généré, taille vérifiée (NFR-07)
  - [ ] Procédure d'installation via « sources inconnues » testée sur au moins deux appareils Android 9.0+
  - [ ] Guide enseignant (`15-guide-enseignant-onboarding.md`) finalisé et inclus dans le package de distribution
  - [ ] Schéma de version de l'application et du contenu appliqué (`14-charte-versionnage-contenu.md`)
  - [ ] Build de release **sans compte de démonstration** (NFR-33, R-28) ; installation à froid vérifiée
  - [ ] Missions F1a et F1b terminées (identité globale, échange v2) ou dérogation documentée

### Mission D1 — Couverture de tests
- **Sprint** : Sprint 10
- **Definition of Done** :
  - [ ] Couverture domaine ≥ 70 % (NFR-20)
  - [ ] Tests instrumentés sur Apprentissage et Écriture (NFR-21)
  - [ ] Pipeline CI GitHub Actions vert

### Mission D2 — Audit accessibilité et offline
- **Sprint** : Sprint 10
- **Definition of Done** :
  - [ ] Contraste vérifié sur tous les écrans (Stark)
  - [ ] Test complet en mode avion sur toutes les fonctionnalités du socle
  - [ ] Aucune dépendance réseau résiduelle détectée

### Mission D3 — Évaluation pilote (fin Cycle 1)
- **Sprint** : Évaluation Cycle 1
- **Prérequis** : Mission D0 validée (distribution) et protocole éthique/consentement (`10-protocole-ethique-consentement.md`) réuni
- **Definition of Done** :
  - [ ] APK distribué localement à l'échantillon d'élèves/enseignants (Yaoundé), voix TTS allemande installée en contexte connecté avant usage terrain (ADR-007)
  - [ ] Données collectées via Google Forms hors ligne, conformément au plan de gestion des données de recherche (`13-plan-gestion-donnees-recherche.md`)
  - [ ] Synthèse rédigée pour la revue de mi-parcours (mois 6)

---

## Bloc E — Phase 3 (Cycle DBR 2, IA différée)

### Mission E1 — Interfaces de domaine anticipées
- **Sprint** : avant Sprint 11
- **Definition of Done** :
  - [ ] Interfaces type `PronunciationEvaluator`, `WritingAssistant` définies dans `domain`, sans implémentation IA
  - [ ] Aucune régression sur le socle Cycle 1

### Mission E2 — Intégration TFLite / Gemini Nano
- **Sprint** : Sprint 11-12
- **Definition of Done** :
  - [ ] Modèle embarqué fonctionnel on-device (aucun appel cloud)
  - [ ] Fallback gracieux si device incompatible
  - [ ] Tests de non-régression du socle passants

### Mission E3 — Documentation académique finale
- **Sprint** : en continu, finalisé avant soutenance
- **Definition of Done** :
  - [ ] Chapitres de revue de littérature rédigés (IA/NLP en apprentissage des langues, didactique numérique du DaF, études empiriques MALL)
  - [ ] Traçabilité DBR complète (journal de bord, décisions, résultats d'évaluation) consolidée

---

## Bloc F — Extension littéraire (horizons H1/H2, voir `18-vision-produit-et-horizons.md`)

> Toutes les missions de ce bloc sont `À faire`. Aucune n'est planifiée dans un sprint tant que F1 n'est pas tranchée, sauf F0 (documentaire, déjà réalisée par le présent lot).

### Mission F0 — Gel de périmètre et gouvernance de la vision
- **Sprint** : Sprint 5 (parallèle, documentaire)
- **Description** : classer la proposition en horizons, formaliser les décisions sans impact code.
- **Definition of Done** :
  - [ ] ADR-020 à ADR-025 intégrés à `06-architecture-technique.md`
  - [ ] `18-…` et `19-…` ajoutés ; `README.md` et `ETAT_ACTUEL.md` mis à jour
  - [ ] Risques R-22 à R-27 inscrits ; FR-35 à FR-46 et NFR-30 à NFR-32 inscrits
- **Statut** : `À faire`

### Mission F1 — Décisions structurantes d'identité, de synchronisation et de packs
- **Prérequis** : F0
- **Description** : trancher ADR-026 (identité globale), ADR-027 (format d'échange v2 par bundles), ADR-028 (packs de contenu, dictionnaire dans une base séparée) et rédiger la spécification `20-specification-formats-echange-et-packs.md`. Mission documentaire ; l'implémentation est répartie en F1a, F1b et F1c.
- **Definition of Done** :
  - [ ] ADR-026, ADR-027, ADR-028 intégrés à `06-architecture-technique.md`
  - [ ] `20-…` ajouté ; `11-…` et `14-…` mis à jour ; R-26 à R-30 inscrits
- **Statut** : `À faire` (documentation rédigée, en attente d'intégration)

### Mission F1a — Identité globale et exclusion du seed de démonstration
- **Exigences** : NFR-33, NFR-35 · **ADR** : ADR-026
- **Sprint** : à planifier ; **avant le pilote (D0) et avant tout usage réel de l'import v2** ; peut être groupée avec la migration 5→6 si celle-ci n'est pas encore fusionnée
- **Description** : ajouter `uid` (index unique) et l'index unique sur `identifiant`, `instanceId` en DataStore, conversion des assignations existantes, seed de démonstration réservé au build de débogage.
- **Definition of Done** :
  - [ ] Migration explicite testée (`MigrationTest`, ADR-017)
  - [ ] Création de compte robuste aux collisions d'`identifiant`
  - [ ] Build de release sans compte de démonstration (vérification à froid)
- **Statut** : `À faire`

### Mission F1b — Échange v2 : bundles, `rev`, import atomique, hash étiqueté
- **Exigences** : FR-29, FR-47, NFR-34 · **ADR** : ADR-027 · **Prérequis** : F1a
- **Description** : bundles `STUDENT_REPORT`, `PROVISION`, `FEEDBACK`, `CLASS_PACKET` conformes à `20-…`, import atomique avec résumé, détection de rejeu, `rev` sur `progression` et `production_ecrite`, hash PBKDF2 étiqueté avec rehash à la connexion.
- **Definition of Done** :
  - [ ] Vecteur de test de `20-…` reproduit par un test unitaire
  - [ ] Tests d'un fichier tronqué, d'un rejeu et d'un import interrompu
  - [ ] Test à deux appareils physiques dans les deux sens (C3-T11)
  - [ ] `14-…` mis à jour (format v2, rupture avec v1)
- **Statut** : `À faire`

### Mission F1c — Packs de contenu : importeur unique et upsert
- **Exigences** : FR-31 · **ADR** : ADR-028 · **Prérequis** : F1a
- **Description** : le seed devient le pack `core` ; importeur unique ; upsert transactionnel avec `revision` et `retire` ; table `pack_installe`.
- **Definition of Done** :
  - [ ] Une correction de contenu se propage sans perte de progression
  - [ ] Installation idempotente d'un même pack
  - [ ] Migration explicite testée (ADR-017)
- **Statut** : `À faire`

### Mission F2 — Atelier d'écriture guidé
- **Sprint** : Sprint 6–7 (à confirmer, cohérent avec Mission B3)
- **Exigences** : FR-35, FR-36, FR-37
- **Prérequis** : F0 ; gabarit `16-…` étendu (fiche-méthode, défi d'écriture)
- **Definition of Done** :
  - [ ] Fiches-méthode, structuration idée–argument–exemple et défis courts implémentés
  - [ ] Contenu relu par un locuteur natif ou l'encadrant (Mission A0)
  - [ ] Tests UI et vérification hors ligne
- **Statut** : `À faire`

### Mission F3 — Boucle de feedback et publication de classe
- **Exigences** : FR-38, FR-39, FR-40
- **Prérequis** : F1b, Mission C3 complète
- **Definition of Done** :
  - [ ] Commentaires enseignant renvoyés à l'élève
  - [ ] Historique de versions et recueil PDF de classe (consentement de l'élève)
  - [ ] Test à deux appareils physiques
- **Statut** : `À faire`

### Mission F4 — Gamification locale
- **Exigences** : FR-41 · **Horizon** : H2 · **Prérequis** : ADR-023
- **Statut** : `À faire`

### Mission F5 — Dictionnaire hors ligne
- **Exigences** : FR-42 · **Horizon** : H2 · **Prérequis** : F1c, ADR-025, audit des licences
- **Statut** : `À faire`

### Mission F6 — Aides à l'écriture à règles
- **Exigences** : FR-43 · **Horizon** : H2 · **Prérequis** : Mission E1 (ports), ADR-024, ADR-025
- **Statut** : `À faire`

### Mission F7 — Club de classe et concours de classe
- **Exigences** : FR-44, FR-45 · **Horizon** : H2 · **Prérequis** : F3, contenu (A0), ADR-025
- **Statut** : `À faire`

### Mission F8 — « DLearn Hub » (conception uniquement)
- **Exigences** : FR-46 · **Horizon** : H3 (après la soutenance) · **Prérequis** : ADR supersédant ADR-002, protocole éthique refait
- **Statut** : `Hors périmètre de la thèse`
