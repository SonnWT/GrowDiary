package com.example.growdiary.roadmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R

sealed class CarouselItem {
    data class Image(val drawableRes: Int) : CarouselItem()
    object AddButton : CarouselItem()
}

class MilestoneCarouselAdapter(private val items: List<CarouselItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_IMAGE = 1
        private const val VIEW_TYPE_ADD = 2
    }

    // Helper function untuk mendapatkan jumlah item asli
    fun getRealItemCount(): Int {
        return items.size
    }

    override fun getItemCount(): Int {
        // Laporkan jumlah item yang sangat besar untuk efek "tak terbatas"
        return Int.MAX_VALUE
    }

    override fun getItemViewType(position: Int): Int {
        // Gunakan modulo untuk mendapatkan posisi item yang sebenarnya
        val realPosition = position % getRealItemCount()
        return when (items[realPosition]) {
            is CarouselItem.Image -> VIEW_TYPE_IMAGE
            is CarouselItem.AddButton -> VIEW_TYPE_ADD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_IMAGE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_carousel_image, parent, false)
            ImageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_carousel_add, parent, false)
            AddViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Gunakan modulo lagi di sini
        val realPosition = position % getRealItemCount()
        if (holder is ImageViewHolder) {
            holder.bind(items[realPosition] as CarouselItem.Image)
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.carousel_image_view)
        fun bind(item: CarouselItem.Image) {
            imageView.setImageResource(item.drawableRes)
        }
    }

    inner class AddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}