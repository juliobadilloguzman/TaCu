package mx.tec.tacu.ui.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import mx.tec.tacu.Helpers.EnumTextItem
import mx.tec.tacu.LoginActivity

import mx.tec.tacu.R
import mx.tec.tacu.model.Persona
import mx.tec.tacu.ui.questions.PreguntasFragment

class PerfilFragment : Fragment() {

    enum class Position {
        deseleccion, masculino, femenino, otro
    }

    //private lateinit var sexoSpinner: Spinner
    private var genero: String = ""

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
        val btnEliminarCuenta = root.findViewById(R.id.btnDeleteAccount) as Button
        val btnEditarCuenta = root.findViewById(R.id.btnEdit) as Button

        val sharedPreferences = this.activity!!.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        val id: String = sharedPreferences.getString("id", EMPTY)!!

        //Inputs
        val nombre = root.findViewById(R.id.nombreMenu) as EditText
        val apellido = root.findViewById(R.id.apellidoMenu) as EditText
        val telefono = root.findViewById(R.id.telefonoMenu) as EditText
        val correo = root.findViewById(R.id.correoMenu) as EditText
        val password = root.findViewById(R.id.passwordMenu) as EditText

        //sexoSpinner = root.findViewById(R.id.generoMenu)

        /*val adaptador = ArrayAdapter<EnumTextItem<Position>>(activity!!, android.R.layout.simple_spinner_item, listOf(EnumTextItem(Position.deseleccion, "Selecciona una opcion"), EnumTextItem(Position.masculino, "Masculino"),  EnumTextItem(Position.femenino, "Femenino"), EnumTextItem(Position.otro, "Otro"))).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sexoSpinner.adapter = adapter
        }*/

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
            //genero.setText(personFinal.sexo)
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

            val intent = Intent(activity, LoginActivity::class.java)

            startActivity(intent)

            activity!!.finish()

        }

        btnEliminarCuenta.setOnClickListener {

            var auth: FirebaseAuth
            auth = FirebaseAuth.getInstance()

            //ELIMINARLO DE LA AUTENTICACION

            var firebaseUser: FirebaseUser = auth.currentUser!!

            println("EL ID DEL CURRENT USER ES: " +  firebaseUser.uid)

            var builder = AlertDialog.Builder(activity!!)

            builder.setTitle("¿Deseas eliminar tu cuenta?")
                .setMessage("No podrás revertir esta acccion")
                .setNegativeButton("Cerrar", { dialog, button->dialog.dismiss()})
                .setPositiveButton("Aceptar") { dialog, button ->

                    firebaseUser.delete()

                    //ELIMINARLO DE SHARED PREFERENCES

                    with(sharedPreferences.edit()) {
                        putString(ID, EMPTY)
                        putString(EMAIL, EMPTY)
                        putString(PASSWORD, EMPTY)
                        commit()
                    }

                    //ELIMINARLO DE LA BASE DE DATOS

                    reference.get().addOnSuccessListener {
                            documents ->

                        var id = ""

                        for(document in documents){
                            id = document.id
                        }

                        val reference = db.collection("PERSONA").document(id)
                        reference.delete().addOnSuccessListener {
                            println("ELIMINADO CORRECTAMENTE")
                        }


                    }

                    auth.signOut()

                    val intent = Intent(activity, LoginActivity::class.java)

                    startActivity(intent)

                    activity!!.finish()

                }
                .show()




        }

        btnEditarCuenta.setOnClickListener {

            val reference = db.collection("PERSONA").whereEqualTo("id", id)

            reference.get().addOnSuccessListener { documents ->

                var id = ""

                for(document in documents){
                    id = document.id
                }

                val referenceEdit = db.collection("PERSONA").document(id)

                println("EL ID A EDITAR ES:" + id)

                val data = HashMap<String, Any>()

                data.put("nombre", nombre.text.toString())
                data.put("apellido", apellido.text.toString())
                data.put("telefono", telefono.text.toString())
                data.put("correo", correo.text.toString())

                println(data)

                referenceEdit.update(data).addOnSuccessListener {

                    Toast.makeText(context, "Cuenta actualizada correctamente", Toast.LENGTH_SHORT).show()

                    nombre.setText(nombre.text.toString())
                    apellido.setText(apellido.text.toString())
                    telefono.setText(telefono.text.toString())
                    correo.setText(correo.text.toString())

                }.addOnFailureListener{
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
                }

                //Editar contraseña de Auth
                var auth: FirebaseAuth
                auth = FirebaseAuth.getInstance()

                var firebaseUser: FirebaseUser = auth.currentUser!!

                firebaseUser.updatePassword(password.text.toString()).addOnSuccessListener {
                    println("Contraseña editada!")
                }
                    .addOnFailureListener{
                        println("ERROR AL EDITAR CONTRASEÑA")
                    }


            }


        }

        return root
    }



}
