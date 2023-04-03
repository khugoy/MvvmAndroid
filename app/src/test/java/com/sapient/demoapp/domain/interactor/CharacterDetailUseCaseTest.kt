package com.sapient.demoapp.domain.interactor

import com.sapient.demoapp.core.MockResponse
import com.sapient.demoapp.core.MockResponse.ID
import com.sapient.demoapp.core.TestCoroutineRule
import com.sapient.demoapp.data.repository.CharacterRepositoryImp
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterDetailUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val characterRepository = mockk<CharacterRepositoryImp>(relaxed = true)

    private val characterDetailUseCase by lazy {
        GetCharacterByIdUseCase(
            characterRepository
        )
    }
    @Test
    fun `Given response data when invoke use case expect result has data`() = runTest {
        coEvery { characterRepository.getCharacter(ID) } returns MockResponse.getDetailData()

        val first = characterDetailUseCase.invoke(ID).first()

        assertEquals(ID, first.data?.id)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }
}