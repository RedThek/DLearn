# Protocole de test — Synchronisation à deux appareils (confirmation R-26 / R-27)

```
Mission liée   : F1 — Décisions structurantes d'identité, de synchronisation et de packs
Ferme aussi    : C3-T11 (« Test bout en bout réel sur deux appareils physiques »), ouvert depuis le Sprint 3
Code testé     : version actuelle du dépôt, AUCUNE modification requise avant ce test
Objectif       : confirmer ou infirmer, par l'observation, les constats R-26 et R-27
Durée estimée  : 45 à 60 minutes
Exécutant      : ______________________________        Date : __________________
```

---

## 1. Objectif du test

Deux constats ont été établis par lecture du code (`08-registre-des-risques.md`, R-26 et R-27), mais **jamais observés en conditions réelles** :

- **R-26 (identité)** : deux appareils fraîchement installés partagent le même « élève de démo » (id local 1). Une donnée importée depuis un appareil élève pourrait se retrouver attribuée au mauvais profil, ou être écartée en silence.
- **R-27 (synchronisation à sens unique)** : rien ne permet à une assignation créée par l'enseignant de revenir vers l'élève sur un autre appareil.

Ce protocole les vérifie **sans écrire une ligne de code**, avant d'ouvrir les missions F1a et F1b qui en dépendent. Il ferme au passage un critère resté ouvert depuis le Sprint 3.

## 2. Portée

**Ce que ce test vérifie :**
- Le comportement réel de l'export/import v1 (`SyncRepositoryImpl`, `AuthRepositoryImpl`, `EnseignantViewModel`) entre deux appareils distincts.
- Le canal de partage de fichier lui-même (au moins un des canaux prévus par ADR-004).

**Ce que ce test NE vérifie PAS :**
- Le futur format v2 (bundles, `rev`) — il n'existe pas encore, c'est un test du format v1 actuel.
- La performance ou la taille des fichiers.
- Le mode avion / le fonctionnement hors ligne (l'export-import ne dépend déjà d'aucun réseau ; ce n'est pas ce qui est mis en doute ici).

## 3. Matériel nécessaire

☐ Deux appareils Android 9.0 ou supérieur (physiques de préférence ; deux émulateurs conviennent à défaut, avec un partage de fichier fonctionnel entre eux)
☐ Un build debug installable sur les deux appareils (le même `versionCode`)
☐ Un moyen de transférer un fichier entre les deux appareils : au choix, Nearby Share, Bluetooth classique, câble/`adb push`, ou carte SD amovible
☐ Accès à ce document (imprimé ou sur un troisième écran) pour cocher au fur et à mesure
☐ De quoi noter les observations (ce document sert de feuille de résultats)

## 4. Préparation — désigner les rôles

Nommer les deux appareils pour la suite du protocole :

- **Appareil A = « Élève »**
- **Appareil B = « Enseignant »**

☐ Désinstaller complètement l'application sur les deux appareils, ou effacer les données de l'application (`Paramètres > Applications > Liteschreib IKII > Stockage > Effacer les données`), pour repartir d'un `SeedCallback` neuf sur chacun.
☐ Réinstaller l'application (build identique) sur les deux appareils.
☐ Noter les versions installées : Appareil A `______________`  Appareil B `______________`

---

## 5. Scénario A — Test de l'identité (R-26)

### A.1 — Créer une activité côté élève

1. Sur l'**appareil A**, ouvrir l'application. Se connecter avec le compte de démo élève (`eleve.2451` / `eleve1234`).
2. Ouvrir une unité d'apprentissage (n'importe laquelle), la terminer via ses exercices, jusqu'à l'écran de résultat.
3. Aller dans l'onglet Écriture, rédiger quelques phrases, appuyer sur **Soumettre**.
4. Aller dans l'onglet Profil, relever le nom affiché de l'élève connecté : `______________________`

☐ Étape A.1 terminée sans erreur ni crash.

### A.2 — Exporter depuis l'élève

5. Toujours sur l'appareil A, dans l'onglet Profil, appuyer sur **Synchroniser maintenant**.
6. La feuille de partage Android s'ouvre-t-elle ? ☐ Oui ☐ Non — *si non, noter le comportement observé : ________________*
7. Transférer le fichier `.json` généré vers l'**appareil B**, par le canal choisi : ☐ Nearby Share ☐ Bluetooth ☐ Câble/`adb push` ☐ Carte SD
8. Noter le nom exact du fichier reçu sur l'appareil B : `______________________________________`

### A.3 — Importer côté enseignant et observer l'identité

9. Sur l'**appareil B**, se connecter avec le compte de démo enseignant (`enseignant.100` / `enseignant1234`).
10. Aller dans l'onglet **Corrections**, appuyer sur le bouton d'import (icône de téléchargement), sélectionner le fichier reçu à l'étape 8.
11. Une confirmation d'import apparaît-elle (Snackbar) ? ☐ Oui ☐ Non — *texte exact affiché : ________________*
12. Dans l'onglet **Corrections**, la production soumise à l'étape A.1.3 apparaît-elle ? ☐ Oui ☐ Non
13. Si elle apparaît, **quel nom d'élève** lui est-il attribué ? `______________________________`
14. Ce nom correspond-il au nom relevé à l'étape A.1.4 ? ☐ Oui, correct ☐ Non, mauvais élève ☐ N/A (rien n'apparaît)
15. Dans l'onglet **Classe** de l'appareil B, combien d'élèves apparaissent, et sous quel(s) nom(s) ? `______________________________`

### Verdict du scénario A

| Observation | Cochez ce qui correspond |
|---|---|
| La production apparaît, attribuée au bon élève | ☐ |
| La production apparaît, mais attribuée à un autre élève (collision d'identité) | ☐ |
| La production n'apparaît pas du tout (écartée en silence) | ☐ |
| Un message d'erreur explicite est montré (pas un écart silencieux) | ☐ — *si coché, noter le message : ________________* |

**→ Si l'une des deux lignes du milieu est cochée : R-26 est CONFIRMÉ.**
**→ Si la première ligne est cochée : R-26 est INFIRMÉ ou partiellement corrigé — noter le mécanisme observé pour qu'il soit réexaminé avant de rédiger F1a.**

---

## 6. Scénario B — Test du canal enseignant → élève (R-27)

### B.1 — Créer une assignation côté enseignant

16. Toujours connecté comme enseignant sur l'**appareil B**, aller dans l'onglet **Contenus**.
17. Choisir une unité différente de celle utilisée au scénario A, appuyer sur **Assigner**.
18. Choisir le mode « Élève(s) », sélectionner l'élève importé à l'étape A.3, confirmer.
19. Une confirmation apparaît-elle ? ☐ Oui ☐ Non

### B.2 — Observer côté élève, sans action manuelle supplémentaire

20. Retourner sur l'**appareil A** (celui de l'élève), fermer et rouvrir l'application (pour forcer un rechargement).
21. Aller sur l'onglet **Accueil**.
22. La section « Assigné par ton enseignant » apparaît-elle avec la nouvelle unité ? ☐ Oui ☐ Non

### Verdict du scénario B

| Observation | Cochez ce qui correspond |
|---|---|
| L'assignation apparaît sur l'appareil A sans action manuelle | ☐ |
| L'assignation n'apparaît pas, quelle que soit la durée d'attente ou le nombre de réouvertures | ☐ |

**→ Si la seconde ligne est cochée : R-27 est CONFIRMÉ** (comportement attendu, puisqu'aucun canal enseignant → élève n'existe en v1).
**→ Si la première ligne est cochée : R-27 est INFIRMÉ — un mécanisme non documenté relie les deux appareils ; à investiguer avant de rédiger F1b.**

---

## 7. Scénario C (complémentaire) — Robustesse du canal de partage

But : vérifier qu'au moins un canal de repli fonctionne réellement (FR-29), indépendamment du résultat des scénarios A et B.

23. Répéter l'étape A.2.7 avec un **second canal** différent du premier (par exemple Bluetooth si Nearby Share a été utilisé en premier).
24. Le transfert aboutit-il ? ☐ Oui ☐ Non — *canal testé : ________________*
25. Si un canal échoue, indiquer le comportement observé (blocage, erreur, fichier tronqué) : `________________________________`

---

## 8. Grille de résultats — synthèse à reporter dans le registre des risques

| Risque | Constat de départ (lecture de code) | Résultat observé | Statut à reporter dans `08-registre-des-risques.md` |
|---|---|---|---|
| R-26 — Identité non globale | Collision ou perte silencieuse attendue | ☐ Confirmé ☐ Infirmé ☐ Partiel | ______________________ |
| R-27 — Synchronisation unidirectionnelle | Aucun canal retour attendu | ☐ Confirmé ☐ Infirmé ☐ Partiel | ______________________ |
| FR-29 — Canaux de repli | Au moins un canal doit fonctionner | ☐ Conforme ☐ Non conforme | ______________________ |

## 9. Anomalies inattendues (hors R-26/R-27)

*(toute observation surprenante, même sans rapport direct avec l'objet du test — crash, incohérence d'affichage, etc.)*

```




```

## 10. Décision suivante

- **Si R-26 et R-27 sont confirmés tels quels** : passer leur statut à « Confirmé » dans `08-registre-des-risques.md`, cocher la Phase 3 de `F1-identite-echange-packs.md`, et ouvrir F1a puis F1b tel que spécifié dans `06-architecture-technique.md` et `20-specification-formats-echange-et-packs.md` sans modification.
- **Si l'un des deux est infirmé ou partiel** : ne pas engager F1a/F1b avant d'avoir signalé l'écart — le mécanisme observé doit être compris et peut changer la spécification.
- **Dans tous les cas** : cette exécution ferme rétroactivement **C3-T11** (`docs/planification/bloc-C-taches.md`) et le critère correspondant de la Definition of Done de la Mission C3, pour le format v1 — un nouveau test sera nécessaire après F1b pour valider le format v2.

## 11. À consigner après exécution

- [ ] Créer ou compléter `docs/journal/YYYY-MM-DD.md` (date réelle d'exécution) avec le résultat de ce protocole
- [ ] Mettre à jour le statut de R-26 et R-27 dans `08-registre-des-risques.md`
- [ ] Cocher C3-T11 dans `docs/planification/bloc-C-taches.md` et la ligne correspondante dans `docs/missions/C3-synchronisation-locale.md`
- [ ] Cocher la ligne « Test à deux appareils physiques » dans la Phase 3 de `docs/missions/F1-identite-echange-packs.md`
- [ ] Si un comportement inattendu a été observé (§9), le signaler avant d'ouvrir F1a
