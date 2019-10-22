package mx.tec.tacu.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import mx.tec.tacu.R

class PerfilFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnCerrarSesion = root.findViewById(R.id.btnCerrarSesion) as Button

        btnCerrarSesion.setOnClickListener{
            println("Cerre Sesión")
        }



        return root
    }

}
