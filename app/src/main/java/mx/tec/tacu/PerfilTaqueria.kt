package mx.tec.tacu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

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

    }
}
