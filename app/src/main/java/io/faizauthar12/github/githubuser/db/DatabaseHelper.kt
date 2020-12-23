package io.faizauthar12.github.githubuser.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import io.faizauthar12.github.githubuser.db.UserContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "favoriteUser"

        const val DATABASE_VERSION = 1

        const val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " (${UserContract.UserColumns.AVATAR} TEXT PRIMARY KEY  NOT NULL," +
                " ${UserContract.UserColumns.LOGIN} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}