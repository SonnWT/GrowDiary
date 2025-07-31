package com.example.growdiary.roadmap

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.example.growdiary.R

class Roadmap0_6MonthsFragment : Fragment() {

    private lateinit var carouselAdapter: MilestoneCarouselAdapter
    private val carouselItems = mutableListOf<CarouselItem>()
    private lateinit var dotsIndicatorContainer: LinearLayout
    private lateinit var viewPager: ViewPager2

    // 1. Enum untuk menentukan perilaku penambahan gambar
    private enum class AddBehavior { AT_START, AT_END }
    // Variabel untuk menyimpan perilaku yang sedang diminta
    private var currentAddBehavior: AddBehavior = AddBehavior.AT_END

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val newImageItem = CarouselItem.ImageUri(it)
            // 4. Panggil addImage dengan perilaku yang sesuai dari state
            val addAtStart = currentAddBehavior == AddBehavior.AT_START
            carouselAdapter.addImage(newImageItem, addAtStart)

            setupDotsIndicator() // Buat ulang dot

            // Logika cerdas untuk pindah ke gambar yang baru ditambahkan
            val realCount = carouselAdapter.getRealItemCount()
            val targetRealPosition = if (addAtStart) {
                0 // Jika ditambah di awal, tujuannya index 0
            } else {
                // Gunakan coerceAtLeast(0) karena mungkin hanya ada tombol tambah
                (realCount - 2).coerceAtLeast(0)
            }

            val currentMiddle = viewPager.currentItem
            // Gunakan realCount.coerceAtLeast(1) untuk menghindari pembagian dengan nol
            val offsetToCenter = currentMiddle % realCount.coerceAtLeast(1)
            val targetPosition = currentMiddle - offsetToCenter + targetRealPosition
            viewPager.setCurrentItem(targetPosition, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roadmap_0_6_bulan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menggunakan fungsi bantu untuk menyiapkan setiap CardView
        setupCardView(
            view = view,
            cardId = R.id.card_item_1,
            thumbnailImageViewId = R.id.image_item_1,   // Pastikan ID ini ada di XML
            dialogTitle = "Bereaksi Terhadap\nSuara",
            initialCarouselItems = listOf(CarouselItem.AddButton), // Awalnya hanya tombol tambah
            addBehavior = AddBehavior.AT_START
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_2,
            thumbnailImageViewId = R.id.image_item_2,   // Pastikan ID ini ada di XML
            dialogTitle = "Menoleh ketika\nDipanggil Namanya",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.ImageResource(R.drawable.baby_playing),
                CarouselItem.ImageResource(R.drawable.baby_smile),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        // Anda bisa menambahkan setup untuk card_item_3, dst. di sini jika ada
        // dengan pola yang sama seperti di Roadmap6_12MonthsFragment.kt
        // Contoh:
        /*
        setupCardView(
            view = view,
            cardId = R.id.card_item_3,
            thumbnailImageViewId = R.id.image_item_3,
            dialogTitle = "Judul Card 3",
            initialCarouselItems = listOf(CarouselItem.ImageResource(R.drawable.some_image), CarouselItem.AddButton),
            addBehavior = AddBehavior.AT_END
        )
        */
    }

    // Fungsi bantu yang diperbarui tanpa parameter deleteButtonId
    private fun setupCardView(
        view: View,
        cardId: Int,
        thumbnailImageViewId: Int,
        dialogTitle: String,
        initialCarouselItems: List<CarouselItem>,
        addBehavior: AddBehavior // Tambahkan parameter addBehavior
    ) {
        val cardView: CardView? = view.findViewById(cardId)
        val thumbnailImageView: ImageView? = view.findViewById(thumbnailImageViewId)

        // Setel OnClickListener untuk CardView
        cardView?.setOnClickListener {
            showCarouselPopupDialog(
                title = dialogTitle,
                initialItems = initialCarouselItems,
                targetImageViewId = thumbnailImageViewId,
                addBehavior = addBehavior // Teruskan addBehavior
            )
        }
        // Tidak ada lagi logika untuk tombol delete di tampilan utama di sini
    }


    // Fungsi showCarouselPopupDialog sekarang tanpa targetDeleteButtonId
    private fun showCarouselPopupDialog(title: String, initialItems: List<CarouselItem>, targetImageViewId: Int, addBehavior: AddBehavior) {
        carouselItems.clear()
        carouselItems.addAll(initialItems)

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.popup_milestone_carousel)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        viewPager = dialog.findViewById(R.id.popup_view_pager)
        dotsIndicatorContainer = dialog.findViewById(R.id.dots_indicator_container)
        val popupTitle = dialog.findViewById<TextView>(R.id.popup_title_carousel)
        val closeButton = dialog.findViewById<ImageView>(R.id.popup_close_button_carousel)
        val setThumbnailButton = dialog.findViewById<Button>(R.id.set_thumbnail_button_carousel)

        popupTitle.text = title
        closeButton.setOnClickListener { dialog.dismiss() }

        carouselAdapter = MilestoneCarouselAdapter(carouselItems) {
            // Sebelum membuka galeri, simpan perilaku yang diminta ke state
            currentAddBehavior = addBehavior
            pickImageLauncher.launch("image/*")
        }
        // PENTING: Atur callback onItemRemoved di sini!
        carouselAdapter.onItemRemoved = { realPosition ->
            // Ketika item dihapus dari carousel, perbarui indikator titik
            setupDotsIndicator()
            // Logika untuk menghapus gambar thumbnail utama jika carousel menjadi kosong
            // setelah penghapusan gambar dari carousel popup
            if (carouselAdapter.getRealItemCount() == 1 && carouselItems.firstOrNull() is CarouselItem.AddButton) {
                val targetImageView = requireView().findViewById<ImageView>(targetImageViewId)
                targetImageView?.setImageDrawable(null) // Hapus gambar thumbnail utama
                // Jika ada tombol delete di tampilan utama, Anda bisa menyembunyikannya di sini
                // Namun, sesuai permintaan, kita tidak mengelola tombol delete utama dari sini.
            }
        }
        viewPager.adapter = carouselAdapter

        setThumbnailButton.setOnClickListener {
            // Periksa apakah ada item yang bisa dipilih selain tombol tambah
            if (carouselAdapter.getRealItemCount() <= 0) {
                return@setOnClickListener
            }

            val currentPosition = viewPager.currentItem
            val realPosition = currentPosition % carouselAdapter.getRealItemCount()
            val selectedItem = carouselItems[realPosition] // Menggunakan carouselItems karena realPosition merujuk ke indeks di sini

            val targetImageView = requireView().findViewById<ImageView>(targetImageViewId)
            // Tidak ada lagi referensi targetDeleteButton di sini

            when (selectedItem) {
                is CarouselItem.ImageResource -> {
                    targetImageView?.setImageResource(selectedItem.drawableRes)
                }
                is CarouselItem.ImageUri -> {
                    targetImageView?.setImageURI(selectedItem.uri)
                }
                is CarouselItem.AddButton -> {
                    // Jika tombol tambah terpilih, jangan lakukan apa-apa atau berikan feedback
                    return@setOnClickListener
                }
            }

            targetImageView?.scaleType = ImageView.ScaleType.CENTER_CROP
            targetImageView?.setPadding(0, 0, 0, 0)

            dialog.dismiss()
        }

        setupDotsIndicator()

        val startPosition = Int.MAX_VALUE / 2
        val realCount = carouselAdapter.getRealItemCount()
        if (realCount > 0) {
            viewPager.setCurrentItem(startPosition - (startPosition % realCount), false)
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
            }
        })

        dialog.show()
    }

    private fun setupDotsIndicator() {
        dotsIndicatorContainer.removeAllViews()
        val realDotCount = carouselAdapter.getRealItemCount()
        if (realDotCount == 0) return

        for (i in 0 until realDotCount) {
            val imageView = ImageView(requireContext())
            val params = LinearLayout.LayoutParams(20, 20).apply { setMargins(10, 0, 10, 0) }
            imageView.layoutParams = params

            imageView.setOnClickListener {
                val currentMiddle = viewPager.currentItem
                val offsetToCenter = currentMiddle % carouselAdapter.getRealItemCount()
                val targetPosition = currentMiddle - offsetToCenter + i
                viewPager.setCurrentItem(targetPosition, true)
            }
            dotsIndicatorContainer.addView(imageView)
        }
        updateDots(viewPager.currentItem)
    }

    private fun updateDots(currentViewPagerPosition: Int) {
        val realCount = carouselAdapter.getRealItemCount()
        if (realCount == 0 || dotsIndicatorContainer.childCount != realCount) return

        val realPosition = currentViewPagerPosition % realCount
        for (i in 0 until realCount) {
            val dot = dotsIndicatorContainer.getChildAt(i) as? ImageView
            dot?.setImageResource(if (i == realPosition) R.drawable.dot_indicator_active else R.drawable.dot_indicator_inactive)
        }
    }
}