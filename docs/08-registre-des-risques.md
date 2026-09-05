# Registre des risques — Liteschreib IKII

Ce registre recense les risques identifiés pour le projet (techniques, pédagogiques, éthiques, organisationnels), à réévaluer à chaque jalon de sprint et à chaque jalon académique. Un risque non traité qui se matérialise doit être consigné dans le journal de bord DBR (traçabilité pour le mémoire).

## Légende
- **Probabilité** : Faible / Moyenne / Élevée
- **Impact** : Faible / Moyen / Élevé / Critique
- **Criticité** = Probabilité × Impact (indicatif : Faible, Modérée, Élevée, Critique)
- **Statut** : Ouvert / Sous contrôle / Clos

---

## Risques techniques

| ID | Risque | Probabilité | Impact | Criticité | Mitigation | Statut | Lié à |
|---|---|---|---|---|---|---|---|
| R-01 | Aucune voix TTS allemande n'est préinstallée sur les appareils bas de gamme visés, nécessitant un téléchargement (contredit l'offline-first) | Moyenne | Critique | Modérée *(réduite)* | **Tranché (ADR-007)** : téléchargement ponctuel de la voix en contexte connecté (école), avant tout usage terrain hors ligne — exception documentée en NFR-01-bis. Risque résiduel : un élève sans jamais aucun accès réseau ne pourrait pas installer la voix ; à couvrir par le guide enseignant (installation groupée en séance) | Sous contrôle | FR-11, FR-32, NFR-01-bis, ADR-007 |
| R-02 | Fragmentation du parc Android (versions, résolutions, RAM) rend certains comportements imprévisibles | Élevée | Moyen | Modérée | **Tranché (ADR-005, ADR-012)** : `minSdkVersion = 28` fixé, devices de référence confirmés (Tecno, Itel et sous-marques). Tester systématiquement sur au moins un appareil de chaque famille avant chaque jalon de sprint UI | Sous contrôle | NFR-04, NFR-06 |
| R-03 | Mécanisme de synchronisation locale BYOD complexe à fiabiliser (conflits de données, pertes) | Moyenne | Élevé | Élevée | **Tranché (ADR-004)** : export/import de fichier via partage système. Risque résiduel : Nearby Share dépend de Google Play Services, absent sur certains appareils bas de gamme — repli Bluetooth classique/carte SD à tester explicitement (voir R-15) | Sous contrôle | FR-29 à FR-31 |
| R-04 | Perte de données élève en cas de casse/perte de l'appareil (pas de sauvegarde cloud, choix assumé) | Moyenne | Élevé | Élevée | Prévoir une fonctionnalité d'export local régulier (vers stockage externe/SD) même minimale ; documenter la limite comme un compromis assumé (ADR-002) | Ouvert | NFR-09 |
| R-05 | Intégration IA Phase 3 (Gemini Nano) incompatible avec les appareils réellement utilisés par le public cible | Élevée | Moyen | Modérée | Concevoir un fallback gracieux dès l'origine (interface `PronunciationEvaluator` avec implémentation par défaut non-IA) ; ne pas conditionner le socle à la disponibilité de Gemini Nano | Ouvert | ADR-003, Mission E2 |
| R-15 *(nouveau)* | Nearby Share (Google Play Services) indisponible ou instable sur certains appareils bas de gamme du parc pilote, dégradant la synchronisation locale (ADR-004) | Moyenne | Moyen | Modérée | Tester systématiquement le canal de repli (Bluetooth classique / export-import manuel de fichier via carte SD) sur les devices de référence avant le pilote | Ouvert | ADR-004, Mission C3 |
| R-16 *(nouveau)* | L'installation par APK partagé localement (ADR-010) déclenche des avertissements de sécurité (Play Protect, « sources inconnues ») pouvant freiner l'adoption ou inquiéter les parents | Moyenne | Moyen | Modérée | Accompagner l'installation via le guide enseignant (`15-guide-enseignant-onboarding.md`) et mentionner explicitement cette étape dans la note d'information parents (`10-protocole-ethique-consentement.md`) | Ouvert | ADR-010, Mission D0 |
| R-17 *(nouveau)* | Le dépôt GitHub hébergeant le projet est **public** (ADR-013) — risque de fuite accidentelle de données sensibles (nom d'élève, formulaire de consentement signé, export de données de recherche, capture d'écran contenant des informations personnelles) si elles sont committées par erreur | Faible | Critique | Élevée | Ne jamais committer de données réelles de participants, uniquement des gabarits vides ; stocker les données de recherche réelles hors du dépôt (cohérent avec `13-plan-gestion-donnees-recherche.md`) ; vérification explicite ajoutée à la checklist avant merge (`05-checklist-quotidienne.md`) | Ouvert | ADR-013, `13-plan-gestion-donnees-recherche.md` |
| R-18 *(nouveau)* | Absence de table `Classe` relationnelle (ADR-016) — pas de cloisonnement élèves/enseignant en cas de pilote multi-enseignants sur appareils partagés | Faible | Moyen | Modérée | Réévaluer si le pilote s'étend au-delà d'un enseignant/classe par appareil ; migration vers un modèle relationnel si nécessaire | Ouvert | ADR-016 |
| R-19 *(nouveau)* | Aucune écriture réelle dans la table `progression` avant Sprint 4 : `MarquerUniteEnCoursUseCase`/`MarquerUniteTermineeUseCase` existaient mais n'étaient appelés par aucun ViewModel — les statistiques Suivi/Enseignant restaient silencieusement à zéro malgré un usage réel (bug B-28, corrigé ce sprint) | Élevée | Élevé | Élevée *(corrigé)* | Wiring complet en Sprint 4 (Phases 1-2 backend, Phase 2 frontend) ; ajouter un test d'intégration ViewModel pour éviter la régression | Clos | FR-20, FR-21, FR-25, Mission B4/C2 |
| R-20 *(nouveau)* | Le module Écriture n'était jamais lié au niveau/à l'unité réelle de l'élève avant Sprint 4 : `EcritureViewModel` chargeait systématiquement la première unité du catalogue (`U-6E-01`), quel que soit le niveau de l'élève (bug B-29, corrigé ce sprint) | Moyenne | Moyen | Modérée *(corrigé)* | Argument de navigation `uniteId` + repli par niveau GeR, voir Phase 2 (backend) et Phase 1 (frontend) | Clos | FR-15, Mission B3 |
| R-06 | Développeur unique sur le projet : indisponibilité (maladie, charge académique) bloque tout le calendrier | Moyenne | Élevé | Élevée | Documentation continue (ce dossier `/docs`) permettant une reprise ou un support ponctuel externe ; marges de sécurité dans le calendrier de sprints | Ouvert | Roadmap |

## Risques pédagogiques / méthodologiques

| ID | Risque | Probabilité | Impact | Criticité | Mitigation | Statut | Lié à |
|---|---|---|---|---|---|---|---|
| R-07 | Volume/profondeur de contenu pédagogique insuffisant par niveau au moment du pilote pour une évaluation significative | Élevée | Élevé | **Élevée** | **Décision (ADR-008, portée confirmée)** : le MVP couvre le collège complet (6e, 5e, 4e, 3e), une bande CECR homogène A1→A2 — le lycée (B1-B2) est reporté, ce qui réduit la variance de niveau par rapport à une couverture étalée jusqu'à B2. Seuil révisé à **5 unités validées par niveau** (20 au total). Mitigation : 4 unités brouillon rédigées (1/niveau), seuil non encore atteint, aucune validée (voir `09-cartographie-contenu-pedagogique.md`, section 5) | Ouvert — sous surveillance renforcée, Mission A0 en cours | ADR-008, Mission A0, Mission A4, cartographie contenu |
| R-08 | Statut légal/droits d'auteur du contenu littéraire utilisé (extraits *Ihr und Wir Plus* ou œuvres tierces) incertain | Moyenne | Élevé | Modérée *(réduite)* | **Tranché (ADR-006)** : approche hybride — domaine public + textes originaux dès le Cycle 1, demande d'autorisation éditeur engagée en parallèle sans dépendance calendaire | Sous contrôle | ADR-006, Cartographie contenu, Mission A4 |
| R-09 | La correspondance contenu ↔ niveau GeR/CECR n'est pas suffisamment rigoureuse pour être défendable académiquement | Moyenne | Élevé | Élevée | Faire valider la cartographie du contenu par l'encadrant académique avant intégration en base | Ouvert | NFR-25, cartographie contenu |
| R-10 | Faible adoption/engagement des enseignants pilotes (dashboard perçu comme complexe ou peu utile) | Moyenne | Moyen | Modérée | Impliquer un enseignant dans la revue heuristique du dashboard avant le pilote ; prioriser FR-24/FR-25 (vue simple) avant les fonctions avancées | Ouvert | Mission C1, C2 |

## Risques éthiques / organisationnels

| ID | Risque | Probabilité | Impact | Criticité | Mitigation | Statut | Lié à |
|---|---|---|---|---|---|---|---|
| R-11 | Délai d'approbation du protocole éthique/consentement (public mineur) retarde l'évaluation pilote du mois 6 | Élevée | Critique | **Critique** | Engager la démarche éthique dès maintenant, en parallèle du développement (voir `10-protocole-ethique-consentement.md`) ; anticiper les délais institutionnels | Ouvert | Jalon mois 6 |
| R-12 | Consentement parental insuffisamment recueilli ou mal documenté | Faible | Critique | Élevée | Utiliser les gabarits normalisés (voir document éthique) ; conserver une trace signée par élève participant | Ouvert | Protocole éthique |
| R-13 | Dérive de calendrier remettant en cause la soutenance du mois 12 | Moyenne | Critique | Élevée | Revue mensuelle de l'avancement roadmap vs réel ; prioriser impitoyablement le socle (Cycle 1) sur les fonctionnalités IA en cas de retard | Ouvert | Roadmap |
| R-14 | Données de recherche collectées (Google Forms) non anonymisées correctement, posant un problème de conformité | Faible | Élevé | Modérée | Définir un plan de gestion des données de recherche précisant les champs collectés et le mode d'anonymisation | Ouvert | Document manquant : plan de gestion des données |

---

## Revue du registre

- **Fréquence** : à chaque fin de sprint (checklist hebdomadaire, `05-checklist-quotidienne.md`) et impérativement avant chaque jalon académique.
- **Responsable** : porteur du projet (développeur unique), avec validation de l'encadrant académique sur les risques R-08, R-09, R-11 à R-14.
- **Procédure en cas de matérialisation d'un risque critique** : consigner l'événement, la date, l'impact réel et la décision prise dans le journal de bord DBR, puis mettre à jour ce registre.
