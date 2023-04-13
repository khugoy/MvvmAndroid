package com.sapient.demoapp.di

import androidx.viewbinding.BuildConfig
import com.sapient.demoapp.constant.AppConstants
import com.sapient.demoapp.data.api.CharacterService
import com.sapient.demoapp.data.api.ServiceFactory
import com.sapient.demoapp.data.mapper.CharacterMapper
import com.sapient.demoapp.data.repository.CharacterRepositoryImp
import com.sapient.demoapp.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideCharacterService(): CharacterService =
         ServiceFactory.create(BuildConfig.DEBUG, AppConstants.BASE_URL)


    @Provides
    fun provideCharRepository(api: CharacterService) : CharacterRepository {
        return CharacterRepositoryImp(api, CharacterMapper())
    }
}