package com.example.architectureproject.mvvm

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.architectureproject.model.Country
import com.example.architectureproject.model.CountryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountriesViewModel: ViewModel() {

    private val countries = MutableLiveData<List<String>>()
    private val countryError = MutableLiveData<Boolean>()

    private var service: CountryService? = null

    init {
        service = CountryService()
        fetchCountries()
    }

    fun getCountries(): LiveData<List<String>>? {
        return countries
    }

    fun getCountryError(): LiveData<Boolean>? {
        return countryError
    }

    @SuppressLint("CheckResult")
    private fun fetchCountries() {
        service?.getCountries()
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<List<Country?>?>() {
                override fun onSuccess(value: List<Country?>) {
                    val countryNames: MutableList<String> = ArrayList()
                    for (country in value) {
                        countryNames.add(country!!.countryName!!)
                    }
                    countries.value = countryNames
                    countryError.value = false
                }

                override fun onError(e: Throwable) {
                    countryError.value = true
                }
            })
    }

    fun onRefresh() {
        fetchCountries()
    }
}