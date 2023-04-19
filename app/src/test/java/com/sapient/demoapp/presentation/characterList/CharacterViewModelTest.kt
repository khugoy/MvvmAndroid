package com.sapient.demoapp.presentation.characterList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.sapient.demoapp.core.MockFileReader
import com.sapient.demoapp.core.TestCoroutineRule
import com.sapient.demoapp.data.mapper.CharacterMapper
import com.sapient.demoapp.data.models.CharacterResponseDataModel
import com.sapient.demoapp.domain.interactor.GetCharacterListUseCase
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
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterViewModelTest {
    @get:Rule
    val instantTaskExecutorRule : TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule: TestCoroutineRule = TestCoroutineRule()

    private val useCase = mockk<GetCharacterListUseCase>()

    private val viewModel: CharactersViewModel by lazy {
        CharactersViewModel(useCase, Dispatchers.Main)
    }


    @Test
    fun `GET character success`() = runTest {

        val listModel = Gson().fromJson(
                MockFileReader().getResponseFromJson(MockFileReader.list_fileName), CharacterResponseDataModel::class.java)

        val characterMapper = CharacterMapper()

        val characterList = listModel.results.map { characterEntity ->
            characterMapper.mapFromModel(characterEntity)
        }


        val flow = flow {
            emit(Resource.OnSuccess(characterList))
        }

        coEvery { useCase.invoke() } returns flow

        viewModel.getCharacterList()

        val result = viewModel.characterList.value as UIState.Success

        assertEquals(MockFileReader.TWO, result.output.size)

    }

    @Test
    fun `GET character list failure`() = runTest {

        val flow = flow {
            emit(Resource.OnFailure(Throwable(MockFileReader.NETWORK_ERROR)))
        }

        coEvery { useCase.invoke() } returns flow

        viewModel.getCharacterList()

        val result = viewModel.characterList.value as UIState.Failure

        assertEquals(MockFileReader.NETWORK_ERROR, result.throwable.message)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }
}