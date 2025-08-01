package com.example.growdiary.vaccine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R

class VaccineToDoAdapter(private val vaccineList: List<Vaccine>) :
    RecyclerView.Adapter<VaccineToDoAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val vaccineToDoName: TextView = itemView.findViewById(R.id.vaccineToDoName)
        val vaccineToDoDose: TextView = itemView.findViewById(R.id.vaccineToDoDose)
        val vaccineToDoPriority: TextView = itemView.findViewById(R.id.vaccineToDoPriority)
        val vaccineToDoDate: TextView = itemView.findViewById(R.id.vaccineToDoDate)
        val vaccineToDoImage: ImageView = itemView.findViewById(R.id.vaccineToDoImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VaccineToDoAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vaccine_to_do,
            parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VaccineToDoAdapter.MyViewHolder, position: Int) {
        val vaccine = vaccineList[position]

        holder.vaccineToDoName.text = vaccine.vaccineName
        holder.vaccineToDoDose.text = vaccine.vaccineDose
        holder.vaccineToDoPriority.text = vaccine.vaccinePriority
        holder.vaccineToDoDate.text = vaccine.vaccineDate

        if(vaccine.vaccineImage != null)
        {
            holder.vaccineToDoImage.setImageResource(vaccine.vaccineImage!!)
        }
    }

    override fun getItemCount(): Int {
        return vaccineList.size
    }
}