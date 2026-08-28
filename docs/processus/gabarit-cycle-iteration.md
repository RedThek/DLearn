# Gabarit — Cycle de réalisation d'une mission / d'un cas d'utilisation / d'une fonctionnalité / d'un changement

Ce gabarit est le document **maître** orchestrant les 5 phases (Conception, Implémentation, Test, Validation, Documentation) de toute unité de travail sur le projet — qu'il s'agisse d'une mission planifiée (`../04-missions-et-sprints.md`), d'un cas d'utilisation, d'une nouvelle fonctionnalité, ou d'un changement/correctif imprévu.

## Comment utiliser ce gabarit

1. Dupliquer ce fichier vers `docs/missions/<ID>-<slug>.md` (ex. `docs/missions/B2-ecran-apprentissage.md`)
2. Remplir les métadonnées ci-dessous
3. Faire vivre le fichier phase par phase, en cochant les cases au fur et à mesure de l'avancement réel
4. Ne jamais supprimer un fichier de mission une fois créé, même terminé — le statut passe à `Terminé`, le fichier est conservé pour la traçabilité DBR (mémoire)
5. Chaque session de travail sur cette mission doit produire une entrée dans `docs/journal/` (voir `_gabarit-rapport-journalier.md`), reliée à ce fichier

Voir `guide-orchestration.md` pour la vue d'ensemble du système et la procédure de reprise après interruption.

---

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | *(reprendre l'ID du backlog `04-missions-et-sprints.md`, ou un nouvel ID si travail non planifié — voir convention ci-dessous)* |
| Titre | |
| Type | Mission planifiée / Cas d'utilisation / Fonctionnalité / Changement / Correctif |
| Sprint | |
| FR/NFR concernés | |
| ADR concerné(s) (si applicable) | |
| **Statut global** | `Conception` / `Implémentation` / `Test` / `Validation` / `Documentation` / `Terminé` / `Suspendu` |
| Date de création de ce fichier | |
| Date de dernière mise à jour | |
| Dernier rapport journalier lié | *(lien vers `docs/journal/YYYY-MM-DD.md`)* |

> **Convention d'ID pour un travail non planifié** : préfixe `X-` suivi d'un numéro séquentiel (ex. `X-01`), avec ajout rétroactif d'une ligne correspondante dans `04-missions-et-sprints.md` — pour ne jamais casser la traçabilité entre backlog et instance.

---

## Phase 1 — Conception

**Objectif** : clarifier ce qui doit être construit et pourquoi, avant d'écrire du code.

### Entrées attendues (Definition of Ready)
- [ ] La mission est décrite dans le backlog (`04-missions-et-sprints.md`) ou le cas d'usage est clairement formulé
- [ ] Les exigences concernées (FR/NFR) sont identifiées et non ambiguës
- [ ] Les dépendances techniques bloquantes sont validées (voir roadmap, section « dépendances critiques »)
- [ ] La maquette Figma correspondante est en état Dev Mode (si écran concerné)

### Notes de conception
*(schémas, choix d'implémentation envisagés, alternatives écartées ; lien vers un nouvel ADR dans `06-architecture-technique.md` si une décision d'architecture est nécessaire)*

### Sortie de phase
- [ ] Approche technique arrêtée et documentée ci-dessus
- [ ] Aucune question bloquante restante

**Statut de la phase :** ☐ À faire ☐ En cours ☐ Terminée

---

## Phase 2 — Implémentation

**Objectif** : traduire la conception en code fonctionnel, conforme à l'architecture du projet.

### Découpage en sous-tâches
- [ ] ...
- [ ] ...

### Points de vigilance obligatoires (rappel `06-architecture-technique.md`)
- [ ] Respect de la séparation Clean Architecture (domain / data / presentation)
- [ ] Injection via Hilt, aucune instanciation manuelle de dépendance
- [ ] Aucune régression offline-first (NFR-01), sauf exception déjà documentée (NFR-01-bis)
- [ ] Fidélité au design system (`Color.kt`, `Type.kt`, `Shape.kt`)

### Notes d'implémentation
*(difficultés rencontrées, écarts par rapport à la conception initiale, liens vers commits)*

**Statut de la phase :** ☐ À faire ☐ En cours ☐ Terminée

---

## Phase 3 — Test

**Objectif** : vérifier que l'implémentation répond aux exigences, sans régression.

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Unitaire (domain) | | ☐ Passant ☐ Échec ☐ N/A |
| Instrumentation (UI Compose) | | ☐ Passant ☐ Échec ☐ N/A |
| Migration Room (si schéma modifié) | | ☐ Passant ☐ Échec ☐ N/A |
| Test manuel offline (mode avion) | | ☐ Passant ☐ Échec ☐ N/A |

### Anomalies détectées et corrigées
*(liste, avec lien vers le commit de correction)*

**Statut de la phase :** ☐ À faire ☐ En cours ☐ Terminée

---

## Phase 4 — Validation

**Objectif** : confirmer que le travail est réellement terminé, selon les critères du projet.

### Definition of Done
*(reprendre celle spécifique à la mission dans `04-missions-et-sprints.md`, sinon la DoD générale de `06-architecture-technique.md`)*
- [ ] ...

### Revue effectuée
- [ ] Auto-revue documentée (travail solo) ou revue croisée
- [ ] Comparaison visuelle Figma (le cas échéant)
- [ ] Pipeline CI (GitHub Actions) vert

### Validation académique (si applicable, traçabilité DBR)
- [ ] Décision(s) de conception consignée(s) en ADR si nécessaire
- [ ] Impact sur un jalon académique évalué (mois 6 / mois 12)

**Statut de la phase :** ☐ À faire ☐ En cours ☐ Terminée

---

## Phase 5 — Documentation

**Objectif** : maintenir la cohérence entre le code et le dossier technique.

### Documents à mettre à jour (cocher ceux concernés par ce travail)
- [ ] `01-exigences-fonctionnelles.md`
- [ ] `02-exigences-non-fonctionnelles.md`
- [ ] `03-roadmap-developpement.md`
- [ ] `04-missions-et-sprints.md` (statut de la mission)
- [ ] `06-architecture-technique.md` (nouvel ADR ?)
- [ ] `08-registre-des-risques.md`
- [ ] `09-cartographie-contenu-pedagogique.md`
- [ ] `11-schema-donnees-room.md`
- [ ] `14-charte-versionnage-contenu.md` (changelog)
- [ ] Autre : ...

### Journal de bord DBR
- [ ] Entrée(s) créée(s)/mise(s) à jour dans `docs/journal/`

**Statut de la phase :** ☐ À faire ☐ En cours ☐ Terminée

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | |
| Commit(s)/PR associé(s) | |
| Statut final | `Terminé` / `Suspendu` (préciser la raison si suspendu) |

> Si ce cycle est suspendu avant clôture, ce fichier reste la référence principale pour la reprise : consulter la phase marquée « En cours », le dernier rapport journalier lié, et `docs/ETAT_ACTUEL.md`.
