# Dossier `missions/` — fiches vivantes des cas d'utilisation, fonctionnalités et changements

Ce dossier contient une fiche par mission, cas d'utilisation, fonctionnalité ou changement **réellement démarré** — instanciée depuis `../processus/gabarit-cycle-iteration.md` et mise à jour phase par phase (Conception → Implémentation → Test → Validation → Documentation).

## Convention de nommage

`<ID>-<slug>.md`, ex. :
- `A1-finaliser-design-system.md`
- `B2-ecran-apprentissage.md`
- `X-01-correctif-crash-navigation.md` *(travail non planifié, voir convention d'ID dans le gabarit)*

L'`ID` reprend celui du backlog (`../04-missions-et-sprints.md`) quand la mission y est déjà listée.

## Cycle de vie d'une fiche

1. **Création** : dès que le travail démarre réellement (statut backlog passé à `En cours`)
2. **Mise à jour** : à chaque phase franchie, et à chaque session de travail (voir entrée correspondante dans `../journal/`)
3. **Clôture** : statut passé à `Terminé` — la fiche **n'est jamais supprimée**, elle constitue une trace pour la soutenance (méthodologie DBR)
4. **Suspension** : si le travail s'arrête avant clôture (priorité changée, blocage), statut `Suspendu` avec la raison explicitement notée dans la section « Clôture du cycle »

## Règle essentielle

Une fiche de mission doit toujours refléter l'état réel du code au moment de la dernière mise à jour. En cas de reprise après une pause, c'est le point de repère principal après `../ETAT_ACTUEL.md` et le dernier rapport de `../journal/` — voir la procédure complète dans `../processus/guide-orchestration.md`.
