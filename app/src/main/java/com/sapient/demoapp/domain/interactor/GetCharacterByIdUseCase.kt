package com.sapient.demoapp.domain.interactor


import com.sapient.demoapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.sapient.demoapp.domain.models.Character
import com.sapient.demoapp.domain.util.Resource

typealias GetCharacterByIdBaseUseCase = BaseUseCase<Int, Flow<Resource<Character>>>

class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharacterByIdBaseUseCase {

    override suspend operator fun invoke(params: Int) = characterRepository.getCharacter(params)
}
