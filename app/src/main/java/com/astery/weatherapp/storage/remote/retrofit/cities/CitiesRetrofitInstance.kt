package com.astery.weatherapp.storage.remote.retrofit.cities

import com.astery.weatherapp.BuildConfig
import com.astery.weatherapp.app.di.Prod
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CitiesRetrofitInstance @Inject constructor() {

    private val retrofit: Retrofit by lazy {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .followSslRedirects(true)
            .followRedirects(true)
            .build()


        Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_API_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val api: CitiesApi by lazy {
        retrofit.create(CitiesApi::class.java)
    }
}
