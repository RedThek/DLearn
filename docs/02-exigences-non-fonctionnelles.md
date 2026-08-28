# Exigences non fonctionnelles — Liteschreib IKII

Chaque exigence non fonctionnelle (NFR) est assortie d'un critère de vérification mesurable, à contrôler lors des revues de sprint et de la checklist quotidienne.

## 1. Disponibilité hors ligne (Offline-first)

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-01 | Aucune fonctionnalité du socle pédagogique (Accueil, Apprentissage, Écriture, Suivi, Profil) ne doit émettre d'appel réseau bloquant | Test manuel en mode avion sur chaque écran ; grep du code pour tout appel HTTP/SDK réseau hors modules explicitement identifiés |
| NFR-01-bis *(exception documentée, ADR-007)* | Seule exception tolérée à NFR-01 : l'installation initiale de la voix TTS allemande (langue non préinstallée) peut nécessiter une connexion ponctuelle unique, réalisée idéalement en contexte scolaire (Wi-Fi établissement) avant le début de l'usage pédagogique. Cette exception ne doit jamais s'étendre à une autre fonctionnalité | Vérification que l'usage du module Apprentissage, une fois la voix installée, fonctionne intégralement en mode avion ; aucune autre requête réseau détectée dans ce module |
| NFR-02 | Aucun SDK d'analytics ou de crash-reporting cloud n'est intégré | Revue des dépendances Gradle à chaque sprint ; liste blanche de dépendances autorisées |
| NFR-03 | Le contenu pédagogique est intégralement disponible dès le premier lancement (pré-population Room) | Test d'installation à froid sans connexion : contenu accessible immédiatement |

## 2. Performance et compatibilité matérielle (contexte BYOD Cameroun)

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-04 | L'application démarre en moins de 3 secondes sur un appareil d'entrée de gamme (≤ 2 Go RAM) | Mesure via Android Studio Profiler sur device de référence bas de gamme |
| NFR-05 | L'application reste fluide (pas de jank perceptible) sur les listes/écrans avec `LazyColumn` | Test manuel + Layout Inspector ; scroll sans frame drop visible |
| NFR-06 *(ADR-005)* | `minSdkVersion = 28` (Android 9.0+), retenu sur la base d'un constat terrain de large diffusion de cette version dans la zone pilote (Yaoundé) | Test sur émulateur/device physique au niveau API 28 ; build configuré avec `minSdk = 28` dans `build.gradle` |
| NFR-07 | La taille de l'APK/AAB reste raisonnable au regard des contraintes de stockage et de transfert local des utilisateurs cibles | Mesure de la taille du build de release à chaque milestone |
| NFR-08 | La consommation batterie reste modérée (pas de service en arrière-plan permanent hors nécessité TTS) | Vérification via Battery Historian / profiler sur un scénario d'usage type |

## 3. Sécurité et confidentialité des données

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-09 | Les données élève (profil, productions écrites, scores) restent stockées localement, jamais transmises à un tiers | Revue de code : aucune dépendance réseau sortante pour ces données |
| NFR-10 | Le transfert local enseignant-élève (BYOD) ne transite pas par un serveur externe | Vérification du mécanisme de synchronisation retenu (ADR) |
| NFR-11 | Les données de recherche (usage anonymisé pour l'évaluation DBR) sont collectées via un mécanisme explicite, exportable manuellement (ex. Google Forms hors ligne), jamais en tâche de fond silencieuse | Consentement explicite affiché ; aucun tracking implicite |

## 4. Utilisabilité et accessibilité

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-12 | L'interface est intégralement en français, cohérente avec le lexique pédagogique du public cible | Revue linguistique de chaque écran avant merge |
| NFR-13 | Les contrastes de couleurs respectent un niveau d'accessibilité suffisant (WCAG AA a minima) | Vérification avec le plugin Stark sur chaque maquette Figma avant handoff |
| NFR-14 | Les tailles de police et zones tactiles restent lisibles/utilisables sur petits écrans (contexte BYOD) | Test sur device à écran ≤ 5.5" |
| NFR-15 | La navigation à 5 onglets reste compréhensible sans formation préalable (test heuristique) | Évaluation heuristique programmée en fin de Cycle DBR 1 (voir roadmap) |

## 5. Maintenabilité et évolutivité

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-16 | Le code respecte la séparation Clean Architecture (domain / data / presentation) sans dépendance inversée | Revue de code + lint de dépendances (voir 06-architecture-technique.md) |
| NFR-17 | L'injection de dépendances est gérée exclusivement via Hilt, dès l'origine du projet | Aucune instanciation manuelle de dépendance métier hors modules Hilt |
| NFR-18 | L'ajout futur des fonctionnalités IA (Phase 3) ne doit pas nécessiter de refactoring du socle | Revue d'architecture avant Cycle DBR 2 ; interfaces de domaine anticipant les ports IA (ex. interface `PronunciationEvaluator` mockable) |
| NFR-19 | Le code est documenté (KDoc sur les classes publiques du domaine) suffisamment pour un usage académique (annexes de mémoire) | Vérification lors des revues de sprint |

## 6. Testabilité

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-20 | Chaque cas d'usage du domaine dispose d'au moins un test unitaire | Couverture mesurée (cible indicative ≥ 70 % sur la couche domain) |
| NFR-21 | Les écrans critiques (Apprentissage, Écriture) disposent de tests d'instrumentation Compose | Suite de tests UI exécutée en CI |
| NFR-22 | Les entités Room disposent de tests de migration dès qu'un schéma évolue | Test de migration exécuté avant merge sur toute modification de schéma |

## 7. Compatibilité et intégration CI/CD

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-23 | Chaque push déclenche un build + suite de tests via GitHub Actions | Pipeline vert obligatoire avant merge sur `main`/`develop` |
| NFR-24 | Le projet compile avec KSP (pas de kapt) | Vérification de configuration Gradle |

## 8. Distribution et installation

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-27 *(ADR-010)* | L'application est installable via un APK partagé localement (USB, Bluetooth, carte SD), sans dépendance au Google Play Store pour la phase pilote | Installation testée par transfert local sur au moins deux appareils différents, sans connexion internet |
| NFR-28 *(ADR-010)* | Le processus d'installation via « sources inconnues » est documenté et accompagné (guide enseignant) pour limiter la friction et les réticences des utilisateurs/parents | Vérification de la présence et de la clarté du guide d'installation avant le déploiement pilote |
| NFR-29 *(ADR-010)* | Les mises à jour d'application et de contenu sont distribuées manuellement selon un schéma de version explicite | Version consignée dans `14-charte-versionnage-contenu.md`, vérifiée à chaque nouvelle diffusion |

## 9. Conformité pédagogique et traçabilité recherche

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-25 | Chaque unité de contenu est traçable à un objectif GeR/CECR et à une unité du curriculum *Ihr und Wir Plus* | Table de correspondance tenue à jour (voir contenu pédagogique) |
| NFR-26 | Chaque cycle de développement DBR produit une trace documentée (journal de bord, décisions, résultats d'évaluation) exploitable pour la soutenance | Revue documentaire avant chaque jalon académique (mois 6, mois 12) |
