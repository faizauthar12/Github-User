package io.faizauthar12.github.githubuser.db

import android.provider.BaseColumns

class UserContract {
    class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val LOGIN = "login"
            const val AVATAR = "avatar"
        }
    }
}