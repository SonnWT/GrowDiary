package com.example.growdiary.roadmap

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R

// Sealed Class tidak perlu diubah
sealed class CarouselItem {
    data class ImageResource(val drawableRes: Int) : CarouselItem()
    data class ImageUri(val uri: Uri) : CarouselItem()
    object AddButton : CarouselItem()
}

class MilestoneCarouselAdapter(
    private val items: MutableList<CarouselItem>,
    private val onAddClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_IMAGE_RESOURCE = 1
        private const val VIEW_TYPE_IMAGE_URI = 2
        private const val VIEW_TYPE_ADD = 3
    }

    fun getRealItemCount(): Int = items.size
    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun getItemViewType(position: Int): Int {
        return when (items[position % getRealItemCount()]) {
            is CarouselItem.ImageResource -> VIEW_TYPE_IMAGE_RESOURCE
            is CarouselItem.ImageUri -> VIEW_TYPE_IMAGE_URI
            is CarouselItem.AddButton -> VIEW_TYPE_ADD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_IMAGE_RESOURCE, VIEW_TYPE_IMAGE_URI -> {
                val view = inflater.inflate(R.layout.item_carousel_image, parent, false)
                ImageViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.item_carousel_add, parent, false)
                AddViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val realPosition = position % getRealItemCount()
        val item = items[realPosition]

        when (holder) {
            is ImageViewHolder -> holder.bind(item)
            is AddViewHolder -> holder.bind()
        }
    }

    // --- FUNGSI addImage DIPERBARUI DENGAN PARAMETER ---
    fun addImage(image: CarouselItem, addAtStart: Boolean) {
        if (image is CarouselItem.AddButton) return

        if (addAtStart) {
            // Perilaku untuk card_item_1: Tambahkan di awal
            items.add(0, image)
            notifyItemInserted(0)
        } else {
            // Perilaku untuk card_item_2: Tambahkan di akhir (sebelum tombol +)
            val insertPosition = items.size - 1
            items.add(insertPosition, image)
            notifyItemInserted(insertPosition)
        }
    }
    // ----------------------------------------------------

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.carousel_image_view)

        fun bind(item: CarouselItem) {
            when (item) {
                is CarouselItem.ImageResource -> {
                    imageView.setImageResource(item.drawableRes)
                }
                is CarouselItem.ImageUri -> {
                    imageView.setImageURI(item.uri)
                }
                is CarouselItem.AddButton -> { /* do nothing */ }
            }
        }
    }

    inner class AddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.setOnClickListener { onAddClick() }
        }
    }
}