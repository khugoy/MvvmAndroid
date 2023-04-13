package com.sapient.demoapp.domain.interactor


import com.sapient.demoapp.domain.repository.CharacterRepository
import javax.inject.Inject


class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(params: Int) = characterRepository.getCharacter(params)
}
