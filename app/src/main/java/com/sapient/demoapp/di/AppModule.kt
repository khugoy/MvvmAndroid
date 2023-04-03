package com.sapient.demoapp.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sapient.demoapp.R
import com.sapient.demoapp.constant.AppConstants
import com.sapient.demoapp.data.api.CharacterService
import com.sapient.demoapp.data.api.ServiceFactory
import com.sapient.demoapp.data.mapper.CharacterMapper
import com.sapient.demoapp.data.repository.CharacterRepositoryImp
import com.sapient.demoapp.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Provides
    @Singleton
    fun provideCharacterService(): CharacterService {
        return ServiceFactory.create(BuildConfig.DEBUG, AppConstants.BASE_URL)
    }

    @Provides
    @Singleton
    fun provideCharRepository(api: CharacterService) : CharacterRepository {
        return CharacterRepositoryImp(api, CharacterMapper())
    }
}