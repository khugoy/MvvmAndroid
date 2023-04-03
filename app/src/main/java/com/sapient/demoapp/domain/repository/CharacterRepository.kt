package com.sapient.demoapp.domain.repository

import kotlinx.coroutines.flow.Flow
import com.sapient.demoapp.domain.models.Character
import com.sapient.demoapp.domain.util.Resource

interface CharacterRepository {
    suspend fun getCharacters(): Flow<Resource<List<Character>>>
    suspend fun getCharacter(characterId: Int): Flow<Resource<Character>>
}