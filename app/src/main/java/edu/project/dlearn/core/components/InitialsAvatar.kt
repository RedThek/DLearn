package edu.project.dlearn.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Avatar circulaire affichant des initiales, utilisé sur les écrans Profil et Accueil.
 * ex: InitialsAvatar("Aïcha N.") -> "AN"  |  InitialsAvatar("Lena") -> "L"
 */
@Composable
fun InitialsAvatar(
    nomComplet: String,
    modifier: Modifier = Modifier,
    taille: Dp = 56.dp
) {
    val initiales = remember(nomComplet) { extraireInitiales(nomComplet) }

    Box(
        modifier = modifier
            .size(taille)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initiales,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = (taille.value / 2.6f).sp
        )
    }
}

private fun extraireInitiales(nomComplet: String): String =
    nomComplet.trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
        .ifBlank { "?" }
