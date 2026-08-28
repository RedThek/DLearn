// presentation/accueil/AccueilScreen.kt
package edu.project.dlearn.presentation.accueil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.project.dlearn.ui.theme.DLearnTheme

@Composable
fun AccueilScreen(
    uiState: AccueilUiState = AccueilUiState(),
    onEleveClick: () -> Unit = {},
    onEnseignantClick: () -> Unit = {}
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(uiState.appName, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(32.dp))
            Button(onClick = onEleveClick, modifier = Modifier.fillMaxWidth()) { Text("Élève") }
            Spacer(Modifier.height(12.dp))
            OutlinedButton(onClick = onEnseignantClick, modifier = Modifier.fillMaxWidth()) { Text("Enseignant") }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccueilScreenPreview() {
    DLearnTheme { AccueilScreen() }
}