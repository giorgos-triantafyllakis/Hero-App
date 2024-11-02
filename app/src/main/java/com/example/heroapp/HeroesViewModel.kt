package com.example.heroapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.heroapp.network.MarvelApi
import com.example.heroapp.network.dataClasses.CharacterRestModel
import com.example.heroapp.paging.HeroesPagingSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HeroesViewModel   : ViewModel() {
     private val _charactersLiveData = MutableLiveData<PagingData<CharacterRestModel>>()
             val charactersLiveData: LiveData<PagingData<CharacterRestModel>>
             get() = _charactersLiveData

    init {
        viewModelScope.launch {
            Pager(
                PagingConfig(pageSize = HeroesPagingSource.PAGE_SIZE)
            ) {
                HeroesPagingSource(MarvelApi) //Load() is used here
            }.flow.cachedIn(viewModelScope).collectLatest {
                _charactersLiveData.value = it
            }
        }
    }
}
