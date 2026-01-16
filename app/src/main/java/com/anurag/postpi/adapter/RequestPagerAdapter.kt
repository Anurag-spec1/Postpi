package com.anurag.postpi.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anurag.postpi.fragment.AuthorizationFragment
import com.anurag.postpi.fragment.BodyFragment
import com.anurag.postpi.fragment.HeadersFragment
import com.anurag.postpi.fragment.ParamsFragment

class RequestPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {

    val paramsFragment = ParamsFragment()
    val headersFragment = HeadersFragment()
    val bodyFragment = BodyFragment()
    val authFragment = AuthorizationFragment()

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> paramsFragment
            1 -> headersFragment
            2 -> bodyFragment
            3 -> authFragment
            else -> paramsFragment
        }
    }
}

