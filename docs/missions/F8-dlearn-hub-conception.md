# Mission F8 — « DLearn Hub » (conception uniquement)

## Métadonnées

| Champ | Valeur |
|---|---|
| ID | F8 |
| Titre | Backend et portail web — communauté, éditeurs, concours ouverts |
| Type | Perspective (horizon H3) |
| Sprint | Après la soutenance |
| FR/NFR concernés | FR-46 |
| ADR concerné(s) | ADR-020 (à superséder pour activer H3) |
| **Statut global** | `Hors périmètre de la thèse` |
| Date de création de ce fichier | 2026-09-22 |
| Date de dernière mise à jour | 2026-09-22 |
| Dernier rapport journalier lié | — |

---

## Avertissement

Cette fiche est un **espace réservé**, pas une mission engagée. Elle documente les prérequis à réunir avant toute ligne de code, conformément à ADR-020 (« aucune capacité H3 n'est compilée ni exposée dans le build pilote », NFR-31).

## Prérequis avant toute ouverture

- [ ] Un nouvel ADR supersédant explicitement ADR-002 (offline-first strict)
- [ ] Un protocole éthique refait (`10-…`, `12-…`, `13-…`) couvrant la collecte de données en ligne de mineurs
- [ ] Un cadre juridique vérifié pour l'hébergement, la modération et la protection des données (au-delà du Cameroun si des contacts internationaux sont prévus)
- [ ] Un plan de maintenance après la soutenance (le développeur unique n'est plus nécessairement disponible)
- [ ] Une décision explicite sur l'activation d'une signature cryptographique des échanges (les champs `signature` réservés dans ADR-027 et ADR-028 sont prévus pour cette éventualité)

## Ce que cette fiche ne contient pas

Aucune conception technique n'est engagée : elle serait prématurée tant que les prérequis ci-dessus ne sont pas réunis. Le format d'échange (`20-…`) est conçu pour qu'un transport réseau puisse s'ajouter au transport par fichier sans rupture de format — c'est la seule anticipation technique faite à ce stade.

---

## Clôture du cycle

| Champ | Valeur |
|---|---|
| Date de clôture | *(sans objet)* |
| Statut final | Hors périmètre de la thèse — à réévaluer après la soutenance |
