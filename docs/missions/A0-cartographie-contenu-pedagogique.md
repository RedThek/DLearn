# Mission A0 — Remplissage et validation de la cartographie de contenu pédagogique

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | A0 |
| Titre | Remplissage et validation de la cartographie de contenu pédagogique |
| Type | Mission planifiée (ajoutée au backlog le 2026-08-27) |
| Sprint | Sprint 0 à 2 (transverse, en parallèle des missions techniques) |
| FR/NFR concernés | NFR-25 |
| ADR concerné(s) | ADR-006, ADR-008 |
| **Statut global** | `Implémentation` — **en cours** |
| Date de création de ce fichier | 2026-08-27 |
| Date de dernière mise à jour | 2026-08-28 |
| Dernier rapport journalier lié | `../journal/2026-08-28.md` |

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] Mission décrite dans le backlog (`../04-missions-et-sprints.md`, Mission A0)
- [x] Population cible confirmée : élèves de la 6ème à la Terminale (système francophone camerounais)
- [x] Aucune dépendance technique bloquante (mission de contenu, indépendante du code)

### Notes de conception
Proposition de correspondance niveau scolaire ↔ niveau GeR établie sur 7 niveaux (6e à Terminale), construite sur des principes standard de progression CECR en langue vivante — **hypothèse à valider**, je n'ai pas accès au curriculum officiel ni au manuel *Ihr und Wir Plus* pour la confirmer précisément. Repères thématiques par niveau GeR proposés (voir `09-cartographie-contenu-pedagogique.md`, section 2).

### Sortie de phase
- [x] Proposition de correspondance rédigée
- [x] Portée MVP tranchée par Mola : collège complet (6e, 5e, 4e, 3e), lycée reporté

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 2 — Implémentation

### Découpage en sous-tâches
- [x] Rédiger un texte original de niveau A1 (6ème) — `U-6E-01 « Ich stelle mich vor »`
- [x] Rédiger un texte original de niveau A1 consolidation (5ème) — `U-5E-01 « Meine Woche »`
- [x] Rédiger un texte original de niveau A2 (4ème) — `U-4E-01 « Ein Tag in Yaoundé »`
- [x] Rédiger un texte original de niveau A2 consolidation (3ème) — `U-3E-01 « Meine Sommerferien in Kribi »`
- [x] Réserver les 2 unités lycée déjà rédigées (2nde, Terminale) hors périmètre MVP, sans les perdre (section 3.3 de la cartographie)
- [ ] Atteindre le seuil de 6 unités validées pour chacun des 4 niveaux du MVP (1/6 rédigé pour chacun à ce stade)
- [ ] Compléter les références aux chapitres *Ihr und Wir Plus* pour chaque unité (champ actuellement vide, à renseigner par toi)

### Points de vigilance obligatoires
- [ ] Chaque texte doit être relu par un locuteur natif ou l'encadrant avant passage au statut `Validé` — **aucune des 4 unités MVP n'est encore relue**
- [x] `statutDroits` documenté pour chaque unité (`Texte original` pour les 4 unités du MVP, cohérent avec ADR-006)

### Notes d'implémentation
Les 4 unités du MVP (6e à 3e) sont des **brouillons générés par un modèle de langage**, non validés linguistiquement. `U-6E-01` et `U-3E-01` partagent une narratrice récurrente (Aïcha, 12 ans en 6e → 14 ans en 3e) comme fil pédagogique intentionnel ; `U-5E-01` introduit un second personnage (Paul) pour diversifier les figures représentées. Les unités lycée (`U-2NDE-01`, `U-TLE-01`) restent conservées en section 3.3 de la cartographie pour un développement ultérieur.

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Phase 3 — Test

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Relecture linguistique (locuteur natif/encadrant) | 4 unités rédigées | ☐ Passant ☐ Échec — **à faire** |
| Vérification de calibrage GeR (longueur, structures grammaticales) | 4 unités rédigées | ☐ Passant ☐ Échec — auto-évalué par le rédacteur, à confirmer |
| Cohérence thématique avec le curriculum réel | 4 unités rédigées | ☐ Passant ☐ Échec ☒ N/A tant que le manuel n'est pas consulté |

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise de la Mission A0, `../04-missions-et-sprints.md`)
- [ ] Correspondance niveau scolaire ↔ niveau GeR validée
- [x] Portée MVP tranchée (collège complet : 6e, 5e, 4e, 3e)
- [ ] Références *Ihr und Wir Plus* complétées pour les 4 niveaux du MVP
- [ ] Les 4 unités du MVP relues et validées
- [ ] Seuil minimal d'unités par niveau retenu atteint (6/niveau)
- [ ] Statut des droits documenté pour chaque unité

**Statut de la phase :** ☒ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour
- [x] `04-missions-et-sprints.md` — Mission A0 ajoutée, Mission A4 mise à jour avec le prérequis
- [x] `09-cartographie-contenu-pedagogique.md` — restructuré et peuplé (sections 2 et 3)
- [x] `08-registre-des-risques.md` — R-07 mis à jour avec l'ampleur réelle du chantier (7 niveaux)
- [ ] Mise à jour finale une fois la validation humaine des 4 unités obtenue

### Journal de bord DBR
- [x] Entrée mise à jour dans `../journal/2026-08-27.md`

**Statut de la phase :** ☐ À faire ☒ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non clôturée)* |
| Commit(s)/PR associé(s) | — *(à faire : versionner le dossier `docs/` mis à jour)* |
| Statut final | En cours — portée MVP confirmée (collège 6e-3e), 4 unités brouillon rédigées ; prochaine étape : relecture humaine et rédaction des unités supplémentaires jusqu'au seuil de 6/niveau |
