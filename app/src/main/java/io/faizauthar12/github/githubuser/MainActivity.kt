
package io.faizauthar12.github.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.faizauthar12.github.githubuser.Adapter.UsernameAdapter
import io.faizauthar12.github.githubuser.ViewModel.UsernameViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var usernameAdapter: UsernameAdapter
    private lateinit var usernameViewModel: UsernameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvResults("Doraemon")
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
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}