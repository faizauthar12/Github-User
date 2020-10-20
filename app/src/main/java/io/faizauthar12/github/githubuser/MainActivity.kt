package io.faizauthar12.github.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<GHUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_GHUser.setHasFixedSize(true)
        list.addAll(getListGHUsers())
        showRecyclerList()
    }

    fun getListGHUsers(): ArrayList<GHUser> {
        val dataUsername = resources.getStringArray(R.array.username)
        val dataName = resources.getStringArray(R.array.name)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataRepository = resources.getStringArray(R.array.repository)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataFollowers = resources.getStringArray(R.array.followers)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataAvatar = resources.obtainTypedArray(R.array.avatar)

        val listGHUser = ArrayList<GHUser>()
        for (position in dataUsername.indices) {
            val ghUser = GHUser(
                    dataUsername[position],
                    dataName[position],
                    dataLocation[position],
                    dataRepository[position],
                    dataCompany[position],
                    dataFollowers[position],
                    dataFollowing[position],
                    dataAvatar.getResourceId(position,-1)
            )
            listGHUser.add(ghUser)
        }
        return listGHUser
    }

    private fun showRecyclerList() {
        rv_GHUser.layoutManager = LinearLayoutManager(this)
        val listGHUserAdapter = ListGHUserAdapter(list)
        rv_GHUser.adapter = listGHUserAdapter
    }
}