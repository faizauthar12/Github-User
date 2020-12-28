package io.faizauthar12.github.ConsumerApp.Activity.Main.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.faizauthar12.github.ConsumerApp.R
import io.faizauthar12.github.ConsumerApp.Activity.Main.Model.Favorite
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_ghuser, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int = this.listFavorites.size

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite){
            with(itemView){
                Glide.with(itemView.context)
                        .load(favorite.avatar)
                        .apply(RequestOptions().override(60,60))
                        .into(itemView.IV_GHUser_Avatar)
                TV_GHUser_username.text = favorite.login
            }
        }
    }
}