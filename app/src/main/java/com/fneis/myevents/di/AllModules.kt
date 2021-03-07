package com.fneis.myevents.di

import com.fneis.myevents.repository.`interface`.CheckInRepository
import com.fneis.myevents.repository.`interface`.EventRepository
import com.fneis.myevents.repository.api.EventApi
import com.fneis.myevents.repository.api.implementation.CheckinRepositoryApi
import com.fneis.myevents.repository.api.implementation.EventRepositoryApi
import com.fneis.myevents.ui.features.event.checkIn.CheckInViewModel
import com.fneis.myevents.ui.features.event.list.EventViewModel
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val eventModule = module {
    factory {
        EventRepositoryApi(get()) as EventRepository
    }

    factory {
        CheckinRepositoryApi(get()) as CheckInRepository
    }
}

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideEventApi(get()) }
    single { provideRetrofit(get()) }
}

val viewModelModule = module {
    single { EventViewModel(get()) }
    single { CheckInViewModel(get()) }
}

val allModules = listOf(networkModule, eventModule, viewModelModule)

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("http://5f5a8f24d44d640016169133.mockapi.io/api/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

fun provideEventApi(retrofit: Retrofit): EventApi = retrofit.create(EventApi::class.java)
