package io.faizauthar12.github.ConsumerApp.Activity.Main.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (
        var login: String? = null,
        var avatar: String? = null,
): Parcelable