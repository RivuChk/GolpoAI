package dev.rivu.golpoai.platform

import androidx.compose.runtime.Composable
import dev.rivu.golpoai.ContextWrapper
import platform.UIKit.*
import platform.Foundation.*
import platform.CoreGraphics.*
import kotlinx.cinterop.useContents // For CGRect.useContents

private fun generateStoryImage(story: String): UIImage? {
    try {
        val imageWidth = 750.0 // Image width in points
        val padding = 30.0    // Padding around the text
        val fontSize = 48.0   // Font size in points

        val textAttributes = mapOf(
            NSFontAttributeName to UIFont.systemFontOfSize(fontSize),
            NSForegroundColorAttributeName to UIColor.blackColor
        )

        val paragraphStyle = NSMutableParagraphStyle().apply {
            setLineSpacing(10.0) // Adjust line spacing as needed
        }
        val fullAttributes = textAttributes + (NSParagraphStyleAttributeName to paragraphStyle)

        val attributedString = NSAttributedString(story, attributes = fullAttributes)

        val drawingWidth = imageWidth - 2 * padding
        val textSize = attributedString.boundingRectWithSize(
            size = CGSizeMake(drawingWidth, Double.MAX_VALUE),
            options = NSStringDrawingUsesLineFragmentOrigin or NSStringDrawingUsesFontLeading,
            context = null
        ).useContents { size } // Use .useContents to access properties of CValue

        val imageHeight = textSize.height + 2 * padding

        val renderer = UIGraphicsImageRenderer(CGSizeMake(imageWidth, imageHeight))
        val image = renderer.imageWithActions { context ->
            // Fill background
            UIColor.whiteColor.setFill()
            context.fillRect(CGRectMake(0.0, 0.0, imageWidth, imageHeight))

            // Draw text
            attributedString.drawInRect(CGRectMake(padding, padding, drawingWidth, textSize.height))
        }
        return image
    } catch (e: Exception) {
        // Basic error logging (consider a more robust logging mechanism for production)
        println("Error generating image: ${e.message}")
        return null
    }
}

actual fun PlatformUtility.shareStory(contextWrapper: ContextWrapper, story: String, imageUri: String?) {
    val contentText = "Here's a story generated with GolpoAI ✨\n\n$story"
    val activityItems = mutableListOf<Any>()
    activityItems.add(contentText)

    // Generate image (imageUri is a signal, actual image is generated)
    val generatedImage = generateStoryImage(story)
    if (generatedImage != null) {
        activityItems.add(generatedImage)
    }

    val activityController = UIActivityViewController(
        activityItems = activityItems,
        applicationActivities = null
    )

    // Try to find the most appropriate view controller to present from
    var presentingViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    while (presentingViewController?.presentedViewController != null) {
        presentingViewController = presentingViewController.presentedViewController
    }

    presentingViewController?.presentViewController(activityController, animated = true, completion = null)
}

@Composable
actual fun PlatformUtility.getContext(): ContextWrapper {
    return ContextWrapper() // ContextWrapper is not used for specific functionalities in iOS like in Android
}

actual fun PlatformUtility.generateUUID(): String = NSUUID().UUIDString()
