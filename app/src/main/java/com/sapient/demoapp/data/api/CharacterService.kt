package com.sapient.demoapp.data.api


import com.sapient.demoapp.constant.AppConstants
import com.sapient.demoapp.data.models.CharacterDataModel
import com.sapient.demoapp.data.models.CharacterResponseDataModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character")
    suspend fun getCharacters() : CharacterResponseDataModel

    @GET("character/{id}")
    suspend fun getCharacter(@Path(AppConstants.ID) id: Int): CharacterDataModel
}