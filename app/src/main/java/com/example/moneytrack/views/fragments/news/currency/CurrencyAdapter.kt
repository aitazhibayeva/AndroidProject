package com.example.moneytrack.views.fragments.news.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytrack.databinding.ItemCurrencyBinding

class CurrencyAdapter(private val currencyMap: Map<String, Double>) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    class CurrencyViewHolder(private val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: Map.Entry<String, Double>) {
            binding.currencyName.text = currency.key
            binding.currencyValue.text = String.format("Value: %.3f USD", currency.value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyMap.entries.toList()[position]
        holder.bind(currency)
    }

    override fun getItemCount(): Int {
        return currencyMap.size
    }
}
