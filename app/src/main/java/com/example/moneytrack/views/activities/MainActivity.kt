package com.example.moneytrack.views.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.moneytrack.R
import com.example.moneytrack.databinding.ActivityMainBinding
import com.example.moneytrack.utils.Database.AppDatabase
import com.example.moneytrack.views.fragments.add.AddFragment
import com.example.moneytrack.views.fragments.analytics.AnalyticsFragment
import com.example.moneytrack.views.fragments.search.ListFragment
import com.example.moneytrack.views.fragments.news.NewsFragment
import com.example.moneytrack.views.fragments.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val analyticsFragment = AnalyticsFragment()
    private val addFragment = AddFragment()
    private val listFragment = ListFragment()
    private val newsFragment = NewsFragment()
    private val settingsFragment = SettingsFragment()

    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = analyticsFragment
    private lateinit var db: AppDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        setupFragments()
        setupBottomNavigation()
    }

    private fun setupFragments() {
        fragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, settingsFragment, "5").hide(settingsFragment)
            add(R.id.fragment_container, newsFragment, "4").hide(newsFragment)
            add(R.id.fragment_container, addFragment, "3").hide(addFragment)
            add(R.id.fragment_container, listFragment, "2").hide(listFragment)
            add(R.id.fragment_container, analyticsFragment, "1")
        }.commit()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_analytics -> {
                    showFragment(analyticsFragment)
                    true
                }
                R.id.nav_list -> {
                    showFragment(listFragment)
                    true
                }
                R.id.nav_add -> {
                    showFragment(addFragment)
                    true
                }
                R.id.nav_news -> {
                    showFragment(newsFragment)
                    true
                }
                R.id.nav_settings -> {
                    showFragment(settingsFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            hide(activeFragment)
            show(fragment)
            commit()
        }
        activeFragment = fragment
    }
}
