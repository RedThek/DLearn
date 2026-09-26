# EXEC-UI-UX-FLUIDITE — Instructions for a coding agent

> **Role of this file:** sequential, file-level instructions to implement the UI/UX fluidity and beauty pass on Liteschreib IKII (DLearn).  
> **Audience:** a coding agent that has **no** prior conversation. Do not invent extra features. Execute **top to bottom**.  
> **Source of the recommendations:** visual and interaction review of the Compose presentation layer (2026-09-24).  
> **This file is self-contained.** Download it and run it as the sole task brief.

---

## 0. Operating rules (non-negotiable)

1. **Presentation-only.** Touch `presentation/`, `core/components/`, `presentation/theme/`, and tests. Do **not** change Room schemas, DAOs, use cases, or seed content unless a task below explicitly says so.
2. **Clean Architecture and Hilt** stay as they are (`docs/06-architecture-technique.md`). No new repositories for cosmetic work.
3. **Offline-first (NFR-01).** No network calls at runtime. Font files, if added, are bundled locally.
4. **UI language is French (NFR-12).** All new user-visible strings in French. No new emoji in the UI.
5. **WCAG AA contrast (NFR-13)** and **touch targets ≥ 48 dp** on small phones (NFR-14). Do not lower contrast to look “softer”.
6. **Five-tab student IA (NFR-15)** stays: Accueil, Apprentissage, Écriture, Suivi, Profil. Do not add a sixth tab. Do not force this bar onto the teacher dashboard.
7. **Do not implement Mission F4** (points, badges, leaderboards, PDF certificates). Do not invent “défis”.
8. **Do not implement ADR-019** (session duration). Do not fake “Temps”.
9. **Do not replace Accueil mock stats** with Room (`GetProgressionStatsUseCase`). That remains Mission B1. You may **relabel** existing Accueil fields.
10. **Do not restyle the teacher dashboard** except if a compile error forces a shared-component API change. Keep the public signature of shared components backward compatible or update all call sites.
11. **Motion budget:** enter/exit ≤ 250 ms. Prefer `tween(220)` or a short spring. No looping animations, no confetti, no gradients on text.
12. **Commit only if the human asks.** After each phase, leave the tree compiling. Prefer one commit per phase if commits are requested: `feat(ui): …`.
13. After each phase: `./gradlew :app:assembleDebug` must succeed. Fix what you break before starting the next phase.

### North star (use this to decide ties)

The product is **literary German learning in a classroom, on BYOD phones**. Beauty = focus, type, and honest feedback. Not extra chrome, not Duolingo carnival, not a new color brand.

---

## 1. Current code map (read before editing)

| Area | Path |
|---|---|
| Theme | `app/src/main/java/edu/project/dlearn/presentation/theme/{Color,Type,Shape,Theme}.kt` |
| Shared UI | `app/src/main/java/edu/project/dlearn/core/components/` |
| Root nav | `presentation/navigation/NavGraph.kt` (`LiteschreibApp`) |
| Student shell | `presentation/navigation/MainScreen.kt`, `BottomNavItem.kt` |
| Accueil | `presentation/accueil/AccueilScreen.kt`, `AccueilViewModel.kt`, `AccueilUiState.kt` |
| Lecture | `presentation/apprentissage/ApprentissageScreen.kt` |
| Exercices | `presentation/exercice/ExerciceScreen.kt` |
| Écriture | `presentation/ecriture/EcritureScreen.kt`, `ClavierAllemand.kt` |
| Suivi | `presentation/suivi/SuiviScreen.kt` |
| Profil | `presentation/profil/ProfilScreen.kt` |
| Connexion | `presentation/connexion/ConnexionScreen.kt` |
| Positionnement | `presentation/positionnement/PositionnementScreen.kt` |
| Instrumentation | `app/src/androidTest/java/edu/project/dlearn/navigation/NavigationTest.kt` |

### Known facts (do not “rediscover” and then change architecture)

- Student `NavHost` in `MainScreen` uses default (instant) transitions. There is **no** `animate*` usage in the app today.
- Bottom items include `Ecriture` (`route = "ecriture"`). The same `NavHost` already has `composable("ecriture?uniteId={uniteId}")`. `EcritureViewModel` already resolves a unit when `uniteId` is null (level, then first catalogue unit). **Keep that behavior.** The tab must open `EcritureScreen`, not a blank destination.
- Accueil greets twice: app-bar “Guten Tag,” + hero “Bonjour, $prenom !”.
- Accueil, Suivi, and Profil each wrap a `Scaffold` **inside** `MainScreen`’s `Scaffold` (nested top bars / paddings).
- Glossary in lecture uses deprecated `ClickableText` + `AlertDialog`.
- `EcritureScreen` prefixes the prompt with `✏`.
- Suivi: segmented 7/30/Tout does nothing; Temps shows `"—"`; “Lancer un défi” has `onClick = { }`.
- Connexion: “Mot de passe oublié ?” calls `onMotDePasseOublie` default `{}`.
- Dark `ColorScheme` in `Theme.kt` omits several M3 roles (containers, tertiary, onPrimary, etc.).
- Typography is `FontFamily.Default` (ADR-014). Bundling webfonts requires ADR-025 (`docs/19-registre-licences-contenus-tiers.md`) + `THIRD_PARTY_NOTICES` (file does not exist yet). **Phase 6 uses the platform serif** unless the human explicitly authorizes OFL binaries.

---

## 2. Sequence

```
P0  Navigation motion + session chrome          [BLOQUANT]
P1  Accueil: one greeting, one next action
P2  Lecture: book-like extract + glossary sheet
P3  Exercices: animated feedback + haptics
P4  Écriture: studio layout
P5  Honest UI (remove or explain dead controls)
P6  Surfaces, nested Scaffolds, dark tokens, type split
P7  Verify, screenshots, journal
```

Do not skip P0. Later phases assume the shell behaves.

---

## Phase 0 — Navigation motion and session chrome

**Goal:** tab and stack changes feel continuous; exercise sessions own the screen; the Écriture tab always opens writing.

### 0-A · Shared transition spec

Create `app/src/main/java/edu/project/dlearn/presentation/navigation/LiteschreibTransitions.kt`:

```kotlin
object LiteschreibTransitions {
    const val DUREE_MS = 220
}
```

Expose helper lambdas used by both `NavHost`s:

- **Tabs / root:** `fadeIn(tween(220))` + `fadeOut(tween(220))`. No horizontal slide on bottom tabs (avoids fighting the bar).
- **Pushed session** (`exercices/{uniteId}`): `slideInHorizontally { it / 4 } + fadeIn` / `slideOutHorizontally { it / 4 } + fadeOut`.

Apply the same fade pair to `LiteschreibApp`’s root `NavHost` (`NavGraph.kt`) for Connexion → Positionnement → Main.

Use `androidx.compose.animation` / `androidx.navigation.compose` APIs already on the Compose BOM. Do not add a new Gradle dependency.

### 0-B · Hide the bottom bar on exercises only

In `MainScreen.kt`:

- Keep `LiteschreibBottomBar` for Accueil, Apprentissage, Écriture, Suivi, Profil.
- **Hide** it when the current destination route starts with `exercices`.
- Do **not** hide it on `ecriture` (it is a first-class tab).
- Animate the bar with `AnimatedVisibility` (slide + fade, 220 ms) so it does not pop.

When the bar is hidden, `Scaffold` content must still use `innerPadding` correctly (status bar / IME). Do not double-pad.

### 0-C · Écriture tab destination

- Confirm `BottomNavItem.Ecriture.route == "ecriture"` is registered as the same graph destination as `ecriture?uniteId={uniteId}` (optional `uniteId`, default `null`).
- Tapping the tab must `navigate` with `popUpTo(start) { saveState = true }`, `launchSingleTop`, `restoreState` — same as other tabs.
- Navigating from Apprentissage “Rédiger” must still pass `uniteId` (`ecriture?uniteId=$uniteId`) without creating a duplicate back-stack entry of the tab. Prefer `navigate("ecriture?uniteId=$id")` with `launchSingleTop = true`.
- If the optional-query route does not match a bare `"ecriture"` click in this Navigation version, add an explicit `composable(BottomNavItem.Ecriture.route)` that shows `EcritureScreen()` **or** a single route pattern that matches both. Do **not** remove the Écriture tab.

### 0-D · Tests

Extend `NavigationTest.kt` **only if** you can stay on the existing demo login path. Minimum:

- After student login + finish or skip of positionnement is already heavy; do **not** rewrite the whole suite.
- Add a comment in `MainScreen` if a UI test cannot reach main without positionnement.

If you add a test: after reaching main (teacher path is easier — dashboard has no student bar). Student-bar tests may need a test tag. Add `Modifier.testTag("bottom_bar")` on `NavigationBar` and `testTag("nav_ecriture")` on the Écriture item.

### 0-E · Manual check

- Tab Accueil → Apprentissage → Écriture → Suivi → Profil: fade, no crash, Écriture shows an editor (not a blank graph).
- Apprentissage → Exercices: bar disappears; back restores bar + lesson.

**Phase 0 DoD:** assembleDebug green; Écriture tab works; bar hidden on exercises.

---

## Phase 1 — Accueil: one home, one next action

**Goal:** Accueil answers “what do I do in the next 10 minutes?”. Suivi keeps “how am I doing?”.

### 1-A · App bar

In `AccueilScreen.kt` `CenterAlignedTopAppBar`:

- Remove the subtitle `"Guten Tag,"`.
- Keep `etat.prenom` as the title (or `"Salut, ${etat.prenom}"` **once** — not also in the hero).
- Keep `InitialsAvatar`.

### 1-B · Hero

Rewrite `HeroCard`:

- **No** second “Bonjour, $prenom !”.
- Title: `"Prêt pour 10 minutes d'allemand ?"`.
- Supporting line: next unit title from `etat.lectureEnCours?.titre`, else `"Lance ta première leçon."`.
- Keep the progress bar + percent from `etat.progressionGlobale`.
- Primary button label:
  - if `lectureEnCours != null`: `"Continuer · ${lecture.titre}"` (truncate with `TextOverflow.Ellipsis`, max 1 line);
  - else: `"Commencer"`.
- `onClick` stays `onOuvrirLecture` (still navigates to the Apprentissage tab). **Do not** invent deep-links to a unit id in this phase.

### 1-C · Section order

Keep this order in the `LazyColumn`:

1. Hero  
2. Stats row (Série / Unités / Temps) — still bound to existing Accueil fields, even if mocked  
3. `"À faire aujourd'hui"` (lecture card or empty state)  
4. `"Assigné par ton enseignant"` only if `assignations.isNotEmpty()`  
5. `"Ma progression"` + `ProgressCard`  
6. `"Conseil du jour"` — keep the existing static card (FR-style pedagogical nudge). Do not add a CMS.

Empty state copy (French): title `"Aucune activité"`, message `"Ton parcours commence ici. Lance ta première leçon."`, action `"Commencer"`.

### 1-D · Assignations click

Leave `onClick = onOuvrirLecture` unless a unit-id navigation already exists. Do not half-build a new route.

**Phase 1 DoD:** one greeting on Accueil; CTA names the current lecture when present; no English strings.

---

## Phase 2 — Lecture as a book + glossary sheet

**Files:** `ApprentissageScreen.kt` (and only extract a tiny composable in the same package if the file becomes unreadable).

### 2-A · Extract typography (no binary fonts yet)

In `Type.kt` add:

```kotlin
val LiteschreibLiteraryText = TextStyle(
    fontFamily = FontFamily.Serif,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    lineHeight = 32.sp
)
```

Use **only** for the German extract in `TexteAvecGlossaire`. UI chrome (titles, buttons, chips) stays on `LiteschreibTypography` / `FontFamily.Default`.

Do **not** download Noto/Source Serif in this phase (licence registry). If you add files under `res/font/` without OFL notices you are out of process.

### 2-B · Layout of `LectureUniteScreen`

- Keep back + title + GeR chip.
- Move `objectifsApprentissage` into a compact `AssistChip` or one-line caption `"Objectifs"` that expands (`AnimatedVisibility`) — default **collapsed** so the extract is first.
- Extract sits in a `Surface` with `color = surface`, **not** heavy `surfaceVariant`. Padding 20.dp, shape `MaterialTheme.shapes.large`.
- Author line stays under the extract: `"— $auteur"`.
- Bottom actions stay: Outlined `"Rédiger"` + Filled `"Exercices"`, height 52.dp.

### 2-C · Replace ClickableText

Replace deprecated `ClickableText` with `Text` + `LinkAnnotation` / `SpanStyle` (Compose 1.7+ `AnnotatedString` `LinkAnnotation.Clickable`), or `ClickableText` only if the BOM cannot compile `LinkAnnotation`.

Keep glossary words: primary color, underline, medium weight. Tappable.

### 2-D · Glossary as a modal bottom sheet

Replace `AlertDialog` with `ModalBottomSheet` (`ExperimentalMaterial3Api`):

- Title: `entree.motAllemand` (serif allowed here).
- Body: `entree.traductionFr`.
- If other fields exist on `EntreeGlossaire`, show them; do **not** invent etymology.
- Dismiss: swipe or button `"Fermer"`.
- Scrim must not destroy scroll position of the extract.

### 2-E · Bibliothèque list

`CarteUnite`: outlined card (`CardDefaults.outlinedCardBorder()`), elevation **0**. Keep GeR chip + chevron. Do not add fake progress bars without data.

**Phase 2 DoD:** extract is serif 20/32; glossary is a sheet; no `ClickableText` if the BOM allows; `AlertDialog` gone from this screen.

---

## Phase 3 — Exercise feedback

**File:** `ExerciceScreen.kt`. Optionally mirror selection styling on `PositionnementScreen.kt` **without** changing scoring logic.

### 3-A · Question change

Wrap the block that shows `exercice.enonce` + options in `AnimatedContent(targetState = etat.indexActuel, transitionSpec = { fadeIn + fadeOut })`. Size transform: `SizeTransform(clip = false)`.

### 3-B · Option chrome

`QcmOptions` / `VraiFauxOptions`:

- Selected (not yet validated): 2.dp border `primary`, fill `primary.copy(alpha = 0.08f)`.
- Correct after validate: border/fill `secondary` (existing success green).
- Wrong selected: `error`.
- Animate color with `animateColorAsState(tween(180))`.
- Minimum height 52.dp; keep 12.dp corners.

Do not use `"✓"` / `"✗"` as the only signal. You may keep a short French sentence **plus** color:

- success: `"Bonne réponse"`
- error: `"Pas tout à fait — continue"`

Remove the unicode checkmarks from the strings.

### 3-C · Haptics

On `onValider` result (in the composable when `etat.resultat` becomes non-null, once per question):

```kotlin
val view = LocalView.current
if (etat.resultat == true) {
    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM) // fallback CLOCK_TICK if API < 30
} else {
    view.performHapticFeedback(HapticFeedbackConstants.REJECT) // fallback LONG_PRESS
}
```

Guard with `Build.VERSION.SDK_INT`. Do not crash on older devices. Fire **once** per validation (key on `indexActuel` + resultat).

### 3-D · Progress bar

Keep `LinearProgressIndicator(progress = { etat.progression })`. Optional: `animateFloatAsState` for the fraction.

### 3-E · Result screen

`EcranResultat`:

- Headline: `"Exercices terminés"`
- Score: `"${bonnes} / ${total} bonnes réponses"`
- One primary button: `"Retour à la leçon"` (same `onTermine`).
- Do **not** auto-navigate to Écriture in this phase (would surprise teachers). Optional text button `"Rédiger"` **only if** you already have `onCommencerEcriture` on this screen; today `ExerciceScreen(onTermine)` does not. **Do not** widen the callback unless it is a 5-line NavHost change. Prefer not to.

Empty state: keep French copy, button `"Retour"`.

**Phase 3 DoD:** no tick/cross-only feedback; haptic once per answer; question crossfade; assembleDebug green.

---

## Phase 4 — Writing studio

**Files:** `EcritureScreen.kt`, `ClavierAllemand.kt`.

### 4-A · Prompt

- Remove `"✏ "` from the consigne.
- Label the surface `"Consigne"` (`labelMedium` + `onSurfaceVariant`).
- Body = existing consigne text. Do **not** swap in `PRODUCTION_GUIDEE` (that is Mission B3-T06, out of scope).
- Default: consigne collapsed to 2 lines with `"Voir plus"` / `"Réduire"` (`AnimatedVisibility` or `maxLines`).

### 4-B · Editor

- `OutlinedTextField` → filled (`TextFieldDefaults.colors` container `surfaceVariant` at low alpha) **or** keep outlined but `Modifier.weight(1f)` with `minHeight(180.dp)`.
- Label stays `"Votre texte en allemand"`.
- Disable when `etat.soumis`.
- Keep debounce save in the ViewModel (do not touch 1500 ms).

### 4-C · German key row

`ClavierAllemand`: stay **above** the system IME (`imePadding` already on the column).

- Replace seven `OutlinedButton`s with compact `TextButton`/`Surface` chips, min 48.dp height, even spacing.
- Keep characters: ä ö ü ß Ä Ö Ü.
- `contentDescription` per key for TalkBack, e.g. `"a tréma"`.

### 4-D · Auto-eval + submit

- Wrap `GrilleAutoEvaluation` in `AnimatedVisibility(visible = etat.afficherAutoEvaluation)`.
- On submit success (`etat.soumis`): `Snackbar` `"Soumis à l'enseignant"` via a local `SnackbarHost` on this screen **or** a `Text` that is not a raw checkmark. Remove `"✓ Soumis à l'enseignant"`.
- Keep `"Auto-évaluation"` / `"Masquer l'auto-évaluation"` and `"Soumettre"`.

**Phase 4 DoD:** no emoji; editor is the dominant surface; umlauts still insert; submit feedback without a checkmark character.

---

## Phase 5 — Honest UI (dead controls)

### 5-A · Suivi (`SuiviScreen.kt`)

1. **Segmented 7 / 30 / Tout**  
   Either wire it to a real filter in `SuiviViewModel` **if and only if** the stats object already has the data to filter, **or remove the row**. Do **not** leave `onClick = { }`. Default: **remove** the `SingleChoiceSegmentedButtonRow` and add a one-line comment: `// Filtre temporel : bloqué tant que ADR-019 / agrégation par période n'existe pas`.

2. **Temps `StatItem`**  
   Keep `"—"` **or** hide the third `StatItem` entirely. Preferred: **hide** so students are not drawn to a dash. Keep the AN-F3-01 comment in source.

3. **“Lancer un défi” card**  
   Remove the encouragement `EmptyStateCard` whose `onActionClick` is empty. Keep a non-action sentence if you want, as `Text` under history — **no** button.

4. History empty card: keep `"Historique bientôt disponible"` (honest). Change `EmptyStateCard` icon only in Phase 6.

### 5-B · Connexion (`ConnexionScreen.kt`)

Replace `"Mot de passe oublié ?"` `TextButton` that no-ops:

- Use a `TextButton` that sets a local `var messageAide` or shows a `Snackbar`: `"Demande un nouveau mot de passe à ton enseignant."`
- Or replace the button with `Text` of that sentence (`bodySmall`, `onSurfaceVariant`).
- Remove the unused `onMotDePasseOublie` parameter if it has no real callers; update call sites (`NavGraph.kt`).

### 5-C · Production guidée inside exercises

`ProductionGuideeConsigne` already explains to use the Écriture module. Keep it. Add a button `"Ouvrir l'écriture"` **only if** you pass a callback from `MainScreen` (`onCommencerEcriture`). If that requires threading a new lambda through `ExerciceScreen`, do it: `ExerciceScreen(onTermine, onOuvrirEcriture: () -> Unit)` and navigate to `ecriture?uniteId=$uniteId` (uniteId is already in the exercices route). This is in scope because it closes a dead-end.

**Phase 5 DoD:** no control that looks tappable and does nothing, except chips that are purely decorative (Profil niveau chip may stay non-navigating; if `onClick = { }` on `SuggestionChip`, set `enabled = false` or use a non-clickable `AssistChip`).

---

## Phase 6 — Surfaces, shell, dark theme, type split

### 6-A · Kill nested Scaffolds (student)

`MainScreen` already provides the only bottom bar `Scaffold`.

- **AccueilScreen:** remove inner `Scaffold`. Use a `Column` + header row (title + avatar) + `LazyColumn`. Apply `contentPadding` yourself (16.dp). Do not read a second `padding` from a nested scaffold.
- **SuiviScreen:** same — title `"Mon suivi"` as first item or a simple header, then list.
- **ProfilScreen:** same — keep the logout `AlertDialog`. Header `"Mon profil"` as list item.

Do **not** remove the teacher `Scaffold` on `EnseignantDashboardScreen`.

Window insets: with one `Scaffold` in `MainScreen`, child screens must **not** add extra `imePadding` except `EcritureScreen` (keep `imePadding` there).

### 6-B · Card language (shared components)

Unify elevation:

| Component | Rule |
|---|---|
| `ActivityCard` | elevation **0**, `outlinedCardBorder()`, shape `MaterialTheme.shapes.medium` |
| `ProgressCard` | already outlined / 0 — keep |
| `EmptyStateCard` | keep 0 elevation; add optional `icon: ImageVector = Icons.Default.Info` |
| `StatItem` | keep; ensure min height ~72.dp so three-up row still tappable |

`EmptyStateCard` usages:

- No activity / no history: `Icons.Outlined.MenuBook` or `Icons.Default.Info` (pick one outlined icon per meaning; still Material icons, no emoji).
- Do not use the same title for empty history and encouragement (encouragement was removed in 5-A).

### 6-C · Dark `ColorScheme`

In `Theme.kt` `DarkColors`, **fill missing M3 roles** using existing tokens in `Color.kt` (`PrimaryContainer`, `OnPrimaryContainer`, `SecondaryContainer`, `Tertiary40/80`, `ErrorContainer`, `OutlineVariant`, `OnSurfaceDark`, etc.):

Set at least: `primaryContainer`, `onPrimary`, `onPrimaryContainer`, `secondaryContainer`, `onSecondary`, `tertiary`, `tertiaryContainer`, `onTertiary`, `errorContainer`, `onErrorContainer`, `outlineVariant`, `surfaceContainerHighest` if the compiler knows it (skip if the M3 version lacks the slot).

Do **not** enable `dynamicColor` (keep `false`, ADR-014).

Light scheme: add `outlineVariant = OutlineVariant` if missing.

### 6-D · Optional OFL fonts (SKIP unless human says yes)

Default: **skip**. Platform `FontFamily.Serif` for literary text is enough.

If explicitly requested:

1. Add/update `docs/19-registre-licences-contenus-tiers.md` (LIC-009 Noto Sans → `Intégré`; new LIC for Source Serif 4 / Literata, SIL OFL 1.1).
2. Create `THIRD_PARTY_NOTICES` at repo root with OFL text + font names/versions.
3. Place `.ttf` in `app/src/main/res/font/` (`noto_sans_regular`, `source_serif4_regular`, weights as needed).
4. Wire `FontFamily` in `Type.kt`.
5. Do not subset-sell fonts; keep OFL reserved-name rules.

### 6-E · Positionnement

Reuse the same option-border treatment as QCM (no haptics required). Keep auto-finish `LaunchedEffect` to Main.

**Phase 6 DoD:** one student `Scaffold`; cards outlined consistently; dark theme does not show unset/black chips; literary serif still in place.

---

## Phase 7 — Verify

### 7-A · Build

```
./gradlew :app:assembleDebug
./gradlew :app:assembleDebugAndroidTest
```

Run `NavigationTest` if the emulator/SDK is available. If not, report that instrumentation was not run.

### 7-B · Manual script (emulator or device)

1. Cold start → Connexion: logo, no dead “forgot password”.
2. Teacher demo login → dashboard unchanged (smoke).
3. Student demo → positionnement → Accueil: **one** greeting; CTA shows lecture title.
4. Tabs: all five work; Écriture opens an editor; fades present.
5. Apprentissage → open a unit → tap a glossary word → **sheet** (not dialog) → Fermer.
6. Exercices: select, Valider, color + haptic, Suivant, result, back → bottom bar returns.
7. Écriture: no emoji; umlauts insert; submit snackbar/text; keyboard does not hide the umlaut row.
8. Suivi: no filtre row, no défi button, no third fake time **or** time hidden.
9. Toggle system dark theme: surfaces readable, primary buttons still contrasted (spot-check Accueil + Exercices).
10. Small width (~360 dp): stats row does not clip labels; 52.dp buttons still full width.

### 7-C · Documentation (minimal)

Update **only**:

- `docs/journal/YYYY-MM-DD.md` (today’s date) with a short DBR entry: what changed, screenshots paths if any.
- Optional: 2–4 screenshots under `docs/screenshots/ui-ux-fluidite/` (accueil, lecture+sheet, exercice feedback, ecriture). Not required if no device.

Do **not** rewrite `04-missions-et-sprints.md` or mark B1/B2/B3 done. This pass is orthogonal polish.

Do **not** create a new ADR unless you bundled webfonts (then a short note in `06-architecture-technique.md` + licence registry).

---

## 3. Out of scope (refuse these if they appear mid-task)

- Gamification, streaks animation beyond existing `StatItem`, leaderboards (F4 / ADR-023).
- TTS (B2-T10+), GeR filter on the library (B2-T08), unit status badges (B2-T09).
- Real consigne from `PRODUCTION_GUIDEE` (B3-T06).
- Wiring Accueil/Profil mocks to Room (B1 remainder).
- Teacher IA redesign, new bottom bar for teachers.
- Material You dynamic color.
- New Gradle libraries (Lottie, extra animation libs).
- English UI, German UI chrome (content extracts stay German; chrome stays French).
- Refactors of domain/data “while you’re here”.

---

## 4. Suggested commits (only if asked)

1. `feat(ui): transitions de navigation et barre masquée sur les exercices`  
2. `feat(ui): accueil à une seule action suivante`  
3. `feat(ui): lecture typographique et glossaire en feuille`  
4. `feat(ui): feedback d'exercice animé et haptique`  
5. `feat(ui): atelier d'écriture et contrôles honnêtes`  
6. `feat(ui): surfaces unifiées et thème sombre complet`

---

## 5. Definition of Done (whole file)

- [ ] P0–P6 implemented as specified (P6-D skipped by default)
- [ ] `assembleDebug` succeeds
- [ ] French-only chrome; no new emoji
- [ ] Écriture tab opens `EcritureScreen`
- [ ] Bottom bar hidden on `exercices/*` only
- [ ] No tappable no-ops on Suivi / Connexion / Profil chips
- [ ] Glossary is a sheet; extract uses serif 20/32
- [ ] Teacher dashboard behavior unchanged
- [ ] Journal note written if the human wants docs; no false “mission validée” flags

If blocked (BOM too old for `LinkAnnotation`, haptic constants missing), implement the fallback named in that task and continue. Do not stop the whole EXEC for a polish API.
