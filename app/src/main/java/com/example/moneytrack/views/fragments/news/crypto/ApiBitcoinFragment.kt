package com.example.moneytrack.views.fragments.news.crypto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneytrack.databinding.FragmentApiBitcoinBinding
import com.example.moneytrack.network.RetrofitProvider


class ApiBitcoinFragment : Fragment() {

    private var _binding: FragmentApiBitcoinBinding? = null
    private val binding get() = _binding!!

    private val retrofit = RetrofitProvider.provideRetrofitForCrypto()
    private val cryptoApi = RetrofitProvider.provideCryptoApi(retrofit)
    private val viewModel: CryptoViewModel by viewModels { CryptoViewModelFactory(cryptoApi) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiBitcoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getAllCrypto().observe(viewLifecycleOwner) { cryptoResponse ->
            val adapter = CryptoAdapter(cryptoResponse.data)
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
