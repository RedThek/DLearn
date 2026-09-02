package edu.project.dlearn.presentation.theme

import androidx.compose.ui.graphics.Color

// Palette canonique — Liteschreib IKII
// Source : Material Design 3 baseline, adaptée à la charte pédagogique du projet.
// Validée visuellement sur Redmi Note 15 Pro (2026-09-02, ADR-014).
// Contraste WCAG AA vérifié par test AccessibilityChecks (voir NavigationTest.kt).

// --- Primaire : bleu pédagogique ---
val Primary40 = Color(0xFF3D5AFE)
val Primary80 = Color(0xFFBAC3FF)
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFDDE1FF)
val OnPrimaryContainer = Color(0xFF00105C)

// --- Secondaire : vert validation / feedback positif ---
val Secondary40 = Color(0xFF2E7D32)
val Secondary80 = Color(0xFFA5D6A7)
val OnSecondary = Color(0xFFFFFFFF)
val SecondaryContainer = Color(0xFFC8E6C9)
val OnSecondaryContainer = Color(0xFF002204)

// --- Tertiaire : orange révision / "à revoir" ---
val Tertiary40 = Color(0xFFEF6C00)
val Tertiary80 = Color(0xFFFFCC80)
val OnTertiary = Color(0xFFFFFFFF)
val TertiaryContainer = Color(0xFFFFDDB4)
val OnTertiaryContainer = Color(0xFF2C1600)

// --- Erreur ---
val Error40 = Color(0xFFBA1A1A)
val OnError = Color(0xFFFFFFFF)
val ErrorContainer = Color(0xFFFFDAD6)
val OnErrorContainer = Color(0xFF410002)

// --- Surfaces / Neutres ---
val Surface = Color(0xFFFDFBFF)
val OnSurface = Color(0xFF1B1B1F)
val SurfaceVariant = Color(0xFFE2E2EC)
val OnSurfaceVariant = Color(0xFF45464F)
val Outline = Color(0xFF767680)
val OutlineVariant = Color(0xFFC5C6D0)

// --- Raffinement Thème Sombre (ADR-014 + Guide Codex UI) ---
val BackgroundDark = Color(0xFF0B0F14)
val SurfaceDark = Color(0xFF121820)
val SurfaceVariantDark = Color(0xFF18212B)
val OnSurfaceDark = Color(0xFFF5F7FA)
val OnSurfaceVariantDark = Color(0xFFAAB4C0)
val BorderDark = Color(0xFF303A46)
val SuccessGreen = Color(0xFF35C759)
val ErrorRed = Color(0xFFFF5C5C)
