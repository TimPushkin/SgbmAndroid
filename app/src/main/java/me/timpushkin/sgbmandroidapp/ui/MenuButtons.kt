package me.timpushkin.sgbmandroidapp.ui

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val XML_MIME = MimeTypeMap.getSingleton().getMimeTypeFromExtension("xml") ?: "*/*"
private const val IMG_MIME = "image/*"

@Composable
fun MenuButtons(
    showPickImagesButton: Boolean,
    showRunButton: Boolean,
    onParamsPick: (Uri) -> Unit,
    onImagesPick: (Uri, Uri) -> Unit,
    onRunClick: () -> Unit,
    onError: (String) -> Unit
) {
    val paramsPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                onParamsPick(uri)
            } else {
                onError("No parameters picked")
            }
        }

    val imagesPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uris ->
            if (uris.size == 2) {
                onImagesPick(uris[0], uris[1])
            } else {
                onError("Expected two images but was ${uris.size}")
            }
        }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { paramsPicker.launch(arrayOf(XML_MIME)) }) {
            Text("Load parameters")
        }

        if (showPickImagesButton) {
            Button(onClick = { imagesPicker.launch(arrayOf(IMG_MIME)) }) {
                Text("Load images")
            }
        }

        if (showRunButton) {
            Button(onClick = onRunClick) {
                Text("Run")
            }
        }
    }
}
