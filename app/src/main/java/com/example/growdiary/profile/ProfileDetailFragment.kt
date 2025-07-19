package com.example.growdiary.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.growdiary.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView

class ProfileDetailFragment : Fragment() {

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

        // Set initial data (replace with actual data loading later)
        textName.text = "Joseph"
        textDob.text = "16 June 2025"
        textGender.text = "Male"
        textWeight.text = "20 kg"
        textHeight.text = "78 cm"
        textNotes.text = "Joseph alergi susu sapi dan kacang tanah. Joseph juga tidak bisa tidur jika tidak dibacakan dongeng."
        profileImage.setImageResource(R.drawable.profile_joseph) // Assuming you have profile_joseph drawable

        btnEditProfile.setOnClickListener {
            // Navigate to EditProfileFragment
            findNavController().navigate(R.id.action_profileDetailFragment_to_editProfileFragment)
        }

        btnBack.setOnClickListener {
            // Navigate back to HomeFragment
            findNavController().popBackStack(R.id.homeFragment, false) // Pop all backstack until homeFragment
        }
    }
}