package com.example.growdiary

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.growdiary.diary.DiaryFragment
import com.example.growdiary.home.HomeFragment
import com.example.growdiary.profile.ProfileFragment // Jika ini adalah Profile utama Anda
import com.example.growdiary.roadmap.RoadmapFragment
import com.example.growdiary.vaccine.VaccineFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.growdiary.firstkid.FirstKidFragment // Penting: import FirstKidFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navIndicators: List<View>
    private lateinit var fabRoadmap: FloatingActionButton
    private lateinit var bottomNav : MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- Inisialisasi Tombol & Indikator ---
        val navHome: LinearLayout = findViewById(R.id.nav_home)
        val navVaccine: LinearLayout = findViewById(R.id.nav_vaccine)
        val navDiary: LinearLayout = findViewById(R.id.nav_diary)
        val navProfile: LinearLayout = findViewById(R.id.nav_profile)
        fabRoadmap = findViewById(R.id.fab_roadmap)
        bottomNav = findViewById(R.id.bottom_nav_card)

        val indicatorHome: View = findViewById(R.id.indicator_home)
        val indicatorVaccine: View = findViewById(R.id.indicator_vaccine)
        val indicatorDiary: View = findViewById(R.id.indicator_diary)
        val indicatorProfile: View = findViewById(R.id.indicator_profile)

        navIndicators = listOf(indicatorHome, indicatorVaccine, indicatorDiary, indicatorProfile)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.diary1, R.id.newDiary, R.id.diary2, R.id.firstKidFragment, R.id.loginFragment,
                     R.id.registerFragment, R.id.diaryBacaFragment -> { // TAMBAHKAN firstKidFragment DI SINI
                    bottomNav.visibility = View.GONE
                    fabRoadmap.visibility = View.GONE
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE
                    fabRoadmap.visibility = View.VISIBLE
                    // Set indikator aktif saat kembali ke fragmen navigasi utama
                    when (destination.id) {
                        R.id.homeFragment -> setActiveIndicator(indicatorHome)
                        R.id.vaccineFragment -> setActiveIndicator(indicatorVaccine)
                        R.id.vaccineDetailFragment -> setActiveIndicator(indicatorVaccine)
                        R.id.diaryFragment -> setActiveIndicator(indicatorDiary)
                        R.id.diaryLuar1Fragment -> setActiveIndicator(indicatorDiary)
                        R.id.diaryLuar2Fragment -> setActiveIndicator(indicatorDiary)
                        R.id.diaryTambahFragment -> setActiveIndicator(indicatorDiary)
                        R.id.profileFragment -> setActiveIndicator(indicatorProfile) // Pastikan ini merujuk ke ID yang benar
                        R.id.roadmapFragment -> { resetIndicators(); setFabState(true) }
                        else -> resetIndicators()
                    }
                }
            }
        }

        // --- OnClickListeners dengan NavController ---
        navHome.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }

        navVaccine.setOnClickListener {
            navController.navigate(R.id.vaccineFragment)
        }

        fabRoadmap.setOnClickListener {
            navController.navigate(R.id.roadmapFragment)
        }

        navDiary.setOnClickListener {
            navController.navigate(R.id.diaryFragment)
        }

        navProfile.setOnClickListener {
            navController.navigate(R.id.profileFragment) // Pastikan ini adalah ID fragment profile yang ingin ditampilkan oleh bottom nav
        }

        // Tidak perlu `if (savedInstanceState == null)` di sini
        // karena `app:startDestination` di `nav_graph.xml` sudah menangani navigasi awal.
        // Jika Anda ingin mengontrol indikator awal (yang seharusnya disembunyikan untuk FirstKidFragment),
        // itu sudah diatur di addOnDestinationChangedListener.
    }

    private fun resetIndicators() {
        for (indicator in navIndicators) {
            indicator.visibility = View.GONE
        }
        setFabState(false)
    }

    private fun setActiveIndicator(activeIndicator: View) {
        resetIndicators()
        activeIndicator.visibility = View.VISIBLE
    }

    private fun setFabState(isActive: Boolean) {
        if (isActive) {
            fabRoadmap.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.fab_active_color))
            fabRoadmap.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
        } else {
            fabRoadmap.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            fabRoadmap.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.fab_inactive_tint))
        }
    }
}