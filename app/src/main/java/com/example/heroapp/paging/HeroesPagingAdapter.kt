package com.example.heroapp.paging


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.heroapp.OnImageClickInterface
import com.example.heroapp.R
import com.example.heroapp.network.dataClasses.CharacterRestModel


class HeroesPagingAdapter(private val clickListener: OnImageClickInterface) :
    PagingDataAdapter<CharacterRestModel, MyViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
        holder.itemImage.setOnClickListener {
            clickListener.onItemClick(data)
        }
    }
}