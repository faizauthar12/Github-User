package io.faizauthar12.github.githubuser.Activity.Detail.Fragment.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import io.faizauthar12.github.githubuser.BuildConfig
import io.faizauthar12.github.githubuser.Activity.Detail.Fragment.Model.Following
import org.json.JSONArray

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<Following>>()

    val listItems = ArrayList<Following>()

    val apiKey = BuildConfig.API_KEY
    val apiUrl = "https://api.github.com"

    val client = AsyncHttpClient()

    fun setFollowing(username: String) {
        val url = "$apiUrl/users/$username/following"

        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                try {
                    //parsing json
                    val result = responseBody?.let { String(it) }
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val following = responseObject.getJSONObject(i)
                        val followingItems = Following()
                        followingItems.username = following.getString("login")
                        followingItems.avatar = following.getString("avatar_url")
                        listItems.add(followingItems)
                    }
                    listFollowing.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowing() : LiveData<ArrayList<Following>> {
        return listFollowing
    }
}