package com.sapient.demoapp.domain.repository

import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(): Flow<Resource<List<CharacterDomainModel>>>
    suspend fun getCharacter(characterId: Int): Flow<Resource<CharacterDomainModel>>
}