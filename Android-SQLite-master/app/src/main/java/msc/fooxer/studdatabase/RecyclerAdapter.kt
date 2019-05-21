package msc.fooxer.studdatabase

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RecyclerAdapter internal constructor (val context: Context, val items: ArrayList <Item>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = inflater.inflate(R.layout.element, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val item = items[position]
        view.idTextView.text = item.id.toString()
        view.nameTextView.text = item.name
        view.dateTextView.text = item.date

        }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val idTextView = view.findViewById(R.id.element_index) as TextView
        val nameTextView = view.findViewById(R.id.element_name) as TextView
        val dateTextView = view.findViewById(R.id.element_date) as TextView

    }
}