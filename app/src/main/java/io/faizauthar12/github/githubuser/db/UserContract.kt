package io.faizauthar12.github.githubuser.db

import android.net.Uri
import android.provider.BaseColumns

object UserContract {
    const val AUTHORITY = "io.faizauthar12.github.githubuser"
    const val SCHEME = "content"

    class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val LOGIN = "login"
            const val AVATAR = "avatar"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}