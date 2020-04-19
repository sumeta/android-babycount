package com.tpsoft.babycount

import android.os.Bundle
import android.view.*
import android.widget.Button
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
    private lateinit var textViewCount: TextView

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

        textViewCount = view.findViewById(R.id.textViewCount)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener{
            nCount++
            val dao = HistoryDao(activity)
            var model = HistoryModel()
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss a")
            val date = Date()
            val createDate = dateFormat.format(date)
            model.count = nCount
            model.createdDate = createDate

            var res = dao.insert(model)
            textViewCount.text = nCount.toString()
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

}
