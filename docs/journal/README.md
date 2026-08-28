# Dossier `journal/` — rapports de session (journal de bord DBR)

Ce dossier contient un rapport par session de travail, au format `YYYY-MM-DD.md` (ou `YYYY-MM-DD_2.md` en cas de deuxième session le même jour). Il constitue à la fois :
- le **journal de bord DBR** attendu par la méthodologie de recherche (traçabilité des décisions et itérations, exploitable pour le mémoire) ;
- le **mécanisme technique de reprise de contexte** en cas d'arrêt brusque ou de longue pause.

## Convention de nommage

- `2026-08-27.md` — un rapport par session
- `2026-08-27_2.md` — si une deuxième session distincte a lieu le même jour

## Contenu attendu

Voir `_gabarit-rapport-journalier.md` pour la structure complète. Chaque rapport doit permettre, à lui seul et sans autre contexte, de comprendre : ce qui a été fait, quelles décisions ont été prises, quel est l'état du code à la fin de la session, et quelle est la prochaine étape prévue.

## Règle essentielle pour la reprise après interruption

**Toujours commencer par lire `../ETAT_ACTUEL.md`**, qui pointe vers le dernier rapport de ce dossier. Ne jamais tenter de reconstituer le contexte uniquement à partir du code — le rapport le plus récent contient l'intention et les décisions que le code seul ne montre pas. Voir la procédure complète dans `../processus/guide-orchestration.md`, section 5.

## Fréquence

Un rapport est créé à **chaque session de travail**, même courte, et surtout avant toute interruption prévisible (fin de journée, pause académique, imprévu). Un rapport non finalisé (session interrompue brutalement) doit tout de même exister, même incomplet — mieux vaut un rapport partiel qu'aucune trace.
