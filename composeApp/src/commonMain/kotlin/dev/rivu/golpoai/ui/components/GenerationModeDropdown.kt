package dev.rivu.golpoai.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.rivu.golpoai.ui.GenerationType

@Composable
fun GenerationModeDropdown(
    selectedType: GenerationType,
    isLocalModelReady: Boolean,
    onTypeSelected: (GenerationType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Column(modifier = modifier) {
        // Dropdown button
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedType.displayName,
                    color = if (selectedType == GenerationType.LOCAL && !isLocalModelReady) {
                        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    } else {
                        MaterialTheme.colors.onSurface
                    }
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Arrow"
                )
            }
        }
        
        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            GenerationType.values().forEach { type ->
                val isEnabled = when (type) {
                    GenerationType.LOCAL -> isLocalModelReady
                    else -> true
                }
                
                DropdownMenuItem(
                    onClick = {
                        if (isEnabled) {
                            onTypeSelected(type)
                            expanded = false
                        }
                    },
                    enabled = isEnabled
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = type.displayName,
                            color = when {
                                !isEnabled -> MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
                                selectedType == type -> MaterialTheme.colors.primary
                                else -> MaterialTheme.colors.onSurface
                            }
                        )
                        
                        if (type == GenerationType.LOCAL && !isLocalModelReady) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "(Not Ready)",
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.error
                            )
                        }
                    }
                }
            }
        }
        
        // Helper text
        when (selectedType) {
            GenerationType.LOCAL -> {
                if (!isLocalModelReady) {
                    Text(
                        text = "⏳ Local model is downloading...",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            GenerationType.OLLAMA -> {
                Text(
                    text = "💻 Using your fine-tuned Ollama model",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            GenerationType.ONLINE_GEMINI -> {
                Text(
                    text = "🌐 Using Google Gemini online service",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}