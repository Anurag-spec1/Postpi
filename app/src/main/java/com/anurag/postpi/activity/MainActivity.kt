package com.anurag.postpi.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anurag.postpi.adapter.RequestPagerAdapter
import com.anurag.postpi.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tabs = binding.requestTabs
        val pager = binding.requestPager

        binding.requestPager.adapter = RequestPagerAdapter(this)

        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Params"
                1 -> "Headers"
                2 -> "Body"
                3 -> "Auth"
                else -> ""
            }
        }.attach()

    }
}