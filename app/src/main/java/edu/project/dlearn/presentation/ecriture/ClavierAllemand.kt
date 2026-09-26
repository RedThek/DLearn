package edu.project.dlearn.presentation.ecriture

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ADR-011 : caractères spéciaux allemands insérables en un tap.
private val CARACTERES_ALLEMANDS = listOf("ä", "ö", "ü", "ß", "Ä", "Ö", "Ü")

@Composable
fun ClavierAllemand(
    onCaractereTap: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier      = modifier.fillMaxWidth(),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier             = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CARACTERES_ALLEMANDS.forEach { caractere ->
                val desc = when (caractere) {
                    "ä" -> "a tréma minuscule"
                    "ö" -> "o tréma minuscule"
                    "ü" -> "u tréma minuscule"
                    "ß" -> "eszett"
                    "Ä" -> "a tréma majuscule"
                    "Ö" -> "o tréma majuscule"
                    "Ü" -> "u tréma majuscule"
                    else -> caractere
                }
                Surface(
                    onClick = { onCaractereTap(caractere) },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier
                        .height(48.dp)
                        .semantics { contentDescription = desc }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(
                            text       = caractere,
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
