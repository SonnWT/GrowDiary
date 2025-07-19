package com.example.growdiary.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout // Penting: Import ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.button.MaterialButton // Penting: Import MaterialButton
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

class HomeFragment : Fragment() {

    // Deklarasi view dari fragment_home.xml
    private lateinit var heightChart: LineChart
    private lateinit var weightChart: LineChart
    private lateinit var diaryRecyclerView: RecyclerView
    private lateinit var chartHorizontalScrollView: android.widget.HorizontalScrollView
    private lateinit var carouselDotsContainer: LinearLayout
    private lateinit var textChartTitle: TextView

    private lateinit var profileImage: ShapeableImageView
    private lateinit var textName: TextView
    private lateinit var textAge: TextView
    private lateinit var profileInfoContainer: LinearLayout // LinearLayout yang berisi profil Joseph di atas

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

        // Inisialisasi view dari fragment_home.xml
        heightChart = view.findViewById(R.id.height_chart)
        weightChart = view.findViewById(R.id.weight_chart)
        diaryRecyclerView = view.findViewById(R.id.diary_recycler_view)
        chartHorizontalScrollView = view.findViewById(R.id.chart_horizontal_scroll_view)
        carouselDotsContainer = view.findViewById(R.id.carousel_dots_container)
        textChartTitle = view.findViewById(R.id.text_chart_title)

        profileImage = view.findViewById(R.id.profile_image)
        textName = view.findViewById(R.id.text_name)
        textAge = view.findViewById(R.id.text_age)
        profileInfoContainer = view.findViewById(R.id.profile_info_container)

        // Set profil default saat fragment pertama kali dibuat
        // Anda bisa mengganti ini dengan data dari preferensi pengguna atau database
        updateCurrentProfile("Joseph", R.drawable.profile_joseph, "1 tahun 3 bulan")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeightChart()
        setupWeightChart()
        setupDiaryRecyclerView()
        setupCarouselDots()
        setupChartScrollViewListener()
        updateChartTitle(0)

        // Atur OnClickListener untuk area profil utama agar memunculkan pop-up
        profileInfoContainer.setOnClickListener {
            // Kita akan menambahkan pop-up ke root View dari Activity
            // Ini membantu agar pop-up bisa menutupi seluruh layar di bawah status bar
            val rootViewGroup = requireActivity().findViewById<ViewGroup>(android.R.id.content)
            showProfileSelectionPopup(rootViewGroup)
        }
    }

    // Fungsi untuk menampilkan pop-up pemilihan profil
    private fun showProfileSelectionPopup(rootViewGroup: ViewGroup) {
        // Jika pop-up belum di-inflate, lakukan inisialisasi
        if (profileSelectionPopupView == null) {
            profileSelectionPopupView = LayoutInflater.from(context).inflate(R.layout.layout_profile_selection_popup, rootViewGroup, false)

            // Inisialisasi elemen-elemen di dalam pop-up
            val btnClose = profileSelectionPopupView?.findViewById<ImageView>(R.id.btn_close_dialog)
            val btnManageProfile = profileSelectionPopupView?.findViewById<MaterialButton>(R.id.btn_manage_profile)
            val profileJosephPopup = profileSelectionPopupView?.findViewById<LinearLayout>(R.id.profile_joseph_in_popup)
            val profileChristopher = profileSelectionPopupView?.findViewById<LinearLayout>(R.id.profile_christopher)
            val profileMichael = profileSelectionPopupView?.findViewById<LinearLayout>(R.id.profile_michael)
            val profileGandhi = profileSelectionPopupView?.findViewById<LinearLayout>(R.id.profile_gandhi)
            val popupRootLayout = profileSelectionPopupView?.findViewById<ConstraintLayout>(R.id.popup_root_layout)
            val profileSelectionCard = profileSelectionPopupView?.findViewById<androidx.cardview.widget.CardView>(R.id.profile_selection_card)
            overlayBackgroundView = profileSelectionPopupView?.findViewById<View>(R.id.overlay_background_view)

            // Listener untuk tombol close (X)
            btnClose?.setOnClickListener {
                hideProfileSelectionPopup()
            }

            // Listener untuk area overlay gelap di luar CardView (untuk menutup pop-up)
            popupRootLayout?.setOnClickListener {
                hideProfileSelectionPopup()
            }

            // Mencegah klik pada CardView (isi pop-up) dari menembus ke overlay gelap di bawahnya
            profileSelectionCard?.setOnClickListener {
                // Do nothing, so the click doesn't propagate to the root layout and close the popup
            }

            // Listener untuk tombol "Manage Joseph's Profile"
            btnManageProfile?.setOnClickListener {
                hideProfileSelectionPopup() // Sembunyikan pop-up terlebih dahulu
                findNavController().navigate(R.id.editProfileFragment) // Navigasi ke ProfileDetailFragment
            }

            // Listener untuk setiap item profil di pop-up
            profileJosephPopup?.setOnClickListener {
                updateCurrentProfile("Joseph", R.drawable.profile_joseph, "1 tahun 3 bulan")
                hideProfileSelectionPopup()
            }

            profileChristopher?.setOnClickListener {
                updateCurrentProfile("Christopher Setiawan", R.drawable.profile_joseph, "1 tahun 1 bulan")
                hideProfileSelectionPopup()
            }

            profileMichael?.setOnClickListener {
                updateCurrentProfile("Michael Onasis Hasri", R.drawable.profile_joseph, "1 tahun 5 bulan")
                hideProfileSelectionPopup()
            }

            profileGandhi?.setOnClickListener {
                updateCurrentProfile("Gandhi Winata Susilo", R.drawable.profile_joseph, "1 tahun 0 bulan")
                hideProfileSelectionPopup()
            }

            // Tambahkan pop-up ke root view Activity
            rootViewGroup.addView(profileSelectionPopupView)
        }

        profileSelectionPopupView?.visibility = View.VISIBLE
    }

    // Fungsi untuk menyembunyikan pop-up
    private fun hideProfileSelectionPopup() {
        profileSelectionPopupView?.visibility = View.GONE
    }

    // Fungsi terpisah untuk memperbarui informasi profil yang sedang aktif di HomeFragment
    private fun updateCurrentProfile(profileName: String, profileImageResId: Int, profileAge: String) {
        profileImage.setImageResource(profileImageResId)
        textName.text = profileName
        textAge.text = profileAge
        // Anda mungkin ingin memuat ulang data chart atau diary di sini jika bergantung pada profil yang dipilih
        // Misalnya: loadChartData(profileName)
        // dan: loadDiaryData(profileName)
    }

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

    private fun setupDiaryRecyclerView() {
        diaryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val diaryItems = ArrayList<DiaryItem>().apply {
            add(DiaryItem(R.drawable.joseph_merangkak, "Joseph Merangkak"))
            add(DiaryItem(R.drawable.main, "Joseph Main"))
            add(DiaryItem(R.drawable.bicara, "Joseph Bicara"))
            add(DiaryItem(R.drawable.jalan, "Joseph Jalan"))
        }

        val adapter = DiaryAdapter(diaryItems)
        diaryRecyclerView.adapter = adapter
    }

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

    data class DiaryItem(val imageResId: Int, val description: String)

    class DiaryAdapter(private val diaryList: List<DiaryItem>) :
        RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
            return DiaryViewHolder(view)
        }

        override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
            val item = diaryList[position]
            holder.imageView.setImageResource(item.imageResId)
            holder.descriptionTextView.text = item.description
        }

        override fun getItemCount(): Int = diaryList.size

        class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.diary_image)
            val descriptionTextView: TextView = itemView.findViewById(R.id.diary_description)
        }
    }
}