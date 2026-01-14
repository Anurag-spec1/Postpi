package com.anurag.postpi.activity

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.anurag.postpi.R
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


        binding.methodSelector.setOnClickListener {
            val popup = PopupMenu(this, binding.methodSelector)
            popup.menuInflater.inflate(R.menu.menu_http_methods, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                binding.methodSelector.text = item.title

                binding.methodSelector.setTextColor(
                    when (item.title) {
                        "GET" -> getColor(carbon.R.color.carbon_green_500)
                        "POST" -> getColor(carbon.R.color.carbon_blue_500)
                        "PUT" -> getColor(carbon.R.color.carbon_orange_500)
                        "DELETE" -> getColor(carbon.R.color.carbon_red_500)
                        else -> getColor(R.color.white)
                    }
                )
                true
            }

            popup.show()
        }

    }
}