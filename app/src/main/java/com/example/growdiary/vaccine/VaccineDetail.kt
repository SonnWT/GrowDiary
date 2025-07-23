package com.example.growdiary.vaccine

import android.os.Parcel
import android.os.Parcelable

data class VaccineDetail(val name:String, val description:String, val image:Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VaccineDetail> {
        override fun createFromParcel(parcel: Parcel): VaccineDetail {
            return VaccineDetail(parcel)
        }

        override fun newArray(size: Int): Array<VaccineDetail?> {
            return arrayOfNulls(size)
        }
    }
}
