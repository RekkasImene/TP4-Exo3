@file:Suppress("DEPRECATION")

package com.example.exercice3

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(){
    var Tasks: ArrayList<Task> = ArrayList()
    var TasksFiltered: ArrayList<Task> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.M)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (checkIsTablet(applicationContext)) {

            addTasks()

            val choices = resources.getStringArray(R.array.options_array)
            val spinner = findViewById<Spinner>(R.id.spinner)

            if (spinner != null) {

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, choices)

                spinner.adapter = adapter
                spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {
                        TasksFiltered.removeAll(TasksFiltered)
                        when (choices[position]) {
                            "Day" -> TasksFiltered.addAll(Tasks.filter { t ->
                                t.date.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(
                                    Calendar.DAY_OF_YEAR
                                )
                            })
                            "Week" -> TasksFiltered.addAll(Tasks.filter { t ->
                                t.date.get(Calendar.DAY_OF_YEAR) >= Calendar.getInstance().get(
                                    Calendar.DAY_OF_YEAR
                                ) && t.date.get(Calendar.DAY_OF_YEAR) < Calendar.getInstance().get(
                                    Calendar.DAY_OF_YEAR
                                ) + 7
                            })
                            "All" -> TasksFiltered.addAll(Tasks)
                        }

                        my_recycler_view.adapter = MyAdapter(TasksFiltered, applicationContext)

                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
            }
        }

        if (checkLandscapre(applicationContext)) {
            val newFragment = menuFragment()
            if (savedInstanceState == null || savedInstanceState != null) { // initial transaction should be wrapped like this
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_menu_frame_layout, newFragment)
                    .commitAllowingStateLoss()
            }
        }

        addTasks()
        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.adapter = MyAdapter(Tasks, this)
        button.setOnClickListener {
            // Initialize a new DatePickerFragment
            val newFragment = DatePickerFragment()
            // Show the date picker dialog
            newFragment.show(fragmentManager, "Date Picker")
        }
    }

    fun addTasks() {
        var c= Calendar.getInstance()
        c.set(Calendar.MONTH, Calendar.APRIL)
        c.set(Calendar.DAY_OF_MONTH, 12)
        c.set(Calendar.YEAR,2020)
        Tasks.add(Task("first",c,false))
        Tasks.add(Task("second",c,false))
        Tasks.add(Task("third",c,false))
        Tasks.add(Task("fourth",c,false))
        var c1= Calendar.getInstance()
        Tasks.add(Task("test day",c1,false))
        var c2= Calendar.getInstance()
        c2.set(Calendar.MONTH, Calendar.JULY)
        c2.set(Calendar.DAY_OF_MONTH, 12)
        c2.set(Calendar.YEAR,2020)
        Tasks.add(Task("test week",c2,false))
    }

    fun Context.toast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun EditText.smartTextWatcher(on: (String) -> Unit, after: (String) -> Unit, before: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                after.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                before.invoke(s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                on.invoke(s.toString())
            }
        })
    }
    fun checkIsTablet(ctx: Context): Boolean {
        return ctx.getResources()
            .getConfiguration().screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= SCREENLAYOUT_SIZE_LARGE
    }

    fun checkLandscapre(ctx:Context): Boolean {
        return (ctx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
    }

}

