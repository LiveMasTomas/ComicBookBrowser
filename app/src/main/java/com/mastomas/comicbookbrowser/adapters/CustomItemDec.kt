package com.mastomas.comicbookbrowser.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mastomas.comicbookbrowser.util.dpToPx

class CustomItemDec : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val context = parent.context
        val outerHorizontalMargin = 20.dpToPx(context)
        val innerHorizontalMargin = 10.dpToPx(context)
        val outerVerticalMargin = 20.dpToPx(context)
        val innerVerticalMargin = 10.dpToPx(context)
        when ((view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex) {
            //Items on the left
            0 -> {
                outRect.left = outerHorizontalMargin
                outRect.right = innerHorizontalMargin
                when (position) {
                    //First item
                    0 -> {
                        outRect.top = outerVerticalMargin
                        outRect.bottom = innerVerticalMargin
                    }
                    //Last item
                    parent.adapter?.itemCount?.minus(1) -> {
                        outRect.top = innerVerticalMargin
                        outRect.bottom = outerVerticalMargin
                    }
                    //Rest of items
                    else -> {
                        outRect.top = innerVerticalMargin
                        outRect.bottom = innerVerticalMargin
                    }
                }
            }
            //Items on the right
            else -> {
                outRect.left = innerHorizontalMargin
                outRect.right = outerHorizontalMargin
                when (position) {
                    //First item
                    0 -> {
                        outRect.top = outerVerticalMargin
                        outRect.bottom = innerVerticalMargin
                    }
                    //Last item
                    parent.adapter?.itemCount?.minus(1) -> {
                        outRect.top = innerVerticalMargin
                        outRect.bottom = outerVerticalMargin
                    }
                    //Rest of items
                    else -> {
                        outRect.top = innerVerticalMargin
                        outRect.bottom = innerVerticalMargin
                    }
                }
            }
        }
    }
}