package com.example.growdiary.vaccine

import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R
import java.security.AccessController.getContext

class VaccineToDoAdapter(private val vaccineData: List<Vaccine>) :
    RecyclerView.Adapter<VaccineToDoAdapter.MyViewHolder>() {

    private val vaccineList: MutableList<Vaccine> = vaccineData.toMutableList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vaccineToDoName: TextView = itemView.findViewById(R.id.vaccineToDoName)
        val vaccineToDoDose: TextView = itemView.findViewById(R.id.vaccineToDoDose)
        val vaccineToDoPriority: TextView = itemView.findViewById(R.id.vaccineToDoPriority)
        val vaccineToDoDate: TextView = itemView.findViewById(R.id.vaccineToDoDate)
        val vaccineToDoImage: ImageView = itemView.findViewById(R.id.vaccineToDoImage)
        val vaccineCheckbox: AppCompatCheckBox = itemView.findViewById(R.id.vaccine_checkbox)
        val vaccineToDo: FrameLayout = itemView.findViewById(R.id.vaccine_to_do)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vaccine_to_do, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vaccineList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val vaccine = vaccineList[position]

        holder.vaccineToDoName.text = vaccine.vaccineName
        holder.vaccineToDoDose.text = vaccine.vaccineDose
        holder.vaccineToDoPriority.text = vaccine.vaccinePriority
        holder.vaccineToDoDate.text = vaccine.vaccineDate

        if (vaccine.vaccineImage != null) {
            holder.vaccineToDoImage.setImageResource(vaccine.vaccineImage!!)
        }

        // Prevent old listeners from triggering
        holder.vaccineCheckbox.setOnCheckedChangeListener(null)

        // Set checkbox state
        holder.vaccineCheckbox.isChecked = vaccine.vaccineChecked

        // Apply foreground tint if checked

        // Checkbox change listener
        holder.vaccineCheckbox.setOnCheckedChangeListener { _, isChecked ->
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                vaccine.vaccineChecked = isChecked
                val movedItem = vaccineList.removeAt(adapterPosition)

                if (isChecked) {
                    vaccineList.add(movedItem)
                    notifyItemMoved(adapterPosition, vaccineList.size - 1)
                } else {
                    vaccineList.add(0, movedItem)
                    notifyItemMoved(adapterPosition, 0)
                }
            }
            holder.vaccineToDo.foreground = if (vaccine.vaccineChecked) {
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.tinted_vaccine)
            } else {
                null
            }
        }
    }
}
