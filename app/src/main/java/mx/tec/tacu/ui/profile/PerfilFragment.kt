package mx.tec.tacu.ui.profile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import mx.tec.tacu.LoginActivity

import mx.tec.tacu.R
import mx.tec.tacu.model.Persona




class PerfilFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var EMPTY = ""
        var EMAIL = "email"
        var ID = "id"
        var PASSWORD = "password"
        var myPreferences = "myPreferences"

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnCerrarSesion = root.findViewById(R.id.btnCerrarSesion) as Button

        val sharedPreferences = this.activity!!.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        val id: String = sharedPreferences.getString("id", EMPTY)!!

        //Inputs
        val nombre = root.findViewById(R.id.nombreMenu) as EditText
        val apellido = root.findViewById(R.id.apellidoMenu) as EditText
        val telefono = root.findViewById(R.id.telefonoMenu) as EditText
        val correo = root.findViewById(R.id.correoMenu) as EditText
        val genero = root.findViewById(R.id.generoMenu) as EditText
        val pass = root.findViewById(R.id.passwordMenu) as EditText

        val nombreTitulo = root.findViewById(R.id.titulo_nombreMenu) as TextView
        val correoTitulo = root.findViewById(R.id.titulo_correoMenu) as TextView
        val telefonoTitulo = root.findViewById(R.id.titulo_telefonoMenu) as TextView


        //Base de datos
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val reference = db.collection("PERSONA").whereEqualTo("id", id)

        reference.get().addOnSuccessListener { documents ->

            //json
            lateinit var persona : String

            //Json para convertir el response a json
            val json = Gson()

            //Convertimos a json la respuesta
            for(document in documents)
                persona = json.toJson(document.data).toString()

            //Almanacenaremos la persona
            var personFinal = Persona()

            //Json para convertir el json a Persona
            val gson = Gson()

            //Convertimos el json al modelo Persona
            personFinal = gson.fromJson<Persona>(persona, Persona::class.java)

            ////*******SUSTITUIMOS LOS DATOS
            nombre.setText(personFinal.nombre)
            apellido.setText(personFinal.apellido)
            telefono.setText(personFinal.telefono)
            correo.setText(personFinal.correo)
            genero.setText(personFinal.sexo)
            pass.setText("********")

            nombreTitulo.text = personFinal.nombre
            correoTitulo.text = personFinal.correo
            telefonoTitulo.text = personFinal.telefono

        }



        btnCerrarSesion.setOnClickListener{

            var auth: FirebaseAuth
            auth = FirebaseAuth.getInstance()

            //Borrar los sharedpreferences
            with(sharedPreferences.edit()) {
                putString(ID, EMPTY)
                putString(EMAIL, EMPTY)
                putString(PASSWORD, EMPTY)
                commit()
            }

            auth.signOut()

            var intent = Intent(activity, LoginActivity::class.java)

            startActivity(intent)

            activity!!.finish()

        }



        return root
    }



}
