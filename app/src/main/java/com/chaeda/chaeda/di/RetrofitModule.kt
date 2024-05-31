package com.chaeda.chaeda.di

import com.chaeda.chaeda.BuildConfig.BASE_URL
import com.chaeda.chaeda.BuildConfig.IMAGE_URL
import com.chaeda.chaeda.addFlipperNetworkPlugin
import com.chaeda.chaeda.di.qualifier.Auth
import com.chaeda.chaeda.di.qualifier.Logger
import com.chaeda.data.interceptor.AuthInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val APPLICATION_JSON = "application/json"

    @Provides
    @Singleton
    @Logger
    fun provideHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthInterceptor(interceptor: AuthInterceptor): Interceptor = interceptor

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    @Named("ChaedaOkHttpClient")
    fun provideChaedaOkHttpClient(
        @Logger loggingInterceptor: Interceptor,
        @Auth authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .addFlipperNetworkPlugin()
        .build()

    @Provides
    @Singleton
    @Named("FileOkHttpClient")
    fun provideFileOkHttpClient(
        @Logger loggingInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addFlipperNetworkPlugin()
        .build()

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(APPLICATION_JSON.toMediaType())

    @Provides
    @Singleton
    @Named("ChaedaRetrofit")
    fun provideChaedaRetrofit(
        @Named("ChaedaOkHttpClient") client: OkHttpClient,
        factory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @Singleton
    @Named("FileRetrofit")
    fun provideFileRetrofit(
        @Named("FileOkHttpClient") client: OkHttpClient,
        factory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(IMAGE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()
}