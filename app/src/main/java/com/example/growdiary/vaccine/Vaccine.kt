package com.example.growdiary.vaccine

data class Vaccine(
    val vaccineName: String,
    val vaccineDose: String,
    val vaccinePriority: String,
    val vaccineDate: String,
    val vaccineImage: Int?,
    var vaccineChecked: Boolean = false // this is essential
)