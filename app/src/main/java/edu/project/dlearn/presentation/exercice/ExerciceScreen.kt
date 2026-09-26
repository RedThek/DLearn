package edu.project.dlearn.presentation.exercice

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.domain.model.Exercice
import edu.project.dlearn.domain.model.TypeExercice

@Composable
fun ExerciceScreen(
    onTermine: () -> Unit,
    viewModel: ExerciceViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(start = 4.dp, end = 16.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onTermine) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
            }
            Text("Exercices", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        when (val etatActuel = etat) {
            ExerciceUiState.Chargement -> BoiteCentree { CircularProgressIndicator() }
            ExerciceUiState.Vide -> BoiteVide(onTermine)
            is ExerciceUiState.EnCours -> ContenuExercice(
                etat = etatActuel,
                onSelectionner = viewModel::onSelectionnerReponse,
                onValider = viewModel::onValider,
                onSuivant = viewModel::onSuivant
            )
            is ExerciceUiState.Termine -> EcranResultat(etatActuel, onTermine)
        }
    }
}

@Composable
private fun ContenuExercice(
    etat: ExerciceUiState.EnCours,
    onSelectionner: (String) -> Unit,
    onValider: () -> Unit,
    onSuivant: () -> Unit
) {
    val view = LocalView.current
    LaunchedEffect(etat.resultat) {
        val res = etat.resultat
        if (res != null) {
            if (res) {
                if (Build.VERSION.SDK_INT >= 30) {
                    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                } else {
                    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                }
            } else {
                if (Build.VERSION.SDK_INT >= 30) {
                    view.performHapticFeedback(HapticFeedbackConstants.REJECT)
                } else {
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                }
            }
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Question ${etat.indexActuel + 1}/${etat.exercices.size}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(progress = { etat.progression }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(24.dp))

        AnimatedContent(
            targetState = etat.indexActuel,
            transitionSpec = { fadeIn(tween(220)) togetherWith fadeOut(tween(220)) },
            modifier = Modifier.weight(1f),
            label = "questionAnimation"
        ) { targetIndex ->
            val exercice = etat.exercices.getOrNull(targetIndex) ?: etat.exerciceActuel
            Column(Modifier.fillMaxWidth()) {
                Text(exercice.enonce, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(20.dp))

                when (exercice.type) {
                    TypeExercice.QCM -> QcmOptions(exercice, etat.reponseSelectionnee, etat.resultat, onSelectionner)
                    TypeExercice.VRAI_FAUX -> VraiFauxOptions(etat.reponseSelectionnee, etat.resultat, onSelectionner)
                    TypeExercice.TEXTE_A_TROUS -> TexteATrousChamp(etat.reponseSelectionnee, etat.resultat, onSelectionner)
                    TypeExercice.PRODUCTION_GUIDEE -> ProductionGuideeConsigne()
                }
            }
        }

        if (etat.resultat != null) {
            Text(
                text = if (etat.resultat) "Bonne réponse !" else "Pas tout à fait — continue !",
                color = if (etat.resultat) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Button(onClick = onSuivant, modifier = Modifier.fillMaxWidth().height(52.dp)) {
                Text(if (etat.indexActuel + 1 < etat.exercices.size) "Suivant" else "Terminer")
            }
        } else {
            Button(
                onClick = onValider,
                enabled = etat.reponseSelectionnee != null,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("Valider", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun QcmOptions(
    exercice: Exercice,
    selection: String?,
    resultat: Boolean?,
    onSelectionner: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        exercice.options.forEach { option ->
            val estSelectionnee = selection == option.id
            val borderColor = when {
                resultat != null && option.estCorrecte -> MaterialTheme.colorScheme.secondary
                resultat != null && estSelectionnee && !option.estCorrecte -> MaterialTheme.colorScheme.error
                estSelectionnee -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outlineVariant
            }
            val backgroundColor = when {
                resultat != null && option.estCorrecte -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
                resultat != null && estSelectionnee && !option.estCorrecte -> MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                estSelectionnee -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                else -> Color.Transparent
            }
            val borderWidth = if (estSelectionnee || (resultat != null && (option.estCorrecte || estSelectionnee))) 2.dp else 1.dp

            val animatedBorderColor by animateColorAsState(targetValue = borderColor, animationSpec = tween(180), label = "border")
            val animatedBgColor by animateColorAsState(targetValue = backgroundColor, animationSpec = tween(180), label = "bg")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 52.dp)
                    .border(borderWidth, animatedBorderColor, RoundedCornerShape(12.dp))
                    .background(animatedBgColor, RoundedCornerShape(12.dp))
                    .clickable(enabled = resultat == null) { onSelectionner(option.id) }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(option.texte, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun VraiFauxOptions(selection: String?, resultat: Boolean?, onSelectionner: (String) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        listOf("VRAI" to "Vrai", "FAUX" to "Faux").forEach { (valeur, libelle) ->
            val estSelectionnee = selection == valeur
            val borderColor = if (estSelectionnee) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
            val backgroundColor = if (estSelectionnee) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f) else Color.Transparent
            val borderWidth = if (estSelectionnee) 2.dp else 1.dp

            val animatedBorderColor by animateColorAsState(targetValue = borderColor, animationSpec = tween(180), label = "vfBorder")
            val animatedBgColor by animateColorAsState(targetValue = backgroundColor, animationSpec = tween(180), label = "vfBg")

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .border(borderWidth, animatedBorderColor, RoundedCornerShape(12.dp))
                    .background(animatedBgColor, RoundedCornerShape(12.dp))
                    .clickable(enabled = resultat == null) { onSelectionner(valeur) },
                contentAlignment = Alignment.Center
            ) {
                Text(libelle, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun TexteATrousChamp(selection: String?, resultat: Boolean?, onSelectionner: (String) -> Unit) {
    OutlinedTextField(
        value = selection ?: "",
        onValueChange = onSelectionner,
        label = { Text("Ta réponse") },
        enabled = resultat == null,
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ProductionGuideeConsigne() {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Cette consigne d'écriture guidée n'est pas corrigée automatiquement — " +
                "rends-toi dans le module Écriture pour rédiger ta réponse, puis reviens ici.",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun EcranResultat(etat: ExerciceUiState.Termine, onTermine: () -> Unit) {
    BoiteCentree {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Exercices terminés !", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "${etat.bonnesReponses} / ${etat.total} bonnes réponses",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))
            Button(onClick = onTermine) { Text("Retour à la lecture") }
        }
    }
}

@Composable
private fun BoiteVide(onTermine: () -> Unit) {
    BoiteCentree {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Aucun exercice n'est encore disponible pour cette unité.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = onTermine) { Text("Retour") }
        }
    }
}

@Composable
private fun BoiteCentree(content: @Composable () -> Unit) {
    Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) { content() }
}
