# Registre des licences et contenus tiers — Liteschreib IKII

> **Statut :** Accepté (ADR-025) — 2026-09-21
> **Rôle :** liste vivante de tout contenu, donnée, police ou bibliothèque **qui n'est pas écrit par le porteur du projet**, ou dont le statut de droits demande une vérification. Complète le champ `statutDroits` des unités (ADR-006).
> ⚠️ Ce registre est un outil de traçabilité, **pas un avis juridique**. Toute ligne marquée « À vérifier » doit être confirmée avec l'encadrant académique avant intégration.

## 1. Contexte à garder en tête

- Le dépôt GitHub est **public** (ADR-013) et son fichier `LICENSE` est **CC0 1.0** : tout ce que le porteur écrit est versé au domaine public.
- CC0 ne peut pas couvrir un contenu tiers. Un élément sous licence à attribution ou à partage à l'identique **ne doit pas être déposé dans le dépôt comme s'il relevait de CC0**.
- Pour cette raison, tout élément tiers embarqué est listé dans un fichier `THIRD_PARTY_NOTICES` (à créer au moment de la première intégration réelle) et dans l'écran « Crédits » de l'application.
- Les contenus produits par des élèves ne sont **jamais** committés (R-17).

## 2. Règles d'admission (ADR-025)

| Catégorie de licence | Intégration dans l'APK | Intégration dans le dépôt |
|---|---|---|
| Domaine public, CC0 | Oui | Oui |
| Permissive (MIT, BSD, Apache-2.0), polices SIL OFL | Oui, avec notice | Oui, avec notice dans `THIRD_PARTY_NOTICES` |
| À attribution (CC BY) | Oui, avec mention dans « Crédits » | Oui, avec notice |
| Partage à l'identique ou copyleft (CC BY-SA, GPL, LGPL, GFDL) | **Non** sans validation écrite de l'encadrant | **Non** ; peut être distribué comme **pack externe** avec sa propre licence (à valider, ADR-028 à venir) |
| Licence inconnue ou « tous droits réservés » | Non | Non |

## 3. Statuts

`Candidat` (envisagé, non intégré) · `À vérifier` · `Approuvé` (validé par l'encadrant) · `Intégré` · `Refusé`

## 4. Registre

| ID | Élément | Type | Source | Licence | Obligations | Compatible dépôt CC0 ? | Statut | Validé par / date |
|---|---|---|---|---|---|---|---|---|
| LIC-001 | Textes originaux des unités (`U-6E-01`, `U-5E-01`, `U-4E-01`, `U-3E-01`, compléments `U-*-02`) | Texte | Rédigés pour le projet (brouillons assistés par modèle de langage) | Versés sous la licence du dépôt (CC0) | Relecture humaine avant statut `Validé` (A0) | Oui | Intégré — **à confirmer** (provenance et relecture) | — |
| LIC-002 | Glossaires, exercices, consignes originaux | Texte | Idem | Idem | Idem | Oui | Intégré — **à confirmer** | — |
| LIC-003 | Bibliothèques Gradle (AndroidX, Hilt/Dagger, Room, Compose) | Code | Dépôts Maven | Apache-2.0 en général | Notice de licence dans l'application | Non concerné (dépendances, non redistribuées dans le dépôt) | Intégré | — |
| LIC-004 | Wiktionary (données lexicales) | Dictionnaire | Wiktionary et extractions dérivées | CC BY-SA (et GFDL) | Attribution + partage à l'identique | **Non** | Candidat — pack externe seulement | — |
| LIC-005 | Textes de Projekt Gutenberg-DE / Wikisource (auteurs du domaine public) | Texte | Éditions en ligne | Domaine public pour l'œuvre ; **éditions modernes, notes ou traductions peuvent être protégées** | Vérifier l'édition et l'année de décès | Oui pour l'œuvre seule | À vérifier (par œuvre) | — |
| LIC-006 | Enregistrements LibriVox | Audio | LibriVox | Domaine public (à confirmer par enregistrement) | Vérifier chaque enregistrement | Oui si confirmé | Candidat | — |
| LIC-007 | OpenThesaurus (synonymes allemands) | Données | OpenThesaurus | **À vérifier** (licence actuelle) | À déterminer | À déterminer | Candidat | — |
| LIC-008 | Dictionnaires Hunspell allemands | Données | Paquets Hunspell | **Varie selon le paquet** | À déterminer | À déterminer | Candidat | — |
| LIC-009 | Polices Noto (dont écriture arabe) | Police | Google Fonts / Noto | SIL OFL 1.1 (à confirmer par version) | Notice ; pas de vente isolée de la police | Oui avec notice | Candidat | — |

## 5. Procédure d'ajout

1. Ajouter une ligne avec le statut `Candidat` **avant** de télécharger ou d'intégrer quoi que ce soit.
2. Renseigner la licence exacte, la source et les obligations. Si l'information n'est pas certaine, écrire « À vérifier » (ne jamais supposer).
3. Appliquer le tableau du §2. En cas de doute : `Refusé` par défaut.
4. Soumettre à l'encadrant (obligatoire pour toute licence à partage à l'identique ou copyleft).
5. À l'intégration : ajouter la notice dans `THIRD_PARTY_NOTICES` et dans l'écran « Crédits », passer le statut à `Intégré`, et noter la version de contenu concernée (`14-charte-versionnage-contenu.md`).

## 6. Lien avec le reste du projet

- `06-architecture-technique.md` — ADR-006 (droits du contenu littéraire), ADR-025 (politique de licences)
- `02-exigences-non-fonctionnelles.md` — NFR-30
- `08-registre-des-risques.md` — R-08, R-17, R-24
- `09-cartographie-contenu-pedagogique.md` — champ « Statut des droits » de chaque unité
- `18-vision-produit-et-horizons.md` — capacités C1, C3, C12 (contenus et données tiers)

## 7. Historique

| Version | Date | Modification |
|---|---|---|
| 1.0 | 2026-09-21 | Création avec les éléments connus et les candidats identifiés |
