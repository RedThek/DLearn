# Diagrammes UML — Liteschreib IKII

Ce document formalise et versionne les diagrammes UML du projet, jusqu'ici réalisés en conception mais non committés dans `/docs`. Le code source PlantUML est directement exploitable avec le plugin PlantUML d'Android Studio déjà utilisé sur le projet, et sert également d'annexe pour le mémoire.

## 1. Diagramme de cas d'usage (vue d'ensemble)

```plantuml
@startuml UseCaseGlobal
left to right direction
actor Élève
actor Enseignant
actor "Système TTS" as TTS

rectangle "Liteschreib IKII" {
  usecase "Créer/gérer son profil" as UC1
  usecase "Lire un extrait littéraire" as UC2
  usecase "Écouter la lecture audio" as UC3
  usecase "Répondre à des exercices" as UC4
  usecase "Rédiger une production écrite" as UC5
  usecase "Consulter sa progression" as UC6
  usecase "Gérer ses classes et élèves" as UC7
  usecase "Assigner un contenu" as UC8
  usecase "Consulter les productions écrites" as UC9
  usecase "Synchroniser les données (hors ligne)" as UC10
  usecase "Installer la voix TTS (une fois, connecté)" as UC11
}

Élève --> UC1
Élève --> UC2
Élève --> UC3
Élève --> UC4
Élève --> UC5
Élève --> UC6
Élève --> UC10
Élève --> UC11

Enseignant --> UC1
Enseignant --> UC7
Enseignant --> UC8
Enseignant --> UC9
Enseignant --> UC10

UC3 ..> TTS : <<include>>
UC11 ..> TTS : <<include>>
@enduml
```

## 2. Diagramme de classes — modèle de domaine simplifié

Reflète le schéma détaillé dans `11-schema-donnees-room.md`, en se limitant aux entités et relations principales (les DTO/Entity Room techniques ne sont pas représentés ici, seule la vue domaine).

```plantuml
@startuml ClassDomaine
class ProfilEleve {
  +id: String
  +nom: String
  +niveauGerCourant: String
}
class ProfilEnseignant {
  +id: String
  +nom: String
}
class Classe {
  +id: String
  +nom: String
}
class UniteApprentissage {
  +id: String
  +niveauGer: String
  +chapitreCurriculum: String
}
class ExtraitLitteraire {
  +id: String
  +texteAllemand: String
  +statutDroits: String
}
class Exercice {
  +id: String
  +type: String
}
class ProductionEcrite {
  +id: String
  +contenuTexte: String
}
class Progression {
  +statut: String
  +scoreMoyen: Float
}
class PlanificationRevision {
  +dateProchaineRevision: Long
}
class Assignation {
  +cibleType: String
}

ProfilEnseignant "1" -- "N" Classe
Classe "1" -- "N" ProfilEleve
ProfilEleve "1" -- "N" Progression
UniteApprentissage "1" -- "N" Progression
UniteApprentissage "1" -- "N" ExtraitLitteraire
UniteApprentissage "1" -- "N" Exercice
ProfilEleve "1" -- "N" ProductionEcrite
UniteApprentissage "1" -- "N" ProductionEcrite
ProfilEleve "1" -- "N" PlanificationRevision
ProfilEnseignant "1" -- "N" Assignation
Assignation "N" -- "1" UniteApprentissage
@enduml
```

## 3. Diagramme de séquence — Installation de la voix TTS (ADR-007)

```plantuml
@startuml SequenceTTS
actor Élève
participant "Écran Apprentissage" as UI
participant "TtsManager" as Manager
participant "Android TTS Engine" as Engine

Élève -> UI : Ouvre le module Apprentissage
UI -> Manager : vérifierVoixInstallee("de-DE")
alt Voix absente
  Manager -> UI : afficherPropositionTelechargement()
  UI -> Élève : Propose le téléchargement (connexion requise)
  Élève -> UI : Accepte
  UI -> Engine : demanderInstallationVoix("de-DE")
  Engine --> UI : Installation terminée
  UI -> Élève : Confirmation "lecture audio disponible hors ligne"
else Voix déjà présente
  Manager -> UI : voixDisponible = true
end
Élève -> UI : Déclenche la lecture (mode avion possible)
UI -> Engine : lireTexte(extrait)
Engine --> Élève : Lecture audio (sans réseau)
@enduml
```

## 4. Diagramme de séquence — Synchronisation locale enseignant-élève (ADR-004)

```plantuml
@startuml SequenceSync
actor Élève
actor Enseignant
participant "App (appareil élève)" as AppEleve
participant "App (appareil enseignant)" as AppEnseignant

Élève -> AppEleve : Exporter mes données
AppEleve -> AppEleve : Générer fichier d'échange (versionFichierEchange)
AppEleve -> AppEnseignant : Transfert (Nearby Share)
alt Nearby Share indisponible
  AppEleve -> AppEnseignant : Transfert (Bluetooth classique)
  alt Bluetooth indisponible
    AppEleve -> AppEleve : Export vers carte SD
    Enseignant -> AppEnseignant : Import manuel depuis carte SD
  end
end
AppEnseignant -> AppEnseignant : Importer et valider le fichier
AppEnseignant -> Enseignant : Afficher progression mise à jour (dashboard)
@enduml
```

## 5. Statut et gouvernance

| Diagramme | Statut | Dernière mise à jour liée à |
|---|---|---|
| Cas d'usage global | Formalisé | Décisions ADR-004, ADR-007, ADR-009 |
| Classes — domaine | Formalisé (simplifié) | `11-schema-donnees-room.md` |
| Séquence — TTS | Formalisé | ADR-007 |
| Séquence — Synchronisation | Formalisé | ADR-004 |

Tout changement structurel du domaine (nouvelle entité, nouveau flux) doit être répercuté dans ce document en même temps que dans `06-architecture-technique.md` et `11-schema-donnees-room.md`, pour éviter une dérive entre la documentation et le code.
