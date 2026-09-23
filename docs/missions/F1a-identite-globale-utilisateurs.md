# Mission F1a — Identité globale des utilisateurs

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F1a |
| Titre | Identité globale des utilisateurs (`uid`, `instanceId`) |
| Type | Mission planifiée |
| Sprint | À planifier — **avant la Mission D0 et avant tout usage réel de l'échange v2** ; groupable avec la migration 5→6 si elle n'est pas encore fusionnée |
| FR/NFR concernés | NFR-33, NFR-35 |
| ADR concerné(s) | ADR-026 |
| **Statut global** | `Conception` — non démarrée |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — *(à créer au démarrage réel)* |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] ADR-026 accepté et intégré à `06-architecture-technique.md`
- [ ] **Test à deux appareils exécuté** (`TEST-PROTOCOLE-F1-DEUX-APPAREILS.md`), confirmant R-26
- [ ] Décision prise sur le regroupement ou non avec la migration Room 5→6

### Notes de conception
Ajout d'une colonne `uid` (UUID v4, index unique) sur `UtilisateurEntity`, d'un index unique sur `identifiant`, d'un `instanceId` en DataStore (jamais dérivé d'un identifiant matériel), et conversion de `assignation.cibleId` (cible `ELEVE`) du `Long` local vers le `uid`. Les comptes de démonstration (`eleve.2451`, `enseignant.100`) reçoivent des `uid` fixes reconnaissables et sont exclus du build de release (NFR-33). Détail complet des faits et des options écartées : `06-architecture-technique.md` (ADR-026).

### Sortie de phase
- [ ] Schéma exact de la migration arrêté (voir `11-schema-donnees-room.md` section 6)
- [ ] Aucune question bloquante restante

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Migration Room : ajout `utilisateur.uid` (défaut généré via `randomblob`), index unique sur `uid` et sur `identifiant`
- [ ] Génération du `uid` à la création de compte (`AuthRepositoryImpl.creerEleve`, seed initial)
- [ ] `instanceId` : génération et persistance dans `SessionManager`/DataStore
- [ ] Conversion de `assignation.cibleId` (cible `ELEVE`) vers le `uid`, avec migration des lignes existantes
- [ ] Gestion de la collision d'`identifiant` à la création de compte (nouvelle tentative)
- [ ] Isoler le `SeedCallback` de démonstration du build de release (flavor ou `BuildConfig`)
- [ ] Remplacer `Build.MODEL` par `instanceId` comme `appareilSource`/`appareilCible` dans `sync_log`

### Points de vigilance obligatoires
- [ ] Aucune donnée personnelle utilisée pour dériver le `uid` (NFR-35)
- [ ] Migration testée (`MigrationTestHelper`, ADR-017)
- [ ] Aucune régression sur les écrans existants (`ConnexionScreen`, `SelectionProfilScreen`, `EnseignantDashboardScreen`)

### Notes d'implémentation
*(à compléter au démarrage)*

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Migration Room | Ajout de `uid`, index uniques, conversion `assignation.cibleId` | ☐ Passant ☐ Échec |
| Unitaire | Génération de `uid`, gestion de collision d'`identifiant` | ☐ Passant ☐ Échec |
| Installation à froid du build de release | Aucun compte de démonstration présent (NFR-33) | ☐ Passant ☐ Échec |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done
- [ ] Migration explicite testée (ADR-017)
- [ ] Création de compte robuste aux collisions d'`identifiant`
- [ ] Build de release sans compte de démonstration (vérification à froid, R-28)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `11-schema-donnees-room.md` (schéma réel après implémentation)
- [ ] `14-charte-versionnage-contenu.md` (numéro de version Room attribué)
- [ ] `08-registre-des-risques.md` (R-26, R-28 → `Clos`)
- [ ] `04-missions-et-sprints.md` (statut de F1a)

### Journal de bord DBR
- [ ] Entrée à créer au démarrage réel

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | Non démarrée — dépend du résultat du test à deux appareils (Mission F1) |
