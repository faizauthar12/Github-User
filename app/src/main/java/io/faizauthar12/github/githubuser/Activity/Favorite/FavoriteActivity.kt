package io.faizauthar12.github.githubuser.Activity.Favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.faizauthar12.github.githubuser.Activity.Favorite.Adapter.FavoriteAdapter
import io.faizauthar12.github.githubuser.Activity.Favorite.Model.Favorite
import io.faizauthar12.github.githubuser.R
import io.faizauthar12.github.githubuser.db.FavoriteHelper
import io.faizauthar12.github.githubuser.db.UserContract.UserColumns.Companion.CONTENT_URI
import io.faizauthar12.github.githubuser.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setActionBarTitle()

        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        rv_favorite.adapter = adapter

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
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
            title = resources.getString(R.string.favorite)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadFavoritesAsync() {
        GlobalScope.launch(Dispatchers.Main){
            val deferredFavorites = async(Dispatchers.IO){
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
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