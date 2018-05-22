package com.vrumen.coreandroidapp.util;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Chandra on 11/16/17.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

public class EndOffsetItemDecoration extends RecyclerView.ItemDecoration {
    private int mOffsetPx;
    private Drawable mOffsetDrawable;
    private int mOrientation;

    public EndOffsetItemDecoration(int offsetPx) {
        mOffsetPx = offsetPx;
    }

    public EndOffsetItemDecoration(Drawable offsetDrawable) {
        mOffsetDrawable = offsetDrawable;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = state.getItemCount();
        if (parent.getChildAdapterPosition(view) != itemCount - 1) {
            return;
        }

        mOrientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            if (mOffsetPx > 0) {
                outRect.right = mOffsetPx;
            } else if (mOffsetDrawable != null) {
                outRect.right = mOffsetDrawable.getIntrinsicWidth();
            }
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            if (mOffsetPx > 0) {
                outRect.bottom = mOffsetPx;
            } else if (mOffsetDrawable != null) {
                outRect.bottom = mOffsetDrawable.getIntrinsicHeight();
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOffsetDrawable == null) {
            return;
        }

        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawOffsetHorizontal(c, parent);
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawOffsetVertical(c, parent);
        }
    }

    private void drawOffsetHorizontal(Canvas canvas, RecyclerView parent) {
        int parentTop = parent.getPaddingTop();
        int parentBottom = parent.getHeight() - parent.getPaddingBottom();

        View lastChild = parent.getChildAt(parent.getChildCount() - 1);
        RecyclerView.LayoutParams lastChildLayoutParams = (RecyclerView.LayoutParams) lastChild.getLayoutParams();
        int offsetDrawableLeft = lastChild.getRight() + lastChildLayoutParams.rightMargin;
        int offsetDrawableRight = offsetDrawableLeft + mOffsetDrawable.getIntrinsicWidth();

        mOffsetDrawable.setBounds(offsetDrawableLeft, parentTop, offsetDrawableRight, parentBottom);
        mOffsetDrawable.draw(canvas);
    }

    private void drawOffsetVertical(Canvas canvas, RecyclerView parent) {
        int parentLeft = parent.getPaddingLeft();
        int parentRight = parent.getWidth() - parent.getPaddingRight();

        View lastChild = parent.getChildAt(parent.getChildCount() - 1);
        RecyclerView.LayoutParams lastChildLayoutParams = (RecyclerView.LayoutParams) lastChild.getLayoutParams();
        int offsetDrawableTop = lastChild.getBottom() + lastChildLayoutParams.bottomMargin;
        int offsetDrawableBottom = offsetDrawableTop + mOffsetDrawable.getIntrinsicHeight();

        mOffsetDrawable.setBounds(parentLeft, offsetDrawableTop, parentRight, offsetDrawableBottom);
        mOffsetDrawable.draw(canvas);
    }
}
