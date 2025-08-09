package com.example.growdiary.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.growdiary.R

data class UserProfile(
    val username: String,
    val email: String,
    val avatarUri: String? = null
)

class ProfileViewModel : ViewModel() {

    // Data list anak yang bisa diobservasi oleh Fragment
    private val _children = MutableLiveData<MutableList<Child>>()
    val children: LiveData<MutableList<Child>> = _children
    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> = _userProfile

    init {
        // Isi dengan data awal
        _children.value = mutableListOf(
            Child(
                name = "Joseph",
                birthDate = "1 May 2024",
                imageUri = null, // Biarkan null
                imageResId = R.drawable.baby_icon,
                gender = "Male",
                weight = "10",
                height = "75",
                notes = "Alergi debu."
            )
        )
        _userProfile.value = UserProfile("Username", "your_domain@email.com", null)
    }

    fun updateUsername(newUsername: String) {
        val currentProfile = _userProfile.value
        if (currentProfile != null) {
            _userProfile.value = currentProfile.copy(username = newUsername)
        }
    }

    // Fungsi untuk mendapatkan data anak berdasarkan posisi
    fun getChildAt(position: Int): Child? {
        return _children.value?.getOrNull(position)
    }

    // Fungsi untuk memperbarui data anak
    fun updateChild(position: Int, updatedChild: Child) {
        val currentList = _children.value
        if (currentList != null && position >= 0 && position < currentList.size) {
            currentList[position] = updatedChild
            _children.value = currentList // Memicu update ke observer
        }
    }

    fun deleteChild(position: Int) {
        val currentList = _children.value
        if (currentList != null && position >= 0 && position < currentList.size) {
            currentList.removeAt(position)
            _children.value = currentList // Memicu update ke observer
        }
    }

    fun addChild(newChild: Child) {
        val currentList = _children.value ?: mutableListOf()
        currentList.add(newChild)
        _children.value = currentList // Memicu update ke observer
    }
}