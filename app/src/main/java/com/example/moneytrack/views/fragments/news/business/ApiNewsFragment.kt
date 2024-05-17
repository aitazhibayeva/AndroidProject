package com.example.moneytrack.views.fragments.news.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneytrack.databinding.FragmentApiNewsBinding
import com.example.moneytrack.network.RetrofitProvider


class ApiNewsFragment : Fragment() {

    private var _binding: FragmentApiNewsBinding? = null
    private val binding get() = _binding!!

    private val retrofit = RetrofitProvider.provideRetrofitForBusiness()
    private val businessApi = RetrofitProvider.provideBusinessApi(retrofit)
    private val viewModel: BusinessViewModel by viewModels { BusinessViewModelFactory(businessApi) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApiNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getBusinessInfo().observe(viewLifecycleOwner) { businessResponse ->
            val adapter = BusinessAdapter(businessResponse.results)
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
