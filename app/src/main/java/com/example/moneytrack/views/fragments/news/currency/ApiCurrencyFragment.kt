package com.example.moneytrack.views.fragments.news.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneytrack.databinding.FragmentApiCurrencyBinding
import com.example.moneytrack.network.AvailableCurrencies
import com.example.moneytrack.network.RetrofitProvider


class ApiCurrencyFragment : Fragment() {

    private var _binding: FragmentApiCurrencyBinding? = null
    private val binding get() = _binding!!

    private val retrofit = RetrofitProvider.provideRetrofitForCurrency()
    private val currencyApi = RetrofitProvider.provideCurrencyApi(retrofit)
    private val viewModel: CurrencyViewModel by viewModels { CurrencyViewModelFactory(currencyApi) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApiCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getAllCurrency(AvailableCurrencies.entries.joinToString(",")).observe(viewLifecycleOwner) { currencyResponse ->
            val adapter = CurrencyAdapter(currencyResponse.data)
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
