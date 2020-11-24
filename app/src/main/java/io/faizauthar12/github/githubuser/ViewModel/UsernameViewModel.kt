package io.faizauthar12.github.githubuser.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import io.faizauthar12.github.githubuser.BuildConfig
import io.faizauthar12.github.githubuser.Model.Username
import org.json.JSONObject

class UsernameViewModel: ViewModel() {
    val listUsername = MutableLiveData<ArrayList<Username>>()

    val listItems = ArrayList<Username>()

    val apiKey = BuildConfig.API_KEY
    val apiUrl = "https://api.github.com"

    val client = AsyncHttpClient()
    fun setUsername(username: String) {
        val url = "$apiUrl/search/users?q=$username"

        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()){
                        val item = items.getJSONObject(i)
                        val ghusername = Username()
                        ghusername.username = item.getString("login")
                        ghusername.avatar = item.getString("avatar_url")
                        listItems.add(ghusername)
                    }
                    listUsername.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getUsername(): LiveData<ArrayList<Username>> {
        return listUsername
    }
}