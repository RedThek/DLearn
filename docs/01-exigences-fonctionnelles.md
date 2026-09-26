# Exigences fonctionnelles — Liteschreib IKII

## 1. Contexte et objectif pédagogique

L'application utilise la littérature en langue allemande comme vecteur de développement de la compétence écrite (production écrite, compréhension), pour des élèves du secondaire francophone au Cameroun, en cohérence avec le curriculum *Ihr und Wir Plus* et les descripteurs GeR/CECR. Elle est **offline-first** : toute exigence fonctionnelle doit rester exécutable sans connexion réseau, sauf mention contraire explicite.

## 2. Acteurs

| Acteur | Description | Périmètre d'action |
|---|---|---|
| **Élève** | Utilisateur principal, apprenant du secondaire | Accueil, Apprentissage, Écriture, Suivi, Profil |
| **Enseignant** | Encadre une ou plusieurs classes | Dashboard enseignant, suivi de classe, assignation de contenus |
| **Système TTS** | Acteur système (Android TTS API) | Lecture audio des textes littéraires et consignes |

## 3. Navigation générale

L'application repose sur une navigation par onglets (5 tabs), identique pour l'élève :

`Accueil` · `Apprentissage` · `Écriture` · `Suivi` · `Profil`

L'enseignant dispose d'un dashboard distinct (device séparé ou profil séparé sur device partagé, à trancher — voir NFR-06 et ADR associé).

## 4. Légende de priorité (MoSCoW)

- **M** = Must have (indispensable à la version pédagogique minimale viable)
- **S** = Should have
- **C** = Could have
- **W** = Won't have (cette itération — reporté en Phase 3 IA)

---

## 5. Module — Profil & authentification locale

| ID | Exigence | Priorité | Critère d'acceptation |
|---|---|---|---|
| FR-01 | L'application permet de créer un profil Élève local (nom, classe, niveau GeR de départ) sans compte en ligne | M | Un profil est créé et persiste après redémarrage de l'app, sans appel réseau |
| FR-02 | L'application permet de créer/sélectionner un profil Enseignant local, avec liste de classes gérées | M | L'enseignant peut ajouter une classe et y rattacher des profils élève (import local ou saisie) |
| FR-03 | Un élève peut consulter et modifier ses informations de profil (avatar, préférences d'affichage) | S | Modifications persistées en base Room |
| FR-04 | Plusieurs profils élève peuvent coexister sur un même appareil partagé (contexte BYOD limité) | C | Sélecteur de profil au lancement si > 1 profil |
| FR-33 *(ADR-009)* | L'application est unique (un seul APK) et propose un sélecteur de rôle/profil (Élève / Enseignant) au lancement, avec bascule possible entre profils sur un même appareil | M | Écran de sélection de rôle affiché si plusieurs profils existent ; navigation racine distincte selon le rôle choisi |
| FR-47 *(ADR-026, ADR-027)* | L'enseignant peut transmettre à un élève son compte (identifiant et mot de passe initial) par un fichier adressé à cet élève ; l'élève se connecte ensuite hors ligne sur son propre appareil | S | Bundle `PROVISION` importé sur l'appareil de l'élève ; connexion réussie sans réseau ; aucun mot de passe en clair dans le fichier |

## 6. Module — Accueil

| ID | Exigence | Priorité | Critère d'acceptation |
|---|---|---|---|
| FR-05 | L'écran Accueil affiche un résumé de la progression (niveau GeR actuel, dernière activité) | M | Données lues depuis Room, cohérentes avec le module Suivi |
| FR-06 | L'écran Accueil propose une reprise directe de la dernière leçon/exercice en cours | M | Un tap redirige vers l'écran Apprentissage ou Écriture au bon point de reprise |
| FR-07 | L'écran Accueil affiche les recommandations du jour (ex. révisions dues via répétition espacée) | S | Liste générée selon l'algorithme de planification (FSRS ou équivalent simplifié) |
| FR-08 | L'écran Accueil affiche les annonces/consignes de l'enseignant si synchronisées | C | Contenu visible après synchronisation locale (voir module Synchronisation) |

## 7. Module — Apprentissage (lecture littéraire)

| ID | Exigence | Priorité | Critère d'acceptation |
|---|---|---|---|
| FR-09 | L'élève peut parcourir les unités/chapitres de littérature organisés par niveau GeR/CECR et alignés sur *Ihr und Wir Plus* | M | Arborescence de contenu navigable, filtrable par niveau |
| FR-10 | L'élève peut lire un extrait littéraire en allemand avec glossaire contextuel (mots difficiles) | M | Tap sur un mot surligné affiche sa traduction/définition |
| FR-11 | L'élève peut déclencher la lecture audio (TTS) d'un extrait, la voix allemande étant installée au préalable (voir FR-32) | M | Le système TTS lit le texte affiché, contrôle play/pause/vitesse ; fonctionne intégralement hors ligne une fois la voix installée |
| FR-32 *(ADR-007)* | Le système vérifie, au premier accès au module Apprentissage, si la voix TTS allemande est installée ; si absente, il propose son téléchargement lors d'une connexion disponible, avant toute utilisation hors ligne du module | M | Écran/dialogue de vérification affiché une seule fois ; usage ultérieur strictement hors ligne (exception documentée en NFR-01) |
| FR-12 | L'élève peut répondre à des exercices de compréhension liés au texte (QCM, texte à trous, vrai/faux) | M | Correction immédiate offline, score enregistré en local |
| FR-13 | Chaque unité affiche le niveau GeR/CECR cible et les objectifs d'apprentissage | S | Métadonnées affichées en en-tête d'unité |
| FR-14 | L'élève peut marquer une unité comme terminée ou la reprendre plus tard | M | État persisté (non commencé / en cours / terminé) |

## 8. Module — Écriture

| ID | Exigence | Priorité | Critère d'acceptation |
|---|---|---|---|
| FR-15 | L'élève peut rédiger un texte en allemand en réponse à une consigne d'écriture liée à l'œuvre étudiée | M | Éditeur de texte simple, sauvegarde automatique locale |
| FR-16 | L'élève peut consulter des exemples de production écrite modèles par niveau GeR | S | Contenu pré-chargé, consultable hors ligne |
| FR-17 | L'élève peut s'auto-évaluer via une grille critériée simplifiée (longueur, cohérence, vocabulaire) — sans correction automatique IA en Phase 1 | S | Grille d'auto-évaluation manuelle, résultat enregistré |
| FR-18 | L'élève peut exporter/partager sa production écrite (fichier texte local, impression, ou transfert vers l'enseignant lors de la synchronisation) | C | Export réussi sans connexion internet |
| FR-34 *(ADR-011)* | L'éditeur d'écriture propose un clavier virtuel dédié affichant les caractères spéciaux allemands (ä, ö, ü, ß, Ä, Ö, Ü), insérables en un tap, en complément du clavier système | M | Les caractères s'insèrent correctement à la position du curseur, indépendamment du clavier système installé |
| FR-19 *(Phase 3 — différé)* | Correction automatique assistée par IA (Automated Writing Evaluation) | W | Hors périmètre tant que le socle pédagogique n'est pas validé |

## 9. Module — Suivi (progression)

| ID | Exigence | Priorité | Critère d'acceptation |
|---|---|---|---|
| FR-20 | L'élève visualise sa progression par niveau GeR/CECR (A1 → cible du curriculum) | M | Graphique/barre de progression basé sur les unités complétées |
| FR-21 | L'élève visualise l'historique de ses scores aux exercices de compréhension | M | Liste chronologique consultable |
| FR-22 | Le système planifie les révisions selon un algorithme de répétition espacée (type FSRS) | S | Items à réviser proposés selon l'échéancier calculé |
| FR-23 | L'élève peut consulter des statistiques simples (temps passé, unités terminées, régularité) | C | Données agrégées localement, aucune transmission externe |

## 10. Module — Enseignant (Dashboard)

| ID | Exigence | Priorité | Critère d'acceptation |
|---|---|---|---|
| FR-24 | L'enseignant visualise la liste de ses classes et élèves rattachés | M | Vue liste/tableau, données locales |
| FR-25 | L'enseignant visualise la progression agrégée de chaque élève/classe (niveau GeR, unités complétées) | M | Vue synthétique par élève et par classe |
| FR-26 | L'enseignant peut assigner une unité ou un exercice spécifique à un élève ou une classe | S | L'assignation apparaît dans le module Accueil de l'élève après synchronisation |
| FR-27 | L'enseignant peut consulter les productions écrites soumises par les élèves | S | Liste des productions, ouverture en lecture |
| FR-28 | L'enseignant peut exporter des données de suivi pour analyse externe (ex. vers Google Forms hors ligne / fichier local) | C | Export CSV/JSON local, aucun SDK cloud |

## 11. Module — Synchronisation locale (offline, BYOD)

| ID | Exigence | Priorité | Critère d'acceptation |
|---|---|---|---|
| FR-29 *(ADR-004, révisé par ADR-027)* | Les données élève/enseignant peuvent être synchronisées **dans les deux sens** sans internet, via des fichiers (bundles) transmis par les mécanismes de partage natifs de l'appareil (Nearby Share, Bluetooth, câble, ou carte SD en repli) | S | Transfert réussi entre deux appareils sans réseau ; import atomique avec résumé ; un fichier corrompu ou déjà importé est refusé avec un message explicite ; format conforme à `20-…` |
| FR-30 | Le contenu pédagogique (textes, exercices) est pré-chargé à l'installation, sans téléchargement obligatoire | M | L'app fonctionne dès le premier lancement, sans connexion |
| FR-31 *(révisé par ADR-028)* | Les mises à jour de contenu et les données de référence (dictionnaire) sont installées via des **packs `.ikiipack`** transférés par fichier, sans écraser la progression de l'élève | **S** | Installation, mise à jour et retrait d'un pack sans perte de progression ; installation idempotente ; contenus retirés masqués et non supprimés |

## 11-bis. Module — Extension littéraire (Bloc F, horizons H1/H2 — voir `18-vision-produit-et-horizons.md`)

Ces exigences sont des **entrées de backlog à spécifier** : leurs critères d'acceptation seront affinés à l'ouverture de chaque mission du Bloc F.

| ID | Exigence | Priorité | Horizon | Critère d'acceptation |
|---|---|---|---|---|
| FR-35 | L'élève peut consulter des fiches-méthode de rédaction par type de production (dialogue, description, lettre, e-mail, publicité, discours, argumentation), calibrées A1–A2 | S | H1 | Fiches consultables hors ligne, rattachées à un niveau GeR |
| FR-36 | L'élève dispose d'un outil de structuration « idée – argument – exemple » avant de rédiger | S | H1 | Trois champs guidés ; le contenu est réutilisable dans l'éditeur d'écriture |
| FR-37 | L'élève peut relever des défis d'écriture courts (thème, longueur indicative, minuteur optionnel) | C | H1 | Production rattachée au défi ; longueur non bloquante, adaptée au niveau (`16-…`, section 5) |
| FR-38 | L'enseignant peut commenter une production, et le commentaire revient à l'élève après synchronisation | S | H1 (fin) | Commentaire visible côté élève après import ; dépend d'ADR-027 |
| FR-39 | L'élève et l'enseignant peuvent consulter l'historique des versions d'une production (brouillon, soumis, révisé) | C | H1 (fin) | Versions horodatées, non modifiables |
| FR-40 | L'enseignant peut générer un recueil PDF de classe à partir de productions sélectionnées, avec le consentement de l'élève | C | H1 (fin) | PDF généré localement ; pseudonyme possible ; aucune donnée transmise |
| FR-41 | Le système affiche des points, niveaux et badges **dérivés** de l'activité réelle, et peut produire un certificat de participation PDF | C | H2 | Aucun compteur persisté ; classement de classe optionnel et désactivé par défaut (ADR-023) |
| FR-42 | L'élève dispose d'un dictionnaire hors ligne allemand ↔ français, puis anglais et espagnol | C | H2 | Pack optionnel ; recherche hors ligne ; licences conformes à ADR-025 |
| FR-43 | L'élève dispose d'aides à l'écriture à règles hors ligne (orthographe, synonymes, règles grammaticales élémentaires du niveau) | C | H2 | Niveau N0 d'ADR-024 ; aucune donnée transmise |
| FR-44 | L'enseignant peut animer un club de lecture de classe (questions guidées, défis de lecture mensuels, quiz) | C | H2 | Contenus distribués par l'enseignant ; aucun forum |
| FR-45 | L'enseignant peut organiser un concours de classe ou d'établissement (ouverture, dépôt par export, jury, résultats diffusés par fichier) | C | H2 | Fonctionne sans connexion ; annonces via FR-08 |
| FR-46 | Publication en ligne, forum, commentaires publics, échanges internationaux, portail éditeurs, soumission de manuscrits, classes virtuelles, IA cloud pour élèves | W | H3 | Hors périmètre de la thèse (ADR-020) |

## 12. Exigences explicitement hors périmètre (Phase 3 — IA/NLP, différées)

Ces fonctionnalités sont **volontairement exclues** du socle initial pour ne pas coupler le risque IA à l'architecture (cf. principe directeur du README) :

- Reconnaissance automatique de la parole (ASR) pour évaluation de la prononciation
- Correction automatique de l'écrit (AWE) au-delà de règles simples
- Système de tutorat intelligent (ITS) adaptatif
- Génération de contenu via Gemini Nano / modèles TFLite embarqués

Elles seront réintroduites comme exigences fonctionnelles à part entière lors du Cycle DBR 2 (voir roadmap), une fois le cœur pédagogique validé par les utilisateurs.
