package com.example.mvvmcounrtyapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmcounrtyapp.model.Counrty
import com.example.mvvmcounrtyapp.service.CountryDatabase
import kotlinx.coroutines.launch
import java.util.UUID

class CountryViewModel(application: Application) : BaseViewModel(application) {
    val countryLiveData  = MutableLiveData<Counrty>()
    fun getDataFromRoom(uuid: Int) {
       launch {

           val dao  = CountryDatabase(getApplication()).countryDao()
          val country= dao.getCountry(uuid)
           countryLiveData.value = country

       }
    }
}