package com.example.architectureproject.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CountryService {
    val BASE_URL = "https://restcountries.eu/rest/v2/"

    private var api: CountriesApi? = null

    fun CountriesService() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        api = retrofit.create<CountriesApi>(CountriesApi::class.java)
    }

    fun getCountries(): Single<List<Country?>?>? {
        return api?.getCountries()
    }
}