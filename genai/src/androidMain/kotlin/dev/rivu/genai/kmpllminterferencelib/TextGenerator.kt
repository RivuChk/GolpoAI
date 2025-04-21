package dev.rivu.genai.kmpllminterferencelib

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import dev.rivu.genai.logging.Logger
import java.io.File
import java.net.URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.net.toUri


actual class TextGenerator(private val context: Context) {

    private var downloadID: Long = 0L
    val job = SupervisorJob()
    val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private val modelUrl = "https://s3.us-east-2.amazonaws.com/rivu.dev/gemma3-1b-it-int4.task"
    private val modelFileName = "gemma3-1b-it-int4.task"

    val parentDir = context.getExternalFilesDir("models")
    private val modelFile: File
        get() = File(parentDir, modelFileName)

    private val _isReady:MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isDownloaded = MutableStateFlow(false)
    actual val isReady: StateFlow<Boolean> = _isReady

    private val onDownloadComplete: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                //Fetching the download id received with the broadcast
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                //Checking if the received broadcast is for our enqueued download by matching download id
                if (downloadID == id) {
                    _isDownloaded.value = true
                    Toast.makeText(context, "Model Download Completed", Toast.LENGTH_LONG).show()
                    this@TextGenerator.context.unregisterReceiver(onDownloadComplete)
                }
            }
        }
    }

    private val llmInference: LlmInference by lazy {
        val options = LlmInference.LlmInferenceOptions.builder()
            .setModelPath(modelFile.absolutePath)
            .setMaxTokens(512)
            .setMaxTopK(40)
            .build()
        LlmInference.createFromOptions(context, options)
    }

    init {
        coroutineScope.launch(Dispatchers.IO) {
            if (!(modelFile.exists() && modelFile.length() > 384568367)) {
                downloadModel()
            }
            Logger.d("Model exists: ${modelFile.exists()} | Size ${modelFile.length()}")
            _isDownloaded.value = modelFile.exists()

            _isDownloaded.collect {
                if (it) {
                    _isReady.value = try {
                        llmInference.generateResponse("are you working yet?").isNotBlank()
                    } catch (e: Exception) {
                        Logger.e("Error initializing model", e)
                        false
                    }
                    Logger.d(
                        "Model ready: ${_isReady.value}"
                    )
                } else {
                    _isReady.value = false
                }
            }
        }


    }

    private fun downloadModel() {
        if (parentDir?.exists() != true) {
            parentDir?.mkdirs()
        }



        Logger.d("Downloading model")
        val request = DownloadManager.Request(modelUrl.toUri())
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Visibility of the download Notification
            .setDestinationUri(Uri.fromFile(modelFile)) // Uri of the destination file
            .setDescription("Downloading Gemma 3 Model") // Title of the Download Notification
            .setTitle("Downloading The Model") // Description of the Download Notification
            .setRequiresCharging(false) // Set if charging is required to begin the download
            .setAllowedOverMetered(true) // Set if download is allowed on Mobile network
            .setAllowedOverRoaming(true) // Set if download is allowed on roaming network

        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request) // enqueue puts the download request in the queue.
        Logger.d("Model Download ID: $downloadID")
        ContextCompat.registerReceiver(
            context,
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    actual suspend fun generate(prompt: String): String {
        if (!_isReady.value) throw IllegalStateException("Model not ready")
        val result = withContext(Dispatchers.IO) {
            llmInference.generateResponse(prompt)
        }
        return result ?: throw IllegalStateException("Model didn't generate")
    }
}
