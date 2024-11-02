package com.example.heroapp

import com.example.heroapp.network.dataClasses.CharacterRestModel

interface OnImageClickInterface {
    fun onItemClick(character: CharacterRestModel?)
}