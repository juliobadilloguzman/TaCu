package mx.tec.tacu.ui.taquerias

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import mx.tec.tacu.R
import mx.tec.tacu.model.Taqueria
import org.json.JSONArray
import org.json.JSONObject


class TaqueriasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_taquerias, container, false)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val reference = db.collection("TAQUERIA")

        reference.get()
            .addOnSuccessListener{ documents ->

                //json
                lateinit var taqueria : String

                var i =0

                val listTaquerias = ArrayList<Taqueria>()

                //Json para convertir el response a json
                val json = Gson()

                for(document in documents) {

                    //Convertimos a json la respuesta
                    taqueria = json.toJson(document.data).toString()

                    //Como esta en string, tenemos que convertirlo a un objeto JSON
                    val jsonObject = JSONObject(taqueria)

                    listTaquerias.add(Taqueria(jsonObject.getString("nombre"), jsonObject.getString("telefono"),
                        jsonObject.getString("descripcion"), jsonObject.getDouble("calificacion"), jsonObject.getString("horario"),
                        jsonObject.getString("imagen"), jsonObject.getDouble("latitud"), jsonObject.getDouble("longitud")))

                    i++

                }

                println(listTaquerias)


            }

        return root
    }

}
