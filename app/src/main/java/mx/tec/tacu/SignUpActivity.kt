package mx.tec.tacu

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        hideKeyboard()

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

        val cal = Calendar.getInstance()

        creacion = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()).toString()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd-MM-yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            registerBirth.setText(sdf.format(cal.time))
        }

        registerBirth.setOnClickListener {
            DatePickerDialog(this@SignUpActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

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

               register(txtNombre.text.toString(), txtApellidos.text.toString(), txtNumeroTelefono.text.toString(), txtCorreo.text.toString(), txtFechaNacimiento.text.toString(),
                   checkSexo, txtPassword.text.toString(), txtConfirmPassword.text.toString(), creacion)

           }else{
               Toast.makeText(this, "Acepta los terminos y condiciones", Toast.LENGTH_SHORT).show()
           }

        }

        btnRegisterLogIn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun register(nombre: String, apellido: String, numeroTelefono: String, correo: String, fechaNacimiento: String,
    sexo: String, password: String, confirmPassword: String, creacion: String){


        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(numeroTelefono)
            && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(fechaNacimiento) && !TextUtils.isEmpty(sexo)
            && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)){


            //Verificar que las contraseñas sean iguales
            if(password != confirmPassword){
                Toast.makeText(this, "Las contraseñas deben de ser iguales", Toast.LENGTH_SHORT).show()
            }else{

                //Verificar que el usuario exista
                auth.fetchSignInMethodsForEmail(correo)
                    .addOnCompleteListener{

                        var exists: Boolean = !it.result!!.signInMethods!!.isEmpty()

                        if(exists){
                            Toast.makeText(this, "El email ya esta en uso", Toast.LENGTH_SHORT).show()
                        }else{

                            println("SI ENTRE AL REGISTRO")

                            println("CORREO: " + correo)
                            println("PASSWORRD: " + password)

                            auth.createUserWithEmailAndPassword(correo, password)
                                .addOnCompleteListener{

                                    if(it.isSuccessful){

                                        val dbReference: CollectionReference = db.collection("PERSONA")

                                        val persona = Persona(FirebaseAuth.getInstance().currentUser!!.uid, nombre, apellido, correo, sexo, numeroTelefono, fechaNacimiento, creacion)
                                        

                                        dbReference.add(persona)
                                            .addOnCompleteListener{

                                                if(it.isSuccessful){
                                                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()


                                                    val intent = Intent(this, LoginActivity::class.java)
                                                    startActivity(intent)
                                                }else{
                                                    Toast.makeText(this, "ERROR al registrar usuario", Toast.LENGTH_SHORT).show()
                                                }


                                            }

                                    }

                                }
                                .addOnFailureListener{
                                    Toast.makeText(this, "ERROR AL REGISTRARSE", Toast.LENGTH_SHORT).show()
                                }
                        }

                    }

            }

        }else{
            Toast.makeText(this, "VERIFICA TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
        }
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        // else {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        // }
    }

}
