package io.faizauthar12.github.githubuser.Activity.Detail

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import io.faizauthar12.github.githubuser.Activity.Detail.Adapter.SectionsPagerAdapter
import io.faizauthar12.github.githubuser.BuildConfig
import io.faizauthar12.github.githubuser.Activity.Main.Model.Username
import io.faizauthar12.github.githubuser.R
import io.faizauthar12.github.githubuser.db.FavoriteHelper
import io.faizauthar12.github.githubuser.db.UserContract.UserColumns.Companion.AVATAR
import io.faizauthar12.github.githubuser.db.UserContract.UserColumns.Companion.CONTENT_URI
import io.faizauthar12.github.githubuser.db.UserContract.UserColumns.Companion.LOGIN
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_ghuser"
    }

    private var statusFavorite = false
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        /* get data from main activity */
        val username = intent.getParcelableExtra<Username>(EXTRA_USER) as Username

        /* open database */
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        /* hidden few iv */
        imageController()

        /* get detail of selected username */
        getDetail(username.username)

        /* create tablayout */
        createTabLayout(username.username)

        /* Create action bar */
        createBackButton()

        /* Check favorite */
        checkFavorite(username.username)

        /* Create FAB favorite */
        createFAB(username.username, username.avatar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun imageController() {
        IV_Location.visibility = View.GONE
        IV_Company.visibility = View.GONE
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
                    /* Parsing json to layout */
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    var name = responseObject.getString("name")
                    val login = responseObject.getString("login")
                    val location = responseObject.getString("location")
                    val company = responseObject.getString("company")

                    /* Get Avatar */
                    Glide.with(applicationContext)
                            .load(responseObject.getString("avatar_url"))
                            .apply(RequestOptions().override(100,100))
                            .into(IV_GHUser_Avatar)

                    /* Set it to layout xml */
                    TV_GHUser_username.text = "@${login}"
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

                    if (company == "null"){
                        TV_Company.visibility = View.GONE
                    } else {
                        TV_Company.text = company
                        IV_Company.visibility = View.VISIBLE
                    }

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

    private fun createBackButton() {
        val actionbar = supportActionBar
        actionbar!!.title = "Detail User"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    private fun checkFavorite(username: String?) {
        var isPresent = favoriteHelper.queryByLogin(username.toString())

        if (isPresent.count>0) {
            statusFavorite = true
        }
    }

    private fun createFAB(username: String?, avatar: String?) {
        setStatusFavorite(statusFavorite)
        fab.setOnClickListener {
            if (statusFavorite) {
                favoriteHelper.deleteByLogin(username.toString())
            } else {
                val values = ContentValues()
                values.put(LOGIN, username)
                values.put(AVATAR, avatar)
                contentResolver.insert(CONTENT_URI,values)
            }
            /* set status after onClick */
            statusFavorite = !statusFavorite
            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if(statusFavorite){
            fab.setImageResource(R.drawable.ic_fill_heart)
        }else{
            fab.setImageResource(R.drawable.ic_unfill_heart)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }
}