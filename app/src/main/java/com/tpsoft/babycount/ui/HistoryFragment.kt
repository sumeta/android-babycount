package com.tpsoft.babycount.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tpsoft.babycount.R
import com.tpsoft.babycount.data.HistoryDao
import com.tpsoft.babycount.data.HistoryModel

class HistoryFragment : Fragment() {

    private var historyList: ArrayList<HistoryModel> = ArrayList()
    private  var adapter: HistoryAdapter? = null
    private lateinit var recyclerViewPriceTrends:RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activity?.title  = "History"
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewPriceTrends = view.findViewById(R.id.recyclerViewCount);
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerViewPriceTrends.layoutManager = layoutManager
        adapter = HistoryAdapter(context, historyList)
        recyclerViewPriceTrends.adapter = adapter

        val dao = HistoryDao(activity)
        var res = dao.list
        historyList.clear()
        for (r:HistoryModel in res){
            var model = HistoryModel()
            model.createdDate = r.createdDate
            model.type = r.type
            historyList.add(model)
        }

        adapter!!.notifyDataSetChanged()


    }
}
