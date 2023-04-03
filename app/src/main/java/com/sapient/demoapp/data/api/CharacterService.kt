package com.sapient.demoapp.data.api


import com.sapient.demoapp.data.dto.CharacterDto
import com.sapient.demoapp.data.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character")
    suspend fun getCharacters() : CharacterResponseDto

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterDto
}