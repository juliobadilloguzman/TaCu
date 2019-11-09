package mx.tec.tacu.ui.questions

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView

import com.diegodobelo.expandingview.ExpandingList

import android.widget.TextView
import com.diegodobelo.expandingview.ExpandingItem

import mx.tec.tacu.R

class PreguntasFragment : Fragment() {

    private var mExpandingList: ExpandingList? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_preguntas, container, false)

        mExpandingList = root.findViewById(R.id.expanding_list_main)
        createItems()

        return root
    }

    private fun createItems() {
        addItem("Pregunta 1", arrayOf("Esta es la respuesta"), R.color.colorRed, R.drawable.arrowdown)
        addItem("Pregunta 2", arrayOf("Dog", "Horse", "Boat"), R.color.colorRed, R.drawable.arrowdown)
        addItem("Pregunta 3", arrayOf("Esta es otra respuesta"), R.color.colorRed, R.drawable.arrowdown)
    }

    private fun addItem(title: String, subItems: Array<String>, colorRes: Int, iconRes: Int) {
        //Let's create an item with R.layout.expanding_layout
        val item = mExpandingList!!.createNewItem(R.layout.expanding_layout)

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes)
            item.setIndicatorIconRes(iconRes)
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            (item.findViewById(R.id.title) as TextView).text = title

            //We can create items in batch.
            item.createSubItems(subItems.size)
            for (i in 0 until item.subItemsCount) {
                //Let's get the created sub item by its index
                val view = item.getSubItemView(i)

                //Let's set some values in
                configureSubItem(item, view, subItems[i])
            }

        }
    }

    private fun configureSubItem(item: ExpandingItem?, view: View, subTitle: String) {
        (view.findViewById(R.id.sub_title) as TextView).text = subTitle
    }



    internal interface OnItemCreated {
        fun itemCreated(title: String)
    }


}
