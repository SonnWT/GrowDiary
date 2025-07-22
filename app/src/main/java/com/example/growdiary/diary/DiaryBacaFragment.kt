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

class DiaryBacaFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_diary_baca, container, false)

        val trash = view.findViewById<ImageView>(R.id.trash)
        val editText = view.findViewById<TextView>(R.id.isi_diary)
        val imageSlider = view.findViewById<ImageSlider>(R.id.slider)
        val silang = view.findViewById<ImageView>(R.id.silang)

        val slideModels = ArrayList<SlideModel>()
        slideModels.add(SlideModel(R.drawable.baca))
        slideModels.add(SlideModel(R.drawable.tambah))


        imageSlider.setImageList(slideModels, true)


        val diaryText = """
    <p>Hari ini, tanggal <b>22 Juli 2025</b>, Mama ingin menangis lagi... kali ini bukan karena kamu merangkak, tapi karena kamu <b>membaca untuk pertama kalinya</b>! 😭📖</p>
    
    <p>Kita lagi duduk bareng, kamu pegang buku cerita bergambar kesukaanmu. Tiba-tiba kamu tunjuk satu kata dan bilang, "<font color='#000000'><b>k-u-c-i-n-g</b>, kucing!</font>" — dan benar! Mama sampai diam sebentar, antara kaget, terharu, dan gak percaya. Kamu lanjut baca beberapa kata lagi... masih terbata, masih pelan, tapi jelas sekali itu <i>kamu</i> yang membacanya sendiri.</p>
    
    <p>Mama tahu kamu sudah berusaha keras belajar huruf demi huruf. Hari ini jadi bukti bahwa semua usaha kecilmu mulai membuahkan hasil. Kamu hebat sekali, Joseph. Teruslah membaca, teruslah penasaran, karena dunia ini penuh dengan kisah indah yang menunggu kamu temukan. Mama bangga luar biasa. 🌟📚</p>
""".trimIndent()

        editText.text = HtmlCompat.fromHtml(diaryText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val back_arrow = view.findViewById<ImageView>(R.id.arrow_back_diary1)

        back_arrow.setOnClickListener {
            findNavController().navigateUp()
        }

        trash.setOnClickListener {
            showCustomDeleteDialog {

            }
        }

        silang.setOnClickListener {
            showCustomPhotoDeleteDialog {

            }
        }

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                val selectedImage = slideModels[position]

                // Jika yang diklik adalah gambar "tambah"
                if (selectedImage.imagePath == R.drawable.tambah) {
                    val newImage = SlideModel(R.drawable.merangkak4)

                    // Hapus semua tambah
                    slideModels.removeAll { it.imagePath == R.drawable.tambah }

                    // Tambahkan gambar baru di awal
                    slideModels.add(3, newImage)

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
            findNavController().navigate(R.id.diaryFragment)
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