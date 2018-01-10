package com.example.acera.theforum.Model

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet


/**
 * Created by acera on 2018/1/11.
 *
 */
class WrapContentLinearLayoutManager : LinearLayoutManager
{
    constructor(context: Context) : super(context)
    {
    }

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
    {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    {
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State)
    {
        try
        {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException)
        {
            e.printStackTrace()
        }

    }
}