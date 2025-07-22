package com.example.growdiary.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.button.MaterialButton
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

import androidx.navigation.fragment.findNavController

// Pastikan mengimpor DiaryAdapter dan DiaryItem dari package yang benar
// Contoh: import com.your_app_name.adapters.DiaryAdapter (jika di folder adapters)
// Contoh: import com.your_app_name.models.DiaryItem (jika di folder models)

class HomeFragment : Fragment(), DiaryAdapter.OnItemClickListener { // Implementasikan interface dengan benar
    private lateinit var diaryAdapter: DiaryAdapter
    private lateinit var diaryRecyclerView: RecyclerView // Hanya satu deklarasi ini yang dibutuhkan

    // Deklarasi view dari fragment_home.xml
    private lateinit var heightChart: LineChart
    private lateinit var weightChart: LineChart // Nama variabel ini sekarang unik
    private lateinit var chartHorizontalScrollView: android.widget.HorizontalScrollView
    private lateinit var carouselDotsContainer: LinearLayout
    private lateinit var textChartTitle: TextView

    private lateinit var profileImage: ShapeableImageView
    private lateinit var textName: TextView
    private lateinit var textAge: TextView
    private lateinit var profileInfoContainer: LinearLayout

    // Variabel untuk menyimpan referensi pop-up
    private var profileSelectionPopupView: View? = null
    private var overlayBackgroundView: View? = null

    private val numberOfCharts = 2
    private var currentChartIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // --- Inisialisasi View ---
        heightChart = view.findViewById(R.id.height_chart)
        weightChart = view.findViewById(R.id.weight_chart) // Menggunakan nama variabel yang sama dengan ID XML
        diaryRecyclerView = view.findViewById(R.id.diary_recycler_view)
        chartHorizontalScrollView = view.findViewById(R.id.chart_horizontal_scroll_view)
        carouselDotsContainer = view.findViewById(R.id.carousel_dots_container)
        textChartTitle = view.findViewById(R.id.text_chart_title)

        profileImage = view.findViewById(R.id.profile_image)
        textName = view.findViewById(R.id.text_name)
        textAge = view.findViewById(R.id.text_age)
        profileInfoContainer = view.findViewById(R.id.profile_info_container)

        // Set profil default saat fragment pertama kali dibuat
        updateCurrentProfile("Joseph", R.drawable.profile_joseph, "1 tahun 3 bulan")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Setup Komponen UI ---
        setupHeightChart()
        setupWeightChart()
        setupDiaryRecyclerView() // Panggil setup RecyclerView di sini
        setupCarouselDots()
        setupChartScrollViewListener()
        updateChartTitle(0)

        // Atur OnClickListener untuk area profil utama agar memunculkan pop-up
        profileInfoContainer.setOnClickListener {
            val rootViewGroup = requireActivity().findViewById<ViewGroup>(android.R.id.content)
            showProfileSelectionPopup(rootViewGroup)
        }
    }

    // --- Fungsi untuk menampilkan pop-up pemilihan profil ---
    private fun showProfileSelectionPopup(rootViewGroup: ViewGroup) {
        if (profileSelectionPopupView == null) {
            profileSelectionPopupView = LayoutInflater.from(context).inflate(R.layout.layout_profile_selection_popup, rootViewGroup, false)

            val btnClose = profileSelectionPopupView?.findViewById<ImageView>(R.id.btn_close_dialog)
            val btnManageProfile = profileSelectionPopupView?.findViewById<MaterialButton>(R.id.btn_manage_profile)
            val profileJosephPopup = profileSelectionPopupView?.findViewById<LinearLayout>(R.id.profile_joseph_in_popup)
            val profileChristopher = profileSelectionPopupView?.findViewById<LinearLayout>(R.id.profile_christopher)
            val profileMichael = profileSelectionPopupView?.findViewById<LinearLayout>(R.id.profile_michael)
            val profileGandhi = profileSelectionPopupView?.findViewById<LinearLayout>(R.id.profile_gandhi)
            val popupRootLayout = profileSelectionPopupView?.findViewById<ConstraintLayout>(R.id.popup_root_layout)
            val profileSelectionCard = profileSelectionPopupView?.findViewById<androidx.cardview.widget.CardView>(R.id.profile_selection_card)
            overlayBackgroundView = profileSelectionPopupView?.findViewById<View>(R.id.overlay_background_view)

            btnClose?.setOnClickListener { hideProfileSelectionPopup() }
            popupRootLayout?.setOnClickListener { hideProfileSelectionPopup() }
            profileSelectionCard?.setOnClickListener { /* Do nothing */ }
            btnManageProfile?.setOnClickListener {
                hideProfileSelectionPopup()
                findNavController().navigate(R.id.editProfileFragment)
            }
            profileJosephPopup?.setOnClickListener { updateCurrentProfile("Joseph", R.drawable.profile_joseph, "1 tahun 3 bulan"); hideProfileSelectionPopup() }
            profileChristopher?.setOnClickListener { updateCurrentProfile("Christopher Setiawan", R.drawable.profile_joseph, "1 tahun 1 bulan"); hideProfileSelectionPopup() }
            profileMichael?.setOnClickListener { updateCurrentProfile("Michael Onasis Hasri", R.drawable.profile_joseph, "1 tahun 5 bulan"); hideProfileSelectionPopup() }
            profileGandhi?.setOnClickListener { updateCurrentProfile("Gandhi Winata Susilo", R.drawable.profile_joseph, "1 tahun 0 bulan"); hideProfileSelectionPopup() }

            rootViewGroup.addView(profileSelectionPopupView)
        }
        profileSelectionPopupView?.visibility = View.VISIBLE
    }

    private fun hideProfileSelectionPopup() {
        profileSelectionPopupView?.visibility = View.GONE
    }

    private fun updateCurrentProfile(profileName: String, profileImageResId: Int, profileAge: String) {
        profileImage.setImageResource(profileImageResId)
        textName.text = profileName
        textAge.text = profileAge
    }

    // --- Setup Chart MPAndroidChart ---
    private fun setupHeightChart() {
        val entries = ArrayList<Entry>().apply {
            add(Entry(10f, 74.0f))
            add(Entry(11f, 75.0f))
            add(Entry(12f, 76.0f))
            add(Entry(13f, 77.0f))
            add(Entry(14f, 78.0f))
            add(Entry(15f, 79.0f))
        }

        val dataSet = LineDataSet(entries, "Tinggi Badan").apply {
            color = ContextCompat.getColor(requireContext(), R.color.merah)
            valueTextColor = Color.BLACK
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.merah))
            circleRadius = 5f
            setDrawValues(false)
            lineWidth = 3f
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(true)
            fillColor = ContextCompat.getColor(requireContext(), R.color.chart_fill_red)
            fillAlpha = 50
        }

        val lineData = LineData(dataSet)
        heightChart.data = lineData

        val xAxisLabelsMap = entries.associate { it.x.roundToInt() to it.x.roundToInt().toString() }

        val xAxis = heightChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            setDrawAxisLine(true)
            granularity = 1f
            textColor = Color.BLACK
            setAvoidFirstLastClipping(true)

            valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return xAxisLabelsMap[value.roundToInt()] ?: ""
                }
            }

            labelCount = entries.size
            setCenterAxisLabels(false)
            isGranularityEnabled = true

            axisMinimum = entries.first().x - 0.5f
            axisMaximum = entries.last().x + 0.5f
        }

        val yAxisLeft = heightChart.axisLeft.apply {
            axisMinimum = 70f
            axisMaximum = 80f
            setDrawGridLines(true)
            textColor = Color.BLACK
            labelCount = 6
        }
        heightChart.axisRight.isEnabled = false

        heightChart.description.isEnabled = false
        heightChart.legend.isEnabled = false
        heightChart.setTouchEnabled(true)
        heightChart.setPinchZoom(false)
        heightChart.setDoubleTapToZoomEnabled(false)
        heightChart.setDrawGridBackground(false)
        heightChart.invalidate()
    }

    private fun setupWeightChart() {
        val entries = ArrayList<Entry>().apply {
            add(Entry(10f, 8.5f))
            add(Entry(11f, 8.7f))
            add(Entry(12f, 9.0f))
            add(Entry(13f, 9.3f))
            add(Entry(14f, 9.5f))
            add(Entry(15f, 9.7f))
        }

        val dataSet = LineDataSet(entries, "Berat Badan").apply {
            color = ContextCompat.getColor(requireContext(), R.color.aqua)
            valueTextColor = Color.BLACK
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.aqua))
            circleRadius = 5f
            setDrawValues(false)
            lineWidth = 3f
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(true)
            fillColor = ContextCompat.getColor(requireContext(), R.color.chart_fill_blue)
            fillAlpha = 50
        }

        val lineData = LineData(dataSet)
        weightChart.data = lineData

        val xAxisLabelsMap = entries.associate { it.x.roundToInt() to it.x.roundToInt().toString() }

        val xAxis = weightChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            setDrawAxisLine(true)
            granularity = 1f
            textColor = Color.BLACK
            setAvoidFirstLastClipping(true)

            valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return xAxisLabelsMap[value.roundToInt()] ?: ""
                }
            }
            labelCount = entries.size
            setCenterAxisLabels(false)
            isGranularityEnabled = true

            axisMinimum = entries.first().x - 0.5f
            axisMaximum = entries.last().x + 0.5f
        }

        val yAxisLeft = weightChart.axisLeft.apply {
            axisMinimum = 8.0f
            axisMaximum = 10.0f
            setDrawGridLines(true)
            textColor = Color.BLACK
            labelCount = 5
        }
        weightChart.axisRight.isEnabled = false

        weightChart.description.isEnabled = false
        weightChart.legend.isEnabled = false
        weightChart.setTouchEnabled(true)
        weightChart.setPinchZoom(false)
        weightChart.setDoubleTapToZoomEnabled(false)
        weightChart.setDrawGridBackground(false)
        weightChart.invalidate()
    }

    // --- Setup RecyclerView ---
    private fun setupDiaryRecyclerView() {
        diaryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val diaryItems = ArrayList<DiaryItem>().apply {
            add(DiaryItem(R.drawable.joseph_merangkak, "Joseph Merangkak"))
            add(DiaryItem(R.drawable.main, "Joseph Main"))
            add(DiaryItem(R.drawable.bicara, "Joseph Bicara"))
            add(DiaryItem(R.drawable.jalan, "Joseph Jalan"))
        }

        // Inisialisasi properti kelas `diaryAdapter` dan pass `this` sebagai listener
        diaryAdapter = DiaryAdapter(diaryItems, this)
        diaryRecyclerView.adapter = diaryAdapter
    }

    // --- Setup Carousel Dots ---
    private fun setupCarouselDots() {
        carouselDotsContainer.removeAllViews()
        for (i in 0 until numberOfCharts) {
            val dot = View(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.dot_size),
                    resources.getDimensionPixelSize(R.dimen.dot_size)
                ).apply {
                    val margin = resources.getDimensionPixelSize(R.dimen.dot_spacing)
                    setMargins(margin, 0, margin, 0)
                }
                setBackgroundResource(if (i == 0) R.drawable.dot_active else R.drawable.dot_inactive)
            }
            carouselDotsContainer.addView(dot)
        }
        updateCarouselDots(0)
    }

    private fun updateCarouselDots(activeIndex: Int) {
        for (i in 0 until carouselDotsContainer.childCount) {
            val dot = carouselDotsContainer.getChildAt(i)
            dot.setBackgroundResource(if (i == activeIndex) R.drawable.dot_active else R.drawable.dot_inactive)
        }
    }

    private fun setupChartScrollViewListener() {
        val chartWidthDp = 350
        val chartMarginEndDp = 8
        val density = resources.displayMetrics.density
        val chartWidthPx = ((chartWidthDp + chartMarginEndDp) * density).toInt()

        chartHorizontalScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            val newIndex = if (scrollX < (chartWidthPx / 2)) 0 else 1
            if (newIndex != currentChartIndex) {
                currentChartIndex = newIndex
                updateCarouselDots(currentChartIndex)
                updateChartTitle(currentChartIndex)
            }
        }
    }

    private fun updateChartTitle(index: Int) {
        textChartTitle.text = when (index) {
            0 -> "Height Progress Over Time"
            1 -> "Weight Progress Over Time"
            else -> ""
        }
    }

    // --- Implementasi OnItemClickListener dari DiaryAdapter ---
    override fun onItemClick(position: Int) {
        // Di sini Anda bisa menggunakan `position` jika Anda ingin menavigasi ke Fragment yang berbeda
        // berdasarkan posisi item yang diklik.
        // Untuk contoh ini, kita akan selalu ke Diary1Fragment.
        findNavController().navigate(R.id.action_homeFragment_to_diary1)

        // Contoh jika Anda ingin menavigasi ke Fragment yang berbeda berdasarkan data item:
        // val selectedDiaryItem = (diaryRecyclerView.adapter as DiaryAdapter).diaryList[position]
        // when (selectedDiaryItem.description) {
        //     "Joseph Merangkak" -> findNavController().navigate(R.id.action_homeFragment_to_diary1)
        //     "Joseph Main" -> findNavController().navigate(R.id.action_homeFragment_to_diary2) // Misalnya ke Diary2Fragment
        //     // ... dan seterusnya
        // }
    }
}

/*
Penting: Pindahkan `DiaryItem` dan `DiaryAdapter` ke file Kotlin terpisah
untuk praktik terbaik dalam struktur proyek Android.

Contoh struktur file:
- app/src/main/java/com/example/growdiary/home/HomeFragment.kt
- app/src/main/java/com/example/growdiary/home/DiaryItem.kt  <-- Pindahkan DiaryItem ke sini
- app/src/main/java/com/example/growdiary/home/DiaryAdapter.kt <-- Pindahkan DiaryAdapter ke sini
*/