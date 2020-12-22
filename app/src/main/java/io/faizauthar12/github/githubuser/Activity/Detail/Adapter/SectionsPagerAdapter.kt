package io.faizauthar12.github.githubuser.Activity.Detail.Adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.faizauthar12.github.githubuser.Activity.Detail.Fragment.FollowersFragment
import io.faizauthar12.github.githubuser.Activity.Detail.Fragment.FollowingFragment
import io.faizauthar12.github.githubuser.R

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var username : String? = null

    @StringRes
    private val TAB_TILES = intArrayOf(R.string.tab_text_following, R.string.tab_text_followers)

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowingFragment.newInstance(username)
            1 -> fragment = FollowersFragment.newInstance(username)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TILES[position])
    }
}