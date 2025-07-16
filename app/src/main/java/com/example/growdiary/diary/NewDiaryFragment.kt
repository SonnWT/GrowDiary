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
import com.example.growdiary.R

class NewDiaryFragment : Fragment() {

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view dulu
        val view = inflater.inflate(R.layout.fragment_new_diary, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val back_arrow = view.findViewById<ImageView>(R.id.arrow_back)
        val trash = view.findViewById<ImageView>(R.id.trash)

        var isFirstImage = true

        imageView.setOnClickListener {
            isFirstImage = false
            if (isFirstImage == false) {
                imageView.setImageResource(R.drawable.baca)
                imageView.setBackgroundResource(R.color.cream)
            }
        }

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
