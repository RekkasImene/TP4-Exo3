package com.example.exercice3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.my_text_view.view.*
import kotlinx.android.synthetic.main.text_view.view.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter(val items: ArrayList<Task>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_text_view, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val year = this.items[position].date.get(Calendar.YEAR)
        val month = this.items[position].date.get(Calendar.MONTH)
        val day = this.items[position].date.get(Calendar.DAY_OF_MONTH)
        holder?.content?.text = this.items[position].content
        holder?.date?.text= formatDate(year,month,day)
        holder?.content.setOnClickListener(){
            this.items.remove(this.items[position])
            notifyDataSetChanged()
        }
        holder?.date.setOnClickListener(){
            this.items.remove(this.items[position])
            notifyDataSetChanged()
        }


    }
    private fun formatDate(year:Int, month:Int, day:Int):String{
        var calendar=Calendar.getInstance()
        // Create a Date variable/object with user chosen date
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time

        // Format the date picker selected date
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val content = view.text_view_id
    val date = view.date_view_id
}
