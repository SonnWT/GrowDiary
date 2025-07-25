package com.example.growdiary.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.growdiary.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView

class ProfileDetailFragment : Fragment() {

    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var profileImage: ShapeableImageView
    private lateinit var textName: TextView
    private lateinit var btnEditProfile: MaterialButton
    private lateinit var btnBack: ImageView
    private lateinit var textDob: TextView
    private lateinit var textGender: TextView
    private lateinit var textWeight: TextView
    private lateinit var textHeight: TextView
    private lateinit var textNotes: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_detail, container, false)

        profileImage = view.findViewById(R.id.profile_image_detail)
        textName = view.findViewById(R.id.text_name_detail)
        btnEditProfile = view.findViewById(R.id.btn_edit_profile_detail)
        btnBack = view.findViewById(R.id.btn_back)
        textDob = view.findViewById(R.id.text_dob_detail)
        textGender = view.findViewById(R.id.text_gender_detail)
        textWeight = view.findViewById(R.id.text_weight_detail)
        textHeight = view.findViewById(R.id.text_height_detail)
        textNotes = view.findViewById(R.id.text_notes_detail)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Ambil posisi dari arguments
        val position = arguments?.getInt("childPosition") ?: -1

        if (position != -1) {
            // 2. Ambil data anak dari ViewModel
            val child = viewModel.getChildAt(position)

            // 3. Tampilkan data di UI
            if (child != null) {
                textName.text = child.name
                textDob.text = child.birthDate
                textGender.text = child.gender
                textWeight.text = "${child.weight} kg"
                textHeight.text = "${child.height} cm"
                textNotes.text = child.notes

                if (child.imageUri != null) {
                    profileImage.setImageURI(Uri.parse(child.imageUri))
                } else {
                    profileImage.setImageResource(R.drawable.ic_launcher_background) // Placeholder
                }
            }
        }

        btnEditProfile.setOnClickListener {
            // Saat edit, kirim juga posisinya agar halaman edit tahu anak mana yg di-edit
            val bundle = Bundle().apply {
                putInt("childPosition", position)
            }
            findNavController().navigate(R.id.action_profileDetailFragment_to_editProfileFragment, bundle)
        }

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}