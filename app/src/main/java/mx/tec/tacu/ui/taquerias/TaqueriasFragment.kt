package mx.tec.tacu.ui.taquerias

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import mx.tec.tacu.R
import mx.tec.tacu.model.Taqueria
import org.json.JSONArray



class TaqueriasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_taquerias, container, false)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val reference = db.collection("TAQUERIA")

        reference.get()
            .addOnSuccessListener{ documents ->




                for (document in documents) {
                    Log.d("TAQUERIA: ", "${document.id} => ${document.data}")
                }



            }

        return root
    }

}
