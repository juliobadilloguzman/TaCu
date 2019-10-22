package mx.tec.tacu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.register.*
import mx.tec.tacu.model.Persona
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {

    //Datos
    private lateinit var txtNombre: EditText
    private lateinit var txtApellidos: EditText
    private lateinit var txtNumeroTelefono: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtFechaNacimiento: EditText
    private lateinit var checkSexo: String
    private lateinit var creacion: String
    private lateinit var txtPassword: EditText
    private lateinit var txtConfirmPassword: EditText
    private var checkTerminos: Boolean = false
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        //Inicializacion de datos
        txtNombre = findViewById(R.id.registerName)
        txtApellidos = findViewById(R.id.registerLastName)
        txtNumeroTelefono = findViewById(R.id.registerPhone)
        txtCorreo = findViewById(R.id.registerEmail)
        txtFechaNacimiento = findViewById(R.id.registerBirth)
        txtPassword = findViewById(R.id.registerPassword)
        txtConfirmPassword = findViewById(R.id.registerConfirmPass)

        auth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        creacion = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()).toString()

        println("CREACION:" + creacion)


        btnRegister.setOnClickListener{

            if(registerMale.isChecked)
                checkSexo = "HOMBRE"
            else if(registerFemale.isChecked)
                checkSexo = "MUJER"
            else if(registerOther.isChecked)
                checkSexo = "OTRO"
            else
                checkSexo = ""

           if(registerConditions.isChecked){

               register(txtNombre.toString(), txtApellidos.toString(), txtNumeroTelefono.toString(), txtCorreo.toString(), txtFechaNacimiento.toString(),
                   checkSexo, txtPassword.toString(), txtCorreo.toString(), creacion)

           }else{
               Toast.makeText(this, "Acepta los terminos y condiciones", Toast.LENGTH_SHORT).show()
           }



        }




    }

    private fun register(nombre: String, apellido: String, numeroTelefono: String, correo: String, fechaNacimiento: String,
    sexo: String, password: String, confirmPassword: String, creacion: String){

        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(numeroTelefono) && !TextUtils.isEmpty(correo)
            && !TextUtils.isEmpty(fechaNacimiento) && !TextUtils.isEmpty(sexo) && sexo.isNullOrBlank() && sexo.isNullOrEmpty() && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)){

            //Verificar que las contraseñas sean iguales

            if(password != confirmPassword){
                Toast.makeText(this, "Las contraseñas deben de ser iguales", Toast.LENGTH_SHORT).show()
            }else{

                val dbReference: CollectionReference = db.collection("PERSONA")

                val persona = Persona(nombre, apellido, correo, sexo, numeroTelefono, fechaNacimiento, creacion)

                dbReference.add(persona)
                    .addOnCompleteListener{

                        if(it.isSuccessful){
                            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                        }


                    }
            }



        }else{
            Toast.makeText(this, "Verifica todos los campos", Toast.LENGTH_SHORT).show()
        }


    }
}
