package io.faizauthar12.github.githubuser.Activity.Detail.Fragment.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Followers (
        var username: String? = null,
        var avatar: String? = null,
): Parcelable