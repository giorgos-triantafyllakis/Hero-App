package com.example.heroapp.paging

import androidx.recyclerview.widget.DiffUtil
import com.example.heroapp.network.dataClasses.CharacterRestModel

class DiffUtilCallBack : DiffUtil.ItemCallback<CharacterRestModel>() {

    override fun areItemsTheSame(
        oldItem: CharacterRestModel,
        newItem: CharacterRestModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: CharacterRestModel,
        newItem: CharacterRestModel
    ): Boolean {
        return oldItem.name == newItem.name
    }
}