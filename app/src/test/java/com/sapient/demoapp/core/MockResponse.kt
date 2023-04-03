package com.sapient.demoapp.core


import com.sapient.demoapp.data.dto.CharacterDto
import com.sapient.demoapp.data.dto.CharacterResponseDto
import com.sapient.demoapp.data.mapper.CharacterMapper
import com.sapient.demoapp.domain.models.Character
import com.sapient.demoapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

object MockResponse {

    const val ID = 1

    private fun getCharacterList(): List<Character> {
        val character = CharacterMapper().mapFromModel(getCharacterDto())
        return listOf(character)
    }


    private fun getCharacterDto() : CharacterDto {
        return  CharacterDto(
            name = "Rick",
            species = "human",
            gender = "male",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            status = "Alive",
            url = "test.com",
            type = "test",
            id = 1,
            created = "monday"
        )
    }

    fun getResponseModel() : CharacterResponseDto {
        val characterModel = getCharacterDto()
        return CharacterResponseDto(results = listOf(characterModel))
    }

    fun getListData(): Flow<Resource<List<Character>>> = channelFlow {
        send(Resource.Success(getCharacterList()))
    }


    fun getDetailData(): Flow<Resource<Character>> = channelFlow {
        send(Resource.Success(getCharacterList().get(0)))
    }

}