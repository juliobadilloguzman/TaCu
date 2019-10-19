package mx.tec.tacu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.recover_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recover_password)

        ////////Ir a el registro
        btnCreateAccountRecover.setOnClickListener{

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }

    }
}
