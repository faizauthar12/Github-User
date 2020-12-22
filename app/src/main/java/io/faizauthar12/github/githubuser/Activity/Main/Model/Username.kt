package io.faizauthar12.github.githubuser.Activity.Main.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Username (
    var username: String? = null,
    var avatar: String? = null,
): Parcelable