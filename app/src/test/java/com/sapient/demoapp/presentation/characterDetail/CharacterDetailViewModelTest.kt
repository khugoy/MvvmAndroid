package com.sapient.demoapp.presentation.characterDetail

import com.sapient.demoapp.core.MockResponse
import com.sapient.demoapp.core.MockResponse.ID
import com.sapient.demoapp.core.TestCoroutineRule
import com.sapient.demoapp.domain.interactor.GetCharacterByIdUseCase
import com.sapient.demoapp.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
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
        CharacterDetailViewModel(useCase)
    }

    @Test
    fun `GET character detail success`() = runTest {
        coEvery { useCase.invoke(ID) } returns MockResponse.getDetailData()
        viewModel.start(ID)
        val result = viewModel.state.value.character
        assertEquals(1, result?.id)
        assertEquals("Rick", result?.name)

    }

    @Test
    fun `GET character List loading`() = runTest {
        coEvery { useCase.invoke(ID) } returns  flow { emit(Resource.Loading()) }
        viewModel.start(ID)
        val result = viewModel.state.value.isLoading
        assertEquals(true, result)
    }

    @Test
    fun `GET character List error`() = runTest {
        coEvery { useCase.invoke(ID) } returns flow { emit(Resource.Error()) }
        viewModel.start(ID)
        val result = viewModel.state.value.isLoading
        assertEquals(false, result)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }
}