package com.tpsoft.babycount.ui

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tpsoft.babycount.R
import com.tpsoft.babycount.data.HistoryDao
import com.tpsoft.babycount.data.HistoryModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var morningCount:Int = 0
    private var noonCount:Int = 0
    private var afterNoonCount:Int = 0
    private lateinit var textViewCount: TextView
    private lateinit var layoutMorning: LinearLayout
    private lateinit var textViewMorning: TextView
    private lateinit var layoutNoon: LinearLayout
    private lateinit var textViewNoon: TextView
    private lateinit var layoutAfterNoon: LinearLayout
    private lateinit var textViewAfterNoon: TextView
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        activity?.title = resources.getString(R.string.app_name);
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //(activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewCount = view.findViewById(R.id.textViewCountAll)
        layoutMorning = view.findViewById(R.id.layoutMoning)
        textViewMorning = view.findViewById(R.id.textViewCountMorning)
        layoutNoon = view.findViewById(R.id.layoutNoon)
        textViewNoon = view.findViewById(R.id.textViewCountNoon)
        layoutAfterNoon = view.findViewById(R.id.layoutAfterNoon)
        textViewAfterNoon = view.findViewById(R.id.textViewCountAfterNoon)

        morningCount = getSection("M")
        noonCount = getSection("N")
        afterNoonCount = getSection("A")

        textViewCount.text = this.countListAll().toString()
        textViewMorning.text = morningCount.toString()
        textViewNoon.text = noonCount.toString()
        textViewAfterNoon.text = afterNoonCount.toString()

        layoutMorning.setOnClickListener{
            morningCount++
            var model = HistoryModel()
            model.count = morningCount
            model.type = "M"
            model.createdDate = getCurrentDate()
            val dao = HistoryDao(activity)
            dao.insert(model)
            Toast.makeText(activity,"เพิ่มแล้ว",Toast.LENGTH_SHORT).show()

            textViewMorning.text = getSection("M").toString()
            updateCountAll()
        }

        layoutMorning.setOnLongClickListener {
            val count = getSection("M")
            if(count > 0){
                morningCount++
                val dao = HistoryDao(activity)
                dao.deleteByLast("M")
                Toast.makeText(activity,"ลบแล้ว",Toast.LENGTH_SHORT).show()
                textViewMorning.text = getSection("M").toString()
                updateCountAll()
            }
            true
        }

        layoutNoon.setOnClickListener{
            noonCount++
            var model = HistoryModel()
            model.count = noonCount
            model.type = "N"
            model.createdDate = getCurrentDate()
            val dao = HistoryDao(activity)
            dao.insert(model)
            Toast.makeText(activity,"เพิ่มแล้ว",Toast.LENGTH_SHORT).show()

            textViewNoon.text = getSection("N").toString()
            updateCountAll()
        }

        layoutNoon.setOnLongClickListener {
            val count = getSection("N")
            if(count > 0){
                noonCount++
                val dao = HistoryDao(activity)
                dao.deleteByLast("N")
                Toast.makeText(activity,"ลบแล้ว",Toast.LENGTH_SHORT).show()
                textViewNoon.text = getSection("N").toString()
                updateCountAll()
            }
            true
        }

        layoutAfterNoon.setOnClickListener{
            afterNoonCount++
            var model = HistoryModel()
            model.count = afterNoonCount
            model.type = "A"
            model.createdDate = getCurrentDate()
            val dao = HistoryDao(activity)
            dao.insert(model)
            Toast.makeText(activity,"เพิ่มแล้ว",Toast.LENGTH_SHORT).show()

            textViewAfterNoon.text = getSection("A").toString()
            updateCountAll()
        }

        layoutAfterNoon.setOnLongClickListener {
            val count = getSection("A")
            if(count > 0){
                afterNoonCount++
                val dao = HistoryDao(activity)
                dao.deleteByLast("A")
                Toast.makeText(activity,"ลบแล้ว",Toast.LENGTH_SHORT).show()
                textViewAfterNoon.text = getSection("A").toString()
                updateCountAll()
            }
            true
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener{
            findNavController().navigate(R.id.abountFragment)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_report -> {
                findNavController().navigate(R.id.reportFragment)
                true
            }
            R.id.action_history -> {
                findNavController().navigate(R.id.SecondFragment)
                true
            }
            R.id.action_settings -> {
                findNavController().navigate(R.id.settingFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentDate():String{
        val date = Date()
        return dateFormat.format(date)
    }

    private fun updateCountAll(){
        textViewCount.text = countListAll().toString()
    }

    private fun getSection(type:String): Int{
        val dao = HistoryDao(activity)
        var his = dao.todayList

        var h = his.filter { !it.type.isNullOrBlank() }.filter { it.type == type }

        return h.size
    }

    private fun countListAll() : Int{
        val dao = HistoryDao(activity)
        var his = dao.todayList
        return his.size
    }
}
