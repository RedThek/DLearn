# Sprint 1 Execution Plan (Mission A1 final + Mission A3 complete)

This plan follows the instructions provided in `EXEC-SPRINT1-AGENT.md` to finalize the Design System (A1) and implement multi-profile navigation (A3). It includes a major architectural decision (ADR-014) to pivot from Figma-based UI to agent-generated UI based on structured UX descriptions.

## User Review Required

> [!IMPORTANT]
> This plan involves significant changes to the navigation flow and the introduction of a new screen (`SelectionProfilScreen`).
> It also formalizes ADR-014, which abandons Figma for UI design in favor of agent-driven development.

## Proposed Changes

### Phase A: Finalize Mission A1 (Design System)

#### [MODIFY] [Color.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/theme/Color.kt)
- Update with canonical palette.

#### [MODIFY] [Type.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/theme/Type.kt)
- Update TODO comment regarding system fonts.

---

### Phase B: Complete Mission A3 (Navigation Compose)

#### [NEW] [Routes.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/navigation/Routes.kt)
- Extract route constants.

#### [MODIFY] [NavGraph.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/navigation/NavGraph.kt)
- Use extracted routes and add `SELECTION_PROFIL` route.

#### [MODIFY] [UtilisateurDao.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/data/local/room/UtilisateurDao.kt)
- Add `getAllUtilisateurs()` query.

#### [MODIFY] [AuthRepository.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/domain/repository/AuthRepository.kt)
- Add `getAllProfils()` signature.

#### [MODIFY] [AuthRepositoryImpl.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/data/repository/AuthRepositoryImpl.kt)
- Implement `getAllProfils()` and refactor `toDomain()`.

#### [NEW] [SelectionProfilUiState.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/selectionprofil/SelectionProfilUiState.kt)
- UI state for profile selection.

#### [NEW] [SelectionProfilViewModel.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/selectionprofil/SelectionProfilViewModel.kt)
- ViewModel for profile selection.

#### [NEW] [SelectionProfilScreen.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/selectionprofil/SelectionProfilScreen.kt)
- New screen for multi-profile selection.

#### [MODIFY] [ConnexionViewModel.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/connexion/ConnexionViewModel.kt)
- Load existing profiles and handle navigation to selection screen.

#### [MODIFY] [ConnexionUiState.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/connexion/ConnexionUiState.kt)
- Add `profilsExistants` field.

#### [MODIFY] [ConnexionScreen.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/connexion/ConnexionScreen.kt)
- Add section for existing profiles and navigation callback.

#### [MODIFY] [BottomNavItem.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/navigation/BottomNavItem.kt)
- Fix accent in "Écriture".

---

### Phase C: Instrumentation Tests

#### [NEW] [NavigationTest.kt](file:///C:/Apps/GitHub/DLearn/app/src/androidTest/java/edu/project/dlearn/navigation/NavigationTest.kt)
- Add tests for navigation flow and profile selection.

---

### Phase D: Verification & Documentation

- Run `./gradlew assembleDebug` and `./gradlew testDebugUnitTest`.
- Update documentation files:
    - `docs/06-architecture-technique.md` (Add ADR-014)
    - `docs/missions/A3-navigation-compose.md`
    - `docs/missions/A1-finaliser-design-system.md`
    - `docs/planification/bloc-A-taches.md`
    - `docs/04-missions-et-sprints.md`
    - `docs/05-checklist-quotidienne.md`
    - `docs/ETAT_ACTUEL.md`
- Create `docs/screenshots/A3/.gitkeep`.

## Verification Plan

### Automated Tests
- `./gradlew assembleDebug`
- `./gradlew testDebugUnitTest`
- `./gradlew lintDebug`
- `./gradlew connectedDebugAndroidTest` (requires device/emulator)

### Manual Verification
- Deploy to device and verify the new profile selection flow.
- Check UI consistency with the new canonical color palette.
