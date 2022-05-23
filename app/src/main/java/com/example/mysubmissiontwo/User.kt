package com.example.mysubmissiontwo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String,
    var description: String,
    var photo: Int,
    var username: String,

): Parcelable
