package com.example.mvvmcounrtyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmcounrtyapp.R
import com.example.mvvmcounrtyapp.databinding.ItemCountryBinding
import com.example.mvvmcounrtyapp.model.Counrty
import com.example.mvvmcounrtyapp.util.downloadFromUrl
import com.example.mvvmcounrtyapp.util.placeholderProgressBar
import com.example.mvvmcounrtyapp.view.FeedFragmentDirections



class CountryAdapter(val counrtyList: ArrayList<Counrty>) : RecyclerView.Adapter<CountryAdapter.CountryHolder>() {
    class CountryHolder(val binding: ItemCountryBinding):RecyclerView.ViewHolder(binding.root),CountryClickListener{
        override fun onCountryClicked( view: View) {

            val uuid = binding.CountryUuid.text.toString().toInt()

            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)

            Navigation.findNavController(view).navigate(action)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val inflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ItemCountryBinding>(inflater, R.layout.item_country,parent,false)
        return CountryHolder(binding)
    }

    override fun getItemCount(): Int {
       return counrtyList.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {

        holder.binding.country = counrtyList[position]
        holder.binding.dinleme = holder

        /*
        holder.binding.name.text = counrtyList[position].counrtyName
        holder.binding.region.text = counrtyList[position].countryRegion

        holder.binding.imageView.setOnClickListener {

        }
        holder.binding.imageView.downloadFromUrl(counrtyList[position].imageUrl,
            placeholderProgressBar(holder.binding.root.context)
        )

    }

         */
    }
    fun updateCountryList(newCountryList:List<Counrty>) {
        counrtyList.clear()
        counrtyList.addAll(newCountryList)
        notifyDataSetChanged()

    }






}