package com.example.growdiary.vaccine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R

class VaccineDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vaccine_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val vaccineList: MutableList<Vaccine> = ArrayList();
        vaccineList.add(Vaccine("Polio", "1ST DOSE", "PRIORITY", "12/08/2025", R.drawable.polio, false))
        vaccineList.add(Vaccine("Polio", "2ND DOSE", "PRIORITY", "31/09/2025", R.drawable.polio, false))
        vaccineList.add(Vaccine("Polio", "3RD DOSE", "PRIORITY", "30/10/2025", R.drawable.polio, false))
        vaccineList.add(Vaccine("Polio", "4TH DOSE", "PRIORITY", "31/11/2025", R.drawable.polio, false))
        vaccineList.add(Vaccine("Polio", "5TH DOSE", "OPTIONAL", "31/02/2027", R.drawable.polio, false))

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = VaccineToDoAdapter(vaccineList)
        recyclerView.adapter = adapter

        val backArrow = view.findViewById<ImageView>(R.id.arrow_back_vaccine)
        backArrow.setOnClickListener {
            findNavController().navigate(R.id.vaccineFragment)
        }
    }
}