package com.example.growdiary.diary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Scroller
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.growdiary.R

class NewDiaryFragment : Fragment() {
    private val slideModels = ArrayList<SlideModel>()


    @SuppressLint("ResourceAsColor", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view dulu
        val view = inflater.inflate(R.layout.fragment_new_diary, container, false)
        val back_arrow = view.findViewById<ImageView>(R.id.arrow_back)
        val trash = view.findViewById<ImageView>(R.id.trash)

        val imageSlider = view.findViewById<ImageSlider>(R.id.sliderNew)

        // Tambahkan tombol tambah sekali saja jika belum ada
        if (slideModels.none { it.imagePath == R.drawable.tambah }) {
            slideModels.add(SlideModel(R.drawable.tambah))
        }

        imageSlider.setImageList(slideModels, true)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                val selectedImage = slideModels[position]

                // Jika yang diklik adalah gambar "tambah"
                if (selectedImage.imagePath == R.drawable.tambah) {
                    val newImage = SlideModel(R.drawable.baca)

                    // Hapus semua tambah
                    slideModels.removeAll { it.imagePath == R.drawable.tambah }

                    // Tambahkan gambar baru di awal
                    slideModels.add(0, newImage)

                    // Tambah kembali tombol tambah di akhir
                    slideModels.add(SlideModel(R.drawable.tambah))

                    imageSlider.setImageList(slideModels, true)
                }
            }
        })



        back_arrow.setOnClickListener {
            findNavController().navigateUp()
        }

        trash.setOnClickListener {
            showCustomDeleteDialog {

            }
        }

        // Akses EditText dari view tersebut
        val editText = view.findViewById<EditText>(R.id.editText)
        editText.setScroller(Scroller(requireContext()))
        editText.isVerticalScrollBarEnabled = true
        editText.movementMethod = android.text.method.ScrollingMovementMethod()

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

}
