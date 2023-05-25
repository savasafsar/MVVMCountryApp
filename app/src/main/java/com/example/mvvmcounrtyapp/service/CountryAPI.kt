package com.example.mvvmcounrtyapp.service

import android.telecom.Call
import com.example.mvvmcounrtyapp.model.Counrty
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {


    //BASE_URL ->https://raw.githubusercontent.com/
    //EXT - atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //GET,POST

   @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
   fun getCountries() : Single<List<Counrty>>





}