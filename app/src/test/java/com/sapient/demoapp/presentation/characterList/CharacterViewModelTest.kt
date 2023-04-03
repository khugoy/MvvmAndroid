package com.sapient.demoapp.presentation.characterList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sapient.demoapp.core.MockResponse
import com.sapient.demoapp.core.TestCoroutineRule
import com.sapient.demoapp.domain.interactor.GetCharacterListUseCase
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
        CharactersViewModel(useCase)
    }


    @Test
    fun `GET character List success`() = runTest {
        coEvery { useCase.invoke(Unit) } returns MockResponse.getListData()
        val result = viewModel.state.value.characterList
        assertEquals(1, result.size)
    }

    @Test
    fun `GET character List loading`() = runTest {
        coEvery { useCase.invoke(Unit) } returns  flow { emit(Resource.Loading()) }
        val result = viewModel.state.value.isLoading
        assertEquals(true, result)
    }

    @Test
    fun `GET character List error`() = runTest {
        coEvery { useCase.invoke(Unit) } returns flow { emit(Resource.Error("Error")) }
        val result = viewModel.state.value.isLoading
        assertEquals(false, result)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }
}