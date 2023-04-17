package com.sapient.demoapp.di

import androidx.viewbinding.BuildConfig
import com.sapient.demoapp.data.api.CharacterService
import com.sapient.demoapp.data.api.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideCharacterService(): CharacterService =
         ServiceFactory.create(BuildConfig.DEBUG, "https://rickandmortyapi.com/api/")

}