package com.example.mini_library

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.mini_library.data.Novel
import com.example.mini_library.ui.MainViewModel
import com.example.mini_library.ui.MainViewModelFactory

class AddNovelActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WikiApplication).repository)
    }

    private var selectedImageUri: Uri? = null
    private lateinit var imageCoverPreview: ImageView

    // New way to handle activity results for picking images
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Persist permission to read the URI across device reboots
            contentResolver.takePersistableUriPermission(it, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
            selectedImageUri = it
            // Use Coil to load the selected image into the preview
            imageCoverPreview.load(it) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background) // Optional placeholder
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_novel)

        // Find the Views
        imageCoverPreview = findViewById(R.id.imageCoverPreview)
        val selectImageButton = findViewById<Button>(R.id.btnSelectCoverImage)
        val titleInput = findViewById<EditText>(R.id.editNovelTitle)
        val authorInput = findViewById<EditText>(R.id.editNovelAuthor)
        val summaryInput = findViewById<EditText>(R.id.editNovelSummary)
        val saveButton = findViewById<Button>(R.id.btnSaveNovel)

        // Set click listener for selecting an image
        selectImageButton.setOnClickListener {
            pickImageLauncher.launch("image/*") // Launches the image picker
        }

        // Set click listener for saving the novel
        saveButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val author = authorInput.text.toString().trim()
            val summary = summaryInput.text.toString().trim()

            if (title.isNotEmpty() && author.isNotEmpty()) {
                val newNovel = Novel(
                    title = title,
                    author = author,
                    summary = summary,
                    coverUrl = "", // No longer using this field for local images
                    coverImageUri = selectedImageUri?.toString() // Save the URI as a String
                )

                viewModel.insertNovel(newNovel)

                Toast.makeText(this, "Novel Saved!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Title and Author cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}