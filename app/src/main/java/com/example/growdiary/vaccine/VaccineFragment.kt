package com.example.growdiary.vaccine

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R

/**
 * A simple [Fragment] subclass.
 * Use the [VaccineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VaccineFragment : Fragment() {
    // TODO: Rename and change types of parameters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaccine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val vaccineHistoryRecyclerView = view.findViewById<RecyclerView>(R.id.vaccineHistoryRecyclerView)

        val vaccineList: MutableList<Vaccine> = ArrayList();
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))
        vaccineList.add(Vaccine("Polio Vaccination", "1ST DOSE", "PRIORITY", "02/01/2024", R.drawable.vaccine_icon))

        recyclerView.layoutManager = LinearLayoutManager(context)
        vaccineHistoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
        vaccineHistoryRecyclerView.setHasFixedSize(true)
        val adapter = VaccineToDoAdapter(vaccineList)
        val vaccineHistoryAdapter = VaccineHistoryAdapter(vaccineList) { position ->
            if (position == 0) {
                findNavController().navigate(R.id.vaccineDetailFragment)
            }
        }



        recyclerView.adapter = adapter
        vaccineHistoryRecyclerView.adapter = vaccineHistoryAdapter

//        vaccineHistoryAdapter.onItemClick = {
//            val intent = Intent(this, VaccineDetails::class.java)
//            intent.putExtra()
//        }
    }

}