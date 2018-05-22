package com.vrumen.coreandroidapp.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bilinedev.ikasmariagitma.util.DialogUtil
import com.vrumen.coreandroidapp.R
import org.jetbrains.anko.find
import pl.aprilapps.easyphotopicker.EasyImage

/**
 * Created by bilinedev on 22/05/18.
 * There are many bug here (may be)
 * just google it <(^o^<)
 */
abstract class MVPActivity : AppCompatActivity() {

    abstract fun initPresenter()
    private var toolbarIcon: ImageButton? = null
    private var toolbarMenu: ImageButton? = null
    private var toolbarTitle: TextView? = null
    private var edSearch: EditText? = null
    private var btnClear: ImageView? = null

    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
    }

    fun <T> goAndFinish(cls: Class<T>) {
        val intent = Intent(this, cls)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun initToolbar() {
        toolbarIcon = findViewById(R.id.toolbar_icon)
        toolbarTitle = findViewById(R.id.toolbar_title)
    }

    fun initToolbarSearch() {
        toolbarIcon = findViewById(R.id.toolbar_icon)
        toolbarMenu = findViewById(R.id.toolbar_menu_right)
        edSearch = findViewById<EditText>(R.id.ed_search)
        btnClear = findViewById<ImageView>(R.id.btn_clear)
    }

    fun setNavigationTitle(title: String) {
        if (toolbarTitle == null) initToolbar()
        toolbarTitle?.text = title
    }

    fun setNavigationBack(title: String) {
        if (toolbarTitle == null || toolbarIcon == null) initToolbar()
        toolbarIcon?.setImageResource(R.drawable.ic_back_line)
        toolbarIcon?.visibility = View.VISIBLE
        toolbarTitle?.text = title
        toolbarIcon?.setOnClickListener({
            setResult(Activity.RESULT_CANCELED, Intent())
            finish()
        })
    }

    fun setNavigationMenu(icon: Int, action: View.OnClickListener) {
        toolbarMenu = findViewById(R.id.toolbar_menu_right)
        toolbarMenu?.visibility = View.VISIBLE
        toolbarMenu?.setImageResource(icon)
        toolbarMenu?.setOnClickListener(action)
    }

    fun setNavigationSearch(adapter: TextWatcherAdapter) {
        initToolbarSearch()
        toolbarIcon?.setImageResource(R.drawable.ic_back_line)
        toolbarIcon?.visibility = View.VISIBLE
        toolbarIcon?.setOnClickListener({
            setResult(Activity.RESULT_CANCELED, Intent())
            finish()
        })

        edSearch?.addTextChangedListener(adapter)
        btnClear?.setOnClickListener {
            edSearch?.setText("")
            btnClear?.visibility = View.GONE
        }
    }

    fun enableSearch() {
        edSearch?.isEnabled = true
    }

    fun dissableSearch() {
        edSearch?.isEnabled = false
    }

    fun showRemoveSearch() {
        btnClear?.visibility = View.VISIBLE
    }

    fun hideRemoveSearch() {
        btnClear?.visibility = View.GONE
    }

    fun configEasyImage(multiple: Boolean) {
        EasyImage.configuration(this)
                .setImagesFolderName(getString(R.string.app_name))
                .setAllowMultiplePickInGallery(multiple)
    }

    fun initDrawer() {
        drawerLayout = find(R.id.drawer_layout)
    }

    fun closeDrawer() {
        drawerLayout?.closeDrawer(Gravity.START)
    }

    fun toggleDrawer() {
        if (drawerLayout?.isDrawerOpen(Gravity.START) == true) drawerLayout?.closeDrawer(Gravity.START)
        else drawerLayout?.openDrawer(Gravity.START)
    }

    private var progressDialog: ProgressDialog? = null

    fun showProgressMessage(context: Context, message: String, cancelable: Boolean?) {
        progressDialog = DialogUtil.createProgressDialog(context, message, cancelable!!)
        progressDialog?.show()
    }

    fun dismissProgressMessage() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
        }
    }

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }

    fun SwipeRefreshLayout.stopRefreshing() {
        isRefreshing = false
    }
}