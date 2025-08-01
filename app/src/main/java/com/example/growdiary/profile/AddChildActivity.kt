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

        setupSpinners()

        binding.btnChangePhoto.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.btnSaveChild.setOnClickListener {
            if (validateInput()) {
                val childName = binding.etChildName.text.toString().trim()
                val day = binding.spinnerDay.text.toString()
                val month = binding.spinnerMonth.text.toString()
                val year = binding.spinnerYear.text.toString()
                val birthDate = "$day $month $year"
                val gender = binding.spinnerGender.text.toString()
                val weight = binding.etWeight.text.toString().trim()
                val height = binding.etHeight.text.toString().trim()
                val notes = binding.etNotes.text.toString().trim()

                val resultIntent = Intent()
                resultIntent.putExtra("EXTRA_CHILD_NAME", childName)
                resultIntent.putExtra("EXTRA_CHILD_BIRTHDATE", birthDate)
                resultIntent.putExtra("EXTRA_CHILD_GENDER", gender)
                resultIntent.putExtra("EXTRA_CHILD_WEIGHT", weight)
                resultIntent.putExtra("EXTRA_CHILD_HEIGHT", height)
                resultIntent.putExtra("EXTRA_CHILD_NOTES", notes)
                selectedImageUri?.let {
                    resultIntent.putExtra("EXTRA_CHILD_IMAGE_URI", it.toString())
                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        binding.tilChildName.error = null
        binding.tilWeight.error = null
        binding.tilHeight.error = null

        if (binding.etChildName.text.toString().trim().isEmpty()){
            binding.tilChildName.error = "Nama tidak boleh kosong"
            return false
        }
        val selectedGender = binding.spinnerGender.text.toString()
        if (selectedGender.isEmpty() || selectedGender == "Pilih Gender") {
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

    private fun setupSpinners() {
        // Setup Spinner Hari
        val days = (1..31).map { it.toString() }
        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        // PERBAIKAN: Gunakan .setAdapter(), bukan .adapter =
        binding.spinnerDay.setAdapter(dayAdapter)

        // Setup Spinner Bulan
        val monthAdapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item)
        // PERBAIKAN: Gunakan .setAdapter(), bukan .adapter =
        binding.spinnerMonth.setAdapter(monthAdapter)

        // Setup Spinner Tahun
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear downTo currentYear - 20).map { it.toString() }
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        // PERBAIKAN: Gunakan .setAdapter(), bukan .adapter =
        binding.spinnerYear.setAdapter(yearAdapter)

        // Setup Spinner Gender
        val genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item)
        // PERBAIKAN: Gunakan .setAdapter(), bukan .adapter =
        binding.spinnerGender.setAdapter(genderAdapter)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish() // Menutup activity saat tombol kembali di toolbar ditekan
        return true
    }
}