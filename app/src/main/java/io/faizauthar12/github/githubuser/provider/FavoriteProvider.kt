package io.faizauthar12.github.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import io.faizauthar12.github.githubuser.db.FavoriteHelper
import io.faizauthar12.github.githubuser.db.UserContract.AUTHORITY
import io.faizauthar12.github.githubuser.db.UserContract.UserColumns.Companion.CONTENT_URI
import io.faizauthar12.github.githubuser.db.UserContract.UserColumns.Companion.TABLE_NAME

class FavoriteProvider: ContentProvider() {
    companion object{
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoriteHelper: FavoriteHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVORITE_ID)
        }
    }

    override fun onCreate(): Boolean {
        favoriteHelper = FavoriteHelper.getInstance(context as Context)
        favoriteHelper.open()
        return true
    }

    override fun query(uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAVORITE -> favoriteHelper.queryAll()
            FAVORITE_ID -> favoriteHelper.queryByLogin(uri.lastPathSegment.toString())
            else -> null
        }
    }


    override fun getType(uri: Uri): String? {
        return null
    }


    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (FAVORITE) {
            sUriMatcher.match(uri) -> favoriteHelper.insert(contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        val updated: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> favoriteHelper.update(uri.lastPathSegment.toString(),contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> favoriteHelper.deleteByLogin(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }
}