package com.sapient.demoapp.di

import com.sapient.demoapp.data.api.CharacterService
import com.sapient.demoapp.data.mapper.CharacterMapper
import com.sapient.demoapp.data.repository.CharacterRepositoryImp
import com.sapient.demoapp.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideCharRepository(api: CharacterService): CharacterRepository {
        return CharacterRepositoryImp(api, CharacterMapper())
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}