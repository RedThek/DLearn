package edu.project.dlearn.presentation.profil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import edu.project.dlearn.core.components.InitialsAvatar

/*
@Composable
fun ProfilScreen(viewModel: ProfilViewModel = hiltViewModel()) {
    val etat by viewModel.uiState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.padding(20.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(etat.nomEleve, style = MaterialTheme.typography.titleLarge)
        Text("Niveau ${etat.niveauCECR}", style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(32.dp))
        HorizontalDivider()

        ListItem(
            headlineContent = { Text("Synthese vocale (Allemand)") },
            trailingContent = {
                Switch(checked = etat.ttsActif, onCheckedChange = viewModel::onToggleTts)
            }
        )
        ListItem(
            headlineContent = { Text("Theme sombre") },
            trailingContent = {
                Switch(checked = etat.themeSombre, onCheckedChange = viewModel::onToggleThemeSombre)
            }
        )
        ListItem(
            headlineContent = { Text("Mode hors-ligne") },
            supportingContent = { Text("Toujours actif (contrainte du projet)") },
            trailingContent = { Switch(checked = true, onCheckedChange = {}, enabled = false) }
        )
    }
} */

@Composable
fun ProfilScreen(
    onDeconnexion: () -> Unit,
    viewModel: ProfilViewModel = hiltViewModel()
) {
    val etat by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.evenements.collect { evenement ->
            when (evenement) {
                ProfilEvenement.Deconnecte -> onDeconnexion()
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValuesLocal
    ) {
        item { EnTeteProfil(etat) }
        item { CarteNiveau(etat) }
        item { TitreSection("Paramètres") }
        item {
            LigneParametre(
                icone = Icons.Filled.Language,
                libelle = "Langue de l'interface",
                valeurAffichee = etat.langueInterface,
                afficherChevron = true
            )
        }
        item {
            LigneParametre(
                icone = Icons.Filled.CloudOff,
                libelle = "Mode hors-ligne",
                valeurAffichee = if (etat.modeHorsLigneActif) "Activé" else "Désactivé",
                couleurValeur = if (etat.modeHorsLigneActif) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            LigneParametre(
                icone = Icons.Filled.Notifications,
                libelle = "Notifications",
                valeurAffichee = if (etat.notificationsActives) "Activé" else "Désactivé",
                couleurValeur = if (etat.notificationsActives) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            LigneParametre(
                icone = Icons.Filled.Sync,
                libelle = "Synchroniser maintenant",
                valeurAffichee = if (etat.synchronisationEnCours) null else etat.derniereSynchro,
                enChargement = etat.synchronisationEnCours,
                onClick = viewModel::onSynchroniserMaintenant
            )
        }
        item { TitreSection("Mes badges") }
        item { RangeeBadges(etat.badges) }
        item {
            Spacer(Modifier.height(24.dp))
            OutlinedButton(
                onClick = viewModel::onDeconnexion,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp)
            ) {
                Icon(Icons.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Se déconnecter")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

private val PaddingValuesLocal = androidx.compose.foundation.layout.PaddingValues(bottom = 8.dp)

@Composable
private fun EnTeteProfil(etat: ProfilUiState) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Mon profil", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        IconButton(onClick = { /* TODO: écran de réglages avancés */ }) {
            Icon(Icons.Filled.Settings, contentDescription = "Réglages")
        }
    }
}

@Composable
private fun CarteNiveau(etat: ProfilUiState) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InitialsAvatar(etat.nomComplet, taille = 72.dp)
        Spacer(Modifier.height(12.dp))
        Text(etat.nomComplet, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(etat.classe, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Surface(
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            val texteNiveau = if (etat.progressionNiveauEnCours) {
                "Niveau ${etat.niveauActuel} → ${etat.niveauCible} (en cours)"
            } else {
                "Niveau ${etat.niveauActuel}"
            }
            Text(
                texteNiveau,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun TitreSection(titre: String) {
    Text(
        titre,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun LigneParametre(
    icone: ImageVector,
    libelle: String,
    valeurAffichee: String?,
    couleurValeur: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurfaceVariant,
    afficherChevron: Boolean = false,
    enChargement: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icone, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.width(12.dp))
            Text(libelle, style = MaterialTheme.typography.bodyLarge)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (enChargement) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
            } else if (valeurAffichee != null) {
                Text(valeurAffichee, color = couleurValeur, style = MaterialTheme.typography.bodyMedium)
            }
            if (afficherChevron) {
                Spacer(Modifier.width(4.dp))
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun RangeeBadges(badges: List<Badge>) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        badges.forEach { badge ->
            val couleurFond = if (badge.deverrouille) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            val couleurIcone = if (badge.deverrouille) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(couleurFond),
                contentAlignment = Alignment.Center
            ) {
                Icon(badge.icone, contentDescription = null, tint = couleurIcone)
            }
        }
    }
}
