package edu.project.dlearn.presentation.selectionprofil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.InitialsAvatar
import edu.project.dlearn.domain.model.Role

/**
 * Écran de sélection d'un profil local existant (FR-04, FR-33, ADR-009).
 * Affiché au démarrage si au moins un compte est enregistré sur l'appareil.
 *
 * UX : liste verticale de cartes de profil + bouton "Autre compte" en bas.
 */
@Composable
fun SelectionProfilScreen(
    onProfilSelectionne: (Role) -> Unit,
    onAutreCompte: () -> Unit,
    viewModel: SelectionProfilViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.evenements.collect { evenement ->
            when (evenement) {
                is SelectionProfilEvenement.ProfilSelectionne ->
                    onProfilSelectionne(evenement.role)
            }
        }
    }

    when (val etat = uiState) {
        is SelectionProfilUiState.Chargement -> BoiteChargement()

        is SelectionProfilUiState.SansProfil -> {
            // Ne devrait pas se produire — rediriger vers l'autre compte.
            LaunchedEffect(Unit) { onAutreCompte() }
        }

        is SelectionProfilUiState.AvecProfils -> ContenuSelection(
            profils    = etat.profils,
            onSelectionner = viewModel::onSelectionnerProfil,
            onAutreCompte  = onAutreCompte
        )
    }
}

@Composable
private fun ContenuSelection(
    profils: List<ProfilResume>,
    onSelectionner: (ProfilResume) -> Unit,
    onAutreCompte: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            text  = "Qui utilise cet appareil ?",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign  = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text  = "Sélectionnez votre profil pour continuer.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        LazyColumn(
            modifier            = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(profils, key = { it.id }) { profil ->
                CarteProfil(profil = profil, onClick = { onSelectionner(profil) })
            }
        }

        Spacer(Modifier.height(24.dp))

        OutlinedButton(
            onClick  = onAutreCompte,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Se connecter avec un autre compte")
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun CarteProfil(profil: ProfilResume, onClick: () -> Unit) {
    val iconeRole: ImageVector = if (profil.role == Role.ENSEIGNANT)
        Icons.Filled.School else Icons.Filled.Person
    val libelleBadge = if (profil.role == Role.ENSEIGNANT) "Enseignant" else "Élève"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialsAvatar(nomComplet = profil.nomAffiche, taille = 52.dp)

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text  = profil.nomAffiche,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            profil.classe?.let {
                Text(
                    text  = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(50),
            color = if (profil.role == Role.ENSEIGNANT)
                MaterialTheme.colorScheme.secondaryContainer
            else
                MaterialTheme.colorScheme.primaryContainer
        ) {
            Row(
                modifier            = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment   = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector        = iconeRole,
                    contentDescription = null,
                    modifier           = Modifier.size(14.dp),
                    tint               = if (profil.role == Role.ENSEIGNANT)
                        MaterialTheme.colorScheme.onSecondaryContainer
                    else
                        MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text  = libelleBadge,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (profil.role == Role.ENSEIGNANT)
                        MaterialTheme.colorScheme.onSecondaryContainer
                    else
                        MaterialTheme.colorScheme.onPrimaryContainer
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
