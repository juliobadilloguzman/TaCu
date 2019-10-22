package mx.tec.tacu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.recover_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var correo: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recover_password)

        correo = findViewById(R.id.recoverEmail)

        auth = FirebaseAuth.getInstance()

        ////////Ir a el registro
        btnCreateAccountRecover.setOnClickListener{

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }

        ///////RECOVER PASSWORD
        btnSendEmail.setOnClickListener{

            sendEmail(correo.text.toString())

        }

    }

    private fun sendEmail(correo: String){

        if(!TextUtils.isEmpty(correo)){

            auth.sendPasswordResetEmail(correo)
                .addOnCompleteListener{

                    if(it.isSuccessful){
                        Toast.makeText(this, "Correo enviado", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "No se pudo enviar el correo", Toast.LENGTH_SHORT).show()
                    }

                }

        }

    }


}
