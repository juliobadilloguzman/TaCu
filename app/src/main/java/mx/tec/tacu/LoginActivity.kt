package mx.tec.tacu

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        txtEmail = findViewById(R.id.loginEmail)
        txtPassword = findViewById(R.id.loginPassword)

        //Auth Firebase
        auth = FirebaseAuth.getInstance()

        ///////LOGIN
        btnLogIn.setOnClickListener{
            login(txtEmail.text.toString(), txtPassword.text.toString())
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

    private fun login(email:String, password: String){

        //Verificar campos vacios
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{

                    //Verificamos si
                    if(it.isSuccessful){

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


}