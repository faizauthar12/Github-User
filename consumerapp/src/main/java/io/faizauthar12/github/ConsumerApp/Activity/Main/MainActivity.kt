package io.faizauthar12.github.ConsumerApp.Activity.Main

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.recyclerview.widget.LinearLayoutManager
import io.faizauthar12.github.ConsumerApp.R
import io.faizauthar12.github.ConsumerApp.Activity.Main.Adapter.FavoriteAdapter
import io.faizauthar12.github.ConsumerApp.Activity.Main.Model.Favorite
import io.faizauthar12.github.ConsumerApp.db.UserContract
import io.faizauthar12.github.ConsumerApp.db.UserContract.UserColumns.Companion.CONTENT_URI
import io.faizauthar12.github.ConsumerApp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBarTitle()

        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        rv_favorite.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadFavoritesAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI,true, myObserver)

        if (savedInstanceState == null) {
            loadFavoritesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorites = list
            }
        }
    }

    private fun setActionBarTitle() {
        if (supportActionBar != null) {
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(true)
            }
            title = resources.getString(R.string.consumer_favorite)
        }
    }

    private fun loadFavoritesAsync() {
        GlobalScope.launch(Dispatchers.Main){
            val deferredFavorites = async(Dispatchers.IO){
                val cursor = contentResolver.query(UserContract.UserColumns.CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorites = deferredFavorites.await()
            if (favorites.size>0){
                adapter.listFavorites = favorites
            } else {
                adapter.listFavorites = ArrayList()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorites)
    }
}