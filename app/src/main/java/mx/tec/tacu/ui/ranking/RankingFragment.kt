package mx.tec.tacu.ui.ranking

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_ranking.*
import mx.tec.tacu.PerfilTaqueria

import mx.tec.tacu.R
import mx.tec.tacu.adapter.ElementoListAdapter
import mx.tec.tacu.model.Taqueria
import mx.tec.tacu.model.TaqueriaRanking
import org.json.JSONObject

class RankingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_ranking, container, false)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        var myFlag: Boolean

        val listTaqueriasRanking = ArrayList<Taqueria>()

        val reference = db.collection("TAQUERIA").orderBy("calificacion", Query.Direction.DESCENDING).limit(3)

        val imageFirst: ImageView = root.findViewById(R.id.imageViewFirstPlace)
        val imageSecond: ImageView = root.findViewById(R.id.imageViewSecondPlace)
        val imageThird: ImageView = root.findViewById(R.id.imageViewThirdPlace)

        reference.get()
            .addOnSuccessListener { documents ->

                //json
                lateinit var taqueria : String

                var i =0



                //Json para convertir el response a json
                val json = Gson()

                for(document in documents) {

                    //Convertimos a json la respuesta
                    taqueria = json.toJson(document.data).toString()


                    //Como esta en string, tenemos que convertirlo a un objeto JSON
                    val jsonObject = JSONObject(taqueria)

                    listTaqueriasRanking.add(Taqueria(document.id,jsonObject.getString("nombre"), jsonObject.getString("telefono"),
                        jsonObject.getString("descripcion"), jsonObject.getDouble("calificacion"), jsonObject.getString("horario"),
                        jsonObject.getString("imagen"), jsonObject.getDouble("latitud"), jsonObject.getDouble("longitud")))

                    i++

                }

                println(("*****TAQUERIAS RANKING***"))
                println(listTaqueriasRanking)


                //Sustituir imagenes en el ranking

                Picasso.get().load(listTaqueriasRanking[0].imagen).into(imageFirst)
                Picasso.get().load(listTaqueriasRanking[1].imagen).into(imageSecond)
                Picasso.get().load(listTaqueriasRanking[2].imagen).into(imageThird)

            }

        imageFirst.setOnClickListener {
            myFlag = true
            val intent = Intent(activity, PerfilTaqueria::class.java)
            intent.putExtra("flag", myFlag)
            intent.putExtra("idTaqueria", listTaqueriasRanking[0].id)
            startActivity(intent)
        }

        imageSecond.setOnClickListener {
            myFlag = true
            val intent = Intent(activity, PerfilTaqueria::class.java)
            intent.putExtra("flag", myFlag)
            intent.putExtra("idTaqueria", listTaqueriasRanking[1].id)
            startActivity(intent)
        }

        imageThird.setOnClickListener {
            myFlag = true
            val intent = Intent(activity, PerfilTaqueria::class.java)
            intent.putExtra("flag", myFlag)
            intent.putExtra("idTaqueria", listTaqueriasRanking[2].id)
            startActivity(intent)
        }

        //LISTA

        val reference2 = db.collection("TAQUERIA").orderBy("calificacion", Query.Direction.DESCENDING)

        reference2.get().addOnSuccessListener { documents ->

            //json
            lateinit var taquerias : String

            var i =0

            val listTaquerias = ArrayList<TaqueriaRanking>()

            //Json para convertir el response a json
            val json = Gson()

            for(document in documents) {

                //Convertimos a json la respuesta
                taquerias = json.toJson(document.data).toString()

                //Como esta en string, tenemos que convertirlo a un objeto JSON
                val jsonObject = JSONObject(taquerias)

                listTaquerias.add(TaqueriaRanking(i+1, jsonObject.getString("nombre"),
                    jsonObject.getDouble("calificacion")))

                i++

            }

            println("TAQUERIAS: ")
            println(listTaquerias)

            //Mostrar local
            val adaptador = ElementoListAdapter(context!!, R.layout.template_ranking_item, listTaquerias)

            listRanking.adapter = adaptador

        }

        return root
    }



}
