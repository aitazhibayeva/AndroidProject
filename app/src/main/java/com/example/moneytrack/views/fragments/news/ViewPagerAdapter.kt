package com.example.moneytrack.views.fragments.news


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moneytrack.views.fragments.news.business.ApiNewsFragment
import com.example.moneytrack.views.fragments.news.crypto.ApiBitcoinFragment
import com.example.moneytrack.views.fragments.news.currency.ApiCurrencyFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        ApiBitcoinFragment(),
        ApiCurrencyFragment(),
        ApiNewsFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
