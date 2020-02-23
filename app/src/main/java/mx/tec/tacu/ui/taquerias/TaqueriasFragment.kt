package mx.tec.tacu.ui.taquerias

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_taquerias.*
import mx.tec.tacu.PerfilTaqueria

import mx.tec.tacu.R
import mx.tec.tacu.adapter.ElementoAdapter
import mx.tec.tacu.model.Taqueria
import org.json.JSONArray
import org.json.JSONObject


class TaqueriasFragment : Fragment() {

    var listTaquerias = ArrayList<Taqueria>()
    lateinit var adapter : ElementoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        /*
        *
        *
        *

        *
        *
        * */



        val root = inflater.inflate(R.layout.fragment_taquerias, container, false)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val reference = db.collection("TAQUERIA")

        reference.get()
            .addOnSuccessListener{ documents ->

                //json
                lateinit var taqueria : String

                var i =0


                //val displayListTaquerias = ArrayList<Taqueria>()

                lateinit var editTextSearch: EditText

                //Json para convertir el response a json
                val json = Gson()

                for(document in documents) {

                    //Convertimos a json la respuesta
                    taqueria = json.toJson(document.data).toString()

                    //Como esta en string, tenemos que convertirlo a un objeto JSON
                    val jsonObject = JSONObject(taqueria)

                    listTaquerias.add(Taqueria(document.id, jsonObject.getString("nombre"), jsonObject.getString("telefono"),
                        jsonObject.getString("descripcion"), jsonObject.getDouble("calificacion"), jsonObject.getString("horario"),
                        jsonObject.getString("imagen"), jsonObject.getDouble("latitud"), jsonObject.getDouble("longitud")))

                    i++

                }

                //println(listTaquerias)
                editTextSearch = root.findViewById(R.id.action_search)
                val myRecycleView : RecyclerView = root.findViewById(R.id.listaTaquerias)
                myRecycleView.layoutManager=GridLayoutManager(activity, 2,RecyclerView.VERTICAL, false)

                adapter= ElementoAdapter(listTaquerias)
                myRecycleView.adapter=adapter

                editTextSearch.addTextChangedListener(object: TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
                        filterList(s.toString())
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                })


/*
                mySearcher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {

                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {

                        if(newText.isNotEmpty()){

                            displayListTaquerias.clear()

                            val search = newText.toLowerCase()
                            repeat(listTaquerias.size) {
                                if(search in it){

                                }
                            }


                            myRecycleView.adapter?.notifyDataSetChanged()

                        } else {
                            displayListTaquerias.clear()
                            displayListTaquerias.addAll(listTaquerias)
                            myRecycleView.adapter?.notifyDataSetChanged()
                        }
                        //adapter.filter(newText)
                        return true
                    }
                })*/

                adapter.onItemClick = { taqueria ->

                    println("SE MANDARA EL ID:" + taqueria.id)

                    val intent = Intent(activity!!, PerfilTaqueria::class.java)

                    intent.putExtra("calificacion", taqueria.calificacion.toString())
                    intent.putExtra("myDescripcion", taqueria.descripcion)
                    intent.putExtra("myHorario", taqueria.horario)
                    intent.putExtra("myImagen", taqueria.imagen)
                    intent.putExtra("idTaqueria", taqueria.id)
                    intent.putExtra("myNombre", taqueria.nombre)
                    intent.putExtra("myTelefono", taqueria.telefono)

                    activity!!.startActivity(intent)
                }




            }

        return root
    }

    private fun filterList(filterItem: String) {
        val tempList: ArrayList<Taqueria> = ArrayList()

        for(d in listTaquerias){

            if(filterItem in d.nombre){tempList.add(d)} else if ( filterItem in d.calificacion.toString()){tempList.add(d)}

        }

        adapter.updateList(tempList)

    }

}
