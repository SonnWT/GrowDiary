package com.example.growdiary.vaccine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.growdiary.R
import com.example.growdiary.roadmap.RoadmapProgressListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Vaccine7_12MonthsFragment : Fragment(){

    private var progressListener: RoadmapProgressListener? = null
    private val milestoneStatus: MutableMap<Int, Boolean> = mutableMapOf()
    private val TOTAL_MILESTONES_0_6_MONTHS = 18
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Dapatkan referensi ke listener dari parentFragment
        if (parentFragment is RoadmapProgressListener) {
            progressListener = parentFragment as RoadmapProgressListener
        } else if (context is RoadmapProgressListener) {
            // Jika Fragment ini di-host langsung oleh Activity
            progressListener = context as RoadmapProgressListener
        } else {
        }
    }

    override fun onDetach() {
        super.onDetach()
        progressListener = null // Kosongkan listener saat fragment dilepas
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vaccine_ranges, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val vaccineList: MutableList<Vaccine> = ArrayList();
        vaccineList.add(Vaccine("Influenza", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.influenza, false) )
        vaccineList.add(Vaccine("MMR", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.mmr, false) )
        vaccineList.add(Vaccine("Japanese Encephalitis", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.je, false) )
        vaccineList.add(Vaccine("PCV", "4TH DOSE", "OPTIONAL", "02/01/2024", R.drawable.pcv, false) )
        vaccineList.add(Vaccine("Hepatitis A", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.hepatitisa, false) )
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = VaccineToDoAdapter(vaccineList)

        recyclerView.adapter = adapter

        return view
    }
}