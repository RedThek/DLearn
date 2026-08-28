# Guide d'orchestration du cycle de développement — Liteschreib IKII

## 1. Objectif de ce guide

Ce document explique comment les différentes pièces du dossier technique s'articulent pour orchestrer le développement au quotidien, et surtout comment **reprendre le travail avec un contexte complet** après un arrêt brusque ou une longue pause — contrainte centrale pour un développeur unique menant un projet académique sur 12 mois.

## 2. Vue d'ensemble du système documentaire

```
docs/
├── ETAT_ACTUEL.md                    ⭐ point d'entrée unique pour reprendre le travail
├── 01-...17-...md                     documents de référence (figés, mis à jour ponctuellement)
├── processus/
│   ├── gabarit-cycle-iteration.md     gabarit maître des 5 phases
│   └── guide-orchestration.md         ce document
├── missions/
│   ├── README.md
│   └── <ID>-<slug>.md                 une fiche vivante par mission / cas d'usage / changement
└── journal/
    ├── README.md
    ├── _gabarit-rapport-journalier.md
    └── YYYY-MM-DD.md                  un rapport par session de travail
```

## 3. Les trois niveaux de granularité

| Niveau | Document(s) | Fréquence de mise à jour | Rôle |
|---|---|---|---|
| **Macro** | `03-roadmap-developpement.md`, `04-missions-et-sprints.md` | À chaque sprint | Vue d'ensemble planifiée, backlog |
| **Méso** | `docs/missions/<ID>-<slug>.md` | À chaque phase franchie | Suivi détaillé d'une unité de travail à travers ses 5 phases |
| **Micro** | `docs/journal/YYYY-MM-DD.md` | À chaque session de travail | Trace fine du contexte, permettant une reprise immédiate |

Ces trois niveaux sont complémentaires, jamais redondants : le backlog dit *quoi faire et dans quel ordre*, la fiche de mission dit *où en est ce travail précis*, le journal dit *ce qui s'est passé concrètement session par session*.

## 4. Cycle de vie d'une mission / d'un cas d'usage

1. La mission existe dans le backlog (`04-missions-et-sprints.md`), ou un besoin non planifié est identifié en cours de sprint (cas d'usage, changement, correctif).
2. Dès le démarrage réel du travail (passage à `En cours`), dupliquer `processus/gabarit-cycle-iteration.md` vers `docs/missions/<ID>-<slug>.md`.
3. Faire progresser ce fichier phase par phase : Conception → Implémentation → Test → Validation → Documentation.
4. À chaque session de travail, créer ou compléter une entrée dans `docs/journal/`, reliée explicitement au fichier de mission actif.
5. À la clôture, passer le statut à `Terminé` — le fichier reste dans `docs/missions/` (jamais supprimé, traçabilité DBR pour le mémoire).
6. Mettre à jour `docs/ETAT_ACTUEL.md` pour refléter la mission active suivante.

## 5. Procédure de reprise après interruption (arrêt brusque ou longue pause)

À suivre dans cet ordre exact, sans sauter d'étape :

1. **Lire `docs/ETAT_ACTUEL.md`** — résumé de l'état global du projet, mission active, dernier rapport, blocages connus.
2. **Lire le dernier rapport dans `docs/journal/`** (celui référencé par `ETAT_ACTUEL.md`) — contexte précis de fin de session précédente : ce qui a été fait, ce qui restait à faire, l'état du build.
3. **Ouvrir le fichier de mission active dans `docs/missions/`** — phase en cours, sous-tâches restantes, points de vigilance non résolus.
4. **Vérifier l'état réel du code** (`git log`, `git status`, build) pour confirmer que le code correspond bien à ce que documente le dernier rapport — en cas d'écart, le code fait foi, et l'écart doit être noté dans une nouvelle entrée de journal avant de continuer.
5. **Exécuter la checklist de début de journée** (`05-checklist-quotidienne.md`).
6. **Reprendre à la phase indiquée comme « En cours »** dans le fichier de mission, en créant une nouvelle entrée de journal pour la session de reprise.

## 6. Règles de cohérence à respecter en continu

- Un fichier de mission ne doit jamais indiquer une phase « Terminée » sans qu'une entrée de journal correspondante existe.
- `docs/ETAT_ACTUEL.md` doit toujours pointer vers une mission réellement active — jamais une mission déjà `Terminée`.
- Toute divergence constatée entre le code et la documentation doit être corrigée (ou au moins consignée) avant de démarrer une nouvelle phase.
- Un fichier de mission suspendu (`Suspendu`) doit indiquer explicitement la raison de la suspension dans sa section de clôture, pour qu'une reprise ultérieure — même après plusieurs mois — comprenne immédiatement pourquoi le travail s'est arrêté là.

## 7. Lien avec la checklist quotidienne

La checklist quotidienne (`05-checklist-quotidienne.md`) est le rituel d'exécution de ce système : elle rappelle, à chaque début et fin de journée, de lire/mettre à jour respectivement `ETAT_ACTUEL.md` et les fichiers de `missions/`/`journal/`. Ce guide décrit le système ; la checklist en est l'usage discipliné au quotidien.
