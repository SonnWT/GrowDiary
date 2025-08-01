package com.example.growdiary.profile

import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

// Data class sudah benar
data class Child(
    val name: String,
    val birthDate: String,
    val imageUri: String? = null,
    val gender: String,
    val weight: String,
    val height: String,
    val notes: String? = null // <-- TAMBAHKAN PROPERTI INI JIKA BELUM ADA
)

class ChildAdapter(
    // Hapus 'children' dari constructor, hanya sisakan listener
    private val listener: OnChildItemClickListener
) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    private var children: List<Child> = emptyList()

    // Fungsi baru untuk meng-update data dari luar (dari ViewModel)
    fun submitList(newChildren: List<Child>) {
        children = newChildren
        notifyDataSetChanged() // Memberi tahu RecyclerView untuk refresh total
    }

    interface OnChildItemClickListener {
        fun onItemClick(position: Int)
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val childName: TextView = view.findViewById(R.id.tv_child_name)
        val childBirthDate: TextView = view.findViewById(R.id.tv_child_birthdate)
        val childPhoto: ImageView = view.findViewById(R.id.iv_child_photo)
        val childMenuButton: ImageButton = view.findViewById(R.id.btn_child_menu)
        val ageYears: TextView = view.findViewById(R.id.tv_age_years)
        val ageMonths: TextView = view.findViewById(R.id.tv_age_months)
        val ageDays: TextView = view.findViewById(R.id.tv_age_days)
        val gender: TextView = view.findViewById(R.id.tv_gender)
        val weight: TextView = view.findViewById(R.id.tv_weight)
        val height: TextView = view.findViewById(R.id.tv_height)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_child_card, parent, false)
        return ChildViewHolder(view)
    }

    // BENAR (Selalu menggunakan posisi yang terbaru)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val child = children[position]

        // Set data dasar
        holder.childName.text = child.name
        holder.childBirthDate.text = child.birthDate

        // Set data gender, berat, dan tinggi
        holder.gender.text = child.gender
        holder.weight.text = "${child.weight} kg"
        holder.height.text = "${child.height} cm"

        // Set gambar
        if (child.imageUri != null) {
            holder.childPhoto.setImageURI(Uri.parse(child.imageUri))
        } else {
            holder.childPhoto.setImageResource(R.drawable.ic_launcher_background) // Ganti dengan placeholder Anda
        }

        // Hitung dan set umur
        try {
            val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH)
            val birthDate = LocalDate.parse(child.birthDate, formatter)
            val currentDate = LocalDate.now()
            val period = Period.between(birthDate, currentDate)

            holder.ageYears.text = period.years.toString()
            holder.ageMonths.text = period.months.toString() // Menampilkan Bulan
            holder.ageDays.text = period.days.toString()
        } catch (e: Exception) {
            // Jika format tanggal salah
            holder.ageYears.text = "-"
            holder.ageMonths.text = "-"
            holder.ageDays.text = "-"
        }

        // Set listener untuk tombol menu di kartu
        holder.childMenuButton.setOnClickListener { anchorView ->
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                showPopupMenu(anchorView, holder.adapterPosition)
            }
        }

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                listener.onItemClick(holder.adapterPosition)
            }
        }
    }

    override fun getItemCount() = children.size

    private fun showPopupMenu(anchorView: View, position: Int) {
        val context = anchorView.context
        val inflater = LayoutInflater.from(context)
        val popupView = inflater.inflate(R.layout.popup_child_options, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        val btnEdit: Button = popupView.findViewById(R.id.btn_popup_edit_child)
        val btnDelete: Button = popupView.findViewById(R.id.btn_popup_delete_child)

        btnEdit.setOnClickListener {
            listener.onEditClick(position)
            popupWindow.dismiss()
        }

        btnDelete.setOnClickListener {
            listener.onDeleteClick(position)
            popupWindow.dismiss()
        }

        popupWindow.showAsDropDown(anchorView)
    }
}