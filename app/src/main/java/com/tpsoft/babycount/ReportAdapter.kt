package com.tpsoft.babycount

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tpsoft.babycount.data.ReportModel
import kotlinx.android.synthetic.main.listview_history.view.*
import kotlinx.android.synthetic.main.listview_history.view.textViewDate
import kotlinx.android.synthetic.main.listview_report.view.*
import java.text.SimpleDateFormat

class ReportAdapter(val context: Context?, private val items: MutableList<ReportModel>) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        if (items.isNotEmpty() || items != null) {
            return items.size;
        }
        return 0;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.listview_report, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val textViewDate = view.textViewDate
        private val textViewMorning = view.textViewMorning
        private val textViewNoon = view.textViewNoon
        private val textViewAfterNoon = view.textViewAfterNoon

        fun bind(report: ReportModel) {
            textViewDate.text = "วันที่ "+report.date
            textViewMorning.text = report.morning.toString()
            textViewNoon.text = report.noon.toString()
            textViewAfterNoon.text = report.afternoon.toString()
        }
    }

}

