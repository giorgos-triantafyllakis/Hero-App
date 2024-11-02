package com.example.heroapp.network


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import com.example.heroapp.network.dataClasses.MarvelApiResponse
import com.example.heroapp.network.MarvelApiConstants.BASE_URL
import com.example.heroapp.network.MarvelApiConstants.MARVEL_API_HASH
import com.example.heroapp.network.MarvelApiConstants.MARVEL_API_PUBLIC_KEY
import com.example.heroapp.network.MarvelApiConstants.MARVEL_API_TS
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("/v1/public/characters?apikey=$MARVEL_API_PUBLIC_KEY&ts=$MARVEL_API_TS&hash=$MARVEL_API_HASH")
    suspend fun getAllCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit :Int
    ): Response<MarvelApiResponse>
    @GET("/v1/public/characters/{id}?apikey=$MARVEL_API_PUBLIC_KEY&ts=$MARVEL_API_TS&hash=$MARVEL_API_HASH")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): Response<MarvelApiResponse>
}


object RetrofitHelper {

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}

object MarvelApi {
    var Marvel_Api_Service: ApiService = RetrofitHelper.getInstance().create(ApiService::class.java)
}


