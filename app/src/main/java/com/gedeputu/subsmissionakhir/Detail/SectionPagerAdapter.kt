package com.gedeputu.subsmissionakhir.Detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gedeputu.subsmissionakhir.Detail.Followers.FollowersFragment
import com.gedeputu.subsmissionakhir.Detail.Following.FollowingFragment
import com.gedeputu.subsmissionakhir.R

class SectionPagerAdapter (private val context : Context, fm : FragmentManager, data: Bundle)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @StringRes
    private val tabTitles = intArrayOf(R.string.tab_followers, R.string.tab_following)
    override fun getCount(): Int = tabTitles.size

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(tabTitles[position])
    }

}