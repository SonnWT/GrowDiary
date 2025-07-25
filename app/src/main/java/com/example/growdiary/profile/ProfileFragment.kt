package com.example.growdiary.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.growdiary.R
import com.example.growdiary.databinding.FragmentProfileBinding
import com.example.growdiary.profile.UserProfile

class ProfileFragment : Fragment(), ChildAdapter.OnChildItemClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by activityViewModels()

    private lateinit var childAdapter: ChildAdapter

    // Launcher untuk menerima data setelah menambah anak baru
    private val addChildLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val newName = data?.getStringExtra("EXTRA_CHILD_NAME")
            val newBirthDate = data?.getStringExtra("EXTRA_CHILD_BIRTHDATE")
            val newImageUri = data?.getStringExtra("EXTRA_CHILD_IMAGE_URI")
            val newGender = data?.getStringExtra("EXTRA_CHILD_GENDER")
            val newWeight = data?.getStringExtra("EXTRA_CHILD_WEIGHT")
            val newHeight = data?.getStringExtra("EXTRA_CHILD_HEIGHT")
            val newNotes = data?.getStringExtra("EXTRA_CHILD_NOTES")

            if (newName != null && newBirthDate != null && newGender != null && newWeight != null && newHeight != null) {
                val newChild = Child(newName, newBirthDate, newImageUri, newGender, newWeight, newHeight, newNotes)
                viewModel.addChild(newChild)
            }
        }
    }

    // Launcher untuk menerima data setelah mengedit profil utama (HANYA SATU)
    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val newUsername = data?.getStringExtra("EXTRA_NEW_USERNAME")
            if (newUsername != null) {
                // Panggil ViewModel, bukan langsung ubah UI
                viewModel.updateUsername(newUsername)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childAdapter = ChildAdapter(this)
        binding.rvChildren.adapter = childAdapter
        binding.rvChildren.layoutManager = LinearLayoutManager(context)

        // Amati data anak dari ViewModel (HANYA SATU)
        viewModel.children.observe(viewLifecycleOwner) { updatedChildrenList ->
            childAdapter.submitList(updatedChildrenList)
        }

        // Amati data profil pengguna utama
        viewModel.userProfile.observe(viewLifecycleOwner) { profile: UserProfile ->
            binding.tvUsername.text = profile.username
            binding.tvEmail.text = profile.email
            // Anda bisa tambahkan logika untuk memuat gambar avatar di sini
        }

        binding.btnAddChild.setOnClickListener {
            val intent = Intent(requireContext(), AddChildActivity::class.java)
            addChildLauncher.launch(intent)
        }

        binding.btnProfileMenu.setOnClickListener { anchorView ->
            val inflater = LayoutInflater.from(requireContext())
            val popupView = inflater.inflate(R.layout.popup_menu, null)
            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
            val btnEdit: Button = popupView.findViewById(R.id.btn_popup_edit_profile)
            val btnLogout: Button = popupView.findViewById(R.id.btn_popup_logout)

            btnEdit.setOnClickListener {
                showEditProfilePage()
                popupWindow.dismiss()
            }
            btnLogout.setOnClickListener {
                Toast.makeText(context, "Logout diklik!", Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
            }
            popupWindow.showAsDropDown(anchorView)
        }
    }

    private fun showEditProfilePage() {
        val intent = Intent(requireContext(), ManageProfileActivity::class.java)
        editProfileLauncher.launch(intent)
    }

    override fun onEditClick(position: Int) {
        val bundle = Bundle().apply {
            putInt("childPosition", position)
        }
        findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)
    }

    override fun onDeleteClick(position: Int) {
        val child = viewModel.getChildAt(position)
        if (child != null) {
            AlertDialog.Builder(requireContext())
                .setTitle("Hapus Anak")
                .setMessage("Apakah Anda yakin ingin menghapus data ${child.name}?")
                .setPositiveButton("Hapus") { _, _ ->
                    viewModel.deleteChild(position)
                    Toast.makeText(context, "${child.name} dihapus", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle().apply {
            putInt("childPosition", position)
        }
        findNavController().navigate(R.id.action_profileFragment_to_profileDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}