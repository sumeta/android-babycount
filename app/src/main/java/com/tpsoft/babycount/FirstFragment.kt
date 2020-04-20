package com.tpsoft.babycount

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tpsoft.babycount.data.HistoryDao
import com.tpsoft.babycount.data.HistoryModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var nCount:Int = 0
    private var morningCount:Int = 0
    private var noonCount:Int = 0
    private var afterNoonCount:Int = 0
    private lateinit var textViewCount: TextView
    private lateinit var textViewMorning: TextView
    private lateinit var textViewNoon: TextView
    private lateinit var textViewAfterNoon: TextView
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewCount = view.findViewById(R.id.textViewCountAll)
        textViewMorning = view.findViewById(R.id.textViewCountMorning)
        textViewNoon = view.findViewById(R.id.textViewCountNoon)
        textViewAfterNoon = view.findViewById(R.id.textViewCountAfterNoon)

        morningCount = getSection("M")
        noonCount = getSection("N")
        afterNoonCount = getSection("A")

        textViewCount.text = this.countListAll().toString()
        textViewMorning.text = morningCount.toString()
        textViewNoon.text = noonCount.toString()
        textViewAfterNoon.text = afterNoonCount.toString()

        view.findViewById<LinearLayout>(R.id.layoutMoning).setOnClickListener{
            nCount++
            morningCount++
            var model = HistoryModel()
            val date = Date()
            val createDate = dateFormat.format(date)
            model.count = nCount
            model.type = "M"
            model.createdDate = createDate
            val dao = HistoryDao(activity)
            dao.insert(model)

            textViewMorning.text = morningCount.toString()
            textViewCount.text = countListAll().toString()
        }

        view.findViewById<LinearLayout>(R.id.layoutNoon).setOnClickListener{
            nCount++
            noonCount++
            var model = HistoryModel()
            val date = Date()
            val createDate = dateFormat.format(date)
            model.count = nCount
            model.type = "N"
            model.createdDate = createDate
            val dao = HistoryDao(activity)
            dao.insert(model)

            textViewNoon.text = noonCount.toString()
            textViewCount.text = countListAll().toString()
        }

        view.findViewById<LinearLayout>(R.id.layoutAfterNoon).setOnClickListener{
            nCount++
            afterNoonCount++
            var model = HistoryModel()
            val date = Date()
            val createDate = dateFormat.format(date)
            model.count = nCount
            model.type = "A"
            model.createdDate = createDate
            val dao = HistoryDao(activity)
            dao.insert(model)

            textViewAfterNoon.text = afterNoonCount.toString()
            textViewCount.text = countListAll().toString()
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener{
            nCount++
            var model = HistoryModel()

            val date = Date()
            val createDate = dateFormat.format(date)
            model.count = nCount
            model.createdDate = createDate
            val dao = HistoryDao(activity)
            dao.insert(model)

            textViewCount.text = countListAll().toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_history -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSection(type:String): Int{
        val dao = HistoryDao(activity)
        var his = dao.todayList

        var h = his.filter { !it.type.isNullOrBlank() }.filter { it.type == type }

        return h.size
    }

    private fun countListAll() : Int{
        val dao = HistoryDao(activity)
        var his = dao.list
        return his.size
    }
}
