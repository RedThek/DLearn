package edu.project.dlearn.presentation.enseignant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.project.dlearn.core.AppConstants
import edu.project.dlearn.core.components.AppTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationEleveScreen(
    onBack: () -> Unit,
    onCreateStudent: (fullName: String, className: String, level: String) -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    var fullName by rememberSaveable { mutableStateOf("") }
    var selectedClass by rememberSaveable { mutableStateOf("") }
    var selectedLevel by rememberSaveable { mutableStateOf("A1") }

    var classExpanded by rememberSaveable { mutableStateOf(false) }
    var levelExpanded by rememberSaveable { mutableStateOf(false) }

    var fullNameError by rememberSaveable { mutableStateOf(false) }
    var classError by rememberSaveable { mutableStateOf(false) }

    val classes = AppConstants.NIVEAUX_COLLEGE
    val levels = listOf("A1", "A2")

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val contentWidth = if (maxWidth > 600.dp) 560.dp else maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = contentWidth)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack, enabled = !isLoading) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = "Créer un élève",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Informations de l'élève",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Renseignez les informations pour créer son compte.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(28.dp))

                AppTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        fullNameError = false
                    },
                    label = "Nom complet",
                    placeholder = "Ex : Divine K.",
                    leadingIcon = Icons.Default.Person,
                    isError = fullNameError,
                    supportingText = if (fullNameError) {
                        "Le nom complet est obligatoire"
                    } else null,
                    enabled = !isLoading
                )

                Spacer(Modifier.height(18.dp))

                ExposedDropdownMenuBox(
                    expanded = classExpanded,
                    onExpandedChange = {
                        if (!isLoading) classExpanded = !classExpanded
                    }
                ) {
                    OutlinedTextField(
                        value = selectedClass,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        label = { Text("Classe") },
                        placeholder = { Text("Sélectionner une classe") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = classExpanded)
                        },
                        isError = classError,
                        supportingText = if (classError) {
                            { Text("Veuillez sélectionner une classe") }
                        } else null,
                        enabled = !isLoading,
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = classExpanded,
                        onDismissRequest = { classExpanded = false }
                    ) {
                        classes.forEach { className ->
                            DropdownMenuItem(
                                text = { Text(className) },
                                onClick = {
                                    selectedClass = className
                                    classExpanded = false
                                    classError = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                ExposedDropdownMenuBox(
                    expanded = levelExpanded,
                    onExpandedChange = {
                        if (!isLoading) levelExpanded = !levelExpanded
                    }
                ) {
                    OutlinedTextField(
                        value = selectedLevel,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        label = { Text("Niveau de départ") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = levelExpanded)
                        },
                        enabled = !isLoading,
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = levelExpanded,
                        onDismissRequest = { levelExpanded = false }
                    ) {
                        levels.forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level) },
                                onClick = {
                                    selectedLevel = level
                                    levelExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(28.dp))

                Text(
                    text = "Les identifiants seront générés par la couche métier.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (errorMessage != null) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        fullNameError = fullName.isBlank()
                        classError = selectedClass.isBlank()

                        if (!fullNameError && !classError) {
                            onCreateStudent(
                                fullName.trim(),
                                selectedClass,
                                selectedLevel
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    enabled = !isLoading
                ) {
                    Text(
                        text = if (isLoading) "Création..." else "Créer le compte élève",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
