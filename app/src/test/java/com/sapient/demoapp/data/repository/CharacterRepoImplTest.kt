package com.sapient.demoapp.data.repository

import com.sapient.demoapp.core.MockFileReader
import com.sapient.demoapp.data.api.CharacterService
import com.sapient.demoapp.data.mapper.CharacterMapper
import com.sapient.demoapp.data.models.CharacterDataModel
import com.sapient.demoapp.data.models.CharacterResponseDataModel
import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CharacterRepositoryImpTest {

    private lateinit var repository: CharacterRepositoryImp
    private val service: CharacterService = mockk()
    private val characterMapper: CharacterMapper = mockk()

    @Before
    fun setup() {
        repository = CharacterRepositoryImp(service, characterMapper)
    }

    @Test
    fun `getCharacters should return list of characters on success`() = runBlocking {
        // Given
        val characters = listOf(mockk<CharacterDataModel>())
        val characterDomainModels = listOf(mockk<CharacterDomainModel>())
        coEvery { service.getCharacters() } returns CharacterResponseDataModel(results = characters)
        coEvery { characterMapper.mapFromModel(any()) } returnsMany characterDomainModels

        // When
        val result = mutableListOf<Resource<List<CharacterDomainModel>>>()
        repository.getCharacters().collect { result.add(it) }

        // Then
        assertEquals(MockFileReader.ONE, result.size)
        assertTrue(result[0] is Resource.OnSuccess)
        assertEquals(characterDomainModels, (result[0] as Resource.OnSuccess).data)
    }

    @Test
    fun `getCharacters should return error on failure`() = runBlocking {
        // Given
        val exception = RuntimeException(MockFileReader.NETWORK_ERROR)
        coEvery { service.getCharacters() } throws exception

        // When
        val result = mutableListOf<Resource<List<CharacterDomainModel>>>()
        repository.getCharacters().collect { result.add(it) }

        // Then
        assertEquals(MockFileReader.ONE, result.size)
        assertTrue(result[0] is Resource.OnFailure)
        assertEquals(exception, (result[0] as Resource.OnFailure).throwable)
    }

    @Test
    fun `getCharacter should return character on success`() = runBlocking {
        // Given
        val characterEntity = mockk<CharacterDataModel>()
        val characterDomainModel = mockk<CharacterDomainModel>()
        coEvery { service.getCharacter(MockFileReader.ID) } returns characterEntity
        coEvery { characterMapper.mapFromModel(characterEntity) } returns characterDomainModel

        // When
        val result = mutableListOf<Resource<CharacterDomainModel>>()
        repository.getCharacter(MockFileReader.ID).collect { result.add(it) }

        // Then
        assertEquals(MockFileReader.ONE, result.size)
        assertTrue(result[0] is Resource.OnSuccess)
        assertEquals(characterDomainModel, (result[0] as Resource.OnSuccess).data)
    }

    @Test
    fun `getCharacter should return error on failure`() = runBlocking {
        // Given
        val exception = RuntimeException(MockFileReader.NETWORK_ERROR)
        coEvery { service.getCharacter(MockFileReader.ID) } throws exception

        // When
        val result = mutableListOf<Resource<CharacterDomainModel>>()
        repository.getCharacter(MockFileReader.ID).collect { result.add(it) }

        // Then
        assertEquals(MockFileReader.ONE, result.size)
        assertTrue(result[0] is Resource.OnFailure)
        assertEquals(exception, (result[0] as Resource.OnFailure).throwable)
    }
}
