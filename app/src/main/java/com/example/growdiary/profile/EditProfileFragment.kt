package com.example.growdiary.profile

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.growdiary.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import java.util.Calendar

class EditProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by activityViewModels()

    // Deklarasi Variabel View
    private lateinit var profileImage: ShapeableImageView
    private lateinit var btnEditPhoto: FloatingActionButton // <-- BARU
    private lateinit var textName: TextView
    private lateinit var btnEditName: ImageView
    private lateinit var autocompleteDay: AutoCompleteTextView
    private lateinit var autocompleteMonth: AutoCompleteTextView
    private lateinit var autocompleteYear: AutoCompleteTextView
    private lateinit var autocompleteGender: AutoCompleteTextView
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editNotes: EditText
    private lateinit var btnSave: MaterialButton

    // ▼▼▼ BARU: Variabel untuk menyimpan URI gambar baru & Launcher galeri ▼▼▼
    private var newImageUri: Uri? = null
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            newImageUri = it
            profileImage.setImageURI(it)
        }
    }
    // ▲▲▲ AKHIR DARI KODE BARU ▲▲▲

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        // Inisialisasi semua view
        profileImage = view.findViewById(R.id.profile_image_edit)
        btnEditPhoto = view.findViewById(R.id.btn_edit_photo) // <-- BARU
        textName = view.findViewById(R.id.text_name_edit)
        btnEditName = view.findViewById(R.id.btn_edit_name)
        autocompleteDay = view.findViewById(R.id.autocomplete_day)
        autocompleteMonth = view.findViewById(R.id.autocomplete_month)
        autocompleteYear = view.findViewById(R.id.autocomplete_year)
        autocompleteGender = view.findViewById(R.id.autocomplete_gender)
        editWeight = view.findViewById(R.id.edit_weight)
        editHeight = view.findViewById(R.id.edit_height)
        editNotes = view.findViewById(R.id.edit_notes)
        btnSave = view.findViewById(R.id.btn_save_profile)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt("childPosition") ?: -1
        var childToEdit: Child? = null

        if (position != -1) {
            childToEdit = viewModel.getChildAt(position)
        }

        if (childToEdit != null) {
            // ... (Kode untuk mengisi form Anda sudah benar)
            textName.text = childToEdit.name
            editWeight.setText(childToEdit.weight)
            editHeight.setText(childToEdit.height)
            editNotes.setText(childToEdit.notes)
            autocompleteGender.setText(childToEdit.gender, false)
            try {
                val dateParts = childToEdit.birthDate.split(" ")
                if (dateParts.size == 3) {
                    autocompleteDay.setText(dateParts[0], false)
                    autocompleteMonth.setText(dateParts[1], false)
                    autocompleteYear.setText(dateParts[2], false)
                }
            } catch (e: Exception) {
                // Biarkan kosong
            }
            if (childToEdit.imageUri != null) {
                profileImage.setImageURI(Uri.parse(childToEdit.imageUri))
            } else {
                profileImage.setImageResource(R.drawable.ic_launcher_background)
            }

        } else {
            Toast.makeText(context, "Data anak tidak ditemukan", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        setupDateDropdowns()
        setupGenderDropdown()

        // ▼▼▼ BARU: Tambahkan listener untuk tombol kamera ▼▼▼
        btnEditPhoto.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }
        // ▲▲▲ AKHIR DARI KODE BARU ▲▲▲

        btnEditName.setOnClickListener {
            showEditNameDialog()
        }
        textName.setOnClickListener {
            showEditNameDialog()
        }

        btnSave.setOnClickListener {
            if (position != -1 && childToEdit != null) {
                val newName = textName.text.toString()
                val newDay = autocompleteDay.text.toString()
                val newMonth = autocompleteMonth.text.toString()
                val newYear = autocompleteYear.text.toString()
                val newBirthDate = "$newDay $newMonth $newYear"
                val newGender = autocompleteGender.text.toString()
                val newWeight = editWeight.text.toString()
                val newHeight = editHeight.text.toString()
                val newNotes = editNotes.text.toString()

                // ▼▼▼ DIUBAH: Logika untuk menentukan URI gambar yang akan disimpan ▼▼▼
                val updatedImageUri = newImageUri?.toString() ?: childToEdit.imageUri

                val updatedChild = childToEdit.copy(
                    name = newName,
                    birthDate = newBirthDate,
                    gender = newGender,
                    weight = newWeight,
                    height = newHeight,
                    notes = newNotes,
                    imageUri = updatedImageUri // <-- DIUBAH
                )
                // ▲▲▲ AKHIR DARI PERUBAHAN ▲▲▲

                viewModel.updateChild(position, updatedChild)

                Toast.makeText(context, "Profil disimpan!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    // ... (Fungsi showEditNameDialog, setupDateDropdowns, setupGenderDropdowns tetap sama)
    private fun showEditNameDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_name, null)
        val editTextNewName = dialogView.findViewById<EditText>(R.id.et_new_name)
        editTextNewName.setText(textName.text)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Ubah Nama")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val newName = editTextNewName.text.toString().trim()
                if (newName.isNotEmpty()) {
                    textName.text = newName
                    Toast.makeText(requireContext(), "Nama diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .create()
        dialog.show()
    }
    private fun setupDateDropdowns() {
        val days = (1..31).map { it.toString() }
        val dayAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, days)
        autocompleteDay.setAdapter(dayAdapter)
        val months = listOf("January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December")
        val monthAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, months)
        autocompleteMonth.setAdapter(monthAdapter)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear - 100..currentYear).map { it.toString() }.reversed()
        val yearAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, years)
        autocompleteYear.setAdapter(yearAdapter)
    }
    private fun setupGenderDropdown() {
        val genders = listOf("Laki-laki", "Perempuan")
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, genders)
        autocompleteGender.setAdapter(genderAdapter)
    }
}