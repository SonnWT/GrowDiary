package com.example.growdiary.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView // Perubahan: Import AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.growdiary.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import java.util.Calendar

class EditProfileFragment : Fragment() {

    private lateinit var profileImage: ShapeableImageView
    private lateinit var textName: TextView
    private lateinit var btnEditName: ImageView
    private lateinit var autocompleteDay: AutoCompleteTextView // Perubahan: AutoCompleteTextView
    private lateinit var autocompleteMonth: AutoCompleteTextView // Perubahan: AutoCompleteTextView
    private lateinit var autocompleteYear: AutoCompleteTextView // Perubahan: AutoCompleteTextView
    private lateinit var autocompleteGender: AutoCompleteTextView // Perubahan: AutoCompleteTextView
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editNotes: EditText
    private lateinit var btnSave: MaterialButton
    private lateinit var btnEditPhoto: FloatingActionButton

    private var newImageUri: Uri? = null
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            newImageUri = it
            profileImage.setImageURI(it)
            // Tambahkan ini untuk memastikan persistensi URI di seluruh konfigurasi perubahan
            it.let { uri ->
                requireContext().contentResolver.takePersistableUriPermission(
                    uri,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        profileImage = view.findViewById(R.id.profile_image_edit)
        textName = view.findViewById(R.id.text_name_edit)
        btnEditName = view.findViewById(R.id.btn_edit_name)
        btnEditPhoto = view.findViewById(R.id.btn_edit_photo)
        autocompleteDay = view.findViewById(R.id.autocomplete_day) // Perubahan: autocomplete_day
        autocompleteMonth = view.findViewById(R.id.autocomplete_month) // Perubahan: autocomplete_month
        autocompleteYear = view.findViewById(R.id.autocomplete_year) // Perubahan: autocomplete_year
        autocompleteGender = view.findViewById(R.id.autocomplete_gender) // Perubahan: autocomplete_gender
        editWeight = view.findViewById(R.id.edit_weight)
        editHeight = view.findViewById(R.id.edit_height)
        editNotes = view.findViewById(R.id.edit_notes)
        btnSave = view.findViewById(R.id.btn_save_profile)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize AutoCompleteTextViews
        setupDateDropdowns() // Perubahan nama fungsi
        setupGenderDropdown() // Perubahan nama fungsi

        // Load existing data (for demonstration)
        textName.text = "Joseph"
        profileImage.setImageResource(R.drawable.profile_joseph)
        editWeight.setText("20.0")
        editHeight.setText("78.0")
        editNotes.setText("Joseph alergi susu sapi dan kacang tanah. Joseph juga tidak bisa tidur jika tidak dibacakan dongeng.")

        // Set initial selections for AutoCompleteTextViews
        autocompleteDay.setText("16", false) // false means don't filter
        autocompleteMonth.setText("June", false)
        autocompleteYear.setText("2024", false)
        autocompleteGender.setText("Male", false)


        btnEditName.setOnClickListener {
            showEditNameDialog()
        }

        textName.setOnClickListener {
            showEditNameDialog()
        }

        btnSave.setOnClickListener {
            // Save logic here (e.g., update data in a ViewModel or database)
//            Toast.makeText(context, "Profil disimpan!", Toast.LENGTH_SHORT).show()
            // Navigate back to ProfileDetailFragment
            findNavController().navigate(R.id.action_editProfileFragment_to_profileDetailFragment)
        }

        btnEditPhoto.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }
    }

    private fun setupDateDropdowns() { // Perubahan nama fungsi
        // Day Dropdown
        val days = (1..31).map { it.toString() }
        val dayAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, days)
        autocompleteDay.setAdapter(dayAdapter)
        autocompleteDay.setOnItemClickListener { parent, view, position, id ->
            // Handle item selection if needed
            // val selectedDay = parent.getItemAtPosition(position).toString()
            // Toast.makeText(context, "Selected Day: $selectedDay", Toast.LENGTH_SHORT).show()
        }

        // Month Dropdown
        val months = listOf("January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December")
        val monthAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, months)
        autocompleteMonth.setAdapter(monthAdapter)
        autocompleteMonth.setOnItemClickListener { parent, view, position, id ->
            // Handle item selection if needed
        }

        // Year Dropdown
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear - 100..currentYear).map { it.toString() }.reversed()
        val yearAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, years)
        autocompleteYear.setAdapter(yearAdapter)
        autocompleteYear.setOnItemClickListener { parent, view, position, id ->
            // Handle item selection if needed
        }
    }

    private fun setupGenderDropdown() { // Perubahan nama fungsi
        val genders = listOf("Male", "Female")
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, genders)
        autocompleteGender.setAdapter(genderAdapter)
        autocompleteGender.setOnItemClickListener { parent, view, position, id ->
            // Handle item selection if needed
        }
    }

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
}