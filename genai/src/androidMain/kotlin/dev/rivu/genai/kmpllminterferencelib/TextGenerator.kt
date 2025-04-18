package dev.rivu.genai.kmpllminterferencelib

import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import dev.rivu.genai.logging.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL

actual class TextGenerator(private val context: Context) {

    private val modelUrl = "https://s3.us-east-2.amazonaws.com/rivu.dev/gemma3-1b-it-int4.task"
    private val modelFileName = "gemma3-1b-it-int4.task"
    private val modelFile: File
        get() = File(context.filesDir, modelFileName)

    private val _isReady = MutableStateFlow(false)
    actual val isReady: StateFlow<Boolean> = _isReady

    private val llmInference: LlmInference by lazy {
        val options = LlmInference.LlmInferenceOptions.builder()
            .setModelPath(modelFile.absolutePath)
            .setMaxTokens(512)
            .setMaxTopK(40)
            .build()
        LlmInference.createFromOptions(context, options)
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (!modelFile.exists()) {
                downloadModel()
            }
            _isReady.value = modelFile.exists()
        }
    }

    private fun downloadModel() {
        Logger.d("Downloading model")
        runCatching {
            URL(modelUrl).openStream().use { input ->
                modelFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            if (modelFile.exists()) {
                Logger.d("Model downloaded")
            } else {
                Logger.e("Model download failed")
            }
        }.onFailure {
            Logger.e("Error downloading model", it)
            it.printStackTrace()
        }
    }

    actual suspend fun generate(prompt: String): String {
        if (!_isReady.value) throw IllegalStateException("Model not ready")
        val result = llmInference.generateResponse(prompt)
        return result ?: throw IllegalStateException("Model didn't generate")
    }
}
