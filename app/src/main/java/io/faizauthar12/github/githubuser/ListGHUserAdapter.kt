package io.faizauthar12.github.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_list_ghuser.view.*
import java.util.ArrayList

class ListGHUserAdapter(private val listGHUser: ArrayList<GHUser>): RecyclerView.Adapter<ListGHUserAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ghUser: GHUser) {
            with(itemView) {
                Glide.with(itemView.context)
                        .load(ghUser.avatar)
                        .apply(RequestOptions().override(60,60))
                        .into(IV_GHUser_Avatar)
                TV_GHUser_name.text = ghUser.name
                TV_GHUser_username.text = "@${ghUser.username}"
                TV_GHUser_Followers.text = "Followers : ${ghUser.followers}"
                TV_GHUser_Following.text = "Following : ${ghUser.following}"

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(ghUser) }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list_ghuser, viewGroup,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listGHUser[position])
    }

    override fun getItemCount(): Int = listGHUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: GHUser)
    }
}