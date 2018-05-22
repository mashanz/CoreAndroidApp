package com.vrumen.coreandroidapp.base

import android.content.Context

/**
 * Created by bilinedev on 22/05/18.
 * There are many bug here (may be)
 * just google it <(^o^<)
 */
open class BasePresenter<V>(context: Context?, view: V) {
    var view: V? = null
    var context: Context? = null

    init {
        this.context = context
        this.view = view
    }

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        view = null
    }
}