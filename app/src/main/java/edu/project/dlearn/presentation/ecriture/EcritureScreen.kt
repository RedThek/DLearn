package edu.project.dlearn.presentation.ecriture

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Ecran encore non maquette dans Figma au moment de cette implementation.
// Structure alignee sur le diagramme de sequence "soumission decriture" deja realise :
// Eleve redige -> sauvegarde brouillon locale (Room) -> soumission -> feedback affiche.
// A remplacer par le composable final des que la maquette Ecriture guidee sera validee.
@Composable
fun EcritureScreen() {
    var texte by remember { mutableStateOf("") }
    var soumis by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Production écrite", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = texte,
            onValueChange = { texte = it; soumis = false },
            label = { Text("Votre texte en allemand") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            maxLines = Int.MAX_VALUE
        )
        Spacer(Modifier.height(12.dp))
        Row {
            OutlinedButton(onClick = { /* TODO: sauvegarder brouillon dans Room */ }) {
                Text("Enregistrer le brouillon")
            }
            Spacer(Modifier.width(12.dp))
            Button(onClick = { soumis = true /* TODO: use case SoumettreEcritureUseCase */ }) {
                Text("Soumettre")
            }
        }
        if (soumis) {
            Spacer(Modifier.height(8.dp))
            Text("Texte soumis à lenseignant", color = MaterialTheme.colorScheme.secondary)
        }
    }
}
