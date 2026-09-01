# Mission A2 — Structure de packages Clean Architecture

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | A2 |
| Titre | Structure de packages Clean Architecture |
| Type | Mission planifiée |
| Sprint | Sprint 0 |
| FR/NFR concernés | NFR-16, NFR-17 |
| ADR concerné(s) | ADR-001 |
| **Statut global** | `Terminé` |
| Date de création de ce fichier | 2026-08-27 |
| Date de dernière mise à jour | 2026-09-01 |
| Dernier rapport journalier lié | `journal/2026-09-01.md` |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] La mission est décrite dans le backlog (`../04-missions-et-sprints.md`, Mission A2)
- [x] Les exigences concernées (NFR-16, NFR-17) sont identifiées
- [x] Aucune dépendance bloquante — peut démarrer en parallèle ou à la suite immédiate de la Mission A1
- [x] N/A — pas d'écran concerné, aucune maquette Figma requise

### Notes de conception
Reprendre telle quelle l'arborescence déjà définie dans `../06-architecture-technique.md` (section 2) : `domain/` (model, usecase, repository), `data/` (local/room, local/datasource, repository, ai réservé Cycle 2), `presentation/` (navigation, modules d'écran, designsystem), `di/`. Aucune nouvelle décision d'architecture requise — application concrète d'ADR-001, déjà tranché.

### Sortie de phase
- [x] Arborescence de packages arrêtée et créée dans le module `app`
- [x] Aucune question bloquante restante

**Statut de la phase :** ✅ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [x] Créer les packages `domain/model`, `domain/usecase`, `domain/repository`
- [x] Créer les packages `data/local/room`, `data/local/datasource`, `data/repository`, `data/ai` (vide, réservé Cycle 2 — ADR-003)
- [x] Créer les packages `presentation/navigation`, `presentation/<module>` (un par écran), `presentation/designsystem`
- [x] Créer le package `di/` (vide, prêt pour la Mission A5)
- [x] Vérifier qu'aucune classe placeholder ne viole la règle de dépendance dès la création

### Points de vigilance obligatoires
- [x] Respect de la séparation Clean Architecture dès la création (pas de retouche a posteriori)
- [x] Build Gradle propre après création de l'arborescence vide

### Notes d'implémentation
Déplacement des fichiers Room existants de `data/local/` vers `data/local/room/`. Mise à jour des imports dans `AppModule.kt`, `ApprentissageRepositoryImpl.kt`, `AuthRepositoryImpl.kt`. Suppression des fichiers legacy (`ui.theme.*`, `DLearnNavGraph.kt`).

**Statut de la phase :** ✅ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | N/A pour cette mission structurelle | ☐ Passant ☐ Échec ☒ N/A |
| Instrumentation (UI Compose) | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Migration Room | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Build Gradle propre | `./gradlew assembleDebug` | ✅ Passant |

**Statut de la phase :** ✅ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise de la Mission A2, `../04-missions-et-sprints.md`)
- [x] Arborescence créée et documentée dans `06-architecture-technique.md`
- [x] Règle de dépendance vérifiée (aucun import `presentation` dans `domain`)
- [x] Build Gradle propre (`./gradlew build` sans erreur)

**Statut de la phase :** ✅ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [x] `04-missions-et-sprints.md` (passer Mission A2 à `Validé`)
- [x] `06-architecture-technique.md` (structure réelle mise à jour)

### Journal de bord DBR
- [x] Entrée créée dans `../journal/2026-09-01.md`

**Statut de la phase :** ✅ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | 2026-09-01 |
| Commit(s)/PR associé(s) | `feat(architecture): structure packages Clean Architecture` |
| Statut final | Terminée ✅ |
