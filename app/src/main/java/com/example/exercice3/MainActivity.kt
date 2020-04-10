@file:Suppress("DEPRECATION")

package com.example.exercice3

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList



class MainActivity : AppCompatActivity(){
    var Tasks: ArrayList<Task> = ArrayList()
    val NewTaskId: Int =R.id.newTask


    @RequiresApi(Build.VERSION_CODES.M)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addTasks()
        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.adapter = MyAdapter(Tasks, this)
        button.setOnClickListener{
            // Initialize a new DatePickerFragment
            val newFragment = DatePickerFragment()
            // Show the date picker dialog
            newFragment.show(fragmentManager, "Date Picker")
            newFragment.
        }
    }

    fun addTasks() {
        var c= Calendar.getInstance()
        c.set(Calendar.MONTH, Calendar.JANUARY)
        c.set(Calendar.DAY_OF_MONTH, 8)
        c.set(Calendar.YEAR,2015)
        Tasks.add(Task("first",c,false))
        Tasks.add(Task("second",c,false))
        Tasks.add(Task("third",c,false))
        Tasks.add(Task("fourth",c,false))

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

}

