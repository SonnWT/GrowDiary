package com.example.growdiary.vaccine
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R

class VaccineHistoryAdapter (private val vaccineList: List<Vaccine>, private val onItemClicked: (Int) -> Unit) :
    RecyclerView.Adapter<VaccineHistoryAdapter.MyViewHolder>() {

//    var onItemClick : ((VaccineDetail) -> Unit)? = null
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vaccineHistoryName: TextView = itemView.findViewById(R.id.vaccineHistoryName)
        val vaccineHistoryImage: ImageView = itemView.findViewById(R.id.vaccineHistoryImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VaccineHistoryAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vaccine_history,
            parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VaccineHistoryAdapter.MyViewHolder, position: Int) {

        val vaccine = vaccineList[position]
        holder.vaccineHistoryName.text = vaccine.vaccineName
        if(vaccine.vaccineImage != null){
            holder.vaccineHistoryImage.setImageResource(vaccine.vaccineImage!!)
        }

        holder.itemView.setOnClickListener {
            onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return vaccineList.size
    }


}