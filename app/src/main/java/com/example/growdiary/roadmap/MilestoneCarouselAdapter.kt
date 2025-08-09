package com.example.growdiary.roadmap

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R

sealed class CarouselItem {
    data class ImageResource(val drawableRes: Int) : CarouselItem()
    data class ImageUri(val uri: Uri) : CarouselItem()
    object AddButton : CarouselItem()
}

class MilestoneCarouselAdapter(
    private val items: MutableList<CarouselItem>,
    private val onAddClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Callback untuk memberitahu Fragment bahwa item telah dihapus
    var onItemRemoved: ((Int) -> Unit)? = null

    companion object {
        private const val VIEW_TYPE_IMAGE_RESOURCE = 1
        private const val VIEW_TYPE_IMAGE_URI = 2
        private const val VIEW_TYPE_ADD = 3
    }

    fun getRealItemCount(): Int = items.size
    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun getItemViewType(position: Int): Int {
        // Handle case where items list might become empty temporarily during removal
        if (getRealItemCount() == 0) return VIEW_TYPE_ADD // Fallback if no real items

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
        val realPosition = position % getRealItemCount() // Dapatkan posisi nyata dalam daftar item

        when (holder) {
            is ImageViewHolder -> holder.bind(items[realPosition], realPosition) // Kirim item dan realPosition
            is AddViewHolder -> holder.bind()
        }
    }

    fun addImage(image: CarouselItem, addAtStart: Boolean) {
        if (image is CarouselItem.AddButton) return

        if (addAtStart) {
            items.add(0, image)
            notifyItemInserted(0)
        } else {
            val addButtonIndex = items.indexOf(CarouselItem.AddButton)
            if (addButtonIndex != -1) {
                items.add(addButtonIndex, image)
                notifyItemInserted(addButtonIndex)
            } else {
                items.add(image)
                notifyItemInserted(items.size - 1)
            }
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.carousel_image_view)
        private val deleteButton: ImageView = itemView.findViewById(R.id.carousel_delete_button)

        fun bind(item: CarouselItem, realPosition: Int) {
            when (item) {
                is CarouselItem.ImageResource -> {
                    imageView.setImageResource(item.drawableRes)
                    imageView.visibility = View.VISIBLE
                    deleteButton.visibility = View.VISIBLE // Tampilkan tombol delete
                }
                is CarouselItem.ImageUri -> {
                    imageView.setImageURI(item.uri)
                    imageView.visibility = View.VISIBLE
                    deleteButton.visibility = View.VISIBLE // Tampilkan tombol delete
                }
                is CarouselItem.AddButton -> {}
            }

            deleteButton.setOnClickListener {
                // Hapus item dari daftar
                items.removeAt(realPosition)
                notifyDataSetChanged() // Memberi tahu adapter untuk memperbarui seluruh tampilan
                onItemRemoved?.invoke(realPosition) // Beri tahu Fragment
            }
        }
    }

    inner class AddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addButton: ImageView = itemView.findViewById(R.id.carousel_add_button)

        fun bind() {
            addButton.setOnClickListener { onAddClick() }
        }
    }
}
