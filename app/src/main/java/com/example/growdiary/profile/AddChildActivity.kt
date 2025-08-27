package com.example.growdiary.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.growdiary.R
import com.example.growdiary.databinding.ActivityAddChildBinding
import java.util.Calendar

class AddChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddChildBinding
    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            binding.ivChildAvatar.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupDateDropdowns()
        setupGenderDropdown()

        binding.btnChangePhoto.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.btnSaveChild.setOnClickListener {
            if (validateInput()) {
                saveChildData()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun saveChildData() {
        val name = binding.etChildName.text.toString()
        val day = binding.autocompleteDay.text.toString()
        val month = binding.autocompleteMonth.text.toString()
        val year = binding.autocompleteYear.text.toString()
        val gender = binding.autocompleteGender.text.toString()
        val weight = binding.etWeight.text.toString()
        val height = binding.etHeight.text.toString()
        val notes = binding.etNotes.text.toString()

        val formattedBirthDate = "$day $month $year"

        val resultIntent = Intent()
        resultIntent.putExtra("EXTRA_CHILD_NAME", name)
        resultIntent.putExtra("EXTRA_CHILD_BIRTHDATE", formattedBirthDate)
        resultIntent.putExtra("EXTRA_CHILD_IMAGE_URI", selectedImageUri?.toString())
        resultIntent.putExtra("EXTRA_CHILD_GENDER", gender)
        resultIntent.putExtra("EXTRA_CHILD_WEIGHT", weight)
        resultIntent.putExtra("EXTRA_CHILD_HEIGHT", height)
        resultIntent.putExtra("EXTRA_CHILD_NOTES", notes)

        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


    private fun validateInput(): Boolean {
        binding.tilChildName.error = null
        binding.tilWeight.error = null
        binding.tilHeight.error = null

        if (binding.etChildName.text.toString().trim().isEmpty()){
            binding.tilChildName.error = "Nama tidak boleh kosong"
            return false
        }
        val selectedGender = binding.autocompleteGender.text.toString()
        if (selectedGender.isEmpty()) {
            Toast.makeText(this, "Harap pilih gender", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etWeight.text.toString().trim().isEmpty()){
            binding.tilWeight.error = "Berat tidak boleh kosong"
            return false
        }
        if (binding.etHeight.text.toString().trim().isEmpty()){
            binding.tilHeight.error = "Tinggi tidak boleh kosong"
            return false
        }
        return true
    }

    private fun setupDateDropdowns() {
        val days = (1..31).map { it.toString() }
        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, days)
        binding.autocompleteDay.setAdapter(dayAdapter)

        val months = listOf("January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December")
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, months)
        binding.autocompleteMonth.setAdapter(monthAdapter)

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear - 100..currentYear).map { it.toString() }.reversed()
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years)
        binding.autocompleteYear.setAdapter(yearAdapter)
    }

    private fun setupGenderDropdown() {
        val genders = listOf("Male", "Female")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders)
        binding.autocompleteGender.setAdapter(genderAdapter)
    }
}