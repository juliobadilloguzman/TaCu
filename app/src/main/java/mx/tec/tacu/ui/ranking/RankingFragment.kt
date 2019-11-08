package mx.tec.tacu.ui.ranking

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

import mx.tec.tacu.R

class RankingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_ranking, container, false)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val reference = db.collection("TAQUERIA").orderBy("calificacion", Query.Direction.DESCENDING).limit(3)

        reference.get()
            .addOnSuccessListener { documents ->

                for(document in documents) {

                    println("Posicion: " + document.data)

                }

            }

        return root
    }

}
