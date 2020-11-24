package io.faizauthar12.github.githubuser

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.faizauthar12.github.githubuser.Activity.DetailGHUserActivity.DetailGHUserActivity
import io.faizauthar12.github.githubuser.Adapter.UsernameAdapter
import io.faizauthar12.github.githubuser.Model.Username
import io.faizauthar12.github.githubuser.ViewModel.UsernameViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var usernameAdapter: UsernameAdapter
    private lateinit var usernameViewModel: UsernameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                rvResults(query)
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

    private fun rvResults(username: String) {
        usernameAdapter = UsernameAdapter()
        usernameAdapter.notifyDataSetChanged()

        rv_GHUser.layoutManager = LinearLayoutManager(this)
        rv_GHUser.adapter = usernameAdapter

        usernameViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UsernameViewModel::class.java)

        showLoading(true)

        usernameViewModel.setUsername(username)

        usernameViewModel.getUsername().observe(this, { GHUsername ->
            if (GHUsername != null) {
                usernameAdapter.setData(GHUsername)
                showLoading(false)
            }
        })
        usernameAdapter.setOnItemClickCallback(object : UsernameAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Username) {
                val intent = Intent(this@MainActivity, DetailGHUserActivity::class.java)
                intent.putExtra(DetailGHUserActivity.EXTRA_USER,data)
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