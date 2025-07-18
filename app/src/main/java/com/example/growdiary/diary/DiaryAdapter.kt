package com.example.growdiary.diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R

class DiaryAdapter(private val diaryList:ArrayList<Diary>, private val onItemClicked: (Int) -> Unit) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>(){


    class DiaryViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.image);
        val textView : TextView = itemView.findViewById(R.id.text_diary);
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return DiaryViewHolder(view)
    }


    override fun getItemCount(): Int {
        return diaryList.size
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val diary = diaryList[position]

        if(position == 0){
            holder.imageView.setImageResource(R.drawable.baseline_add_24)
            holder.imageView.setPadding(40, 120, 40, 0)
            holder.imageView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            holder.textView.text = ""
            holder.textView.setBackgroundResource(R.drawable.bottom_rounded_bg_orange)


        }
        else{
            holder.imageView.setImageResource(diary.diaryImage)
            holder.textView.text = diary.diaryTitle
        }

        holder.itemView.setOnClickListener {
            onItemClicked(position)
        }
    }

}