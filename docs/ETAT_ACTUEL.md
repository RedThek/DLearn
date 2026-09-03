# État actuel du projet — Liteschreib IKII

> ⭐ **Point d'entrée unique pour reprendre le travail.** Mise à jour systématique
> en fin de session. Voir procédure complète : `processus/guide-orchestration.md`, section 5.

**Dernière mise à jour :** 2026-09-03 (The Architect — audit pré-Sprint 2)

---

## 1. Où en est le projet, en une phrase

Build Sprint 1 propre ✅. **16 bugs identifiés** (4 critiques → correctifs obligatoires avant
tout code Sprint 2). Instructions agents générées. Sprint 2 prêt à démarrer.

---

## 2. Missions actives

| Priorité | Fiche | Statut | Bloquée par |
|---|---|---|---|
| 1 | [`missions/A5-modules-hilt.md`](missions/A5-modules-hilt.md) | 🔄 En cours — DataStore session | A4 partiellement |
| 2 | [`missions/A0-cartographie-contenu-pedagogique.md`](missions/A0-cartographie-contenu-pedagogique.md) | 🔄 En cours — relecture humaine requise | — |
| 3 | [`missions/A4-entites-room-prepopulation.md`](missions/A4-entites-room-prepopulation.md) | ⛔ Bloquée | A0-T23 |

---

## 3. Bugs critiques à résoudre avant Sprint 2

> **Référence complète :** `docs/planification/bugs-pre-sprint2.md`

| ID | Sévérité | Description | Agent | Résolu ? |
|---|---|---|---|---|
| B-01 | 🔴 CRITIQUE | `android.yml` JDK 11 ≠ Java 17 | Backend | [x] |
| B-02 | 🔴 CRITIQUE | `codeql.yml` `checkout@v7` inexistant | Backend | [x] |
| B-03 | 🔴 CRITIQUE | `AccueilScreen` import hiltViewModel erroné | Frontend | [x] |
| B-04 | 🔴 CRITIQUE | Room `exportSchema` sans KSP arg | Backend | [x] |
| B-05 | 🟠 ÉLEVÉ | `NavViewModel` jamais câblé dans NavGraph | Frontend | [x] |
| B-06 | 🟠 ÉLEVÉ | `NavRoute` + `Route` — duplication constantes | Backend | [x] |
| B-07 | 🟠 ÉLEVÉ | `utilisateurConnecte()` retourne toujours null | Backend | [x] |
| B-08 | 🟠 ÉLEVÉ | `recupererProfilsLocaux()` retourne toujours vide | Backend | [x] |

---

## 4. Instructions agents Sprint 2

| Fichier | Agent | Priorité |
|---|---|---|
| [`planification/EXEC-SPRINT2-BACKEND-AGENT.md`](planification/EXEC-SPRINT2-BACKEND-AGENT.md) | Backend Android Studio | 1 (démarrer maintenant) |
| [`planification/EXEC-SPRINT2-FRONTEND-AGENT.md`](planification/EXEC-SPRINT2-FRONTEND-AGENT.md) | Frontend Android Studio | 2 (Phase 0 en parallèle, Phase 1+ après Backend Phase 1) |

---

## 5. Missions préparées (non démarrées)

| Ordre | Fiche | Sprint | Dépend de |
|---|---|---|---|
| 4 | [`missions/A4-entites-room-prepopulation.md`](missions/A4-entites-room-prepopulation.md) | Sprint 2 | A0-T23, A2 ✅ |
| 5 | [`missions/A5-modules-hilt.md`](missions/A5-modules-hilt.md) | Sprint 3 | A4 |

---

## 6. Dernier rapport de session

`docs/journal/2026-09-03.md` — Session architecturale The Architect,
audit complet, 16 bugs catalogués, fichiers agents générés.

---

## 7. Sprint et cycle en cours

- **Cycle DBR :** Cycle 1 — Développement en cours
- **Sprint courant :** Sprint 2 (démarrage conditionnel à résolution B-01→B-04)
- **Sprint précédent :** Sprint 1 terminé ✅ (A1, A2, A3 validées)

---

## 8. Décisions et risques prioritaires

- **ADR-014 :** Abandon Figma — génération UI par agent ✅ actif
- **Risque R-07 :** Contenu MVP — 0/5 unités validées par niveau (20 unités manquantes)
- **Risque R-11 :** Délai approbation éthique — à engager **maintenant** en parallèle
- **Nouveau risque à surveiller :** Session DataStore non testée sur appareils Tecno/Itel (ADR-012) — prévoir test physique après A5

---

## 9. Checklist de reprise rapide

1. Lire ce fichier en entier
2. Lire `docs/planification/bugs-pre-sprint2.md` (catalogue bugs)
3. Lancer `EXEC-SPRINT2-BACKEND-AGENT.md` Phase 0 en premier
4. Lancer `EXEC-SPRINT2-FRONTEND-AGENT.md` Phase 0 en parallèle
5. Vérifier le build après chaque phase : `./gradlew assembleDebug`
6. Ne pas avancer en A4 tant que B-01→B-04 ne sont pas résolus