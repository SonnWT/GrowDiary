package com.example.growdiary.roadmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.growdiary.R
import com.example.growdiary.roadmap.Roadmap0_6MonthsFragment // Import Fragment 0-6 Bulan
// Import Fragment-fragment roadmap lainnya setelah Anda membuatnya
// import com.example.growdiary.roadmap.fragments.Roadmap7_12MonthsFragment
// import com.example.growdiary.roadmap.fragments.Roadmap1_2YearsFragment
// dst.

class RoadmapFragment : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String>
    private val items = arrayOf(
        "0 - 6 Bulan",
        "7 - 12 Bulan",
        "1 - 2 Tahun",
        "3 - 5 Tahun",
        "6 - 10 Tahun"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_roadmap, container, false)
        spinner = view.findViewById(R.id.select_spinner)

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
                loadRoadmapFragment(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) { /* Do nothing */ }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val defaultSelection = "0 - 6 Bulan"
        val spinnerPosition = adapter.getPosition(defaultSelection)
        if (spinnerPosition >= 0) {
            spinner.setSelection(spinnerPosition)
            // Muat Fragment roadmap default saat fragment utama dibuat
            loadRoadmapFragment(spinnerPosition)
        }
    }

    private fun loadRoadmapFragment(position: Int) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val newFragment: Fragment = when (position) {
            0 -> Roadmap0_6MonthsFragment()
            1 -> {
                // TODO: Buat dan muat Roadmap7_12MonthsFragment()
                // Untuk saat ini, kita bisa fallback atau tampilkan placeholder
                // return Roadmap7_12MonthsFragment()
                Roadmap0_6MonthsFragment() // Sementara gunakan 0-6 Bulan sebagai placeholder
            }
            2 -> {
                // TODO: Buat dan muat Roadmap1_2YearsFragment()
                Roadmap0_6MonthsFragment() // Sementara
            }
            3 -> {
                // TODO: Buat dan muat Roadmap3_5YearsFragment()
                Roadmap0_6MonthsFragment() // Sementara
            }
            4 -> {
                // TODO: Buat dan muat Roadmap6_10YearsFragment()
                Roadmap0_6MonthsFragment() // Sementara
            }
            else -> Roadmap0_6MonthsFragment() // Fallback
        }
        fragmentTransaction.replace(R.id.roadmap_fragment_container, newFragment)
        fragmentTransaction.commit()
    }
}