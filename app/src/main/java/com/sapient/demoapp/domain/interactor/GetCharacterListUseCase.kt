package com.sapient.demoapp.domain.interactor

import com.sapient.demoapp.domain.models.Character
import com.sapient.demoapp.domain.repository.CharacterRepository
import com.sapient.demoapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetCharacterListBaseUseCase = BaseUseCase<Unit, Flow<Resource<List<Character>>>>

class GetCharacterListUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharacterListBaseUseCase {

    override suspend operator fun invoke(params: Unit) = characterRepository.getCharacters()
}
