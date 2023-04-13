package com.sapient.demoapp.domain.interactor

import com.google.gson.Gson
import com.sapient.demoapp.constant.AppConstants
import com.sapient.demoapp.core.MockFileReader
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
class CharacterDetailUseCaseTest {

    private val ID = 1

    private val fileName = "/characterDetail.json"

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val characterRepository = mockk<CharacterRepositoryImp>()

    private val characterDetailUseCase by lazy {
        GetCharacterByIdUseCase(
            characterRepository
        )
    }

    @Test
    fun `Given response data when invoke use case expect result has data`() = runTest {

        val detailModel = Gson().fromJson(
            MockFileReader().getResponseFromJson(fileName),
            CharacterDomainModel::class.java
        )
        val flow = flow {
            emit(Resource.OnSuccess(detailModel))
        }

        coEvery { characterRepository.getCharacter(ID) } returns flow

        val response = characterDetailUseCase.invoke(ID).first()

        Assert.assertTrue(response is Resource.OnSuccess<*>)
    }

    @Test
    fun `fetch failure data`() = runTest {

        val flow = flow {
            emit(Resource.OnFailure(Throwable(AppConstants.NETWORK_ERROR)))
        }

        coEvery { characterRepository.getCharacter(ID) } returns flow

        val response = characterDetailUseCase.invoke(ID).first()

        Assert.assertTrue(response is Resource.OnFailure)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}