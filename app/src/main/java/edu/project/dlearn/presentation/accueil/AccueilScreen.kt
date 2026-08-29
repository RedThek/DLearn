package edu.project.dlearn.presentation.accueil

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.InitialsAvatar
import edu.project.dlearn.ui.theme.DLearnTheme

/*
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
} */

/*
@Composable
fun AccueilScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Willkommen zurück !",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text("Prêt(e) à progresser en allemand aujourdhui ?", style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(24.dp))

        CarteRaccourci("Continuer lapprentissage", "5 mots à réviser aujourdhui")
        Spacer(Modifier.height(12.dp))
        CarteRaccourci("Production écrite", "1 exercice en attente de soumission")
        Spacer(Modifier.height(12.dp))
        CarteRaccourci("Voir ma progression", "Série actuelle : 5 jours")
    }
}

@Composable
private fun CarteRaccourci(titre: String, sousTitre: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(titre, style = MaterialTheme.typography.titleMedium)
            Text(sousTitre, style = MaterialTheme.typography.bodyMedium)
        }
    }
} */

@Composable
fun AccueilScreen(
    onOuvrirLecture: () -> Unit = {},
    viewModel: AccueilViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        EnTeteSalutation(etat.prenom, etat.niveau)

        Spacer(Modifier.height(24.dp))
        Text("Progression globale", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            LinearProgressIndicator(
                progress = { etat.progressionGlobale },
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(Modifier.width(8.dp))
            Text("${(etat.progressionGlobale * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)
        }

        etat.lectureEnCours?.let { lecture ->
            Spacer(Modifier.height(24.dp))
            Text("Reprendre la lecture", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            CarteLectureEnCours(lecture, onClick = onOuvrirLecture)
        }

        if (etat.miniCours.isNotEmpty()) {
            Spacer(Modifier.height(24.dp))
            Text("Mini-cours", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            etat.miniCours.forEach { cours ->
                LigneMiniCours(cours)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun EnTeteSalutation(prenom: String, niveau: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Bonjour, $prenom", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Text("Niveau $niveau", style = MaterialTheme.typography.bodyMedium)
        }
        InitialsAvatar(prenom, taille = 40.dp)
    }
}

@Composable
private fun CarteLectureEnCours(lecture: LectureEnCours, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(lecture.titre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    "Page ${lecture.pageActuelle} sur ${lecture.pageTotale}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LigneMiniCours(cours: MiniCours) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.Edit,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column(Modifier.weight(1f)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(cours.nom, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                Text("${(cours.progression * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { cours.progression },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (cours.progression >= 0.75f) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
            )
        }
    }
}