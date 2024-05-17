package com.example.moneytrack.views.fragments.news.business

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moneytrack.databinding.ItemBusinessBinding
import com.example.moneytrack.models.BusinessNetworkModel

class BusinessAdapter(private val businessList: List<BusinessNetworkModel>) : RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>() {

    class BusinessViewHolder(private val binding: ItemBusinessBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(business: BusinessNetworkModel) {
            binding.businessTitle.text = business.title
            binding.businessDescription.text = business.description
            binding.businessImage.load(business.image_url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val binding = ItemBusinessBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusinessViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        val business = businessList[position]
        holder.bind(business)
    }

    override fun getItemCount(): Int {
        return businessList.size
    }
}
