package io.faizauthar12.github.githubuser.Adapter.DetailGHUserActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.faizauthar12.github.githubuser.Adapter.UsernameAdapter
import io.faizauthar12.github.githubuser.Model.Following
import io.faizauthar12.github.githubuser.R
import kotlinx.android.synthetic.main.item_list_ghuser.view.*


class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    private val mData = ArrayList<Following>()

    fun setData(items: ArrayList<Following>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner  class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(following: Following) {
            with(itemView) {
                Glide.with(itemView.context)
                        .load(following.avatar)
                        .apply(RequestOptions().override(60,60))
                        .into(IV_GHUser_Avatar)
                TV_GHUser_username.text = following.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAdapter.FollowingViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_ghuser, parent, false)
        return FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}