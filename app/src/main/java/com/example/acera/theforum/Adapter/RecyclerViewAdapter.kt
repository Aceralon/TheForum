package com.example.acera.theforum.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by acera on 2018/1/1.
 *
 */
internal abstract class RecyclerAdapter<in T>(private val myContext: Context, private val myLayoutId: Int, private val myData: List<T>) : RecyclerView.Adapter<ViewHolder>()
{
    var myOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder[myContext, parent, myLayoutId]
    }

    abstract fun convert(holder: ViewHolder, t: T)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        convert(holder, myData[position])

        if (myOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(View.OnClickListener { view -> myOnItemClickListener!!.onClick(view, holder.adapterPosition) })
            holder.itemView.setOnLongClickListener(View.OnLongClickListener { view ->
                myOnItemClickListener!!.onLongClick(view, holder.adapterPosition)
                true
            })
        }
    }

    override fun getItemCount() = myData.size

    interface OnItemClickListener
    {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener)
    {
        this.myOnItemClickListener = onItemClickListener
    }
}

internal class ViewHolder(context: Context, private val mConvertView: View, parent: ViewGroup) : RecyclerView.ViewHolder(mConvertView)
{
    private val mViews: SparseArray<View> = SparseArray()

    fun <T : View> getView(viewId: Int): T
    {
        var view: View? = mViews.get(viewId)
        if (view == null)
        {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    companion object
    {
        operator fun get(context: Context, parent: ViewGroup, layoutId: Int): ViewHolder
        {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return ViewHolder(context, itemView, parent)
        }
    }
}