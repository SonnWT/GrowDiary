package com.example.growdiary

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.growdiary.diary.DiaryFragment
import com.example.growdiary.home.HomeFragment
import com.example.growdiary.profile.ProfileFragment
import com.example.growdiary.roadmap.RoadmapFragment
import com.example.growdiary.vaccine.VaccineFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var navIndicators: List<View>
    private lateinit var fabRoadmap: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- Inisialisasi Tombol & Indikator ---
        val navHome: LinearLayout = findViewById(R.id.nav_home)
        val navVaccine: LinearLayout = findViewById(R.id.nav_vaccine)
        val navDiary: LinearLayout = findViewById(R.id.nav_diary)
        val navProfile: LinearLayout = findViewById(R.id.nav_profile)
        fabRoadmap = findViewById(R.id.fab_roadmap) // Inisialisasi FAB di sini

        val indicatorHome: View = findViewById(R.id.indicator_home)
        val indicatorVaccine: View = findViewById(R.id.indicator_vaccine)
        val indicatorDiary: View = findViewById(R.id.indicator_diary)
        val indicatorProfile: View = findViewById(R.id.indicator_profile)

        val bottomNav : MaterialCardView = findViewById(R.id.bottom_nav_card)

        navIndicators = listOf(indicatorHome, indicatorVaccine, indicatorDiary, indicatorProfile)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.diary1, R.id.newDiary, R.id.diary2 -> {
                    bottomNav.visibility = View.GONE
                    fabRoadmap.visibility = View.GONE
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE
                    fabRoadmap.visibility = View.VISIBLE
                }
            }
        }

        // --- OnClickListeners dengan NavController ---
        navHome.setOnClickListener {
            navController.navigate(R.id.homeFragment)
            setActiveIndicator(indicatorHome)
        }

        navVaccine.setOnClickListener {
            navController.navigate(R.id.vaccineFragment)
            setActiveIndicator(indicatorVaccine)
        }

        fabRoadmap.setOnClickListener {
            navController.navigate(R.id.roadmapFragment)
            resetIndicators()
            setFabState(true)
        }

        navDiary.setOnClickListener {
            navController.navigate(R.id.diaryFragment)
            setActiveIndicator(indicatorDiary)
        }

        navProfile.setOnClickListener {
            navController.navigate(R.id.profileFragment)
            setActiveIndicator(indicatorProfile)
        }

        // --- Awal ---
        if (savedInstanceState == null) {
            navController.navigate(R.id.homeFragment)
            setActiveIndicator(indicatorHome)
        }
    }

    private fun resetIndicators() {
        for (indicator in navIndicators) {
            indicator.visibility = View.GONE
        }
        setFabState(false) // Pastikan FAB kembali ke state normal saat indikator lain aktif
    }

    private fun setActiveIndicator(activeIndicator: View) {
        resetIndicators()
        activeIndicator.visibility = View.VISIBLE
    }

    // Opsi jika Anda mau mengganti file PNG
    private fun setFabState(isActive: Boolean) {
        if (isActive) {
            // State Aktif
            fabRoadmap.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.fab_active_color))
            // Ganti gambar dengan versi putih
            fabRoadmap.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        } else {
            // State Normal
            fabRoadmap.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            // Ganti gambar dengan versi oranye
            fabRoadmap.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.fab_inactive_tint))// Pastikan nama file ini benar
        }
    }

}