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
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = VaccineToDoAdapter(vaccineList)
        recyclerView.adapter = adapter

        val backArrow = view.findViewById<ImageView>(R.id.arrow_back_vaccine)
        backArrow.setOnClickListener {
            findNavController().navigate(R.id.vaccineFragment)
        }
    }
}