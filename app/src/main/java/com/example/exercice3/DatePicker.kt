@file:Suppress("DEPRECATION")

package com.example.exercice3

import android.app.*
import android.os.Bundle
import android.widget.TextView
import android.widget.DatePicker
import android.widget.Toast
import java.text.DateFormat
import java.util.Calendar
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*


open class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var calendar:Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()

        // Get the system current date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this,  year, month, day)
}
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        Toast.makeText(
            activity,
            "Date Set : ${formatDate(year,month,day)}"
            ,Toast.LENGTH_SHORT
        ).show()

        // Display the selected date in text view
        activity.findViewById<TextView>(R.id.newTaskDate).text = formatDate(year,month,day)
        //add task to list
        var c= Calendar.getInstance()
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        c.set(Calendar.YEAR,year)
        (activity as MainActivity).Tasks.add(Task(activity.findViewById<EditText>(R.id.newTaskContent).text.toString(),c,false))
        (activity as MainActivity).my_recycler_view.adapter=MyAdapter((activity as MainActivity).Tasks, activity)
    }

    // Custom method to format date
    private fun formatDate(year:Int, month:Int, day:Int):String{
        // Create a Date variable/object with user chosen date
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time

        // Format the date picker selected date
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }

}