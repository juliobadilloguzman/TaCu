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
import android.widget.TextView
import mx.tec.tacu.LoginActivity

import mx.tec.tacu.R

class PerfilFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var EMPTY = ""
        var EMAIL = "email"
        var PASSWORD = "password"
        var myPreferences = "myPreferences"

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnCerrarSesion = root.findViewById(R.id.btnCerrarSesion) as Button

        val sharedPreferences = this.activity!!.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        btnCerrarSesion.setOnClickListener{

            //Borrar los sharedpreferences
            with(sharedPreferences.edit()) {
                putString(EMAIL, EMPTY)
                putString(PASSWORD, EMPTY)
                commit()
            }

            var intent = Intent(activity, LoginActivity::class.java)

            startActivity(intent)

            activity!!.finish()

        }



        return root
    }

}
