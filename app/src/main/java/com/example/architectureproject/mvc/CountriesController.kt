package com.example.architectureproject.mvc

import android.annotation.SuppressLint
import com.example.architectureproject.model.Country
import com.example.architectureproject.model.CountryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountriesController(val view: MVCActivity) {

    private var countryService: CountryService = CountryService()

    init {
        fetchCountries()
    }

    @SuppressLint("CheckResult")
    private fun fetchCountries() {
        countryService.getCountries()
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<List<Country?>?>() {
                override fun onSuccess(t: List<Country?>) {
                    val countryNames: ArrayList<String> = ArrayList()
                    for (country in t) {
                        countryNames.add(country!!.countryName!!)
                    }
                    view.setValues(countryNames)
                }

                override fun onError(e: Throwable) {
                    view.onError()
                }
            })
    }

    fun onRefresh(){
        fetchCountries()
    }
}