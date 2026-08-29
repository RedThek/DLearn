# Dossier `planification/` — décomposition en tâches par bloc de missions

Ce dossier contient, pour chaque bloc de missions du backlog (`../04-missions-et-sprints.md`), un fichier de décomposition chronologique en tâches suffisamment petites pour être réalisées en une session de travail.

## Convention de nommage

`bloc-<lettre>-taches.md`, ex. :
- `bloc-A-taches.md` — Fondations techniques (A0-A5)
- `bloc-B-taches.md` — Écrans fonctionnels élève *(à créer)*
- `bloc-C-taches.md` — Enseignant & synchronisation *(à créer)*
- `bloc-D-taches.md` — Qualité & durcissement *(à créer)*
- `bloc-E-taches.md` — Phase 3 / Cycle DBR 2 *(à créer)*

## Différence avec les autres niveaux de suivi

| Document | Granularité | Rôle |
|---|---|---|
| `../04-missions-et-sprints.md` | Mission | Quoi faire, dans quel ordre, avec quels critères (Definition of Done) |
| `../missions/<ID>-<slug>.md` | Phase (Conception/Implémentation/Test/Validation/Documentation) | Où en est une mission précise |
| `planification/bloc-<X>-taches.md` | Tâche (quelques heures max) | Quelle est la prochaine action concrète à faire, et de quoi dépend-elle |
| `../journal/YYYY-MM-DD.md` | Session | Ce qui s'est passé narrativement, pour la reprise de contexte |

## Mise à jour

Un fichier de ce dossier doit être mis à jour (case cochée) à chaque tâche terminée — idéalement au même moment que la fiche de mission correspondante, pour ne jamais laisser les deux niveaux diverger.

## Point de départ recommandé pour une nouvelle bloc

Avant de rédiger un nouveau `bloc-<X>-taches.md`, relire le code réel existant (pas seulement la documentation) pour que les tâches reflètent l'état effectif du dépôt — voir la section 1 de `bloc-A-taches.md` comme exemple de ce que cette relecture peut révéler (systèmes dupliqués, incohérences de nommage, etc.).
