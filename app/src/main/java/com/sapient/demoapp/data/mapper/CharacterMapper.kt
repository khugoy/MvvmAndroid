package com.sapient.demoapp.data.mapper


import com.sapient.demoapp.data.dto.CharacterDto
import javax.inject.Inject
import com.sapient.demoapp.domain.models.Character

class CharacterMapper @Inject constructor() : Mapper<CharacterDto, Character> {
    override fun mapFromModel(type: CharacterDto): Character {
        return Character(
            gender = type.gender,
            id = type.id,
            image = type.image,
            name = type.name,
            species = type.species,
            status = type.status
        )
    }
}
