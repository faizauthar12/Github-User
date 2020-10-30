package io.faizauthar12.github.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_g_h_user.*

class DetailGHUser : AppCompatActivity() {
    companion object{
        const val EXTRA_GHUSER = "extra_ghuser"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_g_h_user)

        val ghuser = intent.getParcelableExtra<GHUser>(EXTRA_GHUSER) as GHUser

        TV_GHUser_name.text = ghuser.name
        TV_GHUser_username.text = getString(R.string.GHU_username, ghuser.username)
        TV_GHUser_Followers.text = getString(R.string.GHU_followers, ghuser.followers)
        TV_GHUser_Following.text = getString(R.string.GHU_following, ghuser.following)
        GHUser_repository.text = getString(R.string.GHU_repository, ghuser.repository)
        GHUser_location.text = ghuser.location
        GHUser_company.text = ghuser.company

        Glide.with(applicationContext)
                .load(ghuser.avatar)
                .apply(RequestOptions().override(100,100))
                .into(IV_GHUser_Avatar)

        setActionBarTitle(ghuser.name)
    }
    private fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }
}