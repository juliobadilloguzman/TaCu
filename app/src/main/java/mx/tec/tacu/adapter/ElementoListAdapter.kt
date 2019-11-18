package mx.tec.tacu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.tec.tacu.R
import mx.tec.tacu.model.TaqueriaRanking

class ElementoListAdapter(private val context: Context, private val layout: Int, private val dataSource: List<TaqueriaRanking>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(layout, parent, false)

        val pos = view.findViewById<TextView>(R.id.position)
        val nombre = view.findViewById<TextView>(R.id.name)
        val average = view.findViewById<TextView>(R.id.average)

        val elemento = getItem(position) as TaqueriaRanking


        pos.text = elemento.posicion.toString()
        nombre.text= elemento.nombre
        average.text = elemento.avg.toString()

        return view

    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}