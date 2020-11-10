package io.faizauthar12.github.githubuser.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.faizauthar12.github.githubuser.Model.Username
import io.faizauthar12.github.githubuser.R
import kotlinx.android.synthetic.main.item_list_ghuser.view.*

class UsernameAdapter : RecyclerView.Adapter<UsernameAdapter.usernameViewHolder>() {
    private  val mData = ArrayList<Username>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<Username>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class usernameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(username: Username) {
            with(itemView){
                Glide.with(itemView.context)
                        .load(username.avatar)
                        .apply(RequestOptions().override(60,60))
                        .into(IV_GHUser_Avatar)
                TV_GHUser_username.text = "@${username.username}"

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(Username()) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): usernameViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_ghuser, parent, false)
        return usernameViewHolder(mView)
    }

    override fun onBindViewHolder(holder: usernameViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Username)
    }
}