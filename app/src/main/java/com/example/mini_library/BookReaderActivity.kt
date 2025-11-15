package com.example.mini_library

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mini_library.ui.reader.BookReaderPagerAdapter
import com.example.mini_library.ui.reader.BookReaderViewModel
import com.example.mini_library.ui.reader.BookReaderViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BookReaderActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter: BookReaderPagerAdapter

    private val viewModel: BookReaderViewModel by viewModels {
        BookReaderViewModelFactory(
            (application as WikiApplication).repository,
            intent.getIntExtra(EXTRA_NOVEL_ID, -1)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_reader)

        val novelId = intent.getIntExtra(EXTRA_NOVEL_ID, -1)
        if (novelId == -1) {
            finish()
            return
        }

        viewPager = findViewById(R.id.viewPagerReader)
        tabLayout = findViewById(R.id.tabLayout)

        pagerAdapter = BookReaderPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val titles = pagerAdapter.fragmentTitles
            tab.text = titles[position]
        }.attach()

        val fabAddData = findViewById<FloatingActionButton>(R.id.fabAddData)
        if (BuildConfig.DEBUG) {
            fabAddData.visibility = View.VISIBLE
            fabAddData.setOnClickListener { showAddDataDialog() }
        } else {
            fabAddData.visibility = View.GONE
        }
    }

    private fun showAddDataDialog() {
        val options = arrayOf("Add Arc", "Add Character", "Add Sect", "Add Technique", "Add Rank")
        AlertDialog.Builder(this)
            .setTitle("Admin: Add Data")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showAddArcDialog()
                    1 -> showAddCharacterDialog()
                    2 -> showAddSectDialog()
                    3 -> showAddTechniqueDialog()
                    4 -> showAddRankDialog()
                }
            }
            .show()
    }

    // 1. ARC (Đã thêm ô nhập ảnh)
    private fun showAddArcDialog() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }
        val inputTitle = EditText(this).apply { hint = "Arc Title" }
        val inputSummary = EditText(this).apply { hint = "Summary" }
        val inputImage = EditText(this).apply { hint = "Image URL (Optional)" }

        layout.addView(inputTitle); layout.addView(inputSummary); layout.addView(inputImage)

        AlertDialog.Builder(this)
            .setTitle("Add New Arc")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val title = inputTitle.text.toString()
                val summary = inputSummary.text.toString()
                val imageUrl = inputImage.text.toString().trim()
                if (title.isNotEmpty()) {
                    viewModel.insertArc(title, 0, summary, imageUrl)
                    Toast.makeText(this, "Arc Saved!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null).show()
    }

    // 2. CHARACTER (Đã thêm ô nhập ảnh)
    private fun showAddCharacterDialog() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }
        val inputName = EditText(this).apply { hint = "Name" }
        val inputRole = EditText(this).apply { hint = "Role" }
        val inputImage = EditText(this).apply { hint = "Image URL" }

        layout.addView(inputName); layout.addView(inputRole); layout.addView(inputImage)

        AlertDialog.Builder(this)
            .setTitle("Add Character")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val name = inputName.text.toString()
                val role = inputRole.text.toString()
                val imageUrl = inputImage.text.toString().trim()

                if (name.isNotEmpty()) {
                    viewModel.insertCharacter(name, role, "Bio...", imageUrl)
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null).show()
    }

    // 3. SECT (Đã thêm ô nhập ảnh)
    private fun showAddSectDialog() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }
        val inputName = EditText(this).apply { hint = "Sect Name" }
        val inputLocation = EditText(this).apply { hint = "Location" }
        val inputDesc = EditText(this).apply { hint = "Description" }
        val inputImage = EditText(this).apply { hint = "Logo URL" }

        layout.addView(inputName); layout.addView(inputLocation); layout.addView(inputDesc); layout.addView(inputImage)

        AlertDialog.Builder(this)
            .setTitle("Add Sect")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val name = inputName.text.toString()
                val location = inputLocation.text.toString()
                val desc = inputDesc.text.toString()
                val imageUrl = inputImage.text.toString().trim()

                if (name.isNotEmpty()) {
                    viewModel.insertSect(name, desc, location, imageUrl)
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null).show()
    }

    // 4. TECHNIQUE (Đã thêm ô nhập ảnh)
    private fun showAddTechniqueDialog() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }
        val inputName = EditText(this).apply { hint = "Technique Name" }
        val inputLevel = EditText(this).apply { hint = "Level" }
        val inputDesc = EditText(this).apply { hint = "Description" }
        val inputImage = EditText(this).apply { hint = "Image URL" }

        layout.addView(inputName); layout.addView(inputLevel); layout.addView(inputDesc); layout.addView(inputImage)

        AlertDialog.Builder(this)
            .setTitle("Add Technique")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val name = inputName.text.toString()
                val level = inputLevel.text.toString()
                val desc = inputDesc.text.toString()
                val imageUrl = inputImage.text.toString().trim()

                if (name.isNotEmpty()) {
                    viewModel.insertTechnique(name, desc, level, imageUrl)
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null).show()
    }

    // 5. RANK (Đã thêm ô nhập ảnh)
    private fun showAddRankDialog() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }
        val inputName = EditText(this).apply { hint = "Rank Name" }
        val inputOrder = EditText(this).apply { hint = "Order (1, 2...)" }
        val inputDesc = EditText(this).apply { hint = "Description" }
        val inputImage = EditText(this).apply { hint = "Icon URL" }

        layout.addView(inputName); layout.addView(inputOrder); layout.addView(inputDesc); layout.addView(inputImage)

        AlertDialog.Builder(this)
            .setTitle("Add Rank")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val name = inputName.text.toString()
                val orderStr = inputOrder.text.toString()
                val desc = inputDesc.text.toString()
                val imageUrl = inputImage.text.toString().trim()

                if (name.isNotEmpty()) {
                    val order = orderStr.toIntOrNull() ?: 0
                    viewModel.insertRank(name, order, desc, imageUrl)
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null).show()
    }

    companion object {
        const val EXTRA_NOVEL_ID = "EXTRA_NOVEL_ID"
    }
}