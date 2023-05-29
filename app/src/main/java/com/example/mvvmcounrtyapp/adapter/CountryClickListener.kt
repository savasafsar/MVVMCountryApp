package com.example.mvvmcounrtyapp.adapter

import android.view.View
import com.example.mvvmcounrtyapp.databinding.ItemCountryBinding

interface CountryClickListener {
    fun onCountryClicked(view:View)
}