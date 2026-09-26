# Mission F1 — Décisions structurantes d'identité, de synchronisation et de packs

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F1 |
| Titre | Décisions structurantes d'identité, de synchronisation et de packs |
| Type | Mission planifiée (Bloc F — voir `../18-vision-produit-et-horizons.md`) |
| Sprint | Hors sprint — mission documentaire, menée en parallèle du Sprint 5 |
| FR/NFR concernés | FR-29, FR-31, FR-47, NFR-33, NFR-34, NFR-35 |
| ADR concerné(s) | ADR-026, ADR-027, ADR-028 |
| **Statut global** | `Documentation` — décisions tranchées et validées par le porteur, intégration dans `docs/` en attente |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | `../journal/2026-09-21_2.md` (décisions), `../journal/2026-09-22.md` (présente fiche et protocole) |

> **Nature de cette mission.** F1 ne produit aucun code : elle fige des décisions d'architecture et une spécification, à partir desquelles trois missions d'implémentation s'ouvrent — **F1a** (identité), **F1b** (échange v2), **F1c** (packs). F1 n'est close que lorsque ces décisions sont intégrées dans `docs/` **et** que le test à deux appareils (Phase 3 ci-dessous) a confirmé ou corrigé les constats R-26/R-27 sur lesquels elles reposent.

---

## Phase 1 — Conception

### Entrées attendues (Definition of Ready)
- [x] Le besoin est identifié : une proposition d'extension (`DLearn-New-Fonctionnalités.md`) exige une synchronisation bidirectionnelle et du contenu distribuable, ce que le format v1 (ADR-004, ADR-018) ne permet pas
- [x] Le périmètre produit est gelé en amont (Mission F0, ADR-020 à ADR-025)
- [x] Le code réel a été relu pour fonder les décisions sur des faits vérifiables, et non sur des hypothèses (voir `notes de conception` ci-dessous)
- [x] Aucune dépendance bloquante technique : mission documentaire, peut démarrer dès que F0 est admise

### Notes de conception

**Faits relevés dans le code, à l'origine des trois décisions :**

| Constat | Preuve dans le code | Conséquence |
|---|---|---|
| Identité locale, non globale | `UtilisateurEntity.id` est un `Long` auto-incrémenté ; `SeedCallback` crée un « élève 1 » sur chaque installation ; `EnseignantViewModel.charger()` fait `mapNotNull { elevesParId[p.eleveId] ?: return@mapNotNull null }` | Une production d'un élève inconnu de l'enseignant est **écartée en silence** ; deux appareils fraîchement installés partagent le même id d'élève de démo → **ADR-026** |
| Synchronisation à sens unique | `SyncRepository` n'expose que `exporterDonnees` (élève → fichier) et `importerDonnees` (fichier → base enseignant) ; aucune méthode symétrique | Assignations, comptes, commentaires n'ont **aucun chemin retour** vers l'élève sur un autre appareil → **ADR-027** |
| Fusion par horloge, non par propriété des données | `ADR-018` : `dateExport >= existante.dateMiseAJour` | Une horloge dérivée (fréquent sur les appareils d'entrée de gamme) peut écraser ou perdre des données → traité par **ADR-027** (compteur `rev`) |
| Contenu figé après le premier lancement | `ContentDataSource.peupler()` sort si `countUnites() > 0` ; `ContenuDao` insère avec `OnConflictStrategy.IGNORE` | Les corrections issues de la relecture native (Mission A0) **n'atteindraient jamais** un appareil déjà semé → **ADR-028** |
| Dictionnaire et données lourdes incompatibles avec l'APK | NFR-07, ADR-007 (aucune extension de l'exception réseau), licences CC BY-SA probables (ADR-025) | Nécessité un mécanisme de **pack externe**, distinct du seed → **ADR-028** |

**Discussion des options** : le détail complet (options écartées, arguments) est dans `06-architecture-technique.md`, sections ADR-026 à ADR-028, et a été présenté et validé avec le porteur avant rédaction (voir `../journal/2026-09-21_2.md`).

**Décision de calendrier retenue** : contrairement à une estimation initiale, ADR-026 n'a **pas** à précéder impérativement la migration Room 5→6 (`session_etude` conserve son `eleveId` local). La seule contrainte réelle est : **avant le pilote (Mission D0) et avant tout usage réel de l'échange v2**.

### Sortie de phase
- [x] ADR-026, ADR-027, ADR-028 rédigés (texte prêt à intégrer dans `docs/planification/INTEGRATION-DOCS-VISION-LOT2.md`)
- [x] Spécification technique rédigée : `../20-specification-formats-echange-et-packs.md` (enveloppe des bundles, types, règles de fusion par `rev`, manifeste des packs, **vecteur de test** avec somme de contrôle SHA-256 calculée)
- [x] Choix validés par le porteur du projet
- [ ] Texte intégré dans `docs/06-architecture-technique.md` et les fichiers dérivés (voir Phase 5)

**Statut de la phase :** ☐ À faire ☐ En cours ☒ Terminée

---

## Phase 2 — Implémentation

> F1 elle-même n'écrit pas de code. Cette phase couvre l'**intégration documentaire** du lot 2 dans le dépôt réel. L'implémentation en code est déléguée aux missions filles.

### Découpage en sous-tâches (intégration documentaire)
- [ ] Créer la branche `docs/adr-026-028-identite-echange-packs`
- [ ] Copier `20-specification-formats-echange-et-packs.md` dans `docs/`
- [ ] Coller le texte d'ADR-026 à ADR-028 dans `06-architecture-technique.md`, avec les trois annotations (ADR-004, ADR-016, ADR-018) — voir `INTEGRATION-DOCS-VISION-LOT2.md` §1
- [ ] Mettre à jour `08-registre-des-risques.md` (R-26/R-27 révisés, R-28 à R-30 nouveaux) — §2
- [ ] Mettre à jour `01-…`/`02-…` (FR-29, FR-31 révisés, FR-47, NFR-33 à NFR-35) — §3
- [ ] Mettre à jour `04-missions-et-sprints.md` (Bloc F : F1a, F1b, F1c, ajustements F3/F5/C3/D0) — §4
- [ ] Mettre à jour `03-…`, `11-…`, `14-…`, `10-…`, `12-…`, `13-…`, `18-…` — §5 à §9
- [ ] Petits ajouts (`05-…`, `07-…`, `README.md`, `ETAT_ACTUEL.md`) — §10
- [ ] Copier ce fichier et `TEST-PROTOCOLE-F1-DEUX-APPAREILS.md` dans le dépôt réel

### Points de vigilance obligatoires
- [ ] Vérifier avant collage que FR-47, NFR-33 à NFR-35 et R-28 à R-30 sont libres dans les fiches Sprint 5 réelles
- [ ] Ne pas fusionner cette branche avant que le test à deux appareils (Phase 3) ait produit un résultat consigné

### Notes d'implémentation
Aucune régression de code possible : ce lot ne touche aucun fichier hors de `docs/`.

**Statut de la phase :** ☐ À faire ☒ En cours ☒ Terminée

---

## Phase 3 — Test

**Objectif** : vérifier, avant d'ouvrir F1a/F1b/F1c, que les constats R-26 et R-27 (identité et synchronisation) qui fondent ADR-026/027 sont réels sur l'application telle qu'elle existe aujourd'hui — et non de simple suppositions de lecture de code.

### Tests prévus

| Type de test | Portée | Résultat |
|---|---|---|
| Cohérence documentaire (`grep`) | Numérotation ADR/FR/NFR/risques, absence de fichier hors `docs/` modifié — voir `INTEGRATION-DOCS-VISION-LOT2.md` §11 | ☐ Passant ☐ Échec |
| **Test à deux appareils physiques** | Confirme ou infirme R-26 (identité) et R-27 (canal à sens unique), sur le code **actuel**, sans modification. Protocole détaillé : `TEST-PROTOCOLE-F1-DEUX-APPAREILS.md` | ☐ Conforme aux constats ☐ Contredit les constats ☐ Partiel |
| Reproduction du vecteur de test | Le SHA-256 du vecteur de `20-…` §6 est reproductible par un script indépendant (déjà vérifié par l'Architecte, à revérifier par un test unitaire lors de F1b) | ☒ Passant (vérifié manuellement le 2026-09-21) |

### Anomalies détectées et corrigées
*(à remplir après exécution du test à deux appareils)*

**Statut de la phase :** ☒ À faire ☐ En cours ☒ Terminée

---

## Phase 4 — Validation

### Definition of Done (reprise du backlog, `../04-missions-et-sprints.md`)
- [ ] ADR-026, ADR-027, ADR-028 intégrés à `06-architecture-technique.md`
- [ ] `20-…` ajouté ; `11-…` et `14-…` mis à jour ; R-26 à R-30 inscrits
- [ ] Test à deux appareils exécuté et consigné (Phase 3), avec verdict explicite sur R-26/R-27
- [ ] Fiches filles créées : `F1a-identite-globale-utilisateurs.md`, `F1b-echange-v2-bundles.md`, `F1c-packs-de-contenu.md`, chacune avec une Definition of Ready satisfaite

### Revue effectuée
- [ ] Auto-revue documentée (travail solo)
- [ ] Cohérence vérifiée avec les missions déjà en cours (Sprint 5, Mission A0)

### Validation académique (traçabilité DBR)
- [ ] Décisions consignées en ADR (fait) ; impact sur le calendrier de soutenance évalué (voir `03-roadmap-developpement.md` section 7)
- [ ] Le test à deux appareils est documenté comme preuve empirique dans le journal de bord, exploitable pour le mémoire (traçabilité de la démarche : constat de code → décision → vérification)

**Statut de la phase :** ☐ À faire ☒ En cours ☒ Terminée

---

## Phase 5 — Documentation

### Documents à mettre à jour (cocher ceux concernés)
- [ ] `06-architecture-technique.md` (ADR-026 à ADR-028)
- [ ] `08-registre-des-risques.md` (R-26 à R-30)
- [ ] `01-exigences-fonctionnelles.md`, `02-exigences-non-fonctionnelles.md`
- [ ] `04-missions-et-sprints.md` (Bloc F)
- [ ] `11-schema-donnees-room.md`, `14-charte-versionnage-contenu.md`
- [ ] `10-…`, `12-…`, `13-…` (vie privée et recherche)
- [ ] `18-vision-produit-et-horizons.md`
- [ ] `03-roadmap-developpement.md`, `05-…`, `07-…`, `README.md`, `ETAT_ACTUEL.md`
- [x] `20-specification-formats-echange-et-packs.md` (créé)

### Journal de bord DBR
- [x] Entrée créée : `../journal/2026-09-21_2.md` (décisions)
- [x] Entrée créée : `../journal/2026-09-22.md` (fiche F1, protocole, missions filles et Blocs B/C)
- [ ] Entrée à créer après exécution du test à deux appareils

**Statut de la phase :** ☐ À faire ☒ En cours ☒ Terminée

---

## Sous-missions issues de F1

| Fiche | Objet | Prérequis | Statut |
|---|---|---|---|
| [F1a-identite-globale-utilisateurs.md](F1a-identite-globale-utilisateurs.md) | `uid`, `instanceId`, exclusion des comptes de démo | F1 Phase 4 + test à deux appareils | `Conception` |
| [F1b-echange-v2-bundles.md](F1b-echange-v2-bundles.md) | Bundles, `rev`, import atomique, hash étiqueté | F1a | `Conception` |
| [F1c-packs-de-contenu.md](F1c-packs-de-contenu.md) | Pack `core`, upsert transactionnel, dictionnaire séparé | F1a | `Conception` |

Aucune de ces trois fiches ne passe en `Implémentation` avant que la Phase 3 de F1 ait produit un verdict écrit.

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(non clôturée)* |
| Commit(s)/PR associé(s) | — |
| Statut final | En cours — décisions tranchées et validées ; intégration documentaire et test à deux appareils restant à réaliser avant l'ouverture effective de F1a/F1b/F1c |

> Si ce cycle est suspendu, la référence de reprise est ce fichier, puis `TEST-PROTOCOLE-F1-DEUX-APPAREILS.md` si le test n'a pas encore été exécuté, puis `INTEGRATION-DOCS-VISION-LOT2.md` si l'intégration documentaire est en cours.
