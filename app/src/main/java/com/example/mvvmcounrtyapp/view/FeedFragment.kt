package com.example.mvvmcounrtyapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmcounrtyapp.R
import com.example.mvvmcounrtyapp.adapter.CountryAdapter
import com.example.mvvmcounrtyapp.databinding.FragmentFeedBinding
import com.example.mvvmcounrtyapp.viewmodel.FeedViewModel

class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: FragmentFeedBinding
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter = countryAdapter

        binding.swiperefresh.setOnRefreshListener {
            binding.countryList.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
           viewModel.refreshData()
            binding.swiperefresh.isRefreshing = false
            Toast.makeText(requireContext(),"Reflesh Oke",Toast.LENGTH_SHORT).show()




        }


        observeLiveData()


        // Diğer view elemanlarına binding üzerinden erişebilirsiniz
        // binding.textView.text = "Hello, World!"
    }



  private  fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries->
            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }


        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if (it){
                    binding.countryError.visibility = View.VISIBLE
                } else{
                    binding.countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {loading->
        loading?.let {
            if (it){
                binding.countryLoading.visibility = View.VISIBLE
                binding.countryList.visibility =  View.GONE
                binding.countryError.visibility = View.GONE
            }else {
                binding.countryLoading.visibility = View.GONE

            }
        }

        })
    }
}
