package com.example.growdiary.vaccine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R
import com.example.growdiary.roadmap.Roadmap0_6MonthsFragment
import com.example.growdiary.roadmap.Roadmap1_2YearsFragment
import com.example.growdiary.roadmap.Roadmap6_12MonthsFragment

interface VaccineProgressListener {
    fun onProgressUpdated(completedMilestones: Int, totalMilestones: Int)
}
/**
 * A simple [Fragment] subclass.
 * Use the [Vaccine0_6Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Vaccine0_6Fragment : Fragment(), VaccineProgressListener {
    // TODO: Rename and change types of parameters
    private lateinit var spinner : Spinner;
    private lateinit var adapter : ArrayAdapter<String>;
    private val items = arrayOf(
        "0 - 6 Bulan",
        "7 - 12 Bulan",
        "1 - 2 Tahun",
        "3 - 5 Tahun",
        "6 - 10 Tahun"
    )
    private lateinit var progressTextView: TextView // Deklarasikan TextView untuk progres

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vaccine, container, false)
        spinner = view.findViewById(R.id.select_spinner)
        progressTextView = view.findViewById(R.id.progress_text_value) // Inisialisasi TextView progress
        val vaccineHistoryRecyclerView = view.findViewById<RecyclerView>(R.id.vaccineHistoryRecyclerView)

        adapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_dropdown_item, R.id.spinner_item_text, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val itemView = super.getView(position, convertView, parent) as LinearLayout
                val textView = itemView.findViewById<TextView>(R.id.spinner_item_text)
                textView.text = items[position]
                val divider = itemView.findViewById<View>(R.id.divider_view)
                divider?.visibility = View.GONE
                return itemView
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val itemView = LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item, parent, false)
                val textView = itemView.findViewById<TextView>(R.id.spinner_item_text)
                val divider = itemView.findViewById<View>(R.id.divider_view)

                textView.text = items[position]

                if (position == count - 1) {
                    divider.visibility = View.GONE
                } else {
                    divider.visibility = View.VISIBLE
                }
                return itemView
            }
        }

        spinner.adapter = adapter

        // Atur listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                loadVaccineFragment(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) { /* Do nothing */ }
        }

        val vaccineList: MutableList<Vaccine> = ArrayList();
        vaccineList.add(Vaccine("Polio", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon_1, false))
        vaccineList.add(Vaccine("Bacillus Calmette–Guérin", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon_2, false))
        vaccineList.add(Vaccine("Hepatitis B", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon_3, false))
        vaccineList.add(Vaccine("DPT", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon_4, false))
        vaccineList.add(Vaccine("Polio", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon_5, false))

        vaccineHistoryRecyclerView.layoutManager = LinearLayoutManager(context)
        vaccineHistoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
        vaccineHistoryRecyclerView.setHasFixedSize(true)

        val vaccineHistoryAdapter = VaccineHistoryAdapter(vaccineList) { position ->
            if (position == 0) {
                findNavController().navigate(R.id.vaccineDetailFragment)
            }
        }
        vaccineHistoryRecyclerView.adapter = vaccineHistoryAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val defaultSelection = "0 - 6 Bulan"
        val spinnerPosition = adapter.getPosition(defaultSelection)
        if (spinnerPosition >= 0) {
            spinner.setSelection(spinnerPosition)
            // Muat Fragment roadmap default saat fragment utama dibuat
            loadVaccineFragment(spinnerPosition)
        }
        // Inisialisasi progres awal (bisa 0/0 atau nilai default lainnya)
        onProgressUpdated(0, 0)
    }

    private fun loadVaccineFragment(position: Int) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val newFragment: Fragment = when (position) {
            0 -> Vaccine0_6MonthsFragment()
            1 -> Vaccine7_12MonthsFragment()
            2 -> Vaccine1_2YearssFragment()
            3 -> Vaccine3_5YearsFragment()
            4 -> Vaccine6_10YearsFragment()
            else -> Roadmap0_6MonthsFragment() // Fallback
        }
        fragmentTransaction.replace(R.id.vaccine_fragment_container, newFragment)
        fragmentTransaction.commit()
    }

    // Implementasi metode dari interface RoadmapProgressListener
    override fun onProgressUpdated(completedMilestones: Int, totalMilestones: Int) {
        // Perbarui teks progres di UI RoadmapFragment
        progressTextView.text = "$completedMilestones / $totalMilestones"
    }
}