package com.sapient.demoapp.data.repository

import com.sapient.demoapp.data.api.CharacterService
import com.sapient.demoapp.data.mapper.CharacterMapper
import com.sapient.demoapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.sapient.demoapp.domain.models.Character
import com.sapient.demoapp.domain.util.Resource

class CharacterRepositoryImp @Inject constructor(
    private val service: CharacterService,
    private val characterMapper: CharacterMapper,
) : CharacterRepository {

    override suspend fun getCharacters(): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())
        try {
            val characterList = service.getCharacters().results.map { characterEntity ->
                characterMapper.mapFromModel(characterEntity)
            }
            emit(Resource.Success(characterList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    override suspend fun getCharacter(characterId: Int): Flow<Resource<Character>> = flow {
        emit(Resource.Loading())
        try {
            var character = service.getCharacter(characterId)
            emit(Resource.Success(characterMapper.mapFromModel(character)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}
