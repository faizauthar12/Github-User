package io.faizauthar12.github.githubuser.Activity.Favorite.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (
        var login: String? = null,
        var avatar: String? = null,
): Parcelable