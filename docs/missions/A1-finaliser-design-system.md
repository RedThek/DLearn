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
| **Statut global** | `Validation` |
| Date de création de ce fichier | 2026-08-27 |
| Date de dernière mise à jour | 2026-09-02 |
| Dernier rapport journalier lié | `../journal/2026-08-27.md` |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] La mission est décrite dans le backlog (`../04-missions-et-sprints.md`, Mission A1)
- [x] Les exigences concernées (NFR-13, NFR-16) sont identifiées
- [x] Aucune dépendance bloquante (mission de démarrage de sprint)
- [x] ADR-014 (Abandon Figma) validé

### Notes de conception
Le design system suit Material Design 3, couleur dynamique désactivée pour préserver la fidélité aux maquettes Figma. Quatre fichiers structurent la couche présentation : `Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt`. ADR-014 introduit le pivot vers une génération UI pilotée par agent sans maquette Figma systématique.

### Sortie de phase
- [x] Approche technique arrêtée : canonisation des tokens Color.kt
- [x] Aucune question bloquante restante

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [x] Scaffolding initial de `Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt`
- [x] Canoniser `Color.kt` avec la palette validée (ADR-014)
- [x] Mettre à jour `Type.kt` (commentaire ADR-014)
- [x] Vérifier `Shape.kt`
- [x] Vérifier `Theme.kt` (assemblage final, dynamique désactivée)

### Points de vigilance obligatoires
- [x] Respect de la séparation Clean Architecture (fichiers dans `presentation/theme/`)
- [x] Aucune régression offline-first
- [x] Fidélité au design system canonisé

### Notes d'implémentation
Canonisation de `Color.kt` effectuée le 2026-09-02 suite à l'ADR-014. Suppression des `TODO` Figma.

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

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
