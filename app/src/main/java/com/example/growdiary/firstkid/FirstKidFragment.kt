package com.example.growdiary.firstkid

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.growdiary.R
import java.util.*

class FirstKidFragment : Fragment() {
    private lateinit var ivMaleGender: ImageView
    private lateinit var ivFemaleGender: ImageView
    private var selectedGender: String = ""

    private lateinit var etAge: EditText

    // EditTexts yang akan menggantikan Spinner secara visual
    private lateinit var etDay: EditText
    private lateinit var etMonth: EditText
    private lateinit var etYear: EditText

    // Spinner yang disembunyikan (untuk menyimpan data dan logika)
    private lateinit var spinnerDay: Spinner
    private lateinit var spinnerMonth: Spinner
    private lateinit var spinnerYear: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first_kid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivMaleGender = view.findViewById(R.id.iv_male_gender)
        ivFemaleGender = view.findViewById(R.id.iv_female_gender)
        etAge = view.findViewById(R.id.et_age)

        etDay = view.findViewById(R.id.et_day)
        etMonth = view.findViewById(R.id.et_month)
        etYear = view.findViewById(R.id.et_year)

        spinnerDay = view.findViewById(R.id.spinner_day)
        spinnerMonth = view.findViewById(R.id.spinner_month)
        spinnerYear = view.findViewById(R.id.spinner_year)

        // Inisialisasi gender default
        ivMaleGender.setImageResource(R.drawable.male_bg_blue)
        ivFemaleGender.setImageResource(R.drawable.female_bg_white)
        selectedGender = "male"

        ivMaleGender.setOnClickListener {
            ivMaleGender.setImageResource(R.drawable.male_bg_blue)
            ivFemaleGender.setImageResource(R.drawable.female_bg_white)
            selectedGender = "male"
        }

        ivFemaleGender.setOnClickListener {
            ivMaleGender.setImageResource(R.drawable.male_bg_white)
            ivFemaleGender.setImageResource(R.drawable.female_bg_purple)
            selectedGender = "female"
        }

        // --- Setup Spinner Day ---
        val days = (1..31).map { it.toString() }
        val dayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDay.adapter = dayAdapter
        spinnerDay.setSelection(0)
        etDay.setText(days[0]) // Set text default untuk EditText

        // --- Setup Spinner Month ---
        val monthNames = arrayOf("January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December")
        val months = monthNames.toList()
        val monthAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMonth.adapter = monthAdapter
        spinnerMonth.setSelection(0)
        etMonth.setText(months[0]) // Set text default untuk EditText

        // --- Setup Spinner Year ---
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = (currentYear downTo currentYear - 100).map { it.toString() }
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = yearAdapter
        spinnerYear.setSelection(0)
        etYear.setText(years[0]) // Set text default untuk EditText

        // --- Atur OnClickListener untuk EditTexts yang akan menampilkan pop-up dropdown ---
        etDay.setOnClickListener {
            showDropdown(it, days, spinnerDay, etDay)
        }
        etMonth.setOnClickListener {
            showDropdown(it, months, spinnerMonth, etMonth)
        }
        etYear.setOnClickListener {
            showDropdown(it, years, spinnerYear, etYear)
        }

        view.findViewById<Button>(R.id.btn_done).setOnClickListener {
//            val name = view.findViewById<EditText>(R.id.et_name).text.toString().trim()
//            val age = etAge.text.toString().trim()
//            val day = etDay.text.toString().trim()
//            val month = etMonth.text.toString().trim()
//            val year = etYear.text.toString().trim()
//            val height = view.findViewById<EditText>(R.id.et_height).text.toString().trim()
//            val weight = view.findViewById<EditText>(R.id.et_weight).text.toString().trim()
//
//            if (name.isEmpty() || age.isEmpty() || day.isEmpty() || month.isEmpty() || year.isEmpty() || height.isEmpty() || weight.isEmpty()) {
//                Toast.makeText(requireContext(), "Harap isi semua informasi!", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (age.toIntOrNull() == null) {
//                Toast.makeText(requireContext(), "Usia harus angka valid!", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            try {
//                val cal = Calendar.getInstance()
//                // Konversi nama bulan ke indeks (0=Januari)
//                val monthIndex = monthNames.indexOf(month)
//                if (monthIndex == -1) {
//                    Toast.makeText(requireContext(), "Bulan tidak valid!", Toast.LENGTH_SHORT).show()
//                    return@setOnClickListener
//                }
//                cal.set(year.toInt(), monthIndex, day.toInt())
//                // Periksa apakah tanggal yang disetel sesuai dengan input asli (untuk menangani tanggal tidak valid seperti 31 Februari)
//                if (cal.get(Calendar.MONTH) != monthIndex || cal.get(Calendar.DAY_OF_MONTH) != day.toInt()) {
//                    Toast.makeText(requireContext(), "Tanggal lahir tidak valid!", Toast.LENGTH_SHORT).show()
//                    return@setOnClickListener
//                }
//            } catch (e: Exception) {
//                Toast.makeText(requireContext(), "Tanggal lahir tidak valid!", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            Toast.makeText(requireContext(), "Data telah diisi!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_firstKidFragment_to_homeFragment)
        }
    }

    private fun showDropdown(anchorView: View, items: List<String>, targetSpinner: Spinner, targetEditText: EditText) {
        val listView = ListView(requireContext())
        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_dropdown_item, items) // Menggunakan custom_dropdown_item
        listView.adapter = adapter
        listView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white)) // Latar belakang putih

        // Ukur tinggi satu item untuk menentukan tinggi maksimal ListView
        val listItem = adapter.getView(0, null, listView)
        listItem.measure(
            View.MeasureSpec.makeMeasureSpec(anchorView.width, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val itemHeight = listItem.measuredHeight

        val maxDropdownItems = 3  // Maksimal 3 item yang terlihat
        val maxDropdownHeight = itemHeight * maxDropdownItems + listView.dividerHeight * (maxDropdownItems - 1)

        val popupWindow = PopupWindow(
            listView,
            anchorView.width, // Lebar pop-up sama dengan lebar EditText
            maxDropdownHeight, // Tinggi maksimum pop-up
            true
        )

        popupWindow.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), android.R.color.transparent)))
        popupWindow.elevation = 8f

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = items[position]
            targetEditText.setText(selectedItem)
            targetSpinner.setSelection(position)
            popupWindow.dismiss()
        }

        // Set seleksi pada ListView agar item yang sudah dipilih berada di tengah jika memungkinkan
        val selectedIndex = items.indexOf(targetEditText.text.toString())
        if (selectedIndex != -1) {
            listView.setSelection(selectedIndex)
        }

        popupWindow.showAsDropDown(anchorView)
    }
}