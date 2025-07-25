package com.example.growdiary.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.growdiary.databinding.ActivityManageProfileBinding
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts

class ManageProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageProfileBinding
    private var selectedImageUri: Uri? = null

    // 2. Launcher untuk membuka galeri dan menangani hasilnya
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            // Tampilkan gambar baru di avatar
            binding.ivProfileAvatar.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnChangeAvatar.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.btnConfirmChanges.setOnClickListener {
            if (validateInput()) {
                val newUsername = binding.etUsername.text.toString().trim()

                // 1. Buat Intent untuk membawa data hasil
                val resultIntent = Intent()

                // 2. Masukkan username baru ke dalam Intent
                resultIntent.putExtra("EXTRA_NEW_USERNAME", newUsername)

                // 3. Set hasilnya sebagai "OK" dan sertakan data Intent
                setResult(Activity.RESULT_OK, resultIntent)

                // 4. Tutup activity
                finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        // Reset error sebelumnya
        binding.tilUsername.error = null
        binding.tilOldPassword.error = null
        binding.tilNewPassword.error = null
        binding.tilConfirmPassword.error = null

        val username = binding.etUsername.text.toString().trim()
        val oldPassword = binding.etOldPassword.text.toString().trim()
        val newPassword = binding.etNewPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (username.isEmpty()) {
            binding.tilUsername.error = "Username tidak boleh kosong"
            return false
        }

        if (oldPassword.isEmpty()) {
            binding.tilOldPassword.error = "Password lama tidak boleh kosong"
            return false
        }

        if (newPassword.isEmpty()) {
            binding.tilNewPassword.error = "Password baru tidak boleh kosong"
            return false
        }

        if (newPassword.length < 6) {
            binding.tilNewPassword.error = "Password minimal 6 karakter"
            return false
        }

        if (confirmPassword != newPassword) {
            binding.tilConfirmPassword.error = "Konfirmasi password tidak cocok"
            return false
        }

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish() // Menutup halaman saat tombol kembali ditekan
        return true
    }
}