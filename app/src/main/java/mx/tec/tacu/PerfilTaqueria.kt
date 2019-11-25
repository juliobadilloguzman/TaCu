package mx.tec.tacu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_perfil_taqueria.*

class PerfilTaqueria : AppCompatActivity() {

    private lateinit var txtNombreTaqueria: TextView
    private lateinit var txtCalifTaqueria: TextView
    private lateinit var txtDescripcionTaqueria: TextView
    private lateinit var txtHorarioTaqueria: TextView
    private lateinit var txtTelefonoTaqueria: TextView
    private lateinit var iconTaqueriaImagen: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_taqueria)
        val taqueriaCalif = intent.getStringExtra("myCalif")
        val taqueriaDescripcion = intent.getStringExtra("myDescripcion")
        val taqueriaHorario = intent.getStringExtra("myHorario")
        val taqueriaImagen = intent.getStringExtra("myImagen")
        val taqueriaNombre = intent.getStringExtra("myNombre")
        val taqueriaTelefono = intent.getStringExtra("myTelefono")

        val valorFlag = intent.getBooleanExtra("flag",false)

        //activity_evaluation

        txtNombreTaqueria = findViewById(R.id.textView)
        txtCalifTaqueria = findViewById(R.id.textView3)
        txtDescripcionTaqueria = findViewById(R.id.textView2)
        txtHorarioTaqueria = findViewById(R.id.textView4)
        txtTelefonoTaqueria = findViewById(R.id.textView5)
        iconTaqueriaImagen = findViewById(R.id.imageView)

        txtNombreTaqueria.setText(taqueriaNombre)
        txtCalifTaqueria.setText(taqueriaCalif)
        txtDescripcionTaqueria.setText(taqueriaDescripcion)
        txtHorarioTaqueria.setText(taqueriaHorario)
        txtTelefonoTaqueria.setText(taqueriaTelefono)

        Picasso.get().load(taqueriaImagen).into(iconTaqueriaImagen)

        buttonEvaluar.setOnClickListener {
            val intent = Intent(this@PerfilTaqueria,EvaluationActivity::class.java)
            startActivity(intent)
        }

        if(valorFlag){
            buttonEvaluar.visibility = View.GONE
        }


    }
}
