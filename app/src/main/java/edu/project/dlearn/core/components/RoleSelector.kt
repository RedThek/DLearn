package edu.project.dlearn.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.project.dlearn.domain.model.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelector(
    selectedRole: Role,
    onRoleSelected: (Role) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier.fillMaxWidth()
    ) {
        Role.entries.forEachIndexed { index, role ->
            SegmentedButton(
                selected = selectedRole == role,
                onClick = { onRoleSelected(role) },
                shape = SegmentedButtonDefaults.itemShape(index, Role.entries.size)
            ) {
                Text(if (role == Role.ELEVE) "Élève" else "Enseignant")
            }
        }
    }
}
