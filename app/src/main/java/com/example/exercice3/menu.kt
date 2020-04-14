package com.example.exercice3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class menuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var TasksFiltered: ArrayList<Task> = ArrayList()
        var Tasks = (activity as MainActivity).Tasks
        var adpter = (activity as MainActivity).my_recycler_view
        when (item.itemId) {
            R.id.menu_1 -> TasksFiltered.addAll(Tasks.filter { t ->
                t.date.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(
                    Calendar.DAY_OF_YEAR
                )
            })

            R.id.menu_2 -> TasksFiltered.addAll(Tasks.filter { t ->
                t.date.get(Calendar.DAY_OF_YEAR) >= Calendar.getInstance().get(
                    Calendar.DAY_OF_YEAR
                ) && t.date.get(Calendar.DAY_OF_YEAR) < Calendar.getInstance().get(
                    Calendar.DAY_OF_YEAR
                ) + 7
            })

            R.id.menu_3 -> TasksFiltered.addAll(Tasks)

            }

        (activity as MainActivity).my_recycler_view.adapter=MyAdapter(TasksFiltered,(activity as MainActivity).applicationContext)
        return true
        }
    }
