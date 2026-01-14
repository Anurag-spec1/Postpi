package com.anurag.postpi.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anurag.postpi.fragment.AuthFragment
import com.anurag.postpi.fragment.BodyFragment
import com.anurag.postpi.fragment.HeadersFragment
import com.anurag.postpi.fragment.ParamsFragment

class RequestPagerAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ParamsFragment()
            1 -> HeadersFragment()
            2 -> BodyFragment()
            3 -> AuthFragment()
            else -> ParamsFragment()
        }
    }
}
