package com.sapient.demoapp.presentation.characterDetail

import com.google.gson.Gson
import com.sapient.demoapp.core.MockFileReader
import com.sapient.demoapp.core.TestCoroutineRule
import com.sapient.demoapp.domain.interactor.GetCharacterByIdUseCase
import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.domain.util.Resource
import com.sapient.demoapp.presentation.viewState.UIState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterDetailViewModelTest {

    @get:Rule
    var coroutineRule: TestCoroutineRule = TestCoroutineRule()

    private val useCase = mockk<GetCharacterByIdUseCase>()

    private val viewModel: CharacterDetailViewModel by lazy {
        CharacterDetailViewModel(useCase, Dispatchers.Main)
    }

    @Test
    fun `GET character detail success`() = runTest {

        val detailModel = Gson().fromJson(
            MockFileReader().getResponseFromJson(MockFileReader.detail_fileName),
            CharacterDomainModel::class.java
        )

        val flow = flow {
            emit(Resource.OnSuccess(detailModel))
        }

        coEvery { useCase.invoke(MockFileReader.ID) } returns flow

        viewModel.getCharacter(MockFileReader.ID)

        val result  = viewModel.characterDetail.value as UIState.Success

        assertEquals(MockFileReader.ONE, result.output.id)
        assertEquals(MockFileReader.ALIVE, result.output.status)
    }

    @Test
    fun `GET character List error`() = runTest {
        coEvery { useCase.invoke(MockFileReader.ID) } returns flow {
            emit(Resource.OnFailure(Throwable(MockFileReader.NETWORK_ERROR)))
        }

        viewModel.getCharacter(MockFileReader.ID)
        val result = viewModel.characterDetail.value as UIState.Failure

        assertEquals(MockFileReader.NETWORK_ERROR, result.throwable.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}