package com.example.growdiary.roadmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R

class RoadmapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_roadmap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Temukan RecyclerView dari layout
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_roadmap)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Buat data dummy untuk ditampilkan
        val dummyData = createDummyData()

        // Buat instance dari adapter dan hubungkan ke RecyclerView
        val adapter = RoadmapAdapter(dummyData)
        recyclerView.adapter = adapter
    }

    // Fungsi untuk membuat data contoh
    private fun createDummyData(): List<RoadmapItem> {
        return listOf(
            RoadmapItem("Milestone Terkunci", R.drawable.ic_lock, 2),
            RoadmapItem("Belajar membalikkan badan", R.drawable.img_milestone_placeholder, 0),
            RoadmapItem("Belajar meraih benda", R.drawable.img_milestone_placeholder, 1),
            RoadmapItem("Mulai duduk sendiri", R.drawable.img_milestone_placeholder, 0),
            RoadmapItem("Merangkak", R.drawable.img_milestone_placeholder, 1),
            RoadmapItem("Berdiri dengan bantuan", R.drawable.img_milestone_placeholder, 0),
            RoadmapItem("Merangkak", R.drawable.img_milestone_placeholder, 1),
            RoadmapItem("Berdiri dengan bantuan", R.drawable.img_milestone_placeholder, 0),
            RoadmapItem("Merangkak", R.drawable.img_milestone_placeholder, 1),
            RoadmapItem("Berdiri dengan bantuan", R.drawable.img_milestone_placeholder, 0)
        )
    }
}