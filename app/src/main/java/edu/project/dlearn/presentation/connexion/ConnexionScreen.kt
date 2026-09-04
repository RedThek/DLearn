package edu.project.dlearn.presentation.connexion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.project.dlearn.core.components.AppLogo
import edu.project.dlearn.core.components.AppTextField
import edu.project.dlearn.core.components.InitialsAvatar
import edu.project.dlearn.core.components.PasswordField
import edu.project.dlearn.core.components.RoleSelector
import edu.project.dlearn.domain.model.Role

@Composable
fun ConnexionScreen(
    onConnexionReussie: (Role) -> Unit,
    onNaviguerVersSelectionProfil: () -> Unit,
    onDemanderCompte: () -> Unit = {},
    onMotDePasseOublie: () -> Unit = {},
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

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val contentWidth = if (maxWidth > 600.dp) 460.dp else maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = contentWidth)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(16.dp))

                AppLogo()

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Liteschreib IKII",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Apprendre l'allemand par la littérature",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.CloudDone,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Fonctionne 100% hors-ligne",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(Modifier.height(28.dp))

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
                            // CORRECTION B-13 : naviguer vers SelectionProfilScreen
                            onClick = viewModel::onVoirProfilsExistants,
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            InitialsAvatar(profil.nomAffiche, taille = 28.dp)
                            Spacer(Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f),
                                   horizontalAlignment = Alignment.Start) {
                                Text(profil.nomAffiche,
                                     style = MaterialTheme.typography.bodyMedium,
                                     fontWeight = FontWeight.SemiBold)
                                Text(
                                    text = if (profil.role == Role.ELEVE)
                                               "Élève${profil.classe?.let { " · $it" } ?: ""}"
                                           else "Enseignant",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                    if (etat.profilsExistants.size > 3) {
                        TextButton(
                            onClick = viewModel::onVoirProfilsExistants,
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Voir tous les profils (${etat.profilsExistants.size})") }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                }

                RoleSelector(
                    selectedRole = etat.roleSelectionne,
                    onRoleSelected = viewModel::onChangerRole
                )

                Spacer(Modifier.height(28.dp))

                AppTextField(
                    value = etat.identifiant,
                    onValueChange = viewModel::onChangerIdentifiant,
                    label = "Identifiant",
                    placeholder = "ex : eleve.2451",
                    leadingIcon = Icons.Default.Person,
                    enabled = !etat.enChargement,
                    modifier = Modifier.testTag("champ_identifiant")
                )

                Spacer(Modifier.height(16.dp))

                PasswordField(
                    value = etat.motDePasse,
                    onValueChange = viewModel::onChangerMotDePasse,
                    visible = etat.motDePasseVisible,
                    onVisibilityChange = viewModel::onToggleVisibiliteMotDePasse,
                    enabled = !etat.enChargement,
                    modifier = Modifier.testTag("champ_mot_de_passe")
                )

                TextButton(
                    onClick = onMotDePasseOublie,
                    modifier = Modifier.align(Alignment.End),
                    enabled = !etat.enChargement
                ) {
                    Text("Mot de passe oublié ?")
                }

                if (etat.messageErreur != null) {
                    Text(
                        text = etat.messageErreur!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = viewModel::onSeConnecter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("bouton_connexion"),
                    enabled = !etat.enChargement
                ) {
                    if (etat.enChargement) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Se connecter",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Besoin d'aide ? Contactez votre enseignant.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
