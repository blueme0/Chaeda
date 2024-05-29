package com.chaeda.chaeda.di

import com.chaeda.data.repository.AssignmentRepositoryImpl
import com.chaeda.data.repository.ImageRepositoryImpl
import com.chaeda.data.repository.MemberRepositoryImpl
import com.chaeda.data.repository.ReviewRepositoryImpl
import com.chaeda.data.repository.StatisticsRepositoryImpl
import com.chaeda.data.repository.TextbookRepositoryImpl
import com.chaeda.domain.repository.AssignmentRepository
import com.chaeda.domain.repository.ImageRepository
import com.chaeda.domain.repository.MemberRepository
import com.chaeda.domain.repository.ReviewRepository
import com.chaeda.domain.repository.StatisticsRepository
import com.chaeda.domain.repository.TextbookRepository
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
    fun bindHomeworkRepository(homeworkRepositoryImpl: AssignmentRepositoryImpl): AssignmentRepository

    @Singleton
    @Binds
    fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository

    @Singleton
    @Binds
    fun bindMemberRepository(memberRepositoryImpl: MemberRepositoryImpl): MemberRepository

    @Singleton
    @Binds
    fun bindTextbookRepository(textbookRepositoryImpl: TextbookRepositoryImpl): TextbookRepository

    @Singleton
    @Binds
    fun bindStatisticsRepository(statisticsRepositoryImpl: StatisticsRepositoryImpl): StatisticsRepository

    @Singleton
    @Binds
    fun bindReviewRepository(reviewRepositoryImpl: ReviewRepositoryImpl): ReviewRepository
}