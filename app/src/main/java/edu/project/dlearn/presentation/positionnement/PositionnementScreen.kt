package edu.project.dlearn.presentation.positionnement

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.presentation.positionnement.PositionnementUiState
import edu.project.dlearn.presentation.positionnement.PositionnementViewModel

@Composable
fun PositionnementScreen(
    onTermine: (niveauPropose: String) -> Unit,
    viewModel: PositionnementViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()

    when (val etatActuel = etat) {
        is PositionnementUiState.Chargement -> BoiteChargement()
        is PositionnementUiState.EnCours -> ContenuQuestion(
            etat = etatActuel,
            onSelectionner = viewModel::onSelectionnerOption,
            onSuivant = viewModel::onSuivant
        )
        is PositionnementUiState.Termine -> {
            // Ecran de transition très bref : le résultat est transmis au parent pour préremplir
            // le profil (niveau CECR) puis rediriger vers l'app principale.
            LaunchedEffect(etatActuel) { onTermine(etatActuel.niveauPropose) }
            BoiteChargement()
        }
    }
}

@Composable
private fun ContenuQuestion(
    etat: PositionnementUiState.EnCours,
    onSelectionner: (Int) -> Unit,
    onSuivant: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            "Test de positionnement · Question ${etat.indexQuestion + 1}/${etat.questions.size}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { etat.progression },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(32.dp))
        Text(
            etat.question.enonce,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))
        etat.question.options.forEachIndexed { index, option ->
            OptionReponse(
                texte = option,
                selectionnee = etat.indexOptionSelectionnee == index,
                onClick = { onSelectionner(index) }
            )
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onSuivant,
            enabled = etat.indexOptionSelectionnee != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Suivant")
        }

        Spacer(Modifier.height(12.dp))
        Text(
            "Ce test nous aide à choisir votre niveau de départ (A1 ou A2).",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun OptionReponse(texte: String, selectionnee: Boolean, onClick: () -> Unit) {
    val couleurBordure = if (selectionnee) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val couleurFond = if (selectionnee) MaterialTheme.colorScheme.primaryContainer else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(couleurFond)
            .border(1.dp, couleurBordure, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(texte, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun BoiteChargement() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
