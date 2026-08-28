# Guide enseignant (onboarding) — Liteschreib IKII

Ce guide accompagne l'enseignant référent lors du déploiement pilote. Il couvre l'installation de l'application (ADR-010), la configuration initiale de la voix TTS (ADR-007), et l'usage de base du dashboard.

## 1. Avant de commencer

- [ ] Le protocole éthique (`10-protocole-ethique-consentement.md`) a été suivi : autorisations d'établissement, consentements parentaux et assentiments élèves réunis
- [ ] Un accès Wi-Fi (établissement ou personnel) est disponible **une seule fois**, pour l'étape de configuration de la voix TTS
- [ ] Les appareils des élèves sont sous Android 9.0 ou supérieur (ADR-005) — en cas de doute, vérifier dans `Paramètres > À propos du téléphone > Version d'Android`

## 2. Installer l'application (procédure « sources inconnues »)

L'application est distribuée par transfert direct de fichier, sans passer par le Google Play Store (ADR-010).

1. Recevoir le fichier `.apk` transmis par le porteur du projet (USB, Bluetooth, ou carte SD)
2. Transférer ce fichier sur chaque appareil élève (via le même canal)
3. Sur l'appareil élève, ouvrir le fichier `.apk` depuis le gestionnaire de fichiers
4. Si un message de sécurité apparaît (« Installation bloquée » ou similaire), suivre l'invite pour autoriser l'installation depuis cette source : `Paramètres > Autoriser cette source` (le libellé exact varie selon la version d'Android et le fabricant)
5. Terminer l'installation
6. Rassurer l'élève/le parent si besoin : ce message est une précaution standard d'Android, pas une alerte sur l'application elle-même (voir notice de confidentialité, `12-politique-confidentialite-notice-information.md`)

> Astuce : réaliser cette étape en une seule séance pour tous les élèves de la classe simplifie l'accompagnement et réduit le risque R-16 (friction à l'installation).

## 3. Configurer la voix de lecture audio (une seule fois, ADR-007)

1. S'assurer que l'appareil est connecté au Wi-Fi de l'établissement
2. Ouvrir l'application et accéder au module **Apprentissage**
3. Si la voix allemande n'est pas installée, l'application affiche une proposition de téléchargement — accepter cette proposition
4. Attendre la fin du téléchargement (quelques dizaines de secondes selon la connexion)
5. Une fois terminé, vérifier que la lecture audio fonctionne en **mode avion** (désactiver le Wi-Fi et tester à nouveau) — cela confirme que l'usage ultérieur sera bien hors ligne
6. Répéter cette étape pour chaque appareil élève avant le début de l'usage pédagogique en autonomie

## 4. Créer son profil enseignant et sa classe

1. Au lancement de l'application, sélectionner le profil **Enseignant** (l'application est unique, avec sélecteur de rôle, ADR-009)
2. Créer une classe (nom, ex. « 3ème A »)
3. Ajouter les élèves participants (un profil par élève, voir FR-01/FR-02)

## 5. Utiliser le dashboard enseignant

- **Vue classes/élèves** : consulter la liste des élèves rattachés et leur progression agrégée par niveau GeR
- **Assigner un contenu** : sélectionner une unité et l'assigner à un élève ou à toute la classe
- **Consulter les productions écrites** : ouvrir les productions soumises par les élèves pour lecture

## 6. Synchroniser les données élève ↔ enseignant

Le transfert se fait sans connexion internet, via export/import de fichier (ADR-004) :

1. Sur l'appareil élève, ouvrir le module Suivi/Profil et choisir « Exporter mes données »
2. Transférer le fichier généré vers l'appareil enseignant via Nearby Share (si disponible), Bluetooth, ou carte SD
3. Sur l'appareil enseignant, importer le fichier reçu depuis le dashboard
4. Vérifier que la progression de l'élève apparaît bien à jour dans le dashboard

> En cas d'échec de Nearby Share (message d'erreur ou blocage), utiliser le repli Bluetooth classique ou le transfert par carte SD (voir risque R-15).

## 7. En cas de problème

| Symptôme | Piste de résolution |
|---|---|
| Le fichier `.apk` ne s'installe pas | Vérifier que l'espace de stockage est suffisant ; vérifier la version d'Android (doit être 9.0+) |
| La voix TTS ne se télécharge pas | Vérifier la connexion Wi-Fi ; réessayer une fois la connexion stabilisée |
| La synchronisation échoue | Vérifier que le Bluetooth est activé sur les deux appareils ; essayer le transfert par carte SD en repli |
| Un élève souhaite se retirer du pilote | Se référer au protocole éthique (`10-protocole-ethique-consentement.md`) — supprimer ses données sur demande |

## 8. Contact support

Pour toute question technique pendant le pilote, contacter : *[nom du porteur de projet, à compléter]*.
