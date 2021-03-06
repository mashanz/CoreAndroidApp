package com.vrumen.coreandroidapp.util

import android.support.v4.app.FragmentManager
import com.vrumen.coreandroidapp.R

/**
 * Created by deny on bandung.
 */

object FragmentUtil{

    fun getActiveFragment(manager: FragmentManager): String? {
        val currentFragment = manager.findFragmentById(R.id.content_frame)
        return if (currentFragment == null) ""
        else currentFragment.tag
    }

    fun getActiveFragmentChild(manager: FragmentManager): String? {
        val currentFragment = manager.findFragmentById(R.id.frame_dashboard)
        return if (currentFragment == null) ""
        else currentFragment.tag
    }
}
