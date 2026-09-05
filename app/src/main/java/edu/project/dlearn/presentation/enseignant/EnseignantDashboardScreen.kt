package edu.project.dlearn.presentation.enseignant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudOff
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
    onCreerEleve: () -> Unit = {},
    viewModel: EnseignantViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()

    if (etat.enChargement) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        floatingActionButton = {
            if (etat.ongletActif == OngletEnseignant.CLASSE) {
                FloatingActionButton(onClick = onCreerEleve) {
                    Icon(Icons.Filled.Add, contentDescription = "Créer un élève")
                }
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                Text("Bonjour, ${etat.enseignantNom}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("${etat.eleves.size} élève(s) dans votre classe", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            TabRow(selectedTabIndex = etat.ongletActif.ordinal) {
                OngletEnseignant.entries.forEach { onglet ->
                    Tab(
                        selected = etat.ongletActif == onglet,
                        onClick  = { viewModel.onChangerOnglet(onglet) },
                        text     = {
                            Text(
                                when (onglet) {
                                    OngletEnseignant.CLASSE      -> "Classe"
                                    OngletEnseignant.CONTENUS    -> "Contenus"
                                    OngletEnseignant.CORRECTIONS -> "Corrections (${etat.productionsACorriger.size})"
                                }
                            )
                        }
                    )
                }
            }

            when (etat.ongletActif) {
                OngletEnseignant.CLASSE      -> OngletClasse(etat.eleves)
                OngletEnseignant.CONTENUS    -> OngletContenus(
                    unites  = etat.unitesDisponibles,
                    eleves  = etat.eleves,
                    onAssigner = viewModel::onAssigner
                )
                OngletEnseignant.CORRECTIONS -> OngletCorrections(etat.productionsACorriger)
            }
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
private fun OngletContenus(
    unites: List<UniteApprentissage>,
    eleves: List<EleveResume>,
    onAssigner: (uniteId: String, cibleType: String, cibleId: String) -> Unit
) {
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

    uniteAAssigner?.let { unite ->
        DialogAssignation(
            unite    = unite,
            eleves   = eleves,
            onDismiss = { uniteAAssigner = null },
            onConfirmer = { cibleType, cibleIds ->
                cibleIds.forEach { id -> onAssigner(unite.id, cibleType, id) }
                uniteAAssigner = null
            }
        )
    }
}

@Composable
private fun DialogAssignation(
    unite: UniteApprentissage,
    eleves: List<EleveResume>,
    onDismiss: () -> Unit,
    onConfirmer: (cibleType: String, cibleIds: List<String>) -> Unit
) {
    var modeClasse by remember { mutableStateOf(true) }
    val classesDisponibles = remember(eleves) { eleves.mapNotNull { it.classe }.distinct() }
    var classeSelectionnee by remember(classesDisponibles) { mutableStateOf(classesDisponibles.firstOrNull()) }
    val elevesSelectionnes = remember { mutableStateListOf<Long>() }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Assigner « ${unite.titre} »") },
        text = {
            Column {
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    SegmentedButton(
                        selected = modeClasse, onClick = { modeClasse = true },
                        shape = SegmentedButtonDefaults.itemShape(0, 2)
                    ) { Text("Toute une classe") }
                    SegmentedButton(
                        selected = !modeClasse, onClick = { modeClasse = false },
                        shape = SegmentedButtonDefaults.itemShape(1, 2)
                    ) { Text("Élève(s)") }
                }

                Spacer(Modifier.height(16.dp))

                if (modeClasse) {
                    if (classesDisponibles.isEmpty()) {
                        Text(
                            "Aucune classe renseignée parmi les élèves enregistrés.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        classesDisponibles.forEach { classe ->
                            Row(
                                Modifier.fillMaxWidth().clickable { classeSelectionnee = classe },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(selected = classeSelectionnee == classe, onClick = { classeSelectionnee = classe })
                                Text(classe)
                            }
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier.heightIn(max = 240.dp)) {
                        items(eleves, key = { it.id }) { eleve ->
                            Row(
                                Modifier.fillMaxWidth().clickable {
                                    if (elevesSelectionnes.contains(eleve.id)) elevesSelectionnes.remove(eleve.id)
                                    else elevesSelectionnes.add(eleve.id)
                                },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = elevesSelectionnes.contains(eleve.id),
                                    onCheckedChange = {
                                        if (it) elevesSelectionnes.add(eleve.id) else elevesSelectionnes.remove(eleve.id)
                                    }
                                )
                                Text("${eleve.nomAffiche}${eleve.classe?.let { " · $it" } ?: ""}")
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = if (modeClasse) classeSelectionnee != null else elevesSelectionnes.isNotEmpty(),
                onClick = {
                    if (modeClasse) {
                        classeSelectionnee?.let { onConfirmer("CLASSE", listOf(it)) }
                    } else {
                        onConfirmer("ELEVE", elevesSelectionnes.map { it.toString() })
                    }
                }
            ) { Text("Assigner") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}

@Composable
private fun OngletCorrections(productions: List<ProductionACorriger>) {
    var productionOuverte by remember { mutableStateOf<ProductionACorriger?>(null) }

    if (productions.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                Icon(Icons.Filled.CloudOff, contentDescription = null, modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(16.dp))
                Text("Aucune production soumise pour le moment", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Les productions écrites soumises par les élèves apparaîtront ici automatiquement (même appareil) ou après un transfert local (Nearby Share, Bluetooth ou carte SD — Mission C3).",
                    style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center
                )
            }
        }
        return
    }

    LazyColumn(
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(productions, key = { it.productionId }) { production ->
            Card(
                Modifier.fillMaxWidth().clickable { productionOuverte = production }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(production.eleveNom, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Surface(shape = RoundedCornerShape(50), color = MaterialTheme.colorScheme.secondaryContainer) {
                            Text("Soumis", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                    }
                    Text(production.uniteTitre, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(6.dp))
                    Text(production.extrait, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }

    productionOuverte?.let { production ->
        AlertDialog(
            onDismissRequest = { productionOuverte = null },
            title = { Text("${production.eleveNom} — ${production.uniteTitre}") },
            text = {
                Column(Modifier.heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
                    Text(production.extrait, style = MaterialTheme.typography.bodyMedium)
                }
            },
            confirmButton = {
                TextButton(onClick = { productionOuverte = null }) { Text("Fermer") }
            }
        )
    }
}
