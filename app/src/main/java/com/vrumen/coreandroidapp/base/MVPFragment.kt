package com.vrumen.coreandroidapp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.vrumen.coreandroidapp.MainActivity
import com.vrumen.coreandroidapp.R

/**
 * Created by bilinedev on 22/05/18.
 * There are many bug here (may be)
 * just google it <(^o^<)
 */
abstract class MVPFragment : Fragment() {

    abstract fun initPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
    }

    fun initToolbar(title: String) {
        toolbar_icon?.setImageResource(R.drawable.ic_menu_line)
        toolbar_icon?.setOnClickListener { (activity as MainActivity).toggleDrawer() }
        toolbar_title?.text = title
    }

    fun setNavigationMenu(icon: Int, action: View.OnClickListener) {
        toolbar_menu_right?.setImageResource(icon)
        toolbar_menu_right?.setOnClickListener(action)
    }

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }
}