package com.bilinedev.ikasmariagitma.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by deny on bandung.
 */
class VerticalLineDecorator (space: Int) : RecyclerView.ItemDecoration() {
    private var space = 0

    init {
        this.space = space
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = space

        outRect.bottom = space
    }
}