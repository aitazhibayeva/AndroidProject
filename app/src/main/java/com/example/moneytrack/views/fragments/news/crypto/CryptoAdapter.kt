package com.example.moneytrack.views.fragments.news.crypto

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytrack.databinding.ItemCryptoBinding
import com.example.moneytrack.models.CryptoNetworkModel

class CryptoAdapter(private val cryptoList: List<CryptoNetworkModel>) : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    class CryptoViewHolder(private val binding: ItemCryptoBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("DefaultLocale")
        fun bind(crypto: CryptoNetworkModel) {
            binding.cryptoName.text = crypto.name
            binding.cryptoPrice.text = String.format("Price: %.3f USD", crypto.price_usd.toDouble())
            binding.cryptoPrice.apply {
                maxLines = 1
                ellipsize = TextUtils.TruncateAt.END
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = cryptoList[position]
        holder.bind(crypto)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}
