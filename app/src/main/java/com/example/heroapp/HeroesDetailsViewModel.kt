package com.example.heroapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heroapp.network.MarvelApi
import com.example.heroapp.network.dataClasses.CharacterRestModel
import kotlinx.coroutines.*

class HeroesDetailsViewModel : ViewModel() {


    private val _character = MutableLiveData<List<CharacterRestModel>>(emptyList())
    val character: LiveData<List<CharacterRestModel>> = _character
    private var _heroImage = MutableLiveData<String>()
    val heroImage: LiveData<String> = _heroImage
    private var _heroName = MutableLiveData<String>()
    val heroName: LiveData<String> = _heroName
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error


    fun fetchCharacters(heroId: Int) {
        viewModelScope.launch {
            try {
                val response = MarvelApi.Marvel_Api_Service.getCharacter(id = heroId)
                if (response.isSuccessful) {
                    _character.value = response.body()?.data?.results
                    _heroImage.value = response.body()?.data?.results?.get(0)?.thumbnail?.getUrl().toString()
                    _heroName.value = response.body()?.data?.results?.get(0)?.name.toString()
                  //  Log.i("Success in View Model", "Character details: $character")
                }
            } catch (e: Exception) {
                _error.value = "Failed to fetch character details: ${e.message}"
            }

        }
    }
}