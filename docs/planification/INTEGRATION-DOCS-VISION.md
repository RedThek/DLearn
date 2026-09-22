# INTEGRATION-DOCS-VISION — Lot « décisions sans regret » (documentation uniquement)

> Produit par The Architect le 2026-09-21.
> **Périmètre :** documentation uniquement. **Aucun fichier de code, de build ni de schéma Room n'est modifié.**
> Ce document fournit, pour chaque fichier existant de `docs/`, l'**emplacement exact** et le **texte prêt à coller**. Deux nouveaux fichiers sont déjà rédigés : `18-vision-produit-et-horizons.md` et `19-registre-licences-contenus-tiers.md`.
> Contexte : Sprint 5 en cours. Ce lot est parallélisable avec les exécutions backend/frontend du Sprint 5 (aucun fichier commun avec le code).

## 0. Ordre d'intégration recommandé

| Étape | Fichier | Nature |
|---|---|---|
| 1 | `18-vision-produit-et-horizons.md`, `19-registre-licences-contenus-tiers.md` | Nouveaux (déjà rédigés) |
| 2 | `06-architecture-technique.md` | ADR-020 à ADR-025 (§1 ci-dessous) |
| 3 | `08-registre-des-risques.md` | R-22 à R-27 (§2) |
| 4 | `01-exigences-fonctionnelles.md`, `02-exigences-non-fonctionnelles.md` | FR-35 à FR-46, NFR-30 à NFR-32 (§3) |
| 5 | `04-missions-et-sprints.md` | Bloc F (§4) |
| 6 | `03-roadmap-developpement.md`, `09-cartographie-contenu-pedagogique.md` | Sections d'alignement (§5, §6) |
| 7 | `05-checklist-quotidienne.md`, `07-glossaire.md`, `README.md`, `ETAT_ACTUEL.md` | Petits ajouts (§7, §8) |
| 8 | `journal/2026-09-21.md` | Déjà rédigé |

Branche suggérée : `docs/vision-produit-horizons`. Commit dédié, sans code : `docs: vision produit, horizons H1/H2/H3 et ADR-020 à 025`.

> ⚠️ **Numérotation.** Dans l'analyse, j'avais évoqué ADR-021 (synchronisation), ADR-022 (identité), ADR-023 (packs) et ADR-024 (IA). Pour éviter tout trou de numérotation, ce lot utilise ADR-020 à ADR-025 pour les décisions **sans impact code** ; les décisions d'identité, de synchronisation et de packs deviennent **ADR-026, ADR-027, ADR-028** (lot suivant, voir §9).

---

## 1. `06-architecture-technique.md`

### 1.1 Lignes à ajouter à la table de la section 6, après la ligne ADR-019

```markdown
| ADR-020 | Périmètre produit et horizons (H1/H2/H3) | Accepted |
| ADR-021 | Rôles par capacités et navigation à 5 onglets | Accepted |
| ADR-022 | Convention package-by-feature pour le nouveau code | Accepted |
| ADR-023 | Gamification dérivée et éthique du classement | Accepted |
| ADR-024 | Niveaux d'IA (N0 à N3) | Accepted |
| ADR-025 | Politique de licences des contenus et données tiers | Accepted |
```

### 1.2 Texte des ADR — à insérer à la suite d'ADR-019, avant la phrase finale « Cible de couverture indicative… »

> Remarque d'hygiène : dans le fichier actuel, ADR-018 et ADR-019 sont placés après la section 8 « Stratégie de tests ». Une réorganisation pourra les remonter avec les autres ADR ; elle n'est pas requise pour ce lot.

```markdown
### ADR-020 : Périmètre produit et horizons (H1/H2/H3)
**Statut :** Accepted
**Date :** 2026-09-21

#### Contexte
Une proposition de fonctionnalités (`DLearn-New-Fonctionnalités.md`) élargit le projet vers une plateforme de promotion de la littérature allemande (lecteurs, auteurs, éditeurs, concours, communauté, IA, dictionnaire multilingue, gamification). Elle mêle deux produits : un outil pédagogique DaF hors ligne, compatible avec ADR-002 et ADR-008, et une plateforme sociale et éditoriale en ligne, incompatible avec ADR-002 et exigeant un backend, de la modération et une protection des mineurs. L'analyse d'impact de l'Architecte (2026-09-21) conclut qu'intégrer l'ensemble au MVP menacerait R-06, R-07, R-11, R-13 et la validité de l'évaluation DBR.

#### Décision
1. Le périmètre de la thèse est le **noyau pédagogique hors ligne** (horizon H1, Cycle DBR 1) puis des **enrichissements hors ligne** (H2, Cycle DBR 2). La plateforme en ligne est **H3**, après la soutenance.
2. La classification de référence des capacités est celle de `18-vision-produit-et-horizons.md`, section 5.
3. Toute nouvelle idée est classée dans ce document (capacité, horizon, critères K1–K6) avant d'entrer au backlog. Si elle touche l'architecture, un ADR précède le code.
4. Aucune capacité H3 n'est compilée ni exposée dans le build pilote (NFR-31).
5. Pendant l'évaluation pilote, l'intervention reste stable : les 5 onglets et les flux principaux ne changent pas sans ADR (NFR-32).
6. Règle de capacité : la validation humaine du contenu (Mission A0) est la ressource limitante ; un nouveau type de contenu n'est planifié qu'avec sa capacité de validation identifiée.
7. ADR-002 reste en vigueur. Le superséder exige un ADR dédié et un protocole éthique refait (`10-…`, `12-…`, `13-…`).

#### Options considérées
- Intégrer toute la proposition au MVP (rejeté : volume 5 à 10 fois supérieur, développeur unique, dilution de l'évaluation DBR).
- Refuser la proposition (rejeté : perd des éléments à forte valeur pédagogique, comme la boucle brouillon → feedback → publication de classe).
- Triage par horizons avec noyau pédagogique d'abord (retenu).

#### Conséquences
- Création de `18-vision-produit-et-horizons.md`, du Bloc F dans le backlog et des risques R-22 à R-27.
- Aucun changement de code ; aucun sprint existant n'est modifié.
- La possibilité de H3 est préservée par des décisions d'architecture à prendre au lot suivant (ADR-026 à ADR-028), sans construire H3.

### ADR-021 : Rôles par capacités et navigation à 5 onglets
**Statut :** Accepted
**Date :** 2026-09-21

#### Contexte
La proposition prévoit cinq « espaces » (Lecteurs, Élèves, Enseignants, Auteurs, Éditeurs) et un menu de 7 entrées. Or `Role` vaut {ELEVE, ENSEIGNANT} (ADR-009), `RoleSelector` itère sur `Role.entries`, et la navigation à 5 onglets est validée par NFR-14 et NFR-15 (Material 3 recommande 3 à 5 destinations).

#### Décision
1. `Role` reste {ELEVE, ENSEIGNANT}. Aucune valeur n'est ajoutée avant évaluation du pilote.
2. « Auteur » et « Lecteur » sont des **usages** d'un élève ou d'un enseignant, pas des rôles. « Éditeur » est un acteur H3 (portail web), hors de l'application élève.
3. Les nouvelles capacités sont conditionnées par le rôle existant et par un drapeau de build. Aucun graphe de navigation supplémentaire par rôle.
4. Les 5 onglets sont conservés. Toute nouvelle capacité s'insère dans un onglet existant ou comme sous-écran. Le renommage éventuel Apprentissage → Bibliothèque et Écriture → Atelier est étudié après le pilote (NFR-32).
5. Le menu à 7 entrées de la proposition est rejeté ; sa correspondance avec les 5 onglets est en `18-…`, section 7.

#### Options considérées
- Ajouter des rôles Auteur/Éditeur/Lecteur (rejeté : casse `RoleSelector`, les graphes de navigation et les permissions, pour des rôles sans sens hors ligne).
- Passer à 7 onglets (rejeté : ergonomie sur petits écrans, NFR-14/NFR-15).

#### Conséquences
- `RoleSelector`, `NavGraph` et `MainScreen` restent inchangés.
- Un modèle de permissions plus fin (capacités) pourra être introduit avec H3 par un ADR dédié.

### ADR-022 : Convention package-by-feature pour le nouveau code
**Statut :** Accepted
**Date :** 2026-09-21

#### Contexte
Les couches `domain/` et `data/` sont organisées à plat (`domain/model`, `domain/usecase`, `data/repository`…), alors que `presentation/` est déjà organisée par fonctionnalité. L'ajout de plusieurs contextes métier (atelier, feedback, gamification, dictionnaire) rendrait les dossiers plats difficiles à parcourir.

#### Décision
1. Le **nouveau code** est organisé par fonctionnalité à l'intérieur des couches : `domain/<feature>/{model,repository,usecase}`, `data/<feature>/{room,repository}`, `presentation/<feature>/`.
2. Le **code existant n'est pas déplacé** ; il n'est migré que lorsqu'une mission le modifie déjà en profondeur.
3. Les modules Hilt sont créés **par fonctionnalité** (par exemple un module `Atelier`), dans `core/di/`, sans faire grossir `AppModule.kt`.
4. La règle de dépendance (`presentation` → `domain` ← `data`, NFR-16) est inchangée.
5. Le découpage en modules Gradle est **différé** ; il sera réévalué quand trois fonctionnalités H2 seront livrées ou si le temps de build incrémental devient gênant (seuil à fixer lors de cette réévaluation).

#### Options considérées
- Refactorer tout le code existant maintenant (rejeté : risque de régression sans bénéfice immédiat, sprint en cours).
- Modules Gradle immédiats (rejeté : complexité disproportionnée pour un développeur unique).
- Ne rien changer (rejeté : dérive de lisibilité prévisible).

#### Conséquences
- Une convention à appliquer dès la première mission du Bloc F ; la checklist avant merge est complétée.
- Coexistence temporaire de deux styles d'organisation, assumée et documentée.

### ADR-023 : Gamification dérivée et éthique du classement
**Statut :** Accepted
**Date :** 2026-09-21

#### Contexte
La proposition demande points, niveaux, badges, classement et certificats. Le public est constitué de mineurs ; les données restent locales (ADR-002) ; le modèle actuel contient déjà des événements réels (`reponse_eleve`, `progression`, `production_ecrite`, et `session_etude` avec ADR-019).

#### Décision
1. Points, niveaux et badges sont des **vues dérivées** de l'activité réelle. Aucun compteur mutable n'est persisté. Une table d'événements dédiée n'est créée que pour les événements non enregistrés ailleurs (par exemple un défi accompli).
2. **Aucun classement public ou mondial.** Un classement de classe est **optionnel, désactivé par défaut et commandé par l'enseignant**. L'accent est mis sur la progression personnelle.
3. Pas de mécanique punitive (perte de points, humiliation par le rang).
4. Les points ne sont **pas une mesure de compétence** : dans le mémoire, ils sont des indicateurs d'engagement, jamais des résultats d'apprentissage.
5. Les points ne servent à aucune notation scolaire. Les données étant locales et modifiables, elles ne sont pas fiables pour un enjeu réel.
6. Les certificats de participation sont générés localement (PDF), sans donnée transmise.

#### Options considérées
- Compteurs persistés (rejeté : divergence possible avec les données sources, migrations inutiles).
- Classement global (rejeté : exposition de mineurs, nécessite un serveur, triche).

#### Conséquences
- Aucune migration Room à prévoir pour démarrer.
- Le protocole éthique doit mentionner que la gamification est un facteur d'engagement, pour éviter toute confusion d'interprétation (`10-protocole-ethique-consentement.md`).

### ADR-024 : Niveaux d'IA (N0 à N3)
**Statut :** Accepted
**Date :** 2026-09-21

#### Contexte
La proposition cite une IA pour corriger, améliorer le style, proposer des synonymes, générer des idées, coacher la publication et détecter les textes générés. ADR-003 reporte l'IA au Cycle 2, avec des ports de domaine (Mission E1) ; R-05 signale que Gemini Nano est probablement indisponible sur les appareils de référence (Tecno, Itel, Infinix).

#### Décision
Quatre niveaux, du plus compatible au moins compatible :

| Niveau | Nature | Statut |
|---|---|---|
| **N0** | Règles et dictionnaires hors ligne (orthographe, synonymes, règles grammaticales élémentaires, dictionnaire) | H2, autorisé |
| **N1** | Modèles embarqués (TFLite ; Gemini Nano seulement si détecté, jamais supposé) | Cycle 2, autorisé avec repli gracieux |
| **N2** | IA utilisée **par l'enseignant hors de l'application** pour préparer du contenu ; la sortie est un JSON conforme au gabarit `16-…` et **validé par un humain** avant seed | Autorisé, processus de contenu |
| **N3** | IA cloud pour les élèves dans l'application | **Interdit** tant qu'un ADR dédié n'a pas supersédé ADR-002 |

Règles associées :
1. **Aucune donnée d'élève ne quitte l'appareil**, quel que soit le niveau.
2. Les ports de domaine sont découpés par capacité (`SpellChecker`, `SynonymProvider`, `GrammarRuleChecker`) et distincts de l'assistant génératif (`WritingAssistant`, Mission E1). Les moteurs restent dans `data/` (NFR-16).
3. La **détection de textes générés par IA est écartée** : non fiable sur des textes courts d'apprenants non natifs. On lui substitue la traçabilité du processus d'écriture (versions horodatées, FR-39).
4. Le contenu produit avec de l'IA (N2) ne devient jamais `Validé` sans relecture humaine (Mission A0).

#### Options considérées
- Adopter une IA cloud dès maintenant (rejeté : ADR-002, mineurs, coût de connexion).
- Attendre sans rien préciser (rejeté : laisse le champ libre à des choix incompatibles).

#### Conséquences
- La Mission E1 définit les ports selon ce découpage.
- Le niveau N2 accélère la production de contenu, mais ne réduit pas la charge de validation humaine (R-07).

### ADR-025 : Politique de licences des contenus et données tiers
**Statut :** Accepted
**Date :** 2026-09-21

#### Contexte
Le dépôt est public (ADR-013) et sous licence CC0 (fichier `LICENSE`). ADR-006 encadre les droits des textes littéraires. La proposition ajoute des bibliothèques de textes, un dictionnaire multilingue, des synonymes, des polices et de l'audio, dont les licences sont hétérogènes (attribution, partage à l'identique, copyleft).

#### Décision
1. Tout contenu, donnée, police ou bibliothèque tiers est inscrit dans `19-registre-licences-contenus-tiers.md` **avant** intégration (NFR-30).
2. Règles d'admission : domaine public, CC0, licences permissives et polices SIL OFL sont admis ; les licences à attribution sont admises avec mention dans l'écran « Crédits » ; les licences à partage à l'identique ou copyleft (CC BY-SA, GPL, LGPL, GFDL) ne sont **pas intégrées au dépôt ni à l'APK** sans validation écrite de l'encadrant, et peuvent être distribuées comme **pack externe** sous leur propre licence.
3. Un fichier `THIRD_PARTY_NOTICES` recense les éléments tiers embarqués ; ils ne sont pas couverts par le CC0 du dépôt.
4. En cas de doute sur une licence : **refus par défaut**.
5. Les textes rédigés avec l'aide d'un modèle de langage sont enregistrés comme « texte original » et exigent une relecture humaine.
6. Les productions d'élèves ne sont jamais versées au dépôt (R-17).

#### Options considérées
- Ne pas formaliser et traiter au cas par cas (rejeté : risque de contamination de licence dans un dépôt public).
- Interdire tout contenu tiers (rejeté : priverait le projet de ressources du domaine public et de polices nécessaires).

#### Conséquences
- Création du registre et de `THIRD_PARTY_NOTICES` lors de la première intégration réelle.
- Le dictionnaire (Wiktionary, CC BY-SA) suivra la voie « pack externe » ; sa forme est à décider dans ADR-028.
- Ce registre n'est pas un avis juridique : la validation par l'encadrant reste requise pour les cas non triviaux.
```

---

## 2. `08-registre-des-risques.md`

### 2.1 Table « Risques techniques » — ajouter après la ligne R-21

```markdown
| R-25 *(nouveau)* | Poids des contenus et données envisagés (dictionnaire multilingue, audio, polices, bibliothèque) incompatible avec le stockage et la RAM des appareils bas de gamme et avec le transfert local de l'APK | Moyenne | Moyen | Modérée | Contenus lourds en **packs optionnels** transférés par fichier (ADR-028, à venir) ; mesure de la taille à chaque milestone (NFR-07) ; dictionnaire dans une base séparée | Ouvert | NFR-07, ADR-028, capacités C1, C3, C12 |
| R-26 *(nouveau)* | **Identité utilisateur non globale** : `UtilisateurEntity.id` est un `Long` local à l'appareil, réutilisé comme `eleveId` dans `progression`, `production_ecrite`, `reponse_eleve`, `assignation` (et `session_etude` avec ADR-019). Un import entre appareils peut produire des collisions ou des enregistrements orphelins. *Constat de l'Architecte à partir de la lecture du code, à confirmer par test à deux appareils* | Moyenne | Élevé | Élevée | Décider un identifiant global stable (ADR-026, lot suivant) **avant** le pilote ; test à deux appareils physiques (C3-T11) ; le coût est minimal tant qu'aucune donnée de pilote n'existe | Ouvert — à confirmer | ADR-018, ADR-026, Mission C3 |
| R-27 *(nouveau)* | **Synchronisation unidirectionnelle** : le format d'échange v1 va de l'élève vers l'enseignant ; aucun canal enseignant → élève (assignations, comptes, commentaires). Entre deux appareils distincts, FR-26, FR-08 et FR-38 ne sont pas opérationnels. *Constat de l'Architecte, à confirmer par test à deux appareils* | Élevée | Élevé | Élevée | Format d'échange v2 bidirectionnel (ADR-027, lot suivant) ; test à deux appareils physiques (C3-T11) | Ouvert — à confirmer | ADR-004, ADR-018, ADR-027 |
```

### 2.2 Table « Risques pédagogiques / méthodologiques » — ajouter après la ligne R-10

```markdown
| R-24 *(nouveau)* | Droits et licences des contenus et données tiers (bibliothèque, dictionnaire, synonymes, polices) ; dépôt public sous CC0 : risque de contamination de licence | Moyenne | Élevé | Modérée | ADR-025 ; registre `19-…` ; `THIRD_PARTY_NOTICES` ; refus par défaut en cas de doute ; validation de l'encadrant pour tout partage à l'identique ou copyleft | Ouvert | ADR-006, ADR-025, NFR-30, R-08, R-17 |
```

### 2.3 Table « Risques éthiques / organisationnels » — ajouter après la ligne R-14

```markdown
| R-22 *(nouveau)* | **Dérive de périmètre** : la proposition de plateforme (lecteurs, auteurs, éditeurs, communauté) élargit le projet bien au-delà d'un MVP réalisable par un développeur unique et dilue l'évaluation DBR | Élevée | Élevé | Élevée | ADR-020 ; classement en horizons (`18-…`) ; gel de l'intervention pendant le pilote (NFR-32) ; revue mensuelle avec R-13 | Ouvert | ADR-020, R-06, R-13 |
| R-23 *(nouveau)* | Sécurité des mineurs en cas de publication ou d'échanges en ligne (capacités C9 à C11, horizon H3) : exposition, contacts avec des inconnus, modération impossible sans serveur | Faible *(tant que H3 est exclu)* | Critique | Modérée *(dormant)* | Aucune capacité H3 dans le build pilote (NFR-31) ; activation conditionnée à un nouvel ADR, un protocole éthique refait (`10-…`, `12-…`, `13-…`) et un cadre juridique vérifié | Sous contrôle | ADR-002, ADR-020, NFR-31 |
```

---

## 3. Exigences

### 3.1 `01-exigences-fonctionnelles.md` — nouvelle section à ajouter après la section 11 (avant la section 12 « hors périmètre »)

> Vérifier au préalable qu'aucun identifiant FR-35 ou supérieur n'a été utilisé dans les fiches Sprint 5 ; sinon décaler la numérotation.

```markdown
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
```

### 3.2 `02-exigences-non-fonctionnelles.md` — nouvelle section 10 (à la fin du fichier)

```markdown
## 10. Périmètre, stabilité et licences

| ID | Exigence | Critère de vérification |
|---|---|---|
| NFR-30 | Tout contenu, donnée, police ou bibliothèque tiers intégré est inscrit dans `19-registre-licences-contenus-tiers.md` avec une licence conforme à ADR-025 | Revue du registre et de `THIRD_PARTY_NOTICES` avant chaque merge concerné et à chaque fin de sprint |
| NFR-31 | Aucune fonctionnalité classée H3 (`18-…`) n'est compilée ou exposée dans le build pilote ; toute capacité H2 activée est listée dans le changelog de la version pilote | Revue des routes et des menus du build de release ; changelog (`14-…`, section 6) |
| NFR-32 | L'intervention évaluée reste stable pendant la période pilote : les 5 onglets et les flux principaux ne changent pas sans ADR | Comparaison de la navigation entre la version distribuée et la version en cours ; ADR requis pour toute modification |
```

---

## 4. `04-missions-et-sprints.md` — nouveau Bloc F (à ajouter après le Bloc E)

```markdown
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
- **Description** : trancher ADR-026 (identité globale), ADR-027 (format d'échange v2 bidirectionnel), ADR-028 (packs de contenu, dictionnaire dans une base séparée). Ces décisions touchent le schéma et le format d'échange : elles sont **le préalable** de F3, F5 et des besoins de C3.
- **Definition of Done** :
  - [ ] ADR-026, ADR-027, ADR-028 rédigés et acceptés
  - [ ] Impact sur `11-schema-donnees-room.md` et `14-charte-versionnage-contenu.md` documenté
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
- **Prérequis** : F1 (ADR-026, ADR-027), Mission C3 complète
- **Definition of Done** :
  - [ ] Commentaires enseignant renvoyés à l'élève
  - [ ] Historique de versions et recueil PDF de classe (consentement de l'élève)
  - [ ] Test à deux appareils physiques
- **Statut** : `À faire`

### Mission F4 — Gamification locale
- **Exigences** : FR-41 · **Horizon** : H2 · **Prérequis** : ADR-023
- **Statut** : `À faire`

### Mission F5 — Dictionnaire hors ligne
- **Exigences** : FR-42 · **Horizon** : H2 · **Prérequis** : F1 (ADR-028), ADR-025, audit des licences
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
```

---

## 5. `03-roadmap-developpement.md` — nouvelle section 7 (à la fin du fichier)

```markdown
## 7. Horizons produit (ADR-020)

Les sprints des Cycles DBR 1 et 2 ci-dessus **ne sont pas modifiés**. Les capacités issues de la proposition de plateforme (`18-vision-produit-et-horizons.md`) se rattachent aux horizons suivants.

| Horizon | Cycle | Contenu | Missions |
|---|---|---|---|
| **H1** | Cycle DBR 1 | Atelier d'écriture guidé, boucle de feedback, publication de classe | F2, F3 (après F1) |
| **H2** | Cycle DBR 2 | Gamification locale, dictionnaire, aides à règles, club et concours de classe | F4, F5, F6, F7 |
| **H3** | Après la soutenance | « DLearn Hub » : backend et portail web | F8 (hors périmètre de la thèse) |

Points d'attention :
- Le périmètre IA du Cycle 2 (Sprints 11 à 14) suit les niveaux d'ADR-024 : N0 (règles, dictionnaires) puis N1 (modèles embarqués) ; l'IA cloud pour élèves (N3) est exclue.
- Les décisions d'identité, de synchronisation et de packs (F1) sont un préalable à F3 et à toute évolution de la synchronisation (Mission C3, Sprint 9).
- La règle de capacité (ADR-020) s'applique : ne pas ouvrir une mission F de contenu sans capacité de validation humaine identifiée.
```

---

## 6. `09-cartographie-contenu-pedagogique.md` — nouvelle section 3.5 (après la section 3.4)

```markdown
### 3.5 Types de contenu à prévoir (ADR-020, horizons H1/H2)

La cartographie couvre aujourd'hui un seul type de contenu : l'**unité de lecture** (extrait, glossaire, exercices). La vision produit (`18-…`) en prévoit d'autres. Chacun suit les mêmes règles : statut des droits (ADR-006, ADR-025), relecture humaine, entrée dans le registre `19-…` s'il comporte un élément tiers.

| Type de contenu | Horizon | Gabarit | Validation | Droits |
|---|---|---|---|---|
| Unité de lecture | H1 (existant) | `16-gabarit-auteur-exercice.md` | Relecture native (A0) | Texte original / domaine public |
| Fiche-méthode de rédaction | H1 | À créer (extension de `16-…`) | Relecture native + encadrant | Texte original |
| Défi d'écriture | H1 | À créer (extension de `16-…`) | Relecture native | Texte original |
| Entrée de bibliothèque / inspiration | H2 | À créer | Relecture native + vérification des droits | Domaine public à vérifier par édition |
| Questionnaire de club de lecture | H2 | À créer | Relecture native | Texte original |
| Pack de dictionnaire | H2 | Voir ADR-028 (à venir) | Contrôle de licence | Registre `19-…` |

> **Règle de capacité** (ADR-020) : le seuil de 5 unités validées par niveau (section 5) reste inchangé et prioritaire. Un nouveau type de contenu n'est produit qu'avec sa capacité de validation humaine identifiée, afin de ne pas aggraver R-07.
```

---

## 7. Petits ajouts

### 7.1 `05-checklist-quotidienne.md`

Section 2 (en cours de développement) — ajouter :

```markdown
- [ ] Le nouveau code respecte la convention package-by-feature (ADR-022) ; le code existant n'est pas déplacé
- [ ] Tout contenu, donnée, police ou bibliothèque tiers ajouté est inscrit dans `19-registre-licences-contenus-tiers.md` (ADR-025)
```

Section 5 (avant merge) — ajouter :

```markdown
- [ ] Toute fonctionnalité nouvelle est rattachée à un horizon dans `18-vision-produit-et-horizons.md` ; aucune capacité H3 n'est présente dans le build pilote (NFR-31)
- [ ] Les 5 onglets et les flux principaux ne sont pas modifiés pendant la période pilote sans ADR (NFR-32)
```

### 7.2 `07-glossaire.md` — table « Acronymes propres au projet »

```markdown
| **H1 / H2 / H3** | Horizons produit : H1 = Cycle DBR 1 (noyau hors ligne), H2 = Cycle DBR 2 (enrichissements hors ligne), H3 = après la soutenance (plateforme en ligne) — voir `18-vision-produit-et-horizons.md` |
| **Publication de classe** | Recueil de productions d'élèves sélectionnées par l'enseignant, généré localement en PDF, avec consentement de l'élève |
| **Pack de contenu** | Ensemble de contenus ou de données (par exemple un dictionnaire) distribué séparément de l'APK par transfert de fichier |
| **XP (points d'expérience)** | Indicateur d'engagement dérivé de l'activité réelle ; ne mesure pas la compétence (ADR-023) |
| **Niveaux d'IA N0–N3** | Classification de l'usage de l'IA, de l'hors ligne à règles (N0) à l'IA cloud (N3, interdite) — ADR-024 |
```

### 7.3 `README.md` (dossier `docs/`)

Ajouter deux lignes à la table « Sommaire — Documents de référence » :

```markdown
| 18 | [18-vision-produit-et-horizons.md](18-vision-produit-et-horizons.md) | Vision produit, capacités dédupliquées et classement en horizons H1/H2/H3 (ADR-020) |
| 19 | [19-registre-licences-contenus-tiers.md](19-registre-licences-contenus-tiers.md) | Registre des licences des contenus, données et polices tiers (ADR-025) |
```

Ajouter un principe à la liste « Principes directeurs » :

```markdown
6. **Horizons produit** — toute nouvelle idée est classée H1/H2/H3 dans `18-vision-produit-et-horizons.md` avant d'entrer au backlog ; aucune capacité H3 n'entre dans le build pilote.
```

### 7.4 `ETAT_ACTUEL.md` — nouvelle section (avant « Sprint et cycle en cours »)

```markdown
## Vision produit (2026-09-21)

Une proposition d'extension en plateforme littéraire a été classée en horizons (ADR-020, `18-vision-produit-et-horizons.md`). **Périmètre de la thèse inchangé** : noyau pédagogique hors ligne (H1) puis enrichissements hors ligne (H2) ; la plateforme en ligne est H3, après la soutenance.

| Élément | État |
|---|---|
| ADR-020 à ADR-025 (sans impact code) | Acceptés — à intégrer dans `06-…` |
| Bloc F du backlog | Créé — F0 en cours, F1 à trancher |
| Décisions à prendre au lot suivant (impact code/format) | ADR-026 identité, ADR-027 synchronisation v2, ADR-028 packs de contenu |
| Risques ajoutés | R-22 à R-27 (dont R-26 et R-27 : constats à confirmer par test à deux appareils) |
```

---

## 8. Vérifications de cohérence (après intégration)

```bash
# Numérotation des ADR : 020 à 025 présents une seule fois dans 06
grep -n "^### ADR-02[0-5]" docs/06-architecture-technique.md

# Risques R-22 à R-27 présents dans 08
grep -n "R-2[2-7]" docs/08-registre-des-risques.md

# FR-35 à FR-46 et NFR-30 à NFR-32
grep -n "FR-3[5-9]\|FR-4[0-6]" docs/01-exigences-fonctionnelles.md
grep -n "NFR-3[0-2]" docs/02-exigences-non-fonctionnelles.md

# Aucun FR/NFR dupliqué ailleurs dans docs/ (identifiants libres avant intégration)
grep -rn "FR-3[5-9]\|FR-4[0-6]\|NFR-3[0-2]" docs/ --include=*.md | grep -v "01-exigences\|02-exigences\|04-missions\|18-vision\|INTEGRATION-DOCS-VISION"

# Bloc F dans le backlog
grep -n "Mission F[0-8]" docs/04-missions-et-sprints.md

# Aucun fichier hors docs/ modifié par ce lot
git status --short | grep -v "^.. docs/"
```

Le dernier `grep` doit ne rien afficher : ce lot est strictement documentaire.

---

## 9. Lot suivant — décisions qui touchent le code ou le format (non incluses ici)

| ADR réservé | Sujet | Pourquoi pas dans ce lot | Échéance recommandée |
|---|---|---|---|
| **ADR-026** | Identité globale des utilisateurs (`uid` UUID en plus de l'identifiant local) | Implique une migration Room et l'export/import | **Avant la fusion de la migration 5→6 si possible** ; sinon migration 6→7 dédiée. Le coût reste faible tant qu'aucune donnée de pilote n'existe (R-26) |
| **ADR-027** | Format d'échange v2 « bundles » : bidirectionnel, versionné, horodaté par enregistrement, indépendant du transport | Modifie `14-…` et la Mission C3 | Avant F3 ; utile pour le MVP actuel (R-27), indépendamment de la proposition |
| **ADR-028** | Packs de contenu (extension de FR-31) et dictionnaire dans une base séparée | Dépend d'ADR-025 et du format v2 | Avant F5 |

## 10. Actions humaines (non automatisables)

- [ ] Confirmer que les statuts `Accepted` d'ADR-020 à ADR-025 te conviennent (je les ai rédigés comme tranchés ; les repasser en `Proposed` dans le tableau de `06-…` si tu préfères une validation formelle).
- [ ] Vérifier avec l'encadrant : la politique de licences (ADR-025), le principe « refus par défaut », et la conformité aux règles de protection des données applicables au pilote.
- [ ] Vérifier qu'aucun FR-35 ou supérieur, ni R-22 ou supérieur, n'a été utilisé dans les fichiers Sprint 5 non fournis.
- [ ] Décider si H3 (« DLearn Hub ») est mentionné comme perspective dans le mémoire.
