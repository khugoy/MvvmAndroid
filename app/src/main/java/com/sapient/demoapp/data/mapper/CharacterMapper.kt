package com.sapient.demoapp.data.mapper


import com.sapient.demoapp.data.models.CharacterDataModel
import com.sapient.demoapp.domain.models.CharacterDomainModel
import javax.inject.Inject

class CharacterMapper @Inject constructor() : Mapper<CharacterDataModel, CharacterDomainModel> {
    override fun mapFromModel(type: CharacterDataModel): CharacterDomainModel {
        return CharacterDomainModel(
            gender = type.gender,
            id = type.id,
            image = type.image,
            name = type.name,
            species = type.species,
            status = type.status
        )
    }
}
