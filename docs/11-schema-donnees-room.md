# Schéma de données Room — Liteschreib IKII

Ce document précise le modèle de données attendu pour la Mission A4 (`04-missions-et-sprints.md`). Il doit être mis à jour à chaque évolution de schéma, en parallèle des migrations Room réelles.

## 1. Diagramme relationnel (vue d'ensemble)

```
ProfilEnseignant 1───N Classe 1───N ProfilEleve
                                        │
                                        │ 1
                                        │
                                        N
                                  Progression N───1 UniteApprentissage 1───N ExtraitLitteraire
                                        │                                          │
                                        │                                    1───N GlossaireEntree
                                  ProductionEcrite N───1 UniteApprentissage
                                        │
                                  PlanificationRevision N───1 UniteApprentissage

UniteApprentissage 1───N Exercice 1───N OptionExercice
ProfilEleve 1───N ReponseEleve N───1 Exercice

ProfilEnseignant 1───N Assignation N───1 UniteApprentissage
Assignation N───1 (ProfilEleve ou Classe)

SyncLog (table technique, indépendante du modèle métier)
```

## 2. Entités détaillées

### ProfilEleve
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant local de l'élève |
| `nom` | String | Nom affiché (peut être un pseudonyme pour le pilote, voir protocole éthique) |
| `classeId` (FK → Classe) | String | Classe de rattachement |
| `niveauGerCourant` | String | Niveau GeR actuel (A1, A2, B1…) |
| `avatarRef` | String? | Référence d'avatar local (FR-03) |
| `dateCreation` | Long (timestamp) | Date de création du profil |
| `codeAcces` | String? | PIN local optionnel si device partagé, utilisé par l'écran de sélection de profil de l'application unique (ADR-009, FR-33) |

### ProfilEnseignant
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant local de l'enseignant |
| `nom` | String | Nom affiché |
| `etablissement` | String? | Nom de l'établissement |

### Classe
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant de la classe |
| `nom` | String | Ex. « 3ème A » |
| `enseignantId` (FK → ProfilEnseignant) | String | Enseignant responsable |

### UniteApprentissage
Correspond à une ligne validée de `09-cartographie-contenu-pedagogique.md`.

| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String | Identique à l'ID Unité de la cartographie (ex. `U-001`) |
| `niveauGer` | String | Niveau GeR ciblé |
| `chapitreCurriculum` | String | Référence au chapitre *Ihr und Wir Plus* |
| `titre` | String | Titre de l'unité |
| `objectifsApprentissage` | String | Texte libre ou liste sérialisée |
| `ordreAffichage` | Int | Ordre de présentation dans le parcours |

### ExtraitLitteraire
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant de l'extrait |
| `uniteId` (FK → UniteApprentissage) | String | Unité associée |
| `texteAllemand` | String (long text) | Contenu de l'extrait |
| `auteur` | String? | Auteur de l'œuvre |
| `source` | String? | Référence bibliographique |
| `statutDroits` | String | `domaine_public` / `autorisation_obtenue` / `texte_original` — reflète la cartographie |

### GlossaireEntree
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant |
| `extraitId` (FK → ExtraitLitteraire) | String | Extrait associé |
| `motAllemand` | String | Mot ou expression surligné (FR-10) |
| `traductionFr` | String | Traduction/définition en français |

### Exercice
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant |
| `uniteId` (FK → UniteApprentissage) | String | Unité associée |
| `type` | Enum (`QCM`, `TEXTE_A_TROUS`, `VRAI_FAUX`, `PRODUCTION_GUIDEE`) | Type d'exercice |
| `enonce` | String | Consigne |
| `correctionAttendue` | String? | Réponse attendue (hors QCM, voir OptionExercice) |

### OptionExercice
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant |
| `exerciceId` (FK → Exercice) | String | Exercice associé |
| `texteOption` | String | Texte de l'option |
| `estCorrecte` | Boolean | Vrai si c'est la bonne réponse |

### ReponseEleve
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant |
| `eleveId` (FK → ProfilEleve) | String | Élève ayant répondu |
| `exerciceId` (FK → Exercice) | String | Exercice concerné |
| `reponseDonnee` | String | Réponse fournie |
| `estCorrecte` | Boolean | Résultat de la correction offline |
| `dateReponse` | Long (timestamp) | Date de la réponse |

### ProductionEcrite
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant |
| `eleveId` (FK → ProfilEleve) | String | Auteur |
| `uniteId` (FK → UniteApprentissage) | String | Unité liée |
| `contenuTexte` | String (long text) | Texte rédigé par l'élève |
| `dateCreation` | Long | Date de création |
| `dateModification` | Long | Dernière modification (sauvegarde auto, FR-15) |
| `autoEvaluationJson` | String? | Résultat de la grille d'auto-évaluation (FR-17), sérialisé |

### Progression
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant |
| `eleveId` (FK → ProfilEleve) | String | Élève concerné |
| `uniteId` (FK → UniteApprentissage) | String | Unité concernée |
| `statut` | Enum (`NON_COMMENCE`, `EN_COURS`, `TERMINE`) | État d'avancement (FR-14) |
| `scoreMoyen` | Float? | Score moyen aux exercices de l'unité |
| `dateMiseAJour` | Long | Dernière mise à jour |

### PlanificationRevision
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant |
| `eleveId` (FK → ProfilEleve) | String | Élève concerné |
| `uniteId` (FK → UniteApprentissage) | String | Unité ou item à réviser |
| `dateProchaineRevision` | Long | Échéance calculée (FR-22) |
| `etatAlgorithme` | String | État sérialisé de l'algorithme de répétition espacée (paramètres type FSRS) |

### Assignation
| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant |
| `enseignantId` (FK → ProfilEnseignant) | String | Enseignant à l'origine de l'assignation |
| `cibleType` | Enum (`ELEVE`, `CLASSE`) | Type de cible |
| `cibleId` | String | `eleveId` ou `classeId` selon `cibleType` |
| `uniteId` (FK → UniteApprentissage) | String | Contenu assigné |
| `dateAssignation` | Long | Date de l'assignation (FR-26) |

### SyncLog (table technique)
Reflète le mécanisme tranché en **ADR-004** : export/import de fichier via les mécanismes de partage natifs de l'appareil (Nearby Share en priorité, repli Bluetooth classique/carte SD).

| Champ | Type | Description |
|---|---|---|
| `id` (PK) | String (UUID) | Identifiant |
| `appareilSource` | String | Identifiant local de l'appareil source |
| `appareilCible` | String? | Identifiant de l'appareil cible (si connu) |
| `canalTransfert` | Enum (`NEARBY_SHARE`, `BLUETOOTH`, `FICHIER_MANUEL`) | Canal effectivement utilisé (ADR-004) — permet de mesurer la fréquence de repli (risque R-15) |
| `versionFichierEchange` | String | Numéro de version du format d'échange (voir `14-charte-versionnage-contenu.md`) |
| `dateSync` | Long | Date de synchronisation |
| `statut` | Enum (`SUCCES`, `ECHEC`, `PARTIEL`) | Résultat |
| `resumePayload` | String? | Résumé de ce qui a été transféré (débogage) |

## 3. Stratégie de pré-population

- Le contenu (`UniteApprentissage`, `ExtraitLitteraire`, `GlossaireEntree`, `Exercice`, `OptionExercice`) est fourni sous forme de fichiers **JSON en assets**, générés à partir de la cartographie validée (`09-cartographie-contenu-pedagogique.md`).
- Au premier lancement, un `RoomDatabase.Callback.onCreate()` (ou une tâche d'initialisation dédiée) charge ces JSON et peuple la base — **aucun réseau requis** (NFR-03).
- Les tables liées à l'usage (`ProfilEleve`, `Progression`, `ReponseEleve`, `ProductionEcrite`, `PlanificationRevision`, `Assignation`, `SyncLog`) ne sont **jamais** pré-peuplées : elles se remplissent à l'usage réel.

## 4. Stratégie de migration

- Chaque évolution de schéma incrémente la version de la base (`@Database(version = n)`).
- Une migration explicite (`Migration(n-1, n)`) est écrite et testée avant tout merge modifiant une entité existante (NFR-22).
- Les migrations liées au contenu pédagogique (ajout d'unités, de niveaux GeR) doivent être distinguées des migrations liées à la structure (ajout de colonnes) — les premières peuvent souvent être gérées par un simple ajout de données plutôt qu'une migration de schéma.
- Toute migration est accompagnée d'un test de migration Room (lecture d'une base à l'ancienne version, vérification post-migration).

## 5. Points ouverts restants (les autres ont été tranchés en ADR-004, ADR-006, ADR-009)

- Format exact de sérialisation de `etatAlgorithme` (JSON libre vs colonnes dédiées) — dépend de l'algorithme de répétition espacée retenu (voir roadmap Sprint 7).
- Granularité exacte de `resumePayload` dans `SyncLog` (niveau de détail pour le débogage sans exposer de données sensibles).
