package com.example.mvvmcounrtyapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mvvmcounrtyapp.model.Counrty

@Dao
interface CountryDao {
    //Data Access Object
    @Insert
    suspend fun insertAll(vararg countries : Counrty) : List<Long>

    //Insert - INSERT INTO
    //suspend -> Coroutine , pause &resume
    //vararg -> multiple country objects
    //List<Long>-> primary keys


    @Query("SELECT * FROM counrty")
    suspend fun getAllCountries(): List<Counrty>
    @Query("SELECT * FROM counrty WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int) : Counrty
    @Query("DELETE FROM counrty ")
    suspend fun deleteAllCountries()
}