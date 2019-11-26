package mx.tec.tacu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_perfil_taqueria.*
import mx.tec.tacu.model.Persona
import mx.tec.tacu.model.Taqueria

class PerfilTaqueria : AppCompatActivity() {

    private lateinit var txtNombreTaqueria: TextView
    private lateinit var txtCalifTaqueria: TextView
    private lateinit var txtDescripcionTaqueria: TextView
    private lateinit var txtHorarioTaqueria: TextView
    private lateinit var txtTelefonoTaqueria: TextView
    private lateinit var iconTaqueriaImagen: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_taqueria)

        val taqueriaCalif = intent.getStringExtra("myCalif")
        val taqueriaDescripcion = intent.getStringExtra("myDescripcion")
        val taqueriaHorario = intent.getStringExtra("myHorario")
        val taqueriaImagen = intent.getStringExtra("myImagen")
        val taqueriaNombre = intent.getStringExtra("myNombre")
        val taqueriaTelefono = intent.getStringExtra("myTelefono")

        val valorFlag = intent.getBooleanExtra("flag",false)

        //activity_evaluation

        txtNombreTaqueria = findViewById(R.id.textView)
        txtCalifTaqueria = findViewById(R.id.textView3)
        txtDescripcionTaqueria = findViewById(R.id.textView2)
        txtHorarioTaqueria = findViewById(R.id.textView4)
        txtTelefonoTaqueria = findViewById(R.id.textView5)
        iconTaqueriaImagen = findViewById(R.id.imageView)

        txtNombreTaqueria.setText(taqueriaNombre)
        txtCalifTaqueria.setText(taqueriaCalif)
        txtDescripcionTaqueria.setText(taqueriaDescripcion)
        txtHorarioTaqueria.setText(taqueriaHorario)
        txtTelefonoTaqueria.setText(taqueriaTelefono)

        Picasso.get().load(taqueriaImagen).into(iconTaqueriaImagen)

        buttonEvaluar.setOnClickListener {
            val intent = Intent(this@PerfilTaqueria,EvaluationActivity::class.java)
            startActivity(intent)
        }

        if(valorFlag){

            buttonEvaluar.visibility = View.GONE

            val idTaqueriaRecibido = intent.getStringExtra("idTaqueria")

            println("ID RECIBIDO: " + idTaqueriaRecibido)

            val db: FirebaseFirestore = FirebaseFirestore.getInstance()

            val reference = db.collection("TAQUERIA").document(idTaqueriaRecibido)

            reference.get().addOnSuccessListener { document ->

                //json
                lateinit var taqueria : String

                println("DOCUMENT DATA:" + document.data)

                //Json para convertir el response a json
                val json = Gson()

                taqueria = json.toJson(document.data).toString()

                //Almanacenaremos la persona
                var taqueriaObject = Taqueria()

                //Convertimos el json al modelo Persona
                taqueriaObject = json.fromJson<Taqueria>(taqueria, Taqueria::class.java)

                txtNombreTaqueria.setText(taqueriaObject.nombre)
                txtCalifTaqueria.setText(taqueriaObject.calificacion.toString())
                txtDescripcionTaqueria.setText(taqueriaObject.descripcion)
                txtHorarioTaqueria.setText(taqueriaObject.horario)
                txtTelefonoTaqueria.setText(taqueriaObject.telefono)

                Picasso.get().load(taqueriaObject.imagen).into(iconTaqueriaImagen)

            }



        }


    }
}
