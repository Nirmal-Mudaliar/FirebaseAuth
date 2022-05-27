package com.example.demoapp.feature_auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var uid: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var phoneNo: String? = null,
    var email: String? = null,
    var password: String? = null,
    var online: Boolean? = null
): Parcelable
