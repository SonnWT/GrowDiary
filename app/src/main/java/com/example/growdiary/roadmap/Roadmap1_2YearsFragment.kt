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
import android.content.Context
import com.example.growdiary.R

class Roadmap1_2YearsFragment : Fragment() {
    private lateinit var carouselAdapter: MilestoneCarouselAdapter
    private val carouselItems = mutableListOf<CarouselItem>()
    private lateinit var dotsIndicatorContainer: LinearLayout
    private lateinit var viewPager: ViewPager2

    // Untuk melacak progres:
    private var progressListener: RoadmapProgressListener? = null
    private val milestoneStatus: MutableMap<Int, Boolean> = mutableMapOf() // Map: ImageViewId -> isCompleted (has custom image)
    private val TOTAL_MILESTONES_1_2_YEARS = 15 // Updated total milestones

    // Enum untuk menentukan perilaku penambahan gambar
    private enum class AddBehavior { AT_START, AT_END }
    // Variabel untuk menyimpan perilaku yang sedang diminta
    private var currentAddBehavior: AddBehavior = AddBehavior.AT_END

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val newImageItem = CarouselItem.ImageUri(it)
            // Panggil addImage dengan perilaku yang sesuai dari state
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Dapatkan referensi ke listener dari parentFragment
        if (parentFragment is RoadmapProgressListener) {
            progressListener = parentFragment as RoadmapProgressListener
        } else if (context is RoadmapProgressListener) {
            // Jika Fragment ini di-host langsung oleh Activity
            progressListener = context as RoadmapProgressListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        progressListener = null // Kosongkan listener saat fragment dilepas
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengembang layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_roadmap1_2_years, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize milestone statuses
        milestoneStatus[R.id.image_item_3] = false
        milestoneStatus[R.id.image_item_4] = false
        milestoneStatus[R.id.image_item_5] = false
        milestoneStatus[R.id.image_item_6] = false
        milestoneStatus[R.id.image_item_7] = false
        milestoneStatus[R.id.image_item_8] = false
        milestoneStatus[R.id.image_item_9] = false
        milestoneStatus[R.id.image_item_10] = false
        milestoneStatus[R.id.image_item_11] = false
        milestoneStatus[R.id.image_item_12] = false
        milestoneStatus[R.id.image_item_13] = false // This will be set to false by setupCardView
        milestoneStatus[R.id.image_item_14] = false
        milestoneStatus[R.id.image_item_15] = false
        milestoneStatus[R.id.image_item_16] = false
        milestoneStatus[R.id.image_item_17] = false

        setupCardView(
            view = view,
            cardId = R.id.card_item_3,
            thumbnailImageViewId = R.id.image_item_3,
            dialogTitle = "Menumpuk 4 - 5 Kubus",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.ImageResource(R.drawable.baby_playing),
                CarouselItem.ImageResource(R.drawable.baby_smile),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_4,
            thumbnailImageViewId = R.id.image_item_4,
            dialogTitle = "Menyebutkan 1 benda / gambar",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.ImageResource(R.drawable.baby_playing),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_5,
            thumbnailImageViewId = R.id.image_item_5,
            dialogTitle = "Menunjukkan 6 Bagian tubuh\nyang Ditanyakan",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_smile),
                CarouselItem.ImageResource(R.drawable.baby_playing),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_6,
            thumbnailImageViewId = R.id.image_item_6,
            dialogTitle = "Menggabungkan beberapa kata",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_terlentang),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_7,
            thumbnailImageViewId = R.id.image_item_7,
            dialogTitle = "Menunjukan 2 Benda / Gambar\nsesuai Namanya",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_smile),
                CarouselItem.ImageResource(R.drawable.baby_terlentang),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_8,
            thumbnailImageViewId = R.id.image_item_8,
            dialogTitle = "Berbicara 6 Kata",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.ImageResource(R.drawable.baby_smile),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_9,
            thumbnailImageViewId = R.id.image_item_9,
            dialogTitle = "Berlari",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_hold_toy),
                CarouselItem.ImageResource(R.drawable.baby_playing),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_10,
            thumbnailImageViewId = R.id.image_item_10,
            dialogTitle = "Menumpuk 2 kubus",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_11,
            thumbnailImageViewId = R.id.image_item_11,
            dialogTitle = "Bebicara 3 kata",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_12,
            thumbnailImageViewId = R.id.image_item_12,
            dialogTitle = "Minum dari Cangkir",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        // *** MODIFIED ITEM 13 SETUP ***
        setupCardView(
            view = view,
            cardId = R.id.card_item_13,
            thumbnailImageViewId = R.id.image_item_13,
            dialogTitle = "Mencoret - coret", // Changed title to match the new XML
            initialCarouselItems = listOf(CarouselItem.AddButton), // Changed to start empty
            addBehavior = AddBehavior.AT_START // Changed to add new images at the start
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_14,
            thumbnailImageViewId = R.id.image_item_14,
            dialogTitle = "Berjalan",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_15,
            thumbnailImageViewId = R.id.image_item_15,
            dialogTitle = "Berbicara 1 kata",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_16,
            thumbnailImageViewId = R.id.image_item_16,
            dialogTitle = "Memasukkan Kubus ke Gelas",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        setupCardView(
            view = view,
            cardId = R.id.card_item_17,
            thumbnailImageViewId = R.id.image_item_17,
            dialogTitle = "Menirukan Kegiatan:\n" +
                    "Menyapu dan Mengepel",
            initialCarouselItems = listOf(
                CarouselItem.ImageResource(R.drawable.baby_see_hand),
                CarouselItem.ImageResource(R.drawable.baby_sitting),
                CarouselItem.AddButton
            ),
            addBehavior = AddBehavior.AT_END
        )

        updateOverallProgress()
    }

    private fun setupCardView(
        view: View,
        cardId: Int,
        thumbnailImageViewId: Int,
        dialogTitle: String,
        initialCarouselItems: List<CarouselItem>,
        addBehavior: AddBehavior
    ) {
        val cardView: CardView? = view.findViewById(cardId)
        cardView?.setOnClickListener {
            showCarouselPopupDialog(
                title = dialogTitle,
                initialItems = initialCarouselItems,
                targetImageViewId = thumbnailImageViewId,
                addBehavior = addBehavior
            )
        }

        // Set initial milestone status based on whether there are pre-existing images
        if (initialCarouselItems.any { it !is CarouselItem.AddButton }) {
            milestoneStatus[thumbnailImageViewId] = true
        } else {
            milestoneStatus[thumbnailImageViewId] = false
        }
    }

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
            currentAddBehavior = addBehavior
            pickImageLauncher.launch("image/*")
        }
        carouselAdapter.onItemRemoved = {
            setupDotsIndicator()
            // Check if the only remaining item is the add button
            if (carouselItems.all { it is CarouselItem.AddButton }) {
                val targetImageView = requireView().findViewById<ImageView>(targetImageViewId)
                targetImageView?.setImageResource(R.drawable.ic_lock) // Revert to lock icon
                targetImageView?.scaleType = ImageView.ScaleType.CENTER_INSIDE
                val padding = (20 * resources.displayMetrics.density).toInt()
                targetImageView?.setPadding(padding, padding, padding, padding)

                milestoneStatus[targetImageViewId] = false // Update status
                updateOverallProgress()
            }
        }
        viewPager.adapter = carouselAdapter

        setThumbnailButton.setOnClickListener {
            val realItems = carouselItems.filter { it !is CarouselItem.AddButton }
            if (realItems.isEmpty()) {
                // Can't set a thumbnail if there are no images.
                return@setOnClickListener
            }

            val currentPosition = viewPager.currentItem
            val realPosition = currentPosition % realItems.size
            val selectedItem = realItems[realPosition]

            val targetImageView = requireView().findViewById<ImageView>(targetImageViewId)

            when (selectedItem) {
                is CarouselItem.ImageResource -> {
                    targetImageView?.setImageResource(selectedItem.drawableRes)
                    milestoneStatus[targetImageViewId] = true
                    updateOverallProgress()
                }
                is CarouselItem.ImageUri -> {
                    targetImageView?.setImageURI(selectedItem.uri)
                    milestoneStatus[targetImageViewId] = true
                    updateOverallProgress()
                }
                else -> {
                    // This else branch handles the AddButton case and makes the 'when' exhaustive.
                    // We don't do anything here because AddButton is filtered out from realItems.
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

    private fun updateOverallProgress() {
        val completedCount = milestoneStatus.count { it.value }
        val totalCount = milestoneStatus.size
        progressListener?.onProgressUpdated(completedCount, totalCount)
    }
}
