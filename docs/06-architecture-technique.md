# Architecture technique — Liteschreib IKII

## 1. Vue d'ensemble — Clean Architecture + MVVM

```
┌─────────────────────────────────────────────┐
│                presentation/                 │
│  Composables (UI) · ViewModel · UiState      │
│  → dépend de domain uniquement                │
└──────────────────────┬────────────────────────┘
                        │ appelle
┌──────────────────────▼────────────────────────┐
│                   domain/                      │
│  Entités métier · UseCases · Interfaces        │
│  (Repository, PronunciationEvaluator...)       │
│  → aucune dépendance Android/Compose/Room      │
└──────────────────────▲────────────────────────┘
                        │ implémente les interfaces
┌──────────────────────┴────────────────────────┐
│                    data/                       │
│  Repository (impl) · Room (Entity/DAO)         │
│  · DataSources (assets, fichiers locaux)       │
│  · (Cycle 2) DataSources IA : TFLite/Gemini    │
└─────────────────────────────────────────────────┘
```

### Règle de dépendance
- `domain` ne connaît **aucun** framework Android, Compose ou Room. Uniquement Kotlin pur.
- `presentation` dépend de `domain` (UseCases, modèles), jamais directement de `data`.
- `data` implémente les interfaces définies dans `domain` (principe d'inversion de dépendance).
- Toute violation de cette règle est un motif de refus en revue de code (voir NFR-16).

## 2. Structure de packages — état réel (mise à jour Mission A2)

> Nom de package racine canonique : `edu.project.dlearn`
> Toute référence à `com.liteschreib.ikii` ou `com.ikii.liteschreib` dans le code est un artefact
> à supprimer (voir tâche A4-T02 dans docs/planification/bloc-A-taches.md).

```
edu.project.dlearn/
├── domain/
│   ├── model/            # Entités métier pures (Vocabulaire, Utilisateur, ProgressionStats...)
│   ├── usecase/          # Cas d'usage (GetFlashcardsUseCase, ValiderReponseExerciceUseCase...)
│   └── repository/       # Interfaces (ApprentissageRepository, AuthRepository...)
│                         # → RÈGLE : aucun import android.*, androidx.*, compose.* autorisé ici
├── data/
│   ├── local/
│   │   ├── room/         # Entités Room, DAO, AppDatabase — [migré Mission A2]
│   │   └── datasource/   # Pré-population assets → Room (placeholder Mission A4)
│   ├── repository/       # Implémentations des interfaces domain
│   └── ai/               # RÉSERVÉ Cycle DBR 2 — TFLite/Gemini Nano (ADR-003, Mission E2)
│                         # → RÈGLE : aucun import depuis ce package avant fin Cycle 1
├── presentation/
│   ├── navigation/       # NavGraph.kt, MainScreen.kt, BottomNavItem.kt
│   ├── accueil/          # AccueilScreen, AccueilViewModel, AccueilUiState
│   ├── apprentissage/    # ApprentissageScreen, ApprentissageViewModel, ApprentissageUiState
│   ├── connexion/        # ConnexionScreen, ConnexionViewModel, ConnexionUiState
│   ├── ecriture/         # EcritureScreen (placeholder Mission B3)
│   ├── enseignant/       # RÉSERVÉ Mission C2 — Dashboard enseignant
│   ├── positionnement/   # PositionnementScreen, PositionnementViewModel, PositionnementUiState
│   ├── profil/           # ProfilScreen, ProfilViewModel, ProfilUiState
│   ├── suivi/            # SuiviScreen, SuiviViewModel
│   └── theme/            # Color.kt, Type.kt, Shape.kt, Theme.kt (LiteschreibTheme)
└── core/
    ├── di/               # AppModule.kt (DatabaseModule + RepositoryModule)
    └── components/       # Composables partagés (InitialsAvatar)
```


## 3. Git workflow

> Le suivi opérationnel de sprint (tableau Kanban) est assuré par **GitHub Projects**, lié à ce dépôt — voir **ADR-013**. Le dépôt étant public, aucune donnée réelle sensible n'y est jamais committée (voir risque R-17).

### Branches
- `main` : version stable, toujours déployable/démontrable (soutenance, revue académique)
- `develop` : intégration continue des sprints
- `feature/<mission-id>-<description-courte>` : ex. `feature/A3-navigation-compose`
- `fix/<description-courte>` : correctifs ponctuels

### Convention de commit (Conventional Commits)
```
feat(module): description courte
fix(module): description courte
test(module): description courte
docs: description courte
refactor(module): description courte
```

### Cycle de Pull Request
1. Branche `feature/*` créée depuis `develop`
2. Développement + tests locaux
3. Push + Pull Request vers `develop`, liée à la mission concernée
4. CI GitHub Actions verte obligatoire
5. Revue (auto-revue documentée si travail solo) puis merge
6. `develop` fusionné vers `main` aux jalons de sprint validés

## 4. Definition of Ready (avant de démarrer une mission)

- [ ] La mission est décrite dans `04-missions-et-sprints.md` avec sa Definition of Done
- [ ] Les dépendances techniques (missions bloquantes) sont validées
- [ ] La description UX structurée (ADR-014) est rédigée dans la fiche de mission (si écran)

## 5. Definition of Done (rappel général, voir aussi missions individuelles)

- [ ] Code conforme à la règle de dépendance Clean Architecture
- [ ] Tests unitaires/instrumentés associés passants
- [ ] Build + lint sans erreur
- [ ] Screenshot validé sur device/émulateur, archivé dans `docs/screenshots/<ID-mission>/`
- [ ] Documentation technique mise à jour si nécessaire
- [ ] Aucune régression offline-first introduite

## 6. Registre des décisions d'architecture (ADR)

Format utilisé : voir gabarit en section 7. Chaque décision structurante est numérotée et conservée même si superseded (traçabilité académique).

| ADR | Titre | Statut |
|---|---|---|
| ADR-001 | Adoption de Clean Architecture + MVVM | Accepted |
| ADR-002 | Offline-first strict : aucun SDK cloud/analytics | Accepted |
| ADR-003 | Report des fonctionnalités IA/NLP en Cycle DBR 2 | Accepted |
| ADR-004 | Mécanisme de synchronisation locale enseignant-élève (BYOD) | **Accepted** — export/import de fichier via partage système |
| ADR-005 | `minSdkVersion` cible selon parc Android local | **Accepted** — API 28 (Android 9.0+) |
| ADR-006 | Statut des droits du contenu littéraire | **Accepted** — approche hybride |
| ADR-007 | Lecture audio (TTS) — téléchargement ponctuel de la voix | **Accepted** — TTS système + téléchargement unique |
| ADR-008 | Portée du contenu MVP (niveaux GeR) | **Accepted** — couverture large, plusieurs niveaux |
| ADR-009 | Application unique avec sélecteur de profil | **Accepted** |
| ADR-010 | Distribution de l'application | **Accepted** — APK partagé localement |
| ADR-011 | Clavier allemand dans l'éditeur d'écriture | **Accepted** — clavier virtuel dédié |
| ADR-012 | Devices de référence pour les tests de performance | **Accepted** — Tecno et Itel (et sous-marques) |
| ADR-013 | Outil de suivi de sprint | **Accepted** — GitHub Projects (dépôt public) |
| ADR-014 | Abandon du workflow Figma — génération UI par agent de codage | Accepted |
| ADR-015 | Stratégie de seed de développement (déblocage A4) | **Accepted** |

### ADR-001 : Adoption de Clean Architecture + MVVM
**Statut :** Accepted
**Contexte :** Le projet doit intégrer des fonctionnalités IA en Phase 3 sans refactoring majeur, et doit rester testable et maintenable sur 12 mois avec un développeur unique (contexte mémoire de Master).
**Décision :** Structurer le code en trois couches (domain, data, presentation) avec inversion de dépendance, et MVVM côté présentation avec Jetpack Compose.
**Conséquences :** Meilleure testabilité et découplage IA ; coût initial de mise en place plus élevé que d'une architecture monolithique.

### ADR-002 : Offline-first strict
**Statut :** Accepted
**Contexte :** Public cible en zone à connectivité limitée/coûteuse (Yaoundé) ; exigence de confidentialité des données scolaires.
**Décision :** Aucune fonctionnalité du socle ne dépend du réseau ; aucun SDK d'analytics ou de crash-reporting cloud n'est intégré ; contenu pré-chargé en base.
**Conséquences :** Simplifie la conformité vie privée ; complexifie la synchronisation enseignant-élève (voir ADR-004) et exclut certains outils de monitoring standards.

### ADR-003 : Report des fonctionnalités IA/NLP
**Statut :** Accepted
**Contexte :** Coupler le risque technique IA (modèles embarqués, disponibilité Gemini Nano selon device) à l'architecture dès le départ est un anti-pattern identifié pour ce projet.
**Décision :** Le socle pédagogique (Cycle DBR 1) est développé et évalué sans dépendance IA. Les interfaces de domaine anticipent les futurs ports IA (ex. `PronunciationEvaluator`) sans implémentation avant Cycle DBR 2.
**Conséquences :** Réduction du risque de blocage technique en début de projet ; nécessite une discipline pour ne pas coupler prématurément domain/data aux futures dépendances IA.

### ADR-004 : Mécanisme de synchronisation locale enseignant-élève
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** FR-29 exige un transfert de données enseignant-élève sans internet, sur un parc d'appareils BYOD hétérogène.
**Options considérées :** QR code (limité en volume), export/import de fichier via les mécanismes de partage natifs Android, Wi-Fi Direct/Bluetooth géré en propre par l'application, copie manuelle de fichier sans automatisation.
**Décision :** Export/import de fichier en s'appuyant sur les mécanismes de partage natifs de l'appareil (Nearby Share si disponible, sinon Bluetooth classique, transfert par câble, ou carte SD en repli). Le fichier échangé est un export structuré (voir `SyncLog` et format d'échange dans `11-schema-donnees-room.md` et `14-charte-versionnage-contenu.md`).
**Conséquences :**
- Pas de limite pratique de taille de transfert (contrairement au QR code) ; complexité d'implémentation raisonnable pour un développeur seul.
- Dépendance résiduelle à Google Play Services pour Nearby Share sur certains appareils bas de gamme — un repli Bluetooth classique/fichier manuel doit être prévu et testé (voir risque R-15).
- Nécessite un format de fichier d'échange versionné et documenté (voir Mission C3).

### ADR-005 : `minSdkVersion` cible
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** NFR-06 nécessitait une donnée terrain sur le parc Android réel des élèves/enseignants ciblés (BYOD, zone de Yaoundé).
**Décision :** `minSdkVersion = 28` (Android 9.0), sur la base d'un constat terrain direct indiquant une large diffusion de cette version et des suivantes dans la zone pilote.
**Conséquences :**
- Réduction significative de la charge de compatibilité et du code de repli par rapport à une cible plus ancienne (API 21/24).
- Accès à des API modernes stables (WorkManager, Compose sans limitation majeure).
- Exclut les appareils antérieurs à Android 9 — à surveiller lors du recrutement des participants au pilote (risque résiduel mineur, à consigner si un cas se présente).

### ADR-006 : Statut des droits du contenu littéraire
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** Risque R-08 — statut légal incertain des textes pouvant servir de support ; risque de blocage de la Mission A4 (entités Room et pré-population) si la question n'est pas tranchée.
**Options considérées :** domaine public exclusivement, extraits *Ihr und Wir Plus* sous autorisation éditeur, textes originaux rédigés/adaptés pour le projet, approche hybride combinant plusieurs sources.
**Décision :** Approche hybride — le contenu du Cycle 1 s'appuie sur des textes du domaine public et des textes originaux rédigés/adaptés pour le projet, sans attendre d'autorisation externe. Une démarche de demande d'autorisation auprès de l'éditeur d'*Ihr und Wir Plus* est engagée en parallèle, **sans dépendance calendaire** sur les sprints en cours.
**Conséquences :**
- Démarrage immédiat de la Mission A4 possible, sans blocage juridique.
- Le champ `statutDroits` (voir `11-schema-donnees-room.md`) doit être renseigné et suivi unité par unité dans la cartographie de contenu (`09-cartographie-contenu-pedagogique.md`).
- Si l'autorisation éditeur est obtenue ultérieurement, ce contenu pourra être intégré en Cycle 2 sans remettre en cause le socle déjà construit.

### ADR-007 : Lecture audio (TTS) — téléchargement ponctuel de la voix
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** Risque R-01 (le plus critique du registre) — FR-11 (lecture audio) risquait d'entrer en conflit avec NFR-01 (offline strict) si aucune voix allemande n'est préinstallée sur les appareils cibles.
**Options considérées :** pari sur une voix système déjà présente, téléchargement ponctuel de la voix avant usage offline, moteur TTS tiers embarqué fonctionnant nativement offline (ex. eSpeak-NG), audio pré-enregistré pour chaque extrait.
**Décision :** Utilisation du moteur Android TTS système, avec une vérification explicite de la présence de la voix allemande et, si absente, une procédure de configuration initiale proposant son téléchargement lors d'une connexion disponible (typiquement au sein de l'établissement scolaire), **avant** tout usage hors ligne du module Apprentissage.
**Conséquences :**
- Développement plus léger qu'un moteur tiers ou qu'une production audio pré-enregistrée complète.
- Introduit une **exception documentée** à l'offline-first strict (NFR-01), limitée exclusivement à cette opération unique d'installation de voix — à ne jamais étendre à d'autres fonctionnalités.
- Nécessite une UX dédiée de vérification/installation au premier accès au module Apprentissage (nouvelle exigence FR-32) et une communication claire dans le guide enseignant (`15-guide-enseignant-onboarding.md`) pour que cette étape soit réalisée en contexte connecté (école) avant le déploiement terrain.

### ADR-008 : Portée du contenu MVP (niveaux GeR)
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** Arbitrage entre profondeur (un seul niveau GeR couvert en détail) et largeur (plusieurs niveaux couverts plus superficiellement) pour le contenu du Cycle DBR 1.
**Options considérées :** un seul niveau en profondeur, plusieurs niveaux superficiellement, une thématique commune déclinée sur plusieurs niveaux.
**Décision :** Le MVP couvrira **plusieurs niveaux GeR** (ex. A1 à B1, à préciser dans la cartographie de contenu), avec une profondeur de contenu par niveau volontairement plus légère qu'une couverture mono-niveau.
**Conséquences :**
- Permet de démontrer la couverture du curriculum dès la revue de mi-parcours (mois 6) et en soutenance.
- **Élève la criticité du risque R-07** (contenu insuffisant par niveau pour une évaluation fiable) — le registre des risques est mis à jour en conséquence, avec un seuil minimal d'unités par niveau à respecter (voir `08-registre-des-risques.md` et `09-cartographie-contenu-pedagogique.md`).
- Impose une vigilance accrue sur la charge de production de contenu, potentiellement à répartir sur plusieurs sprints plutôt que concentrée sur le seul Sprint 2.

### ADR-009 : Application unique avec sélecteur de profil
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** Arbitrage entre une application unique avec sélecteur de rôle (Élève/Enseignant) et deux builds/flavors distincts.
**Décision :** Un seul APK, avec un sélecteur de profil/rôle au lancement et une bascule possible entre profils sur un même appareil.
**Conséquences :**
- Maintenance et distribution simplifiées pour un développeur unique — cohérent avec ADR-010 (distribution locale d'un seul APK).
- Nécessite une navigation racine gérant deux graphes de navigation distincts (élève à 5 onglets / enseignant), et un mécanisme de protection d'accès optionnel (`codeAcces`, voir `11-schema-donnees-room.md`) si un appareil est partagé entre plusieurs profils.

### ADR-010 : Distribution de l'application
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** Choix du mode de distribution pour le pilote, en cohérence avec le principe offline-first.
**Options considérées :** Google Play Store, APK partagé localement, distribution encadrée par l'établissement lors d'une séance dédiée.
**Décision :** Distribution par partage direct de l'APK (USB, Bluetooth, carte SD), sans passage par un store applicatif pour la phase pilote.
**Conséquences :**
- Cohérence totale avec l'offline-first ; aucune dépendance à un compte développeur Play Store ni à une connexion pour l'installation.
- Nécessite d'accompagner les utilisateurs pour l'activation des « sources inconnues » sur leur appareil (voir `15-guide-enseignant-onboarding.md` et notice d'information mise à jour dans `10-protocole-ethique-consentement.md`).
- Absence de mécanisme de mise à jour automatique : les mises à jour d'application et de contenu sont distribuées manuellement, selon un schéma de version défini dans `14-charte-versionnage-contenu.md`.

### ADR-011 : Clavier allemand dans l'éditeur d'écriture
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** FR-15 (production écrite en allemand) nécessite un accès fiable aux caractères spéciaux allemands (ä, ö, ü, ß, majuscules associées). Le clavier système par défaut varie selon l'appareil et n'offre pas toujours un accès simple à ces caractères, en particulier sur les claviers OEM allégés fréquents sur les appareils d'entrée de gamme (voir ADR-012).
**Options considérées :** clavier système standard (pari sur la disponibilité des diacritiques par appui long), clavier virtuel dédié intégré à l'éditeur d'écriture.
**Décision :** Un clavier virtuel dédié est ajouté dans l'éditeur d'écriture, affichant ä, ö, ü, ß, Ä, Ö, Ü, insérables en un tap, en complément du clavier système standard.
**Conséquences :**
- Garantit un accès fiable à l'orthographe allemande correcte, indépendamment du clavier système installé sur l'appareil de l'élève — particulièrement pertinent compte tenu des devices de référence retenus (ADR-012).
- Effort de développement supplémentaire dans l'écran Écriture (Mission B3) : une rangée ou barre d'outils de caractères spéciaux au-dessus du clavier système.
- Nouvelle exigence fonctionnelle FR-34 ajoutée en conséquence (`01-exigences-fonctionnelles.md`).

### ADR-012 : Devices de référence pour les tests de performance
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** NFR-04 nécessitait un modèle d'appareil précis pour des mesures de performance fiables, faute de quoi les tests restaient génériques (« ≤ 2 Go RAM »).
**Décision :** Les tests de performance et de compatibilité sont réalisés en priorité sur les appareils **Tecno** et **Itel**, ainsi que leurs sous-marques et gammes associées (Infinix, Camon, séries A2, A8, etc.), identifiées comme les plus répandues dans la zone pilote.
**Conséquences :**
- Les mesures de performance (démarrage, fluidité `LazyColumn`, consommation batterie) doivent être effectuées sur au moins un appareil de chacune de ces familles avant chaque jalon de sprint impliquant une nouvelle fonctionnalité UI lourde.
- Le clavier système et les fonctionnalités TTS de ces appareils (souvent des surcouches Android allégées) doivent être vérifiés spécifiquement — renforce la pertinence d'ADR-011 (clavier virtuel dédié) et d'ADR-007 (vérification explicite de la voix TTS plutôt qu'un pari sur sa présence).
- À défaut de disposer physiquement de tous les modèles, privilégier des émulateurs configurés avec des spécifications proches (RAM, résolution, version Android) de ces familles d'appareils.

### ADR-013 : Outil de suivi de sprint
**Statut :** Accepted
**Date :** tranché lors de l'arbitrage des décisions en attente
**Contexte :** Le suivi de sprint n'était pas formellement outillé (options envisagées : GitHub Projects, Trello, Notion).
**Décision :** **GitHub Projects**, lié au dépôt GitHub du projet, est retenu comme outil de suivi de sprint. Le dépôt est **public**.
**Conséquences :**
- Intégration native avec les Issues, Pull Requests et GitHub Actions déjà utilisés (cohérent avec l'outillage existant, pas de nouvel outil externe à maintenir).
- **Point de vigilance majeur lié au caractère public du dépôt** : aucune donnée réelle sensible (nom d'élève, formulaire de consentement signé, export de données de recherche, capture d'écran contenant des informations personnelles) ne doit jamais être committée dans le dépôt — seuls les gabarits et modèles vides le sont. Voir le risque **R-17** ajouté au registre des risques et la checklist avant merge (`05-checklist-quotidienne.md`) mise à jour en conséquence.
- Le backlog (`04-missions-et-sprints.md`) et les fiches vivantes (`docs/missions/`) restent la source de vérité documentaire ; GitHub Projects sert de vue opérationnelle (tableau Kanban) synchronisée manuellement avec ces documents.

### ADR-014 : Abandon du workflow Figma — génération UI par agent de codage
**Statut :** Accepted
**Date :** 2026-09-02

#### Contexte
Le workflow Figma (maquettes → Dev Mode → export tokens → handoff) s'est avéré peu productif pour un développeur unique disposant d'un agent de codage. Le temps consacré à maintenir les maquettes synchronisées avec le code représente une charge sans valeur ajoutée pour la recherche DBR.

#### Décision
Le workflow Figma est abandonné dès ce sprint. L'UI de chaque écran est désormais générée par un agent de codage (Claude Code / Gemini Android Studio) à partir d'une **description UX structurée** rédigée dans la fiche de mission correspondante. La validation visuelle se fait sur device/émulateur, avec screenshot archivé dans `docs/screenshots/<ID-mission>/`.

#### Options considérées
- Continuer Figma (rejeté : trop chronophage, bloque les missions UI)
- Figma IA (rejeté : coût et complexité supplémentaires)
- Design system MD3 pur sans maquette (rejeté : trop peu de guidage pour l'agent)
- **Description UX structurée + agent** (retenu : équilibre guidage/vitesse)

#### Conséquences sur le processus
- **DoR (toutes missions UI)** : "Maquette Figma en état Dev Mode" remplacé par "Description UX rédigée dans la fiche".
- **DoD (toutes missions UI)** : "Comparaison visuelle Figma validée" remplacé par "Screenshot validé sur device/émulateur, archivé dans docs/screenshots/".
- **Accessibilité (contraste)** : Validation via test instrumentation AccessibilityChecks (Android) au lieu du plugin Stark (Figma).
- **Mission A1 (design tokens)** : Débloquée : valeurs actuelles de Color.kt canonisées.

### ADR-015 : Stratégie de seed de développement — déblocage Mission A4
**Statut :** Accepted
**Date :** 2026-09-03

#### Contexte
La Mission A4 (entités Room & pré-population) est bloquée par A0-T23 (5 unités validées
par niveau requises). La validation humaine du contenu est hors de portée du développement
technique et bloque toute la chaîne B1→B3, C1→C3.

#### Décision
Ajouter un champ `isValidated: Boolean = false` à `UniteApprentissageEntity`. Procéder
à l'implémentation complète de A4 avec les 4 brouillons existants dans `seed_v1.json`,
marqués `isValidated: false`. Le code n'applique aucun filtre sur ce champ en développement :
toutes les unités sont visibles. La DoD de A0 (validation humaine) reste inchangée et doit
être satisfaite avant Mission D0 (distribution pilote).

#### Options considérées
- Attendre A0-T23 (rejeté : bloque indéfiniment le développement)
- Seed minimaliste factice (rejeté : perd la cohérence avec le vrai contenu)
- **Option B — seed draft + flag `isValidated`** (retenu : débloque A4 sans mentir sur le statut)

#### Conséquences
- Schéma Room incrémenté : version 3 → 4 (migration `ALTER TABLE unite_apprentissage ADD COLUMN`)
- `seed_v1.json` reçoit le champ `"isValidated": false` sur les 4 unités existantes
- `ContentDataSource.kt` lit le nouveau champ lors du seed
- Les unités validées après A0-T23 seront mises à jour via `UPDATE` ou rechargement du seed
- Risque R-07 reste Ouvert — le champ `isValidated` permet de le mesurer précisément

## 7. Gabarit ADR (pour toute nouvelle décision)

```markdown
# ADR-[numéro] : [Titre]

**Statut :** Proposed | Accepted | Deprecated | Superseded
**Date :** [date]

## Contexte
[Quelle situation, quelles contraintes ?]

## Décision
[Quel choix est fait ?]

## Options considérées
[Alternatives envisagées et pourquoi elles n'ont pas été retenues]

## Conséquences
- [Ce qui devient plus simple]
- [Ce qui devient plus complexe]
- [Ce qu'il faudra réexaminer]
```

## 8. Stratégie de tests (synthèse)

| Couche | Type de test | Outil |
|---|---|---|
| `domain` | Tests unitaires des UseCases | JUnit, coroutines-test |
| `data` (Room) | Tests DAO, tests de migration | Room testing, JUnit |
| `presentation` | Tests d'état de ViewModel | JUnit, Turbine (Flow) |
| UI Compose | Tests d'instrumentation sur parcours critiques (Apprentissage, Écriture) | Compose UI Test, Espresso |
| Intégration | Test bout en bout de la synchronisation locale | Test instrumenté sur deux instances/émulateurs |

Cible de couverture indicative : ≥ 70 % sur `domain`, tests d'instrumentation obligatoires sur les écrans marqués **M** (Must have) dans les exigences fonctionnelles.
