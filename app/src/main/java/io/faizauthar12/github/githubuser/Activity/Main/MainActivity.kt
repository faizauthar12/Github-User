
package io.faizauthar12.github.githubuser.Activity.Main

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.faizauthar12.github.githubuser.Activity.Detail.DetailActivity
import io.faizauthar12.github.githubuser.Activity.Favorite.FavoriteActivity
import io.faizauthar12.github.githubuser.Activity.Settings.SettingsActivity
import io.faizauthar12.github.githubuser.Activity.Main.Adapter.UsernameAdapter
import io.faizauthar12.github.githubuser.Activity.Main.Model.Username
import io.faizauthar12.github.githubuser.R
import io.faizauthar12.github.githubuser.Activity.Main.ViewModel.UsernameViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var usernameAdapter: UsernameAdapter
    private lateinit var usernameViewModel: UsernameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvResults()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /*
             * get username
             */
            override fun onQueryTextSubmit(query: String): Boolean {
                usernameViewModel.setUsername(query)
                showLoading(true)
                return true
            }

            /*
             * respons every character changes
             */
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorite -> {
                Intent(this@MainActivity, FavoriteActivity::class.java).apply { startActivity(this) }
            }
            R.id.settings -> {
                Intent(this@MainActivity,SettingsActivity::class.java).apply { startActivity(this) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun rvResults() {
        usernameAdapter = UsernameAdapter()
        usernameAdapter.notifyDataSetChanged()

        rv_GHUser.layoutManager = LinearLayoutManager(this)
        rv_GHUser.adapter = usernameAdapter

        usernameViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UsernameViewModel::class.java)

        usernameViewModel.getUsername().observe(this, { GHUsername ->
            if (GHUsername != null) {
                usernameAdapter.setData(GHUsername)
                showLoading(false)
            }
        })

        usernameAdapter.setOnItemClickCallback(object : UsernameAdapter.OnItemClickCallback {
            override fun onItemClicked(username: Username) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER,username)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}