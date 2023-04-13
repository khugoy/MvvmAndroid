package com.sapient.demoapp.data.repository

import com.sapient.demoapp.data.api.CharacterService
import com.sapient.demoapp.data.mapper.CharacterMapper
import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.domain.repository.CharacterRepository
import com.sapient.demoapp.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepositoryImp @Inject constructor(
    private val service: CharacterService,
    private val characterMapper: CharacterMapper,
) : CharacterRepository {

    override suspend fun getCharacters(): Flow<Resource<List<CharacterDomainModel>>> = flow {
        try {
            val characterList = service.getCharacters().results.map { characterEntity ->
                characterMapper.mapFromModel(characterEntity)
            }
            emit(Resource.OnSuccess(characterList))
        } catch (e: Exception) {
            emit(Resource.OnFailure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCharacter(characterId: Int): Flow<Resource<CharacterDomainModel>> = flow {
        try {
            var character = service.getCharacter(characterId)
            emit(Resource.OnSuccess(characterMapper.mapFromModel(character)))
        } catch (e: Exception) {
            emit(Resource.OnFailure(e))
        }
    }.flowOn(Dispatchers.IO)
}
