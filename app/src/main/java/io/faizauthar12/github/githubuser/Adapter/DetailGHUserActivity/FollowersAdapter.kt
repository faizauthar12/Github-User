package io.faizauthar12.github.githubuser.Adapter.DetailGHUserActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.faizauthar12.github.githubuser.Model.Followers
import io.faizauthar12.github.githubuser.R
import kotlinx.android.synthetic.main.item_list_ghuser.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    private val mData = ArrayList<Followers>()

    fun setData(items: ArrayList<Followers>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner  class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followers: Followers) {
            with(itemView) {
                Glide.with(itemView.context)
                        .load(followers.avatar)
                        .apply(RequestOptions().override(60,60))
                        .into(IV_GHUser_Avatar)
                TV_GHUser_username.text = followers.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersAdapter.FollowersViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_ghuser, parent, false)
        return FollowersViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}