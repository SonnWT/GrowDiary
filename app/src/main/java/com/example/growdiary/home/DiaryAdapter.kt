// com.example.growdiary.home/DiaryAdapter.kt (atau di package adapter Anda)
package com.example.growdiary.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView // Penting: Import CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R // Pastikan ini benar

class DiaryAdapter(
    private val diaryList: List<DiaryItem>, // List item yang akan ditampilkan
    private val listener: OnItemClickListener // Listener untuk menangani klik item
) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    // --- Interface untuk Callback Klik Item ---
    interface OnItemClickListener {
        fun onItemClick(position: Int) // Akan dipanggil saat item di RecyclerView diklik
    }

    // --- ViewHolder untuk setiap item ---
    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.diary_image)
        val descriptionTextView: TextView = itemView.findViewById(R.id.diary_description)
        val diaryCardItem: CardView = itemView.findViewById(R.id.card_diary_item) // Ambil referensi CardView

        init {
            // Set OnClickListener pada CardView di dalam ViewHolder
            diaryCardItem.setOnClickListener {
                val position = adapterPosition
                // Pastikan posisi valid sebelum memanggil listener
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position) // Meneruskan klik ke Fragment/Activity melalui listener
                }
            }
        }
    }

    // --- Metode Adapter Override ---
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val item = diaryList[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.descriptionTextView.text = item.description
    }

    override fun getItemCount(): Int = diaryList.size
}