# Mission A1 — Finaliser le design system

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | A1 |
| Titre | Finaliser le design system |
| Type | Mission planifiée |
| Sprint | Sprint 0 |
| FR/NFR concernés | NFR-13 (contraste), NFR-16 (Clean Architecture) |
| ADR concerné(s) | — |
| **Statut global** | `Implémentation` |
| Date de création de ce fichier | 2026-08-27 |
| Date de dernière mise à jour | 2026-08-27 |
| Dernier rapport journalier lié | `../journal/2026-08-27.md` |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] La mission est décrite dans le backlog (`../04-missions-et-sprints.md`, Mission A1)
- [x] Les exigences concernées (NFR-13, NFR-16) sont identifiées
- [x] Aucune dépendance bloquante (mission de démarrage de sprint)
- [x] Les maquettes Figma (Apprentissage, Suivi, Profil élève) sont en état Dev Mode

### Notes de conception
Le design system suit Material Design 3, couleur dynamique désactivée pour préserver la fidélité aux maquettes Figma. Quatre fichiers structurent la couche présentation : `Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt`. Aucune décision d'architecture nouvelle nécessaire (cohérent avec ADR-001).

### Sortie de phase
- [x] Approche technique arrêtée : reprise exacte des tokens Figma Dev Mode, pas de valeurs approximées
- [x] Aucune question bloquante restante

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [x] Scaffolding initial de `Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt`
- [ ] Remplacer les `TODO` de `Color.kt` par les valeurs exactes du panneau Figma Dev Mode (Inspect)
- [ ] Remplacer les `TODO` de `Type.kt` (échelle typographique)
- [ ] Remplacer les `TODO` de `Shape.kt` (rayons de coins, élévations)
- [ ] Vérifier `Theme.kt` (assemblage final, dynamique désactivée)

### Points de vigilance obligatoires
- [x] Respect de la séparation Clean Architecture (fichiers dans `presentation/designsystem/`)
- [x] Aucune régression offline-first (aucun impact réseau sur cette mission)
- [ ] Fidélité au design system — **en cours de vérification**, TODO restants à résoudre

### Notes d'implémentation
Le scaffolding de base est en place avec des valeurs provisoires marquées `TODO`. 
*Session 2026-08-29* : Rétablissement du build suite à des imports manquants dans `ApprentissageScreen.kt` et `EcritureScreen.kt` (placeholders UI).
Prochaine session : exporter les tokens exacts depuis Figma Dev Mode (Inspect panel) et les reporter fichier par fichier.

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | N/A pour cette mission (couche présentation pure) | ☐ Passant ☐ Échec ☒ N/A |
| Instrumentation (UI Compose) | Comparaison visuelle Compose Preview vs Figma | ☐ Passant ☐ Échec — *à faire une fois les TODO résolus* |
| Migration Room | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Test manuel offline | N/A (pas d'impact réseau) | ☐ Passant ☐ Échec ☒ N/A |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise de la Mission A1, `04-missions-et-sprints.md`)
- [ ] Aucun `TODO` restant dans les fichiers du design system
- [ ] Comparaison visuelle écran-par-écran (Compose Preview vs Figma) validée
- [ ] Contraste vérifié avec Stark (NFR-13)
- [ ] Commit dédié avec message clair (`feat(design-system): tokens Figma définitifs`)

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `04-missions-et-sprints.md` (passer Mission A1 à `Validé` une fois terminée)

### Journal de bord DBR
- [x] Entrée créée dans `../journal/2026-08-27.md`

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non clôturée)* |
| Commit(s)/PR associé(s) | *(à venir)* |
| Statut final | En cours — reprise prévue par l'export des tokens Figma Dev Mode |
