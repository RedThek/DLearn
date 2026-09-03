package edu.project.dlearn.presentation.ecriture

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EcritureScreen(
    viewModel: EcritureViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()

    if (etat.enChargement) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        // En-tête
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text("Production écrite", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                etat.unite?.let {
                    Text(it.titre, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            etat.unite?.let {
                Surface(shape = RoundedCornerShape(50), color = MaterialTheme.colorScheme.primaryContainer) {
                    Text(
                        text = it.niveauGer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Consigne
        val consigne = etat.unite?.objectifsApprentissage ?: "Rédigez un texte en allemand."
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text     = "✏ $consigne",
                style    = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(12.dp),
                color    = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(12.dp))

        // Éditeur
        OutlinedTextField(
            value         = etat.texteEnCours,
            onValueChange = viewModel::onTexteChange,
            label         = { Text("Votre texte en allemand") },
            modifier      = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            maxLines      = Int.MAX_VALUE,
            enabled       = !etat.soumis
        )

        // Compteur de mots
        val nbMots = etat.texteEnCours.trim().split(Regex("\\s+")).count { it.isNotBlank() }
        Text(
            text     = "$nbMots mot${if (nbMots > 1) "s" else ""}",
            style    = MaterialTheme.typography.bodySmall,
            color    = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp, top = 4.dp)
        )

        // Clavier allemand (ADR-011 / FR-34)
        ClavierAllemand(onCaractereTap = viewModel::onInserterCaractere)

        // Auto-évaluation expandable (FR-17)
        if (etat.afficherAutoEvaluation) {
            GrilleAutoEvaluation(
                ae       = etat.autoEvaluation,
                onChange = viewModel::onAutoEvaluationChange
            )
        }

        // Barre d'actions
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick  = viewModel::onToggleAutoEvaluation,
                modifier = Modifier.weight(1f)
            ) {
                Text(if (etat.afficherAutoEvaluation) "Masquer l'auto-évaluation" else "Auto-évaluation")
            }
            if (!etat.soumis && etat.texteEnCours.isNotBlank()) {
                Button(
                    onClick  = viewModel::onSoumettre,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Soumettre", fontWeight = FontWeight.SemiBold)
                }
            }
        }

        if (etat.soumis) {
            Text(
                text     = "✓ Soumis à l'enseignant",
                color    = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun GrilleAutoEvaluation(
    ae: AutoEvaluation,
    onChange: (String, Boolean) -> Unit
) {
    Surface(
        modifier       = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        shape          = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("Ma grille d'auto-évaluation", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            CritereSwitch("Longueur respectée",          ae.longueurRespectee)      { onChange("longueur",    it) }
            CritereSwitch("Cohérence avec la consigne",  ae.coherenceAvecConsigne)  { onChange("coherence",   it) }
            CritereSwitch("Vocabulaire du niveau utilisé",ae.vocabulaireNiveauGer) { onChange("vocabulaire", it) }
        }
    }
}

@Composable
private fun CritereSwitch(label: String, valeur: Boolean?, onChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment         = Alignment.CenterVertically,
        horizontalArrangement     = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        Switch(
            checked         = valeur ?: false,
            onCheckedChange = onChange
        )
    }
}
