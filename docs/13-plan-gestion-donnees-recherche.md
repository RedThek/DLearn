# Plan de gestion des données de recherche — Liteschreib IKII

Ce document précise, pour les besoins de la recherche DBR (mémoire de Master), quelles données sont collectées lors des évaluations pilotes, comment elles sont anonymisées, conservées et exploitées. Il complète le protocole éthique (`10-protocole-ethique-consentement.md`), auquel il est directement subordonné : aucune collecte décrite ici ne démarre avant l'obtention des consentements requis.

## 1. Données collectées à des fins de recherche

| Donnée | Source | Finalité de recherche | Anonymisée avant analyse ? |
|---|---|---|---|
| Progression par niveau GeR | Application (module Suivi) | Évaluer l'efficacité pédagogique du dispositif | Oui — code participant, jamais le nom |
| Scores aux exercices de compréhension | Application (module Apprentissage) | Analyser la progression et les difficultés récurrentes | Oui |
| Productions écrites (extraits anonymisés) | Application (module Écriture) | Analyse qualitative de la compétence écrite | Oui — retrait de toute mention nominative avant citation dans le mémoire |
| Questionnaire d'appréciation (élèves) | Google Forms hors ligne | Évaluation heuristique de l'expérience utilisateur | Oui |
| Questionnaire d'usage (enseignants) | Google Forms hors ligne | Évaluation du dashboard et de l'utilité pédagogique perçue | Oui |
| Statistiques d'usage agrégées (temps passé, régularité) | Application (module Suivi) | Analyse de l'engagement | Oui |

**Exclu explicitement de la collecte de recherche** : toute donnée hors du périmètre pédagogique (contacts, localisation précise, identifiants d'appareil autres que ceux nécessaires au débogage technique de la synchronisation).

## 2. Processus de collecte

1. Les données d'usage sont générées localement par l'application pendant l'usage normal (aucune collecte active supplémentaire).
2. En fin de période pilote, un export local est réalisé (fichier CSV/JSON, voir FR-28) depuis l'appareil de chaque participant, avec l'assistance de l'enseignant.
3. Le code participant (ex. `EL-014`) remplace tout nom avant l'agrégation des exports — la table de correspondance nom ↔ code est conservée séparément et distinctement des données d'analyse (voir section 4).
4. Les réponses au questionnaire Google Forms hors ligne sont collectées de façon anonyme dès la conception du formulaire (pas de champ nom/email).

## 3. Durée de conservation

| Type de donnée | Durée de conservation | Justification |
|---|---|---|
| Données anonymisées (progression, scores, productions écrites anonymisées) | Jusqu'à la soutenance + [durée à préciser selon les règles de l'établissement, ex. 3 ans] | Nécessaire pour d'éventuelles vérifications académiques post-soutenance |
| Table de correspondance nom ↔ code participant | Détruite au plus tard à la fin de la phase d'analyse, avant rédaction finale du mémoire | Minimisation des données conservées |
| Formulaires de consentement signés (papier ou numérique) | Conservés séparément, selon la durée exigée par le protocole éthique de l'établissement | Traçabilité du consentement |

## 4. Séparation des données d'identification et des données d'analyse

- La table de correspondance nom ↔ code participant est stockée séparément (fichier distinct, si possible protégé par mot de passe), jamais mélangée aux fichiers d'export utilisés pour l'analyse.
- Aucune donnée d'analyse partagée avec l'encadrant académique ou intégrée au mémoire ne doit permettre de réidentifier un participant.

## 5. Accès aux données

| Rôle | Accès |
|---|---|
| Porteur du projet (toi) | Accès complet aux données anonymisées et à la table de correspondance |
| Encadrant académique | Accès aux données anonymisées uniquement, pour supervision du mémoire |
| Tiers externes | Aucun accès, sauf obligation légale ou académique explicite communiquée au préalable aux participants |

## 6. Sécurité de la conservation

- Les exports de données restent stockés localement (ordinateur du porteur de projet), sans hébergement cloud tiers (cohérent avec ADR-002).
- Une sauvegarde chiffrée sur support externe (clé USB, disque dur) est recommandée pour éviter la perte de données de recherche.

## 7. Utilisation des données dans le mémoire

- Seules des données agrégées ou anonymisées apparaissent dans le corps du mémoire.
- Toute citation d'extrait de production écrite d'élève est reformulée ou anonymisée de façon à ne pas permettre l'identification, même indirecte, de l'élève concerné.

## 8. Lien avec les autres documents

- Ce plan s'applique uniquement dans le cadre du consentement obtenu via `10-protocole-ethique-consentement.md`.
- Il est référencé par la Mission D3 (`04-missions-et-sprints.md`) comme prérequis à la collecte de données pilote.
- Le risque associé à une mauvaise anonymisation est consigné sous R-14 dans `08-registre-des-risques.md`.
