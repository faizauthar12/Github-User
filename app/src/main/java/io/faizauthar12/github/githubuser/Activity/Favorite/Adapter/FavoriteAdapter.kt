package io.faizauthar12.github.githubuser.Activity.Favorite.Adapter

import android.app.Activity
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.faizauthar12.github.githubuser.Activity.Favorite.Model.Favorite
import io.faizauthar12.github.githubuser.R
import kotlinx.android.synthetic.main.item_list_ghuser.view.*

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){
    var listFavorites = ArrayList<Favorite>()
    set(listFavorites) {
        if(listFavorites.size > 0 ){
            this.listFavorites.clear()
        }
        this.listFavorites.addAll(listFavorites)

        notifyDataSetChanged()
    }

    var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_ghuser, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        //holder.bind(listFavorites[position])
        val favorite = listFavorites[position]
        holder.bind(favorite){
            onItemClickCallback?.onItemClicked(favorite)
        }
    }

    override fun getItemCount(): Int = this.listFavorites.size

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite, itemClicked: (View) -> Unit){
            with(itemView){
                Glide.with(itemView.context)
                        .load(favorite.avatar)
                        .apply(RequestOptions().override(60,60))
                        .into(itemView.IV_GHUser_Avatar)
                TV_GHUser_username.text = favorite.login

                setOnClickListener {
                    itemClicked.invoke(itemView)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(favorite: Favorite)
    }
}