package com.example.growdiary.vaccine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.growdiary.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Vaccine0_6MonthsFragment : Fragment(){

    private var progressListener: VaccineProgressListener? = null
    private val milestoneStatus: MutableMap<Int, Boolean> = mutableMapOf()
    private val TOTAL_MILESTONES_0_6_MONTHS = 18

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Dapatkan referensi ke listener dari parentFragment
        if (parentFragment is VaccineProgressListener) {
            progressListener = parentFragment as VaccineProgressListener
        } else if (context is VaccineProgressListener) {
            // Jika Fragment ini di-host langsung oleh Activity
            progressListener = context as VaccineProgressListener
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
        vaccineList.add(Vaccine("Bacillus Calmette–Guérin", "1ST DOSE", "PRIORITY", "12/08/2025", R.drawable.bcg, false))
        vaccineList.add(Vaccine("Hepatitis B", "1ST DOSE", "PRIORITY", "12/08/2025", R.drawable.hepatitisb, false))
        vaccineList.add(Vaccine("Polio", "1ST DOSE", "PRIORITY", "31/08/2025", R.drawable.polio, false) )
        vaccineList.add(Vaccine("DPT", "1ST DOSE", "PRIORITY", "31/9/2025", R.drawable.dpt, false))
        vaccineList.add(Vaccine("HiB", "1ST DOSE", "PRIORITY", "31/9/2025", R.drawable.hib, false))
        vaccineList.add(Vaccine("PCV", "1ST DOSE", "PRIORITY", "31/9/2025", R.drawable.pcv, false))
        vaccineList.add(Vaccine("Rotavirus", "1ST DOSE", "PRIORITY", "31/9/2025", R.drawable.rotavirus, false))
        vaccineList.add(Vaccine("Hepatitis B", "2ND DOSE", "PRIORITY", "31/09/2025", R.drawable.hepatitisb, false))
        vaccineList.add(Vaccine("Polio", "2ND DOSE", "PRIORITY", "31/09/2025", R.drawable.polio, false) )
        vaccineList.add(Vaccine("DPT", "2ND DOSE", "PRIORITY", "31/10/2025", R.drawable.dpt, false))
        vaccineList.add(Vaccine("HiB", "2ND DOSE", "PRIORITY", "31/10/2025", R.drawable.hib, false))
        vaccineList.add(Vaccine("Hepatitis B", "3RD DOSE", "PRIORITY", "31/10/2025", R.drawable.hepatitisb, false))
        vaccineList.add(Vaccine("Polio", "3RD DOSE", "PRIORITY", "31/10/2025", R.drawable.polio, false) )
        vaccineList.add(Vaccine("DPT", "3RD DOSE", "PRIORITY", "31/11/2025", R.drawable.dpt, false))
        vaccineList.add(Vaccine("HiB", "3RD DOSE", "PRIORITY", "31/11/2025", R.drawable.hib, false))
        vaccineList.add(Vaccine("PCV", "2ND DOSE", "PRIORITY", "31/11/2025", R.drawable.pcv, false))
        vaccineList.add(Vaccine("Rotavirus", "2ND DOSE", "PRIORITY", "31/11/2025", R.drawable.rotavirus, false))

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = VaccineToDoAdapter(vaccineList)

        recyclerView.adapter = adapter

        updateOverallProgress(9, 18)
        return view
    }

    private fun updateOverallProgress(completed:Int, total:Int) {
        val completedCount = completed
        val totalCount = total

        progressListener?.onProgressUpdated(completedCount, totalCount)
    }
}