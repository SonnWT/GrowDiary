package com.example.growdiary.diary


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R


class DiaryLuar2Fragment : Fragment() {

    private lateinit var adapter: DiaryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var diaryArrayList: ArrayList<Diary>

    lateinit var diaryImage : Array<Int>
    lateinit var diaryTitle : Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary_luar2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataInitialize()

        val layoutManager = GridLayoutManager(context, 2)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
//        adapter = DiaryAdapter(diaryArrayList)
        val adapter = DiaryAdapter(diaryArrayList) { position ->
            if (position == 0) {
                // ke Fragment A
                findNavController().navigate(R.id.newDiary)
            } else if (position == 1){
                // ke Fragment B
                findNavController().navigate(R.id.diary1)
            }

        }
        recyclerView.adapter = adapter

    }


    private fun dataInitialize(){
        diaryArrayList = arrayListOf<Diary>()

        diaryImage = arrayOf(
            0,
            R.drawable.merangkak,
            R.drawable.bicara,
            R.drawable.jalan,
            R.drawable.makan,
            R.drawable.main_air,
            R.drawable.tengkurap
        )

        diaryTitle = arrayOf(
            "",
            getString(R.string.title_merangkak),
            getString(R.string.title_bicara),
            getString(R.string.title_jalan),
            getString(R.string.title_makan),
            getString(R.string.title_main_air),
            getString(R.string.title_tengkurap)
        )

        for (i in diaryImage.indices){
            val diary = Diary(diaryTitle[i], diaryImage[i])
            diaryArrayList.add(diary)
        }
    }

}