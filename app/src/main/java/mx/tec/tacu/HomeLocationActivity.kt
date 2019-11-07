package mx.tec.tacu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.home_location.*
import kotlinx.android.synthetic.main.recover_password.*

class HomeLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_location)

        hideKeyboard()

        ////////Ir al main activity
        btnHomeEvaluate.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

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
