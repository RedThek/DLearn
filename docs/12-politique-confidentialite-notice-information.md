# Politique de confidentialité et notice d'information — Liteschreib IKII

Ce document est la notice destinée aux utilisateurs finaux (élèves, parents, enseignants) de l'application, distincte du protocole de consentement à la recherche (`10-protocole-ethique-consentement.md`) qui encadre spécifiquement l'évaluation pilote DBR. Cette notice doit être affichée ou remise lors de l'installation de l'application (voir `15-guide-enseignant-onboarding.md`).

## 1. Quelles données sont collectées ?

| Donnée | Où elle est stockée | Transmise à un tiers ? |
|---|---|---|
| Profil (nom, classe, niveau GeR) | Localement sur l'appareil (base Room) | Non |
| Progression et scores aux exercices | Localement sur l'appareil | Non |
| Productions écrites | Localement sur l'appareil | Non, sauf transfert volontaire vers l'enseignant (synchronisation locale, ADR-004) |
| Données de recherche (si participation au pilote) | Google Forms hors ligne, exportées manuellement | Uniquement dans le cadre du protocole éthique signé (`10-protocole-ethique-consentement.md`), sous forme anonymisée |

## 2. L'application se connecte-t-elle à internet ?

Non, à une exception près et documentée : l'installation initiale de la voix de synthèse vocale allemande peut nécessiter une connexion ponctuelle unique (voir ADR-007), réalisée en contexte scolaire avant tout usage. En dehors de cette opération, l'application fonctionne intégralement hors ligne (ADR-002, NFR-01).

Aucun SDK d'analytics, de publicité ou de suivi cloud n'est intégré à l'application.

## 3. Comment les données circulent-elles entre élève et enseignant ?

Par transfert local direct entre appareils (Nearby Share, Bluetooth, ou carte SD — ADR-004), sans passer par un serveur externe. Aucune donnée élève ne transite par internet à cette occasion.

## 4. Combien de temps les données sont-elles conservées ?

- Les données d'usage (progression, productions écrites) restent sur l'appareil de l'élève tant que l'application y est installée.
- Les données de recherche collectées pour l'évaluation pilote sont conservées selon la durée définie dans `13-plan-gestion-donnees-recherche.md`.

## 5. Quels sont vos droits ?

- Droit de consulter les données stockées sur l'appareil (via l'écran Profil et Suivi de l'application)
- Droit de retrait à tout moment de la participation à l'évaluation pilote, sans conséquence sur la scolarité (voir protocole éthique)
- Droit de suppression des données de recherche déjà collectées, sur simple demande

## 6. Comment l'application est-elle installée ?

L'application est distribuée par transfert direct de fichier (APK), sans passage par un magasin d'applications en ligne (ADR-010). Cela nécessite d'autoriser temporairement l'installation depuis une source autre que le Google Play Store sur l'appareil (« sources inconnues »). Cette étape est réalisée avec l'accompagnement de l'enseignant (voir `15-guide-enseignant-onboarding.md`).

## 7. Contact

Pour toute question relative à cette notice ou à l'utilisation de vos données, contactez : *[nom du porteur de projet / encadrant académique, à compléter]*.

## 8. Historique des versions de cette notice

| Version | Date | Modification |
|---|---|---|
| 1.0 | *à compléter* | Version initiale, alignée sur ADR-002, ADR-004, ADR-007, ADR-010 |
