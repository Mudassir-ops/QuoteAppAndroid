package com.example.quotationapp.json

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Quotes(
    @SerializedName("text") var text: String? = null,
    @SerializedName("author") var author: String? = null,
    var isFavourite: Boolean = false


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeString(author)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quotes> {
        override fun createFromParcel(parcel: Parcel): Quotes {
            return Quotes(parcel)
        }

        override fun newArray(size: Int): Array<Quotes?> {
            return arrayOfNulls(size)
        }
    }
}