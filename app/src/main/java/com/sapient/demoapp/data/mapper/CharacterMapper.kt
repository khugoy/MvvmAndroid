package com.sapient.demoapp.data.mapper


import com.sapient.demoapp.data.dto.CharacterDto
import com.sapient.demoapp.domain.models.Character
import javax.inject.Inject

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
