# Mission A5 — Modules Hilt

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | A5 |
| Titre | Modules Hilt |
| Type | Mission planifiée |
| Sprint | Sprint 3 |
| FR/NFR concernés | NFR-17 |
| ADR concerné(s) | ADR-001 |
| **Statut global** | `Conception` — **non démarrée** |
| Date de création de ce fichier | 2026-08-27 |
| Date de dernière mise à jour | 2026-08-27 |
| Dernier rapport journalier lié | — *(à créer au démarrage réel)* |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] La mission est décrite dans le backlog (`../04-missions-et-sprints.md`, Mission A5)
- [x] L'exigence concernée (NFR-17) est identifiée
- [ ] **Dépendance bloquante non satisfaite** : la Mission A2 (structure de packages, dont `di/`) doit être terminée
- [ ] **Dépendance bloquante non satisfaite** : la Mission A4 (entités Room) doit être suffisamment avancée pour que les implémentations de repository existent à lier
- [x] Aucune maquette Figma requise (mission technique pure)

### Notes de conception
Modules envisagés : `AppModule` (dépendances singleton type contexte applicatif, `TtsManager`), `DataModule` (bindings des interfaces `Repository` du domaine vers leurs implémentations `data`, instance `RoomDatabase`), et `DomainModule` si des cas d'usage nécessitent un binding explicite. Scopes envisagés : `@Singleton` pour la base de données et les repositories, `@ViewModelScoped` si un cas d'usage doit être recréé par écran. Aucune nouvelle décision d'architecture structurante n'est attendue — application directe d'ADR-001.

### Sortie de phase
- [ ] Liste définitive des modules et de leurs scopes arrêtée
- [ ] Aucune question bloquante restante *(en attente des clôtures de A2 et A4)*

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Annoter la classe `Application` avec `@HiltAndroidApp`
- [ ] Créer `AppModule` (dépendances applicatives génériques)
- [ ] Créer `DataModule` (bindings `Repository`, instance `RoomDatabase`, DAO)
- [ ] Créer `DomainModule` si nécessaire (bindings de cas d'usage spécifiques)
- [ ] Retirer toute instanciation manuelle de dépendance déjà présente dans le code existant (le cas échéant)

### Points de vigilance obligatoires
- [ ] Aucune instanciation manuelle de repository/use case dans les ViewModels après cette mission
- [ ] Scopes cohérents avec le cycle de vie réel des dépendances (éviter les fuites mémoire)

### Notes d'implémentation
*(à compléter au démarrage)*

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | N/A directement, mais les tests existants ne doivent pas régresser après intégration Hilt | ☐ Passant ☐ Échec |
| Instrumentation (UI Compose) | Vérifier qu'un écran déjà implémenté (le cas échéant) reste fonctionnel après injection | ☐ Passant ☐ Échec ☒ N/A |
| Migration Room | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Build complet | `./gradlew build` après intégration Hilt | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise de la Mission A5, `../04-missions-et-sprints.md`)
- [ ] Aucune instanciation manuelle de repository/use case dans les ViewModels
- [ ] Build + tests passants après intégration Hilt
- [ ] Documentation courte des scopes retenus dans `06-architecture-technique.md`

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `04-missions-et-sprints.md` (passer Mission A5 à `Validé`)
- [ ] `06-architecture-technique.md` (section injection de dépendances : modules et scopes retenus)

### Journal de bord DBR
- [ ] Entrée à créer dans `../journal/` au démarrage réel

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | Non démarrée — dépend de la clôture des Missions A2 et A4 |
