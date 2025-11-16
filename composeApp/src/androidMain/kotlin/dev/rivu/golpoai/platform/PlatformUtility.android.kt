package dev.rivu.golpoai.platform

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import android.text.StaticLayout
import android.text.Layout
import android.text.TextPaint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.rivu.golpoai.ContextWrapper
import java.util.UUID
import android.util.Log // Import Log for error logging

private fun generateStoryImage(context: android.content.Context, story: String): Uri? {
    try {
        val imageWidth = 1080
        val padding = 60 // Increased padding
        val textPaint = TextPaint().apply {
            color = Color.BLACK
            textSize = 48f // Increased text size
            isAntiAlias = true
            textAlign = Paint.Align.LEFT // Align text to left
        }

        // Calculate text height considering line breaks
        val staticLayout = StaticLayout.Builder.obtain(story, 0, story.length, textPaint, imageWidth - 2 * padding)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(0f, 1.2f) // Increased line spacing for readability
            .setIncludePad(true)
            .build()

        val imageHeight = staticLayout.height + 2 * padding

        val bitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Fill background
        canvas.drawColor(Color.WHITE)

        // Draw text
        canvas.save()
        canvas.translate(padding.toFloat(), padding.toFloat())
        staticLayout.draw(canvas)
        canvas.restore()

        // Save image
        val imagesDir = File(context.cacheDir, "images")
        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }
        val imageFile = File(imagesDir, "story_${UUID.randomUUID()}.png")
        FileOutputStream(imageFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        // Get content URI
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
    } catch (e: Exception) {
        Log.e("PlatformUtility", "Error generating image: ${e.message}", e) // Log the error
        return null
    }
}

actual fun PlatformUtility.shareStory(contextWrapper: ContextWrapper, story: String, imageUri: String?) {
    val context = contextWrapper.context
    val generatedImageUri = imageUri ?: generateStoryImage(context, story) // Use provided imageUri or generate one

    val intent = Intent(Intent.ACTION_SEND)

    if (generatedImageUri != null) {
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, generatedImageUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant read permission for the URI
        intent.putExtra(Intent.EXTRA_SUBJECT, "Generated with GolpoAI")
        intent.putExtra(Intent.EXTRA_TEXT, "Here's a story generated with GolpoAI ✨\n\n$story")
    } else {
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Generated with GolpoAI")
        intent.putExtra(Intent.EXTRA_TEXT, "Here's a story generated with GolpoAI ✨\n\n$story")
    }

    val chooser = Intent.createChooser(intent, "Share via")
    context.startActivity(chooser)
}


@Composable
actual fun PlatformUtility.getContext(): ContextWrapper {
    return ContextWrapper(LocalContext.current)
}


actual fun PlatformUtility.generateUUID(): String = UUID.randomUUID().toString()
