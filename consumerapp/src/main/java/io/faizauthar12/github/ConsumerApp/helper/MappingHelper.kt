package io.faizauthar12.github.ConsumerApp.helper

import android.database.Cursor
import io.faizauthar12.github.ConsumerApp.Activity.Main.Model.Favorite
import io.faizauthar12.github.ConsumerApp.db.UserContract

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

    fun mapCursorToObject(notesCursor: Cursor?): Favorite {
        var favorite = Favorite()
        notesCursor?.apply {
            moveToFirst()
            val login = getString(getColumnIndexOrThrow(UserContract.UserColumns.LOGIN))
            val avatar = getString(getColumnIndexOrThrow(UserContract.UserColumns.AVATAR))
            favorite = Favorite(login, avatar)
        }
        return favorite
    }
}