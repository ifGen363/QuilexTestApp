package org.qtestapp.adapters

import android.support.v7.widget.RecyclerView


abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder, M> : RecyclerView.Adapter<VH>() {

    protected var data: MutableList<M> = ArrayList()

    fun resetData(newData: List<M>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun addData(newData: M) {
        data.add(newData)
        notifyDataSetChanged()
    }

    fun addData(newData: List<M>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun getAllData(): List<M> = data

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size
}
