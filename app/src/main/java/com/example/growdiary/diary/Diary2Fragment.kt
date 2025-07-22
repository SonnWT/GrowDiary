package com.example.growdiary.diary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.growdiary.R

class Diary2Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_diary1, container, false)

        val trash = view.findViewById<ImageView>(R.id.trash)
        val editText = view.findViewById<TextView>(R.id.isi_diary)
        val imageSlider = view.findViewById<ImageSlider>(R.id.slider)
        val silang = view.findViewById<ImageView>(R.id.silang)

        val slideModels = ArrayList<SlideModel>()
        slideModels.add(SlideModel(R.drawable.main))
        slideModels.add(SlideModel(R.drawable.main2))
        slideModels.add(SlideModel(R.drawable.tambah))


        imageSlider.setImageList(slideModels, true)

        val diaryText = """
    <p>Hari ini, tanggal <b>18 Juli 2025</b>, Mama melihat sesuatu yang sederhana tapi sangat membahagiakan. Untuk <b>pertama kalinya</b>, <font color='#000000'>kamu bermain sendiri</font>! 🥺💖</p>
    
    <p>Kamu duduk di lantai, menggulingkan mobil-mobilan kecil sambil tertawa pelan 🚗💥. Tanganku ingin merekam semuanya, tapi hati Mama lebih sibuk menyimpan momen itu dalam-dalam. Dunia kecilmu mulai terbuka dan kamu tampak begitu penasaran 🌟😄.</p>
    
    <p>Terima kasih ya, Nak, sudah membiarkan Mama menyaksikan keajaiban kecilmu hari ini. Semoga langkah kecil dan tawa lucumu ini jadi awal dari banyak petualangan menyenangkan. 🎈👶💞</p>
""".trimIndent()

        editText.text = HtmlCompat.fromHtml(diaryText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val back_arrow = view.findViewById<ImageView>(R.id.arrow_back_diary1)

        back_arrow.setOnClickListener {
            findNavController().navigateUp()
        }

        silang.setOnClickListener {
            showCustomPhotoDeleteDialog {

            }
        }

        trash.setOnClickListener {
            showCustomDeleteDialog {

            }
        }

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                val selectedImage = slideModels[position]

                // Jika yang diklik adalah gambar "tambah"
                if (selectedImage.imagePath == R.drawable.tambah) {
                    val newImage = SlideModel(R.drawable.main3)

                    // Hapus semua tambah
                    slideModels.removeAll { it.imagePath == R.drawable.tambah }

                    // Tambahkan gambar baru di awal
                    slideModels.add(2, newImage)

                    // Tambah kembali tombol tambah di akhir
                    slideModels.add(SlideModel(R.drawable.tambah))

                    imageSlider.setImageList(slideModels, true)
                }
            }
        })

        return view
    }

    @SuppressLint("MissingInflatedId")
    private fun showCustomDeleteDialog(onDeleteConfirmed: () -> Unit) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_confirm, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val btnDelete = dialogView.findViewById<Button>(R.id.delete_btn)
        val btnCancel = dialogView.findViewById<Button>(R.id.cancel_btn)

        btnDelete.setOnClickListener {
            findNavController().navigateUp()
            alertDialog.dismiss()
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showCustomPhotoDeleteDialog(onDeleteConfirmed: () -> Unit) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_confirm_photo, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val btnDelete = dialogView.findViewById<Button>(R.id.delete_btn)
        val btnCancel = dialogView.findViewById<Button>(R.id.cancel_btn)

        btnDelete.setOnClickListener {
            alertDialog.dismiss()
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}