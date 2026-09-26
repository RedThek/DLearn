package edu.project.dlearn.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object LiteschreibTransitions {
    const val DUREE_MS = 220

    val enterFade = fadeIn(tween(DUREE_MS))
    val exitFade = fadeOut(tween(DUREE_MS))

    val enterSlide = slideInHorizontally(tween(DUREE_MS)) { it / 4 } + fadeIn(tween(DUREE_MS))
    val exitSlide = slideOutHorizontally(tween(DUREE_MS)) { it / 4 } + fadeOut(tween(DUREE_MS))
}
