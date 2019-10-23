package mx.tec.tacu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var auth: FirebaseAuth

    //Shared Preferences
    private var EMPTY = ""
    private var EMAIL = "email"
    private var PASSWORD = "password"
    private var myPreferences = "myPreferences"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         val sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        //Verificar que haya un usuario
        if(sharedPreferences.getString(EMAIL, EMPTY) != EMPTY){

            var intent = Intent(this, HomeLocationActivity::class.java)

            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)


        }else{

            setContentView(R.layout.login)

            txtEmail = findViewById(R.id.loginEmail)
            txtPassword = findViewById(R.id.loginPassword)

            //Auth Firebase
            auth = FirebaseAuth.getInstance()

            ///////LOGIN
            btnLogIn.setOnClickListener{

                //Verificar campos vacios
                if(!TextUtils.isEmpty(txtEmail.text.toString()) && !TextUtils.isEmpty(txtPassword.text.toString())){

                    auth.signInWithEmailAndPassword(txtEmail.text.toString(), txtPassword.text.toString())
                        .addOnCompleteListener{

                            //Verificamos si
                            if(it.isSuccessful){

                                //Agregar los datos al shared preferences
                                with(sharedPreferences.edit()){
                                    putString(EMAIL, txtEmail.text.toString())
                                    putString(PASSWORD, txtPassword.text.toString())
                                    commit()
                                }

                                val intent = Intent(this, HomeLocationActivity::class.java)
                                startActivity(intent)


                            }else{

                                Toast.makeText(this, "Datos invalidos", Toast.LENGTH_SHORT).show()

                            }


                        }


                }else{
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()

                }

            }

            ////////Ir a el registro
            btnCreateAccount.setOnClickListener{
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }

            ////////Ir a olvidaste tu contraseña
            btnForgetPassword.setOnClickListener{
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }


        }



    }


}