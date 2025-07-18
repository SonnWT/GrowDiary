////package com.example.growdiary.home
////
////import android.os.Bundle
////import androidx.fragment.app.Fragment
////import android.view.LayoutInflater
////import android.view.View
////import android.view.ViewGroup
////import com.example.growdiary.R
////
////// TODO: Rename parameter arguments, choose names that match
////// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
////private const val ARG_PARAM1 = "param1"
////private const val ARG_PARAM2 = "param2"
////
/////**
//// * A simple [Fragment] subclass.
//// * Use the [HomeFragment.newInstance] factory method to
//// * create an instance of this fragment.
//// */
////class HomeFragment : Fragment() {
////    // TODO: Rename and change types of parameters
////    private var param1: String? = null
////    private var param2: String? = null
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        arguments?.let {
////            param1 = it.getString(ARG_PARAM1)
////            param2 = it.getString(ARG_PARAM2)
////        }
////    }
////
////    override fun onCreateView(
////        inflater: LayoutInflater, container: ViewGroup?,
////        savedInstanceState: Bundle?
////    ): View? {
////        // Inflate the layout for this fragment
////        return inflater.inflate(R.layout.fragment_home, container, false)
////    }
////
////    companion object {
////        /**
////         * Use this factory method to create a new instance of
////         * this fragment using the provided parameters.
////         *
////         * @param param1 Parameter 1.
////         * @param param2 Parameter 2.
////         * @return A new instance of fragment HomeFragment.
////         */
////        // TODO: Rename and change types and number of parameters
////        @JvmStatic
////        fun newInstance(param1: String, param2: String) =
////            HomeFragment().apply {
////                arguments = Bundle().apply {
////                    putString(ARG_PARAM1, param1)
////                    putString(ARG_PARAM2, param2)
////                }
////            }
////    }
////}
//
//package com.example.growdiary.home
//
//import android.graphics.Color
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.growdiary.R
//import com.github.mikephil.charting.charts.LineChart
//import com.github.mikephil.charting.components.XAxis
//import com.github.mikephil.charting.data.Entry
//import com.github.mikephil.charting.data.LineData
//import com.github.mikephil.charting.data.LineDataSet
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
//import com.google.android.material.imageview.ShapeableImageView
//
//// Hapus atau abaikan ARG_PARAM1 dan ARG_PARAM2 jika tidak digunakan lagi
//// private const val ARG_PARAM1 = "param1"
//// private const val ARG_PARAM2 = "param2"
//
//class HomeFragment : Fragment() {
//
//    private lateinit var heightChart: LineChart
//    private lateinit var weightChart: LineChart
//    private lateinit var diaryRecyclerView: RecyclerView
//
//    // Jika Anda masih menggunakan param1/param2, biarkan saja.
//    // Jika tidak, Anda bisa menghapus bagian ini jika tidak lagi diperlukan.
//    // private var param1: String? = null
//    // private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // arguments?.let {
//        //     param1 = it.getString(ARG_PARAM1)
//        //     param2 = it.getString(ARG_PARAM2)
//        // }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_home, container, false)
//
//        // Inisialisasi View dari layout yang di-inflate
//        heightChart = view.findViewById(R.id.height_chart)
//        weightChart = view.findViewById(R.id.weight_chart)
//        diaryRecyclerView = view.findViewById(R.id.diary_recycler_view)
//
//        // Set gambar profil
//        val profileImage: ShapeableImageView = view.findViewById(R.id.profile_image)
//        profileImage.setImageResource(R.drawable.profile_joseph)
//
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // Lakukan setup chart dan RecyclerView di sini setelah View dipastikan sudah dibuat
//        setupHeightChart()
//        setupWeightChart()
//        setupDiaryRecyclerView()
//    }
//
//    private fun setupHeightChart() {
//        val entries = ArrayList<Entry>().apply {
//            add(Entry(0f, 50f)) // Bulan 0, 50 cm
//            add(Entry(3f, 58f)) // Bulan 3, 58 cm
//            add(Entry(6f, 62f)) // Bulan 6, 62 cm
//            add(Entry(9f, 68f)) // Bulan 9, 68 cm
//            add(Entry(12f, 72f)) // Bulan 12, 72 cm
//            add(Entry(15f, 75f)) // Bulan 15, 75 cm
//        }
//
//        val dataSet = LineDataSet(entries, "Height (cm)").apply {
//            color = requireContext().getColor(R.color.orange) // Menggunakan warna 'orange' dari colors.xml
//            valueTextColor = Color.BLACK
//            setCircleColor(requireContext().getColor(R.color.orange))
//            circleRadius = 5f
//            setDrawValues(false) // Jangan tampilkan nilai di atas titik
//            lineWidth = 3f
//            mode = LineDataSet.Mode.CUBIC_BEZIER // Membuat garis lebih halus
//        }
//
//        val lineData = LineData(dataSet)
//        heightChart.data = lineData
//
//        // Kustomisasi X-axis
//        val xAxis = heightChart.xAxis.apply {
//            position = XAxis.XAxisPosition.BOTTOM
//            setDrawGridLines(false)
//            setDrawAxisLine(true)
//            granularity = 1f // Hanya angka bulat
//            valueFormatter = IndexAxisValueFormatter(arrayOf("0", "3", "6", "9", "12", "15"))
//            labelCount = 6 // Pastikan 6 label ditampilkan
//            axisMinimum = 0f
//            axisMaximum = 15f
//        }
//
//        // Kustomisasi Y-axis
//        heightChart.axisLeft.apply {
//            axisMinimum = 0f
//            axisMaximum = 80f
//            setDrawGridLines(true)
//        }
//        heightChart.axisRight.isEnabled = false // Matikan Y-axis kanan
//
//        // Kustomisasi chart lainnya
//        heightChart.description.isEnabled = false // Tanpa teks deskripsi
//        heightChart.legend.isEnabled = false // Tanpa legenda
//        heightChart.setTouchEnabled(false) // Nonaktifkan interaksi sentuh jika tidak diperlukan
//        heightChart.setDrawGridBackground(false)
//        heightChart.invalidate() // Refresh chart
//    }
//
//    private fun setupWeightChart() {
//        val entries = ArrayList<Entry>().apply {
//            add(Entry(0f, 3.5f)) // Bulan 0, 3.5 kg
//            add(Entry(3f, 6.0f)) // Bulan 3, 6.0 kg
//            add(Entry(6f, 7.5f)) // Bulan 6, 7.5 kg
//            add(Entry(9f, 8.5f)) // Bulan 9, 8.5 kg
//            add(Entry(12f, 9.5f)) // Bulan 12, 9.5 kg
//            add(Entry(15f, 10.2f)) // Bulan 15, 10.2 kg
//        }
//
//        val dataSet = LineDataSet(entries, "Weight (kg)").apply {
//            color = requireContext().getColor(R.color.green_chart) // Menggunakan warna 'green_chart' dari colors.xml
//            valueTextColor = Color.BLACK
//            setCircleColor(requireContext().getColor(R.color.green_chart))
//            circleRadius = 5f
//            setDrawValues(false)
//            lineWidth = 3f
//            mode = LineDataSet.Mode.CUBIC_BEZIER
//        }
//
//        val lineData = LineData(dataSet)
//        weightChart.data = lineData
//
//        // Kustomisasi X-axis
//        val xAxis = weightChart.xAxis.apply {
//            position = XAxis.XAxisPosition.BOTTOM
//            setDrawGridLines(false)
//            setDrawAxisLine(true)
//            granularity = 1f
//            valueFormatter = IndexAxisValueFormatter(arrayOf("0", "3", "6", "9", "12", "15"))
//            labelCount = 6
//            axisMinimum = 0f
//            axisMaximum = 15f
//        }
//
//        // Kustomisasi Y-axis
//        weightChart.axisLeft.apply {
//            axisMinimum = 0f
//            axisMaximum = 12f // Sesuaikan maks berdasarkan rentang berat yang diharapkan
//            setDrawGridLines(true)
//        }
//        weightChart.axisRight.isEnabled = false
//
//        // Kustomisasi chart lainnya
//        weightChart.description.isEnabled = false
//        weightChart.legend.isEnabled = false
//        weightChart.setTouchEnabled(false)
//        weightChart.setDrawGridBackground(false)
//        weightChart.invalidate()
//    }
//
//    private fun setupDiaryRecyclerView() {
//        diaryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        val diaryItems = ArrayList<DiaryItem>().apply {
//            add(DiaryItem(R.drawable.joseph_merangkak, "Joseph Merangkak"))
//            // Jika Anda hanya memiliki satu gambar, Anda bisa mengulanginya atau menambahkan gambar lain
//            add(DiaryItem(R.drawable.joseph_merangkak, "Joseph Bermain"))
//            add(DiaryItem(R.drawable.joseph_merangkak, "Joseph Tidur"))
//            add(DiaryItem(R.drawable.joseph_merangkak, "Joseph Makan"))
//        }
//
//        val adapter = DiaryAdapter(diaryItems)
//        diaryRecyclerView.adapter = adapter
//    }
//
//    // Kelas data untuk item Diary (buat ini di luar kelas Fragment atau di file terpisah jika ingin lebih rapi)
//    data class DiaryItem(val imageResId: Int, val description: String)
//
//    // Adapter untuk RecyclerView (bisa di dalam atau di luar kelas Fragment)
//    class DiaryAdapter(private val diaryList: List<DiaryItem>) :
//        RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
//            return DiaryViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
//            val item = diaryList[position]
//            holder.imageView.setImageResource(item.imageResId)
//            holder.descriptionTextView.text = item.description
//        }
//
//        override fun getItemCount(): Int = diaryList.size
//
//        class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            val imageView: ImageView = itemView.findViewById(R.id.diary_image)
//            val descriptionTextView: TextView = itemView.findViewById(R.id.diary_description)
//        }
//    }
//    // Hapus companion object NewInstance jika tidak digunakan lagi
//    // companion object {
//    //     @JvmStatic
//    //     fun newInstance(param1: String, param2: String) =
//    //         HomeFragment().apply {
//    //             arguments = Bundle().apply {
//    //                 putString(ARG_PARAM1, param1)
//    //                 putString(ARG_PARAM2, param2)
//    //             }
//    //         }
//    // }
//}

// app/src/main/kotlin/com/example/growdiary/home/HomeFragment.kt
package com.example.growdiary.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growdiary.R
import com.google.android.material.imageview.ShapeableImageView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis // Import YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class HomeFragment : Fragment() {

    private lateinit var heightChart: LineChart
    private lateinit var weightChart: LineChart
    private lateinit var diaryRecyclerView: RecyclerView
    private lateinit var chartHorizontalScrollView: android.widget.HorizontalScrollView
    private lateinit var carouselDotsContainer: LinearLayout
    private lateinit var textChartTitle: TextView // Inisialisasi TextView untuk judul

    private val numberOfCharts = 2
    private var currentChartIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        heightChart = view.findViewById(R.id.height_chart)
        weightChart = view.findViewById(R.id.weight_chart)
        diaryRecyclerView = view.findViewById(R.id.diary_recycler_view)
        chartHorizontalScrollView = view.findViewById(R.id.chart_horizontal_scroll_view)
        carouselDotsContainer = view.findViewById(R.id.carousel_dots_container)
        textChartTitle = view.findViewById(R.id.text_chart_title) // Inisialisasi di sini

        val profileImage: ShapeableImageView = view.findViewById(R.id.profile_image)
        profileImage.setImageResource(R.drawable.profile_joseph)

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
    }

    private fun setupHeightChart() {
        val entries = ArrayList<Entry>().apply {
            add(Entry(0f, 45f)) // Bulan 0, 45 cm (mulai lebih rendah)
            add(Entry(3f, 55f)) // Bulan 3, 55 cm
            add(Entry(6f, 65f)) // Bulan 6, 65 cm
            add(Entry(9f, 70f)) // Bulan 9, 70 cm
            add(Entry(12f, 75f)) // Bulan 12, 75 cm
            add(Entry(15f, 78f)) // Bulan 15, 78 cm
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

        val xAxis = heightChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            setDrawAxisLine(true)
            granularity = 3f
            valueFormatter = IndexAxisValueFormatter(arrayOf("0", "3", "6", "9", "12", "15"))
            labelCount = 6
            axisMinimum = 0f
            axisMaximum = 16f
            textColor = Color.BLACK
            setAvoidFirstLastClipping(true)
        }

        val yAxisLeft = heightChart.axisLeft.apply {
            axisMinimum = 40f
            axisMaximum = 85f // Sesuaikan rentang Y
            setDrawGridLines(true)
            textColor = Color.BLACK
            labelCount = 6
        }
        heightChart.axisRight.isEnabled = false // Matikan Y-axis kanan

        // Menambahkan Label Sumbu Y secara manual (jika library tidak mendukung label langsung)
        // Atau Anda bisa menggunakan custom formatter untuk label sumbu Y jika MPAndroidChart mendukungnya.
        // Untuk contoh ini, kita akan asumsikan sumbu Y adalah CM
        // Di MPAndroidChart, label sumbu Y sudah otomatis jika AxisMinimum/AxisMaximum diatur

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
            add(Entry(0f, 2.5f)) // Bulan 0, 2.5 kg (mulai lebih rendah)
            add(Entry(3f, 5.0f)) // Bulan 3, 5.0 kg
            add(Entry(6f, 6.5f)) // Bulan 6, 6.5 kg
            add(Entry(9f, 8.0f)) // Bulan 9, 8.0 kg
            add(Entry(12f, 9.0f)) // Bulan 12, 9.0 kg
            add(Entry(15f, 10.0f)) // Bulan 15, 10.0 kg
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

        val xAxis = weightChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            setDrawAxisLine(true)
            granularity = 3f
            valueFormatter = IndexAxisValueFormatter(arrayOf("0", "3", "6", "9", "12", "15"))
            labelCount = 6
            axisMinimum = 0f
            axisMaximum = 16f
            textColor = Color.BLACK
            setAvoidFirstLastClipping(true)
        }

        val yAxisLeft = weightChart.axisLeft.apply {
            axisMinimum = 2f
            axisMaximum = 11f // Sesuaikan rentang Y
            setDrawGridLines(true)
            textColor = Color.BLACK
            labelCount = 6
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
            add(DiaryItem(R.drawable.joseph_merangkak, "Joseph Bermain")) // Contoh: pakai gambar yang sama
            add(DiaryItem(R.drawable.joseph_merangkak, "Joseph Tidur"))
            add(DiaryItem(R.drawable.joseph_merangkak, "Joseph Makan"))
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
        val chartWidthPx = resources.getDimensionPixelSize(R.dimen.chart_width_with_margin)

        chartHorizontalScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            // Menentukan grafik mana yang sedang aktif
            // Jika scrollX kurang dari setengah lebar grafik pertama, maka grafik pertama aktif
            // Jika tidak, grafik kedua aktif
            val newIndex = if (scrollX < (chartWidthPx / 2)) 0 else 1
            if (newIndex != currentChartIndex) {
                currentChartIndex = newIndex
                updateCarouselDots(currentChartIndex)
                updateChartTitle(currentChartIndex)
            }
        }
    }

    private fun updateChartTitle(index: Int) {
        // Sesuaikan judul grafik berdasarkan indeks
        textChartTitle.text = when (index) {
            0 -> "Height Progress Over Time"
            1 -> "Weight Progress Over Time"
            else -> ""
        }

//        textChartTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
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