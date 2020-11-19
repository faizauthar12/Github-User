package io.faizauthar12.github.githubuser.Activity.DetailGHUserActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import io.faizauthar12.github.githubuser.Activity.DetailGHUserActivity.Fragment.FollowingFragment
import io.faizauthar12.github.githubuser.Adapter.DetailGHUserActivity.SectionsPagerAdapter
import io.faizauthar12.github.githubuser.BuildConfig
import io.faizauthar12.github.githubuser.Model.Username
import io.faizauthar12.github.githubuser.R
import kotlinx.android.synthetic.main.activity_detail_g_h_user.*
import org.json.JSONObject

class DetailGHUserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_ghuser"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_g_h_user)

        val username = intent.getParcelableExtra<Username>(EXTRA_USER) as Username

        getDetail(username.username)
        createTabLayout(username.username)
    }

    private fun getDetail(username: String?) {
        val apiKey = BuildConfig.API_KEY
        val apiUrl = "https://api.github.com"

        val client = AsyncHttpClient()
        val url = "$apiUrl/users/$username"

        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    //parsing json to layout.
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    var name = responseObject.getString("name")
                    val login = responseObject.getString("login")
                    val location = responseObject.getString("location")

                    Glide.with(applicationContext)
                            .load(responseObject.getString("avatar_url"))
                            .apply(RequestOptions().override(100,100))
                            .into(IV_GHUser_Avatar)

                    if (name == "null"){
                        TV_GHUser_name.visibility = View.GONE
                    } else {
                        TV_GHUser_name.text = name
                    }

                    if (location == "null"){
                        TV_Location.visibility = View.GONE
                    } else {
                        TV_Location.text = location
                        IV_Location.visibility = View.VISIBLE
                    }

                    TV_GHUser_username.text = "@${login}"

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }


    private fun createTabLayout(username: String?) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = username
        VP_detailuser.adapter = sectionsPagerAdapter
        tl_detailuser.setupWithViewPager(VP_detailuser)
        supportActionBar?.elevation = 0f
    }
}