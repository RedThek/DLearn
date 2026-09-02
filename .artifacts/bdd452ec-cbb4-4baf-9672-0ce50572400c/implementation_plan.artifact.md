# UI/UX Modernization Plan - DLearn

Modernize the `Accueil`, `Suivi`, and `Profil` screens of the DLearn application to adhere to the "Modern Learning Dashboard" design system (Material 3), while respecting the existing Clean Architecture and offline-first principles.

## User Review Required

> [!IMPORTANT]
> The UI update will strictly use the existing `ViewModels` and `UiState` to ensure business logic remains intact. If any piece of data requested by the new UI is missing from the current `UiState`, a placeholder or neutral state will be used as per the documentation's "no fake data in production" rule.

> [!NOTE]
> New shared components will be added to `edu.project.dlearn.core.components` to promote reusability across the three screens.

## Proposed Changes

### Core Components Modernization
New reusable components will be implemented to standardize the look and feel across screens.

#### [NEW] [DlearnSectionHeader.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/core/components/DlearnSectionHeader.kt)
Generic section header with title, optional subtitle, and optional action.

#### [NEW] [ProgressCard.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/core/components/ProgressCard.kt)
Standardized card for displaying progression with a linear indicator.

#### [NEW] [StatItem.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/core/components/StatItem.kt)
Compact statistic item with an icon, value, and label.

#### [NEW] [ActivityCard.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/core/components/ActivityCard.kt)
Generic activity card for "À faire aujourd'hui" or "Historique" sections.

#### [NEW] [EmptyStateCard.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/core/components/EmptyStateCard.kt)
Standardized empty state representation.

---

### UI Modernization

#### [MODIFY] [AccueilScreen.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/accueil/AccueilScreen.kt)
- Refactor to use `LazyColumn`.
- Implement Hero Card for "Continuer mon apprentissage".
- Integrate `StatItem` for quick stats (Streak, Units, Time).
- Modernize "À faire aujourd'hui" and "Ma progression" sections using new shared components.

#### [MODIFY] [SuiviScreen.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/suivi/SuiviScreen.kt)
- Implement global progression summary card.
- Modernize statistics grid using `StatItem`.
- Refactor progression by level into a list of `ProgressCard`.
- Implement compact activity history.

#### [MODIFY] [ProfilScreen.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/profil/ProfilScreen.kt)
- Redesign Header with larger avatar and clear identity/role.
- Integrate progression card for the current level.
- Clean up preferences list using Material 3 `ListItem`.

---

### Theme & Design Tokens

#### [MODIFY] [Color.kt](file:///C:/Apps/GitHub/DLearn/app/src/main/java/edu/project/dlearn/presentation/theme/Color.kt)
- Ensure all roles (`Primary`, `Secondary`, `Tertiary`, `Surface`, etc.) match the "Modern Learning Dashboard" palette.
- Add semantic roles for success/warning if missing.

## Verification Plan

### Automated Tests
- Run existing UI tests: `./gradlew app:connectedDebugAndroidTest`
- Run unit tests for ViewModels: `./gradlew app:testDebugUnitTest`

### Manual Verification
- **Visual Check**: Verify layout responsiveness on 360dp and 412dp screen widths.
- **Offline Check**: Ensure app functionality in Airplane mode.
- **Accessibility**: Verify touch target sizes (>= 48dp) and contrast.
- **Navigation**: Verify all navigation callbacks (Hero card click, activity clicks) lead to correct routes.
