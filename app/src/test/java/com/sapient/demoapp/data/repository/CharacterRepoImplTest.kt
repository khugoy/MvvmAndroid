package com.sapient.demoapp.data.repository

import com.sapient.demoapp.core.MockResponse
import com.sapient.demoapp.core.MockResponse.ID
import com.sapient.demoapp.core.TestCoroutineRule
import com.sapient.demoapp.data.api.CharacterService
import com.sapient.demoapp.data.mapper.CharacterMapper
import com.sapient.demoapp.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterRepoImplTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val service = mockk<CharacterService>()

    private val repositoryImp by lazy {
        CharacterRepositoryImp(
            service, CharacterMapper()
        )
    }
    @Test
    fun `Given response data when invoke repository has data`() =
        runBlocking {
        coEvery { service.getCharacters() } returns MockResponse.getResponseModel()

        repositoryImp.getCharacters().collect{

        }
        repositoryImp.getCharacters().onEach { result ->
            when (result) {

                is Resource.Loading -> {
                  println("Loading")
                }
                is Resource.Success -> {
                    println("success")
                    assertEquals(ID, result.data?.get(0)?.name)
                }
                is Resource.Error -> {
                    println("error")
                    assertEquals(ID, result.data?.get(0)?.name)
                }

            }

        //val first = repositoryImp.getCharacters().drop(1).first()
        //assertEquals(ID, first.data?.get(0)?.id)

        }
    }


    @After
    fun tearDown(){
        unmockkAll()
    }
}