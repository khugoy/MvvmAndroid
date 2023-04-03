package com.sapient.demoapp.domain.interactor


import com.sapient.demoapp.domain.models.Character
import com.sapient.demoapp.domain.repository.CharacterRepository
import com.sapient.demoapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetCharacterByIdBaseUseCase = BaseUseCase<Int, Flow<Resource<Character>>>

class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharacterByIdBaseUseCase {

    override suspend operator fun invoke(params: Int) = characterRepository.getCharacter(params)
}
