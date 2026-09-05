package edu.project.dlearn.presentation.apprentissage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.domain.model.EntreeGlossaire
import edu.project.dlearn.domain.model.ExtraitAvecGlossaire
import edu.project.dlearn.domain.model.UniteApprentissage

@Composable
fun ApprentissageScreen(
    onCommencerExercices: (uniteId: String) -> Unit = {},
    onCommencerEcriture: (uniteId: String) -> Unit = {},
    viewModel: ApprentissageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val etat = uiState) {
        is ApprentissageUiState.Chargement       -> BoiteChargement()
        is ApprentissageUiState.Erreur           -> BoiteErreur(etat.message)
        is ApprentissageUiState.Bibliotheque     -> BibliothequeLectures(
            unites  = etat.unites,
            onOuvrir = viewModel::onOuvrirUnite
        )
        is ApprentissageUiState.LectureUnite     -> LectureUniteScreen(
            etat    = etat,
            onRetour = viewModel::onRetourBibliotheque,
            onCommencerExercices = onCommencerExercices,
            onCommencerEcriture = onCommencerEcriture
        )
    }
}

@Composable
private fun BibliothequeLectures(
    unites: List<UniteApprentissage>,
    onOuvrir: (UniteApprentissage) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Text(
            text  = "Mes leçons",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
        if (unites.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "Contenu en cours de chargement…",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(unites, key = { it.id }) { unite ->
                    CarteUnite(unite = unite, onClick = { onOuvrir(unite) })
                }
            }
        }
    }
}

@Composable
private fun CarteUnite(unite: UniteApprentissage, onClick: () -> Unit) {
    Card(
        modifier  = Modifier.fillMaxWidth().clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(unite.titre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text     = unite.niveauGer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style    = MaterialTheme.typography.labelMedium,
                        color    = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LectureUniteScreen(
    etat: ApprentissageUiState.LectureUnite,
    onRetour: () -> Unit,
    onCommencerExercices: (uniteId: String) -> Unit,
    onCommencerEcriture: (uniteId: String) -> Unit
) {
    var motGlossaireSelectionne by remember { mutableStateOf<EntreeGlossaire?>(null) }

    Column(Modifier.fillMaxSize()) {
        // AppBar
        Row(
            Modifier.fillMaxWidth().padding(start = 4.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onRetour) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
            }
            Text(
                text     = etat.unite.titre,
                style    = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text     = etat.unite.niveauGer,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    style    = MaterialTheme.typography.labelLarge,
                    color    = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // Objectifs
            Text(
                text  = etat.unite.objectifsApprentissage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            // Extrait littéraire avec glossaire interactif
            if (etat.extrait != null) {
                TexteAvecGlossaire(
                    extrait   = etat.extrait,
                    onMotTap  = { entree -> motGlossaireSelectionne = entree }
                )
            } else {
                Text(
                    "Contenu de la leçon non disponible.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(24.dp))
        }

        // Barre d'actions bas de page (remplace le Button unique existant)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick  = { onCommencerEcriture(etat.unite.id) },
                modifier = Modifier.weight(1f).height(52.dp)
            ) {
                Text("Rédiger", fontWeight = FontWeight.SemiBold)
            }
            Button(
                onClick  = { onCommencerExercices(etat.unite.id) },
                modifier = Modifier.weight(1f).height(52.dp)
            ) {
                Text("Exercices", fontWeight = FontWeight.SemiBold)
            }
        }
    }

    // Dialog glossaire
    motGlossaireSelectionne?.let { entree ->
        AlertDialog(
            onDismissRequest  = { motGlossaireSelectionne = null },
            title = { Text(entree.motAllemand, fontWeight = FontWeight.Bold) },
            text  = { Text(entree.traductionFr) },
            confirmButton = {
                TextButton(onClick = { motGlossaireSelectionne = null }) { Text("Fermer") }
            }
        )
    }
}

@Composable
private fun TexteAvecGlossaire(
    extrait: ExtraitAvecGlossaire,
    onMotTap: (EntreeGlossaire) -> Unit
) {
    // Construction du texte annoté : mots du glossaire soulignés en bleu primaire
    val motsDuGlossaire = extrait.glossaire.associateBy { it.motAllemand.lowercase() }
    val texte = extrait.texteAllemand

    val annotatedString = buildAnnotatedString {
        var curseur = 0
        // Recherche simple mot par mot (split sur espaces + ponctuations)
        val mots = texte.split(Regex("(?<=\\s)|(?=\\s)|(?=[.,!?;:\"()–])"))
        for (fragment in mots) {
            val cle = fragment.trim().lowercase().trimEnd('.', ',', '!', '?', ';', ':')
            val entree = motsDuGlossaire[cle]
            if (entree != null) {
                pushStringAnnotation(tag = "GLOSSAIRE", annotation = cle)
                withStyle(
                    SpanStyle(
                        color          = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        fontWeight     = FontWeight.Medium
                    )
                ) { append(fragment) }
                pop()
            } else {
                append(fragment)
            }
        }
    }

    Surface(
        color  = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape  = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        ClickableText(
            text     = annotatedString,
            style    = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp),
            modifier = Modifier.padding(16.dp),
            onClick  = { offset ->
                annotatedString.getStringAnnotations("GLOSSAIRE", offset, offset)
                    .firstOrNull()?.let { annotation ->
                        val entree = extrait.glossaire.find {
                            it.motAllemand.lowercase() == annotation.item
                        }
                        entree?.let { onMotTap(it) }
                    }
            }
        )
    }

    // Crédit auteur
    extrait.auteur?.let {
        Spacer(Modifier.height(8.dp))
        Text(
            text  = "— $it",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
