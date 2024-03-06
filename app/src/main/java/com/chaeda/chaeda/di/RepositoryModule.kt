package com.chaeda.chaeda.di

import com.chaeda.data.repository.HomeworkRepositoryImpl
import com.chaeda.data.repository.ImageRepositoryImpl
import com.chaeda.data.repository.SampleRepositoryImpl
import com.chaeda.domain.repository.HomeworkRepository
import com.chaeda.domain.repository.ImageRepository
import com.chaeda.domain.repository.SampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindSampleRepository(sampleRepositoryImpl: SampleRepositoryImpl): SampleRepository

    @Singleton
    @Binds
    fun bindHomeworkRepository(homeworkRepositoryImpl: HomeworkRepositoryImpl): HomeworkRepository

    @Singleton
    @Binds
    fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository
}