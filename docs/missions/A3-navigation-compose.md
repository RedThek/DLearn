# Mission A3 — Navigation Compose

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | A3 |
| Titre | Navigation Compose |
| Type | Mission planifiée |
| Sprint | Sprint 1 |
| FR/NFR concernés | FR-33 |
| ADR concerné(s) | ADR-009 |
| **Statut global** | `Conception` — **non démarrée** |
| Date de création de ce fichier | 2026-08-27 |
| Date de dernière mise à jour | 2026-08-27 |
| Dernier rapport journalier lié | — *(à créer au démarrage réel)* |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] La mission est décrite dans le backlog (`../04-missions-et-sprints.md`, Mission A3)
- [x] Les exigences concernées (FR-33, navigation à 5 onglets) sont identifiées
- [ ] **Dépendance bloquante non satisfaite** : la Mission A2 (structure de packages) doit être terminée avant de démarrer celle-ci
- [x] Maquettes Figma en état Dev Mode disponibles pour Apprentissage, Suivi, Profil élève ; dashboard enseignant en cours (Mission C1)

### Notes de conception
La navigation repose sur un graphe racine avec un écran de sélection de profil (ADR-009 — application unique, sélecteur de rôle), suivi de deux sous-graphes distincts : élève (5 onglets : Accueil, Apprentissage, Écriture, Suivi, Profil) et enseignant (dashboard). Prévoir `NavHost` imbriqué (nested navigation) plutôt qu'un unique graphe plat, pour isoler proprement les deux parcours.

### Sortie de phase
- [ ] Approche technique de la navigation imbriquée arrêtée
- [ ] Aucune question bloquante restante *(en attente de la clôture de A2)*

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [ ] Écran de sélection de profil (Élève/Enseignant) au lancement, si plusieurs profils existent (FR-33)
- [ ] `NavHost` racine avec les deux sous-graphes (élève / enseignant)
- [ ] `BottomNavigationBar` à 5 onglets pour l'élève, fidèle à la maquette Figma
- [ ] Route dashboard enseignant reliée au sous-graphe enseignant
- [ ] Gestion du retour arrière cohérente entre les graphes
- [ ] Bascule de profil sur appareil partagé (voir `codeAcces`, `11-schema-donnees-room.md`)

### Points de vigilance obligatoires
- [ ] Respect de la séparation Clean Architecture (logique de navigation cantonnée à `presentation/navigation`)
- [ ] Fidélité Figma pour la `BottomNavigationBar` (couleurs, icônes du design system)

### Notes d'implémentation
*(à compléter au démarrage)*

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Instrumentation (UI Compose) | Navigation entre les 5 onglets + bascule de profil | ☐ Passant ☐ Échec |
| Migration Room | N/A | ☐ Passant ☐ Échec ☒ N/A |
| Test manuel offline | N/A (aucun appel réseau dans cette mission) | ☐ Passant ☐ Échec ☒ N/A |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise de la Mission A3, `../04-missions-et-sprints.md`)
- [ ] Écran de sélection de profil fonctionnel si plusieurs profils existent
- [ ] `NavHost` fonctionnel avec les 5 routes élève + route enseignant
- [ ] `BottomNavigationBar` fidèle à la maquette Figma
- [ ] Navigation testée manuellement (aucun crash, retour arrière cohérent, bascule de profil correcte)
- [ ] Test d'instrumentation basique de navigation

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [ ] `04-missions-et-sprints.md` (passer Mission A3 à `Validé`)
- [ ] `01-exigences-fonctionnelles.md` si FR-33 nécessite une précision après implémentation réelle

### Journal de bord DBR
- [ ] Entrée à créer dans `../journal/` au démarrage réel

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non démarrée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | Non démarrée — dépend de la clôture de la Mission A2 |
