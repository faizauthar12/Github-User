package io.faizauthar12.github.githubuser.helper

import android.database.Cursor
import io.faizauthar12.github.githubuser.Model.Favorite
import io.faizauthar12.github.githubuser.db.UserContract

object MappingHelper {
    fun mapCursorToArrayList(favoritesCursor: Cursor?): ArrayList<Favorite>{
        val favoriteList = ArrayList<Favorite>()

        favoritesCursor?.apply {
            while (moveToNext()){
                val login = getString(getColumnIndexOrThrow(UserContract.UserColumns.LOGIN))
                val avatar = getString(getColumnIndexOrThrow(UserContract.UserColumns.AVATAR))
                favoriteList.add(Favorite(
                        login,
                        avatar)
                )
            }
        }
        return favoriteList
    }
}