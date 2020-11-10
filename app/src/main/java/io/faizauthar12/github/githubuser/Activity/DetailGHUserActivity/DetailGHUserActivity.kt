package io.faizauthar12.github.githubuser.Activity.DetailGHUserActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.faizauthar12.github.githubuser.Model.Username
import io.faizauthar12.github.githubuser.R

class DetailGHUserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_ghuser"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_g_h_user)

        val username = intent.getParcelableExtra<Username>(EXTRA_USER) as Username
    }
}