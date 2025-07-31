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
import androidx.cardview.widget.CardView // Pastikan CardView diimpor
import androidx.viewpager2.widget.ViewPager2
import com.example.growdiary.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Roadmap6_12MonthsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Roadmap6_12MonthsFragment : Fragment() {
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
            // Pastikan realCount > 1 untuk menghindari masalah indeks jika hanya ada tombol tambah
            val targetRealPosition = if (addAtStart) {
                0 // Jika ditambah di awal, tujuannya index 0
            } else {
                // Jika ditambah di akhir, tujuannya gambar terakhir sebelum tombol '+'
                // Gunakan coerceAtLeast(1) untuk menghindari error jika realCount menjadi 1
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
        // Mengembang layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_roadmap6_12_months, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menggunakan fungsi bantu untuk menyiapkan setiap CardView dan tombol delete-nya
        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_3,
            deleteButtonId = R.id.delete_button_item_3,
            thumbnailImageViewId = R.id.image_item3, // ID sesuai XML
            dialogTitle = "Memanggil\nMama Papa",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.ImageResource(R.drawable.baby_playing),
                CarouselItem.ImageResource(R.drawable.baby_smile),
                CarouselItem.AddButton
            )
        )

        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_4,
            deleteButtonId = R.id.delete_button_item_4,
            thumbnailImageViewId = R.id.image_item4, // ID sesuai XML
            dialogTitle = "Menunjuk untuk\nMeminta sesuatu",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.ImageResource(R.drawable.baby_playing),
                CarouselItem.AddButton
            )
        )

        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_5,
            deleteButtonId = R.id.delete_button_item_5,
            thumbnailImageViewId = R.id.image_item5, // ID sesuai XML
            dialogTitle = "Melambaikan Tangan\n(Bye - Bye)",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_smile),
                CarouselItem.ImageResource(R.drawable.baby_playing),
                CarouselItem.AddButton
            )
        )

        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_6,
            deleteButtonId = R.id.delete_button_item_6,
            thumbnailImageViewId = R.id.image_item6, // ID sesuai XML
            dialogTitle = "Berdiri Sendiri\nTanpa Dibantu",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_terlentang),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            )
        )

        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_7,
            deleteButtonId = R.id.delete_button_item_7,
            thumbnailImageViewId = R.id.image_item7, // ID sesuai XML
            dialogTitle = "Berdiri Dipegang",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_smile),
                CarouselItem.ImageResource(R.drawable.baby_terlentang),
                CarouselItem.AddButton
            )
        )

        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_8,
            deleteButtonId = R.id.delete_button_item_8,
            thumbnailImageViewId = R.id.image_item8, // ID sesuai XML
            dialogTitle = "Mengucapkan Suku\nKata Bersambung",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.ImageResource(R.drawable.baby_smile),
                CarouselItem.AddButton
            )
        )

        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_9,
            deleteButtonId = R.id.delete_button_item_9,
            thumbnailImageViewId = R.id.image_item9, // ID sesuai XML
            dialogTitle = "Memegang 2 Mainan\ndengan Tangan\nKanan-Kiri",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_hold_toy),
                CarouselItem.ImageResource(R.drawable.baby_playing),
                CarouselItem.AddButton
            )
        )

        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_10,
            deleteButtonId = R.id.delete_button_item_10,
            thumbnailImageViewId = R.id.image_item10, // ID sesuai XML
            dialogTitle = "Mengucapkan Satu Suku\n Kata: Ba, Pa, Ma",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            )
        )

        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_11,
            deleteButtonId = R.id.delete_button_item_11,
            thumbnailImageViewId = R.id.image_item11, // ID sesuai XML
            dialogTitle = "Mencari Benda Jatuh/Disembunyikan",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            )
        )

        setupCardViewAndButton(
            view = view,
            cardId = R.id.card_item_12,
            deleteButtonId = R.id.delete_button_item_12,
            thumbnailImageViewId = R.id.image_item12, // ID sesuai XML
            dialogTitle = "Duduk Tanpa Dipegang",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            )
        )
    }

    // Fungsi bantu untuk menghindari pengulangan kode di onViewCreated
    private fun setupCardViewAndButton(
        view: View,
        cardId: Int,
        deleteButtonId: Int,
        thumbnailImageViewId: Int,
        dialogTitle: String,
        initialCarouselItems: List<CarouselItem>
    ) {
        val cardView: CardView? = view.findViewById(cardId)
        val deleteButton: ImageView? = view.findViewById(deleteButtonId)
        val thumbnailImageView: ImageView? = view.findViewById(thumbnailImageViewId)

        // Setel OnClickListener untuk CardView
        cardView?.setOnClickListener {
            showCarouselPopupDialog(
                title = dialogTitle,
                initialItems = initialCarouselItems,
                targetImageViewId = thumbnailImageViewId,
                targetDeleteButtonId = deleteButtonId, // Kirim ID tombol hapus ke dialog
                addBehavior = AddBehavior.AT_END
            )
        }

        // Setel OnClickListener untuk tombol hapus
        deleteButton?.setOnClickListener {
            thumbnailImageView?.setImageDrawable(null) // Hapus gambar dari ImageView
            // Atau jika ada gambar placeholder default, setel ke itu:
            // thumbnailImageView?.setImageResource(R.drawable.default_placeholder)
            // Jika Anda memiliki src default di XML, ini akan kembali ke sana.

            deleteButton.visibility = View.GONE // Sembunyikan tombol hapus
            // Opsional: Reset data terkait jika diperlukan, misalnya di database atau shared preferences
            // Logika untuk menghapus gambar dari state (misal dari daftar milistone yang disimpan)
        }
    }

    // 2. Tambahkan parameter 'targetDeleteButtonId' pada fungsi ini
    private fun showCarouselPopupDialog(title: String, initialItems: List<CarouselItem>, targetImageViewId: Int, targetDeleteButtonId: Int, addBehavior: AddBehavior) {
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
            // Opsional: Jika gambar yang dihapus adalah yang sedang ditampilkan sebagai thumbnail utama,
            // Anda mungkin ingin menghapus thumbnail utama juga atau menggantinya dengan placeholder.
            // Ini akan membutuhkan logika yang lebih kompleks untuk melacak thumbnail mana yang sedang ditampilkan.
            // Untuk saat ini, kita hanya memperbarui indikator titik.
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
            val targetDeleteButton = requireView().findViewById<ImageView>(targetDeleteButtonId) // Dapatkan referensi tombol hapus

            when (selectedItem) {
                is CarouselItem.ImageResource -> {
                    targetImageView?.setImageResource(selectedItem.drawableRes)
                    targetDeleteButton?.visibility = View.VISIBLE // Tampilkan tombol hapus
                }
                is CarouselItem.ImageUri -> {
                    targetImageView?.setImageURI(selectedItem.uri)
                    targetDeleteButton?.visibility = View.VISIBLE // Tampilkan tombol hapus
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
