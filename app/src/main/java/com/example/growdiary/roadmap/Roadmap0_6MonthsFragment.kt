package com.example.growdiary.roadmap

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.growdiary.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

class Roadmap0_6MonthsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roadmap_0_6_bulan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Listener untuk Card Item 1 (tetap seperti sebelumnya)
        val cardItem1 = view.findViewById<CardView>(R.id.card_item_1)
        cardItem1.setOnClickListener {
            showPopupDialog()
        }

        // --- TAMBAHAN BARU: Listener untuk Card Item 2 ---
        val cardItem2 = view.findViewById<CardView>(R.id.card_item_2)
        cardItem2.setOnClickListener {
            showCarouselPopupDialog()
        }
    }

    private fun showPopupDialog() {
        // ... (kode fungsi ini tidak berubah)
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_milestone)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setCancelable(true)
        val closeButton = dialog.findViewById<ImageView>(R.id.popup_close_button)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        val setThumbnailButton = dialog.findViewById<Button>(R.id.set_thumbnail_button)
        setThumbnailButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    // --- FUNGSI BARU: Untuk menampilkan Pop-up dengan Carousel ---
    private fun showCarouselPopupDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_milestone_carousel)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setCancelable(true)

        val closeButton = dialog.findViewById<ImageView>(R.id.popup_close_button_carousel)
        val setThumbnailButton = dialog.findViewById<Button>(R.id.set_thumbnail_button_carousel)
        val viewPager = dialog.findViewById<ViewPager2>(R.id.popup_view_pager)
        val title = dialog.findViewById<TextView>(R.id.popup_title_carousel)
        val dotsIndicatorContainer = dialog.findViewById<LinearLayout>(R.id.dots_indicator_container)

        title.text = "Menoleh ketika\nDipanggil Namanya"

        val carouselItems = listOf(
            CarouselItem.Image(R.drawable.baby_sitting),
            CarouselItem.Image(R.drawable.baby_playing),
            CarouselItem.Image(R.drawable.baby_smile),
            CarouselItem.AddButton
        )
        val adapter = MilestoneCarouselAdapter(carouselItems)
        viewPager.adapter = adapter

        val dots = mutableListOf<ImageView>()
        val realDotCount = adapter.getRealItemCount()

        val dotActive = ContextCompat.getDrawable(requireContext(), R.drawable.dot_indicator_active)
        val dotInactive = ContextCompat.getDrawable(requireContext(), R.drawable.dot_indicator_inactive)

        for (i in 0 until realDotCount) {
            val imageView = ImageView(requireContext())
            imageView.setImageDrawable(dotInactive)

            val params = LinearLayout.LayoutParams(20, 20).apply {
                setMargins(10, 0, 10, 0)
            }
            imageView.layoutParams = params

            imageView.setOnClickListener {
                val currentMiddle = viewPager.currentItem
                val offsetToCenter = currentMiddle % realDotCount
                val targetPosition = currentMiddle - offsetToCenter + i
                viewPager.setCurrentItem(targetPosition, true)
            }

            dots.add(imageView)
            dotsIndicatorContainer.addView(imageView)
        }

        // --- PERBAIKAN DI SINI ---

        // 1. Daftarkan listener SEBELUM mengatur posisi awal.
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val realPosition = position % realDotCount
                for (i in 0 until realDotCount) {
                    dots[i].setImageDrawable(if (i == realPosition) dotActive else dotInactive)
                }
            }
        })

        // 2. Atur posisi awal di tengah.
        val startPosition = Int.MAX_VALUE / 2
        viewPager.setCurrentItem(startPosition - (startPosition % realDotCount), false)

        // 3. SECARA MANUAL, aktifkan dot pertama sebagai fallback.
        // Ini memastikan tampilan awal selalu benar.
        if (dots.isNotEmpty()) {
            dots[0].setImageDrawable(dotActive)
        }
        // -------------------------

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        setThumbnailButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}