package com.example.growdiary.vaccine

class Vaccine {
    var vaccineName: String? = null
    var vaccineDose: String? = null
    var vaccinePriority: String? = null
    var vaccineDate: String? = null
    var vaccineImage: Int? = null

    constructor(vaccineName: String, vaccineDose: String, vaccinePriority: String,
        vaccineDate: String, vaccineImage: Int? = null)
    {
        this.vaccineName = vaccineName
        this.vaccineDose = vaccineDose
        this.vaccinePriority = vaccinePriority
        this.vaccineDate = vaccineDate
        this.vaccineImage = vaccineImage
    }
}