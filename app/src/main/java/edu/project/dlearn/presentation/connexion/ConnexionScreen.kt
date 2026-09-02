package edu.project.dlearn.presentation.connexion

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.InitialsAvatar
import edu.project.dlearn.domain.model.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnexionScreen(
    onConnexionReussie: (Role) -> Unit,
    onNaviguerVersSelectionProfil: () -> Unit,
    viewModel: ConnexionViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.evenements.collect { evenement ->
            when (evenement) {
                is ConnexionEvenement.ConnexionReussie -> onConnexionReussie(evenement.role)
                is ConnexionEvenement.NaviguerVersSelectionProfil -> onNaviguerVersSelectionProfil()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.School,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(Modifier.height(16.dp))
            Text("Liteschreib IKII", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Text(
                "Apprendre l'allemand par la littérature",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.CloudDone,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "Fonctionne 100% hors-ligne",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(Modifier.height(24.dp))

            // --- Section profils existants (visible si > 0 profils sur l'appareil) ---
            if (etat.profilsExistants.isNotEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    "Déjà sur cet appareil",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                etat.profilsExistants.take(3).forEach { profil ->
                    OutlinedButton(
                        onClick = viewModel::onVoirProfilsExistants,
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        InitialsAvatar(profil.nomAffiche, taille = 28.dp)
                        Spacer(Modifier.width(8.dp))
                        Text(profil.nomAffiche)
                    }
                    Spacer(Modifier.height(4.dp))
                }
                if (etat.profilsExistants.size > 3) {
                    TextButton(
                        onClick = viewModel::onVoirProfilsExistants,
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Voir tous les profils (${etat.profilsExistants.size})") }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }

            Spacer(Modifier.height(24.dp))

            // Sélecteur de rôle Élève / Enseignant (segmented button M3).
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                Role.entries.forEachIndexed { index, role ->
                    SegmentedButton(
                        selected = etat.roleSelectionne == role,
                        onClick = { viewModel.onChangerRole(role) },
                        shape = SegmentedButtonDefaults.itemShape(index, Role.entries.size)
                    ) {
                        Text(if (role == Role.ELEVE) "Élève" else "Enseignant")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Identifiant",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = etat.identifiant,
                onValueChange = viewModel::onChangerIdentifiant,
                placeholder = { Text("ex : eleve.2451") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Mot de passe",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = etat.motDePasse,
                onValueChange = viewModel::onChangerMotDePasse,
                singleLine = true,
                visualTransformation = if (etat.motDePasseVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = viewModel::onToggleVisibiliteMotDePasse) {
                        Icon(
                            if (etat.motDePasseVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            etat.messageErreur?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = viewModel::onSeConnecter,
                enabled = !etat.enChargement,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                if (etat.enChargement) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                } else {
                    Text("Se connecter")
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "Besoin d'aide ? Contactez votre enseignant.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
