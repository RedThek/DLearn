package edu.project.dlearn.presentation.apprentissage

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.domain.model.ExerciceTexteATrous
import edu.project.dlearn.domain.model.Vocabulaire
import edu.project.dlearn.presentation.apprentissage.ApprentissageUiState
import edu.project.dlearn.presentation.apprentissage.ApprentissageViewModel

@Composable
fun ApprentissageScreen(
    viewModel: ApprentissageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val etat = uiState) {
        is ApprentissageUiState.Chargement -> BoiteChargement()
        is ApprentissageUiState.Erreur -> BoiteErreur(etat.message)
        is ApprentissageUiState.Succes -> ApprentissageContenu(
            etat = etat,
            onChangerOnglet = viewModel::onChangerOnglet,
            onReponseFlashcard = viewModel::onReponseFlashcard,
            onValiderExercice = viewModel::onValiderExercice
        )
    }
}

@Composable
private fun ApprentissageContenu(
    etat: ApprentissageUiState.Succes,
    onChangerOnglet: (Boolean) -> Unit,
    onReponseFlashcard: (Boolean) -> Unit,
    onValiderExercice: (Long, String, String) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        // Correspond au sélecteur "Vocabulaire / Texte à trous" en haut de la maquette Apprentissage.
        TabRow(selectedTabIndex = if (etat.ongletFlashcard) 0 else 1) {
            Tab(
                selected = etat.ongletFlashcard,
                onClick = { onChangerOnglet(true) },
                text = { Text("Vocabulaire") }
            )
            Tab(
                selected = !etat.ongletFlashcard,
                onClick = { onChangerOnglet(false) },
                text = { Text("Texte à trous") }
            )
        }

        if (etat.ongletFlashcard) {
            FlashcardSection(
                flashcards = etat.flashcards,
                index = etat.indexFlashcardActuelle,
                onReponse = onReponseFlashcard
            )
        } else {
            TexteATrousSection(
                exercices = etat.exercices,
                onValider = onValiderExercice
            )
        }
    }
}

@Composable
private fun FlashcardSection(
    flashcards: List<Vocabulaire>,
    index: Int,
    onReponse: (Boolean) -> Unit
) {
    val carte = flashcards.getOrNull(index)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        if (carte == null) {
            Text("Bravo, aucune carte à réviser pour le moment !", style = MaterialTheme.typography.titleMedium)
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LinearProgressIndicator(
                    progress = { (index + 1f) / flashcards.size },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
                FlashcardRetournable(carte)
                Spacer(Modifier.height(32.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedButton(onClick = { onReponse(false) }) {
                        Text("À revoir")
                    }
                    Button(onClick = { onReponse(true) }) {
                        Text("Je savais")
                    }
                }
            }
        }
    }
}

@Composable
private fun FlashcardRetournable(carte: Vocabulaire) {
    // Tap pour retourner la carte : allemand au recto, français + exemple au verso.
    var retournee by remember(carte.id) { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (retournee) 180f else 0f, label = "rotationCarte")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .graphicsLayer { rotationY = rotation },
        onClick = { retournee = !retournee },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (rotation <= 90f) {
                Text(carte.motAllemand, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.graphicsLayer { rotationY = 180f }
                ) {
                    Text(carte.motFrancais, style = MaterialTheme.typography.headlineLarge)
                    carte.exemplePhrase?.let {
                        Spacer(Modifier.height(8.dp))
                        Text(it, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun TexteATrousSection(
    exercices: List<ExerciceTexteATrous>,
    onValider: (Long, String, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(exercices, key = { it.id }) { exercice ->
            ExerciceCarte(exercice, onValider)
        }
    }
}

@Composable
private fun ExerciceCarte(
    exercice: ExerciceTexteATrous,
    onValider: (Long, String, String) -> Unit
) {
    var reponse by remember { mutableStateOf("") }
    var resultat by remember { mutableStateOf<Boolean?>(null) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(exercice.phraseAvecTrou, style = MaterialTheme.typography.bodyLarge)
            exercice.indice?.let {
                Text("Indice : $it", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = reponse,
                onValueChange = { reponse = it; resultat = null },
                label = { Text("Votre réponse") },
                singleLine = true,
                isError = resultat == false,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                val correcte = reponse.trim().equals(exercice.reponseCorrecte.trim(), ignoreCase = true)
                resultat = correcte
                onValider(exercice.id, reponse, exercice.reponseCorrecte)
            }) {
                Text("Valider")
            }
            resultat?.let { estCorrecte ->
                Text(
                    if (estCorrecte) "✓ Correct !" else "✗ Réponse attendue : ${exercice.reponseCorrecte}",
                    color = if (estCorrecte) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun BoiteChargement() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun BoiteErreur(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(message, color = MaterialTheme.colorScheme.error)
    }
}
