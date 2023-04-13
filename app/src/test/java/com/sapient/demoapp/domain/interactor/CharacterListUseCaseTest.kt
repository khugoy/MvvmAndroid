package com.sapient.demoapp.domain.interactor

import com.sapient.demoapp.core.TestCoroutineRule
import com.sapient.demoapp.data.repository.CharacterRepositoryImp
import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterListUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val characterRepository = mockk<CharacterRepositoryImp>()

    private val characterListUseCase by lazy {
        GetCharacterListUseCase(
            characterRepository
        )
    }

    @Test
    fun `Given response data when invoke use case expect result has data`() = runTest {
        val flow = flow {
            emit(Resource.OnSuccess(emptyList<CharacterDomainModel>()))
        }

        coEvery { characterRepository.getCharacters() } returns flow

        val response = characterListUseCase.invoke().first()

        Assert.assertTrue(response is Resource.OnSuccess<*>)
    }

    @Test
    fun `fetch failure data`() = runTest {

        val flow = flow {
            emit(Resource.OnFailure(Throwable("Error")))
        }

        coEvery { characterRepository.getCharacters() } returns flow

        val response = characterListUseCase.invoke().first()

        Assert.assertTrue(response is Resource.OnFailure)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}