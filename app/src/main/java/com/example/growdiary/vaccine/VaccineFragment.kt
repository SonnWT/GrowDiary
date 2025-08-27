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

interface VaccineProgressListener {
    fun onProgressUpdated(completedMilestones: Int, totalMilestones: Int)
}

class Vaccine0_6Fragment : Fragment(), VaccineProgressListener {
    private lateinit var spinner : Spinner;
    private lateinit var adapter : ArrayAdapter<String>;
    private val items = arrayOf(
        "0 - 6 Months",
        "7 - 12 Months",
        "1 - 2 Years",
        "3 - 5 Years",
        "6 - 10 Years"
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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                loadVaccineFragment(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val vaccineList: MutableList<Vaccine> = ArrayList();
        vaccineList.add(Vaccine("Polio", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.polio, false))
        vaccineList.add(Vaccine("Bacillus Calmette–Guérin", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.bcg, false))
        vaccineList.add(Vaccine("Hepatitis B", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.hepatitisb, false))
        vaccineList.add(Vaccine("DPT", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.dpt, false))
        vaccineList.add(Vaccine("Haemophilus Influenzae Type B", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.hib, false))
        vaccineList.add(Vaccine("PCV", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.pcv, false))
        vaccineList.add(Vaccine("Rotavirus", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.rotavirus, false))
        vaccineList.add(Vaccine("Influenza", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.influenza, false))
        vaccineList.add(Vaccine("MMR", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.mmr, false) )
        vaccineList.add(Vaccine("JE Vaccine", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.je, false) )
        vaccineList.add(Vaccine("Hepatitis A", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.hepatitisa, false) )

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
            loadVaccineFragment(spinnerPosition)
        }
        onProgressUpdated(0, 0)
    }

    private fun loadVaccineFragment(position: Int) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val newFragment: Fragment = when (position) {
            0 -> Vaccine0_6MonthsFragment()
            1 -> Vaccine7_12MonthsFragment()
            2 -> Vaccine1_2YearsFragment()
            3 -> Vaccine3_5YearsFragment()
            4 -> Vaccine6_10YearsFragment()
            else -> Roadmap0_6MonthsFragment()
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