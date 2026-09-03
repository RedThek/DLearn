package edu.project.dlearn.presentation.ecriture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ADR-011 : caractères spéciaux allemands insérables en un tap.
// Positionnée entre le champ de texte et le clavier système, toujours visible.
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
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CARACTERES_ALLEMANDS.forEach { caractere ->
                OutlinedButton(
                    onClick  = { onCaractereTap(caractere) },
                    modifier = Modifier.height(36.dp)
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
