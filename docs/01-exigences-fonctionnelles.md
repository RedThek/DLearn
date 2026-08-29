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
| FR-29 *(ADR-004)* | Les données élève/enseignant peuvent être synchronisées sans internet, via export/import de fichier utilisant les mécanismes de partage natifs de l'appareil (Nearby Share, Bluetooth, câble, ou carte SD en repli) | S | Transfert réussi entre deux appareils sans réseau mobile/Wi-Fi internet, avec repli fonctionnel si Nearby Share indisponible |
| FR-30 | Le contenu pédagogique (textes, exercices) est pré-chargé à l'installation, sans téléchargement obligatoire | M | L'app fonctionne dès le premier lancement, sans connexion |
| FR-31 | Les mises à jour de contenu peuvent être appliquées via un fichier local (ex. package de contenu versionné) | C | Import d'un package met à jour le contenu sans écraser la progression élève |

## 12. Exigences explicitement hors périmètre (Phase 3 — IA/NLP, différées)

Ces fonctionnalités sont **volontairement exclues** du socle initial pour ne pas coupler le risque IA à l'architecture (cf. principe directeur du README) :

- Reconnaissance automatique de la parole (ASR) pour évaluation de la prononciation
- Correction automatique de l'écrit (AWE) au-delà de règles simples
- Système de tutorat intelligent (ITS) adaptatif
- Génération de contenu via Gemini Nano / modèles TFLite embarqués

Elles seront réintroduites comme exigences fonctionnelles à part entière lors du Cycle DBR 2 (voir roadmap), une fois le cœur pédagogique validé par les utilisateurs.
