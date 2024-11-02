package com.example.heroapp.paging

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.heroapp.databinding.CardLayoutBinding
import com.example.heroapp.network.dataClasses.CharacterRestModel

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: CardLayoutBinding = CardLayoutBinding.bind(view)
    var itemImage: ImageView = binding.itemImage
    var itemTitle: TextView = binding.itemTitle

    fun bind(data: CharacterRestModel) {
        itemTitle.text = data.name
        Glide.with(itemImage)
            .load(data.thumbnail.getUrl())
            .circleCrop()
            .into(itemImage)
    }
}