package com.example.growdiary.diary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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

class Diary1Fragment : Fragment() {


    @SuppressLint("MissingInflatedId")
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
        slideModels.add(SlideModel(R.drawable.merangkak))
        slideModels.add(SlideModel(R.drawable.merangkak2))
        slideModels.add(SlideModel(R.drawable.merangkak3))
        slideModels.add(SlideModel(R.drawable.tambah))


        imageSlider.setImageList(slideModels, true)


        val diaryText = """
    <p>Hari ini, tanggal <b>2 Juni 2025</b>, jadi salah satu hari yang tidak akan pernah Mama lupakan. Untuk <b>pertama kalinya</b>, <font color='#000000'>Joseph merangkak sendiri!</font> 🥺💛</p>
    
    <p>Awalnya cuma bolak-balik badan, lalu tiba-tiba dia dorong tubuh mungilnya ke depan, satu tangan... lalu satu lutut... dan dia berhasil! Mama langsung teriak pelan (biar gak bikin kamu kaget 😅), terus air mata ini jatuh juga. Anak kecilku yang dulu cuma bisa digendong sekarang sudah mulai menjelajah dunia kecilnya sendiri.</p>
    
    <p><i>Masih pelan, masih goyah</i>, tapi semangatmu besar sekali. Mama bangga sekali sama kamu, Joseph. Tumbuhlah sehat, kuat, dan bahagia selalu. Langkah kecilmu hari ini adalah awal dari petualangan besar di dunia ini. 🌙</p>
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