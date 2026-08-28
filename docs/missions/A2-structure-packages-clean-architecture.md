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
| **Statut global** | `Conception` — **non démarrée** |
| Date de création de ce fichier | 2026-08-27 |
| Date de dernière mise à jour | 2026-08-27 |
| Dernier rapport journalier lié | — *(à créer au démarrage réel)* |

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
- [ ] Arborescence de packages arrêtée et créée dans le module `app`
- [ ] Aucune question bloquante restante

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Créer les packages `domain/model`, `domain/usecase`, `domain/repository`
- [ ] Créer les packages `data/local/room`, `data/local/datasource`, `data/repository`, `data/ai` (vide, réservé Cycle 2 — ADR-003)
- [ ] Créer les packages `presentation/navigation`, `presentation/<module>` (un par écran), `presentation/designsystem`
- [ ] Créer le package `di/` (vide, prêt pour la Mission A5)
- [ ] Vérifier qu'aucune classe placeholder ne viole la règle de dépendance dès la création

### Points de vigilance obligatoires
- [ ] Respect de la séparation Clean Architecture dès la création (pas de retouche a posteriori)
- [ ] Build Gradle propre après création de l'arborescence vide

### Notes d'implémentation
*(à compléter au démarrage)*

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | N/A pour cette mission structurelle | ☐ Passant ☐ Échec ☒ N/A |
| Instrumentation (UI Compose) | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Migration Room | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Build Gradle propre sur l'arborescence vide | `./gradlew build` | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise de la Mission A2, `../04-missions-et-sprints.md`)
- [ ] Arborescence créée et documentée dans `06-architecture-technique.md`
- [ ] Règle de dépendance vérifiée (aucun import `presentation` dans `domain`)
- [ ] Build Gradle propre (`./gradlew build` sans erreur)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `04-missions-et-sprints.md` (passer Mission A2 à `Validé`)
- [ ] `06-architecture-technique.md` si l'arborescence réelle créée diverge de celle déjà documentée en section 2

### Journal de bord DBR
- [ ] Entrée à créer dans `../journal/` au démarrage réel

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | Non démarrée — prête à être prise en Sprint 0, à la suite ou en parallèle de la Mission A1 |
