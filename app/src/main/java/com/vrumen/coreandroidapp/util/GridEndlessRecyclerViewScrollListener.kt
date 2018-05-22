package com.bilinedev.ikasmariagitma.util

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log


/**
 * Created by Chandra on 20/03/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
open class GridEndlessRecyclerViewScrollListener(gridLayoutManager: GridLayoutManager, dataLoader: DataLoader) : RecyclerView.OnScrollListener() {
    private var gridLayoutManager: GridLayoutManager? = null
    private var dataLoader: DataLoader? = null
    private var previousItemCount: Int = 0
    private var loading: Boolean = false

    init {
        this.gridLayoutManager = gridLayoutManager
        this.dataLoader = dataLoader
        reset()
    }

    override fun onScrolled(view: RecyclerView?, dx: Int, dy: Int) {
        Log.e("DX | DY", "$dx | $dy")
        /*Log.e("Visible Item", "$visibleItemCount")
        Log.e("Total Item", "$totalItemCount")
        Log.e("Past Visible Item", "$pastVisibleItems")*/
        if (dy > 0) {
            val itemCount = gridLayoutManager?.itemCount

            if (itemCount != previousItemCount) {
                loading = false
            }

            if (!loading && gridLayoutManager?.findLastVisibleItemPosition() ?: 0 >= itemCount ?: 0 - 1) {
                previousItemCount = itemCount ?: 0
                loading = dataLoader?.onLoadMore() ?: false
            }
        }
    }

    private fun reset() {
        this.loading = false
        this.previousItemCount = -1
    }

    interface DataLoader {
        fun onLoadMore(): Boolean
    }
}