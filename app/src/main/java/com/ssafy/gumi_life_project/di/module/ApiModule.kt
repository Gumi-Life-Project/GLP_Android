package com.ssafy.gumi_life_project.di.module

import com.ssafy.gumi_life_project.data.remote.ApiService
import com.ssafy.gumi_life_project.util.network.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val baseUrl = "https://gumiinsider-ea09851d1f47.herokuapp.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).addInterceptor {
                val request = it.request()
                if (request.url().encodedPath().equals("/board/list", true)
                    || request.url().encodedPath().equals("/weather/", true)
                    || request.url().encodedPath().equals("/board/list/new", true)
                    || request.url().encodedPath().equals("/tip/list", true)
                ) {
                    it.proceed(request)
                } else {
                    it.proceed(request.newBuilder().apply {
                        addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4IiwiZXhwIjoxNjg5MzIwODM2fQ.JG3TW3oymun1iQUgLlGm6ACHQOdN0rmFrUKHjXWg4Nmz7zIEXBQkHj8PI8qIuWbry_M3OMKct_PDaJFe5DYWAw")
                    }.build())
                }
            }.build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}