package com.tpsoft.babycount

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tpsoft.babycount.data.HistoryModel
import kotlinx.android.synthetic.main.listview_history.view.*

class HistoryAdapter(val context: Context?, private val items: ArrayList<HistoryModel>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        if (items.isNotEmpty() || items != null) {
            return items.size;
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.listview_history, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val textViewDate = view.textViewDate
        private val textViewType = view.textViewType

        fun bind(his: HistoryModel) {
            textViewDate.text = his.createdDate
            when (his.type) {
                "M" -> {
                    textViewType.text = "เช้า"
                }
                "N" -> {
                    textViewType.text = "เที่ยง"
                }
                "A" -> {
                    textViewType.text = "เย็น"
                }
            }

        }
    }

}

