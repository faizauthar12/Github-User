package io.faizauthar12.github.githubuser.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import io.faizauthar12.github.githubuser.BuildConfig
import io.faizauthar12.github.githubuser.Model.Followers
import org.json.JSONArray

class FollowersViewModel: ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<Followers>>()

    val listItems = ArrayList<Followers>()

    val apiKey = BuildConfig.API_KEY
    val apiUrl = "https://api.github.com"

    val client = AsyncHttpClient()

    fun setFollowers(username: String) {
        val url = "$apiUrl/users/$username/followers"

        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                try {
                    //parsing json
                    val result = responseBody?.let { String(it) }
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val followers = responseObject.getJSONObject(i)
                        val followersItems = Followers()
                        followersItems.username = followers.getString("login")
                        followersItems.avatar = followers.getString("avatar_url")
                        listItems.add(followersItems)
                    }
                    listFollowers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowers() : LiveData<ArrayList<Followers>> {
        return listFollowers
    }
}