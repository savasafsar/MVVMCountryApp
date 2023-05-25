package com.example.mvvmcounrtyapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.util.wrapMappedColumns
import com.example.mvvmcounrtyapp.model.Counrty
import com.example.mvvmcounrtyapp.service.CountryAPIService
import com.example.mvvmcounrtyapp.service.CountryDatabase
import com.example.mvvmcounrtyapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application){
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private val customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 0.1*60 * 1000 * 1000 * 1000L


    val countries = MutableLiveData<List<Counrty>>() //Değiştirilebilir
    val countryError  = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customPreferences.getTime()
        if (updateTime !=null && updateTime !=0L && System.nanoTime()-updateTime< refreshTime) {
          getDataFromSQLite()
         } else {
            getDataFromAPI()
        }


    }

    private fun getDataFromSQLite(){
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_SHORT).show()
        }
    }


    private fun getDataFromAPI() {
        countryLoading.value = true
        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Counrty>>(){
                    override fun onSuccess(t: List<Counrty>) {
                        stroeInSQLite(t)
                        Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value= false
                        countryError.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList:List<Counrty>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value =false
    }

    private fun stroeInSQLite(list:List<Counrty>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
          val listLong =  dao.insertAll(*list.toTypedArray())
            var i = 0
            while(i<list.size) {
                list[i].uuid = listLong[i].toInt()
                i=i+1

            }
            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime())

    }

}