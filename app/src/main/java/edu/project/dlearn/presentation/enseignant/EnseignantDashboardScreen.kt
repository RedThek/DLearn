package edu.project.dlearn.presentation.enseignant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.InitialsAvatar
import edu.project.dlearn.domain.model.UniteApprentissage

@Composable
fun EnseignantDashboardScreen(
    viewModel: EnseignantViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()

    if (etat.enChargement) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(Modifier.fillMaxSize()) {
        // En-tête
        Column(Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
            Text("Bonjour, ${etat.enseignantNom}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("${etat.eleves.size} élève(s) dans votre classe", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        // Onglets
        TabRow(selectedTabIndex = etat.ongletActif.ordinal) {
            OngletEnseignant.entries.forEach { onglet ->
                Tab(
                    selected = etat.ongletActif == onglet,
                    onClick  = { viewModel.onChangerOnglet(onglet) },
                    text     = {
                        Text(
                            when (onglet) {
                                OngletEnseignant.CLASSE     -> "Classe"
                                OngletEnseignant.CONTENUS   -> "Contenus"
                                OngletEnseignant.CORRECTIONS-> "Corrections"
                            }
                        )
                    }
                )
            }
        }

        when (etat.ongletActif) {
            OngletEnseignant.CLASSE      -> OngletClasse(etat.eleves)
            OngletEnseignant.CONTENUS    -> OngletContenus(etat.unitesDisponibles)
            OngletEnseignant.CORRECTIONS -> OngletCorrections()
        }
    }
}

@Composable
private fun OngletClasse(eleves: List<EleveResume>) {
    if (eleves.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.School, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Text("Aucun élève enregistré", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Utilisez le bouton + pour créer un élève.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    } else {
        LazyColumn(
            contentPadding      = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(eleves, key = { it.id }) { eleve ->
                CarteEleve(eleve)
            }
        }
    }
}

@Composable
private fun CarteEleve(eleve: EleveResume) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InitialsAvatar(nomComplet = eleve.nomAffiche, taille = 44.dp)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(eleve.nomAffiche, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    "${eleve.classe ?: "—"} · Niveau ${eleve.niveauGer ?: "—"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LinearProgressIndicator(
                        progress = { eleve.scoreMoyen / 100f },
                        modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp))
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("${eleve.scoreMoyen}%", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun OngletContenus(unites: List<UniteApprentissage>) {
    var uniteAAssigner by remember { mutableStateOf<UniteApprentissage?>(null) }

    LazyColumn(
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(unites, key = { it.id }) { unite ->
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(unite.titre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Surface(shape = RoundedCornerShape(50), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.padding(top = 4.dp)) {
                            Text(unite.niveauGer, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                    OutlinedButton(onClick = { uniteAAssigner = unite }) {
                        Text("Assigner")
                    }
                }
            }
        }
    }

    // Dialog d'assignation
    uniteAAssigner?.let { unite ->
        AlertDialog(
            onDismissRequest = { uniteAAssigner = null },
            title            = { Text("Assigner « ${unite.titre} »") },
            text             = {
                Text(
                    // TODO Sprint 3 : liste des élèves à sélectionner
                    "Cette fonctionnalité assignera l'unité à tous les élèves de votre classe.\n\nLa sélection individuelle sera disponible après la Mission C3 (synchronisation BYOD).",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(onClick = {
                    // TODO Sprint 3 : AssignerContenuUseCase
                    uniteAAssigner = null
                }) { Text("Assigner à la classe") }
            },
            dismissButton = {
                TextButton(onClick = { uniteAAssigner = null }) { Text("Annuler") }
            }
        )
    }
}

@Composable
private fun OngletCorrections() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Icon(Icons.Filled.CloudOff, contentDescription = null, modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(16.dp))
            Text("Corrections disponibles après synchronisation", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(8.dp))
            Text("Les productions écrites des élèves apparaîtront ici après un transfert local (Nearby Share, Bluetooth ou carte SD).", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            Spacer(Modifier.height(16.dp))
            Text("Mission C3 — Sprint 9", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        }
    }
}
