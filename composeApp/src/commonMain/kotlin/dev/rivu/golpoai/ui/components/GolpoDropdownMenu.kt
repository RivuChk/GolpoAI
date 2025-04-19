package dev.rivu.golpoai.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rivu.golpoai.ui.theme.KotlinBlue

@Composable
fun GolpoDropdownMenu(selectedGenre: String, onGenreSelected: (String) -> Unit) {
    val genres = listOf("Sci-fi", "Mythology", "Romance", "Horror", "Adventure")
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Select Genre", style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.height(4.dp))
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = KotlinBlue)
            ) {
                Text(selectedGenre + "\t\t\t\uD83D\uDD3D")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                genres.forEach { genre ->
                    DropdownMenuItem(onClick = {
                        onGenreSelected(genre)
                        expanded = false
                    }) {
                        Text(text = genre)
                    }
                }
            }
        }
    }
}
