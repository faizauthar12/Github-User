package io.faizauthar12.github.githubuser.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import io.faizauthar12.github.githubuser.db.UserContract.UserColumns.Companion.LOGIN
import io.faizauthar12.github.githubuser.db.UserContract.UserColumns.Companion.TABLE_NAME
import java.sql.SQLException

class FavoriteHelper (context: Context){
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavoriteHelper? = null

        fun getInstance(context: Context): FavoriteHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: FavoriteHelper(context)
                }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$LOGIN ASC"
        )
    }

    fun queryByLogin(login: String): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                "$LOGIN = ?",
                arrayOf(login),
                null,
                null,
                null,
                null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteByLogin(id: String): Int {
        return database.delete(DATABASE_TABLE, "$LOGIN = '$id'", null)
    }
}