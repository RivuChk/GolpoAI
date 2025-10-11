package dev.rivu.golpoai.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun OllamaUrlInput(
    url: String,
    onUrlChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var textFieldValue by remember(url) { 
        mutableStateOf(TextFieldValue(text = url, selection = androidx.compose.ui.text.TextRange(url.length)))
    }
    
    Column(modifier = modifier) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
                onUrlChange(newValue.text)
            },
            label = { Text("Ollama Server URL") },
            placeholder = { Text("http://192.168.1.11:11434/") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Text(
            text = "Enter your Ollama server URL (e.g., http://192.168.1.100:11434)",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}