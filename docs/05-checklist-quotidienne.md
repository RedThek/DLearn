# Checklists de développement — Liteschreib IKII

Ces checklists rythment le travail quotidien afin de sécuriser la qualité technique, le respect des contraintes du projet (offline-first, Clean Architecture) et la traçabilité nécessaire à la recherche DBR.

## 1. Checklist de début de journée

- [ ] Lire `docs/ETAT_ACTUEL.md` (point d'entrée unique de reprise de contexte)
- [ ] Si reprise après une pause : lire le dernier rapport dans `docs/journal/` et la fiche active dans `docs/missions/` avant toute chose (voir `docs/processus/guide-orchestration.md`, section 5)
- [ ] `git pull` sur la branche de travail (ou `develop`) pour repartir d'un état à jour
- [ ] Relire l'objectif du sprint en cours et la mission assignée du jour (`04-missions-et-sprints.md`)
- [ ] Vérifier que le build compile sur l'état actuel (`./gradlew build`) avant toute modification
- [ ] Créer/mettre à jour une branche dédiée à la mission du jour (voir convention de nommage dans `06-architecture-technique.md`)
- [ ] Créer l'entrée du jour dans `docs/journal/` depuis `_gabarit-rapport-journalier.md` (même incomplète, à compléter en fin de journée)

## 2. Checklist en cours de développement

- [ ] Respecter la séparation des couches : aucune classe `domain` n'importe Android SDK ou Compose
- [ ] Toute nouvelle dépendance ajoutée est justifiée et compatible offline-first (pas de SDK cloud, pas d'analytics)
- [ ] Toute donnée sensible (profil, production écrite) reste locale — aucun appel réseau introduit par erreur
- [ ] Les valeurs de couleur/typographie/espacement utilisées proviennent du design system (`Color.kt`, `Type.kt`, `Shape.kt`), jamais codées en dur dans un composable
- [ ] Chaque nouveau cas d'usage (`UseCase`) du domaine est accompagné d'au moins un test unitaire
- [ ] Chaque nouvel écran est validé visuellement sur device/émulateur, screenshot archivé dans docs/screenshots/<ID-mission>/

## 3. Checklist de fin de journée

- [ ] Build propre (`./gradlew build`) et lint sans nouvelle erreur (`./gradlew lint`)
- [ ] Suite de tests concernée exécutée localement et passante
- [ ] Commit(s) réalisés avec messages clairs (convention type Conventional Commits — voir `06-architecture-technique.md`)
- [ ] Push de la branche vers GitHub (sauvegarde, pas de travail non versionné en fin de journée)
- [ ] Mise à jour du statut de la mission du jour dans le tracker (`À faire` → `En cours` / `En revue` / `Validé`)
- [ ] Fiche vivante de la mission mise à jour dans `docs/missions/<ID>-<slug>.md` (phase courante, sous-tâches, notes)
- [ ] Entrée du jour complétée dans `docs/journal/YYYY-MM-DD.md` (voir `_gabarit-rapport-journalier.md`) — c'est elle qui permettra une reprise de contexte immédiate
- [ ] `docs/ETAT_ACTUEL.md` mis à jour si la mission active ou la phase en cours a changé
- [ ] `TODO`/`FIXME` laissés dans le code sont explicites et référencés dans une issue si non résolus le jour même

## 4. Checklist hebdomadaire (mi-sprint / fin de sprint)

- [ ] Revue de l'avancement du sprint par rapport à l'objectif (`03-roadmap-developpement.md`)
- [ ] Vérification de la couverture de tests sur les modules touchés dans la semaine
- [ ] Vérification NFR : test en mode avion des écrans modifiés (offline-first, NFR-01)
- [ ] Vérification accessibilité (Stark) sur tout nouvel écran ou modification visuelle
- [ ] Nettoyage du backlog : missions terminées marquées `Validé`, nouvelles missions identifiées ajoutées
- [ ] Pipeline CI (GitHub Actions) vérifié vert sur `develop`
- [ ] Point sur les dépendances bloquantes entre missions (voir section 6 de la roadmap)

## 5. Checklist avant merge sur `develop`/`main`

- [ ] Pull Request créée avec description reliant la mission concernée (`04-missions-et-sprints.md`)
- [ ] Definition of Done de la mission entièrement cochée
- [ ] Revue de code effectuée (auto-revue si travail solo, ou revue croisée si encadrant/pair disponible)
- [ ] CI verte (build + tests + lint)
- [ ] Aucun conflit de fusion non résolu
- [ ] Documentation technique mise à jour si l'architecture ou une exigence a évolué

## 6. Checklist avant un jalon académique (mois 6 / mois 12)

- [ ] Version fonctionnelle du périmètre concerné testée de bout en bout
- [ ] Résultats d'évaluation (heuristique/pilote) synthétisés
- [ ] Documents techniques (`01` à `07`) à jour et cohérents avec l'état réel du code
- [ ] Journal de bord DBR consolidé pour rédaction du mémoire
