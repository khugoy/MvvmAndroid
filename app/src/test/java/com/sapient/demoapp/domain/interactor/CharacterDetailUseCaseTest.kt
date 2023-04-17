package com.sapient.demoapp.domain.interactor

import com.google.gson.Gson
import com.sapient.demoapp.core.MockFileReader
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
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterDetailUseCaseTest {

    private val characterRepository = mockk<CharacterRepositoryImp>()

    private val characterDetailUseCase by lazy {
        GetCharacterByIdUseCase(
            characterRepository
        )
    }

    @Test
    fun `Given response data when invoke use case expect result has data`() = runTest {

        val detailModel = Gson().fromJson(
            MockFileReader().getResponseFromJson(MockFileReader.detail_fileName),
            CharacterDomainModel::class.java
        )
        val flow = flow {
            emit(Resource.OnSuccess(detailModel))
        }

        coEvery { characterRepository.getCharacter(MockFileReader.ID) } returns flow

        val response = characterDetailUseCase.invoke(MockFileReader.ID).first()

        Assert.assertTrue(response is Resource.OnSuccess<*>)
    }

    @Test
    fun `fetch failure data`() = runTest {

        val flow = flow {
            emit(Resource.OnFailure(Throwable(MockFileReader.NETWORK_ERROR)))
        }

        coEvery { characterRepository.getCharacter(MockFileReader.ID) } returns flow

        val response = characterDetailUseCase.invoke(MockFileReader.ID).first()

        Assert.assertTrue(response is Resource.OnFailure)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}