package mx.tec.tacu

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_evaluation.*
import android.widget.LinearLayout
import kotlin.math.roundToInt
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso


class EvaluationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)

        //Variables de evaluación
        var evalSabor : Int = 0
        var evalTam : Int = 0
        var evalAtencion : Int = 0
        var evalSalsas : Int = 0
        var evalPrecio : Int = 0

        val mLayoutSabor = findViewById<LinearLayout>(R.id.linearSabor)
        val mLayoutTam = findViewById<LinearLayout>(R.id.linearTam)
        val mLayoutPersonal = findViewById<LinearLayout>(R.id.linearAtencion)
        val mLayoutSalsas = findViewById<LinearLayout>(R.id.linearSalsas)
        val mLayoutPrecio = findViewById<LinearLayout>(R.id.linearPrecio)

        //Recibe datos
        var nombre = intent.getStringExtra("nombre")
        var imagen = intent.getStringExtra("imagen")

        var imagenTaqueria: ImageView = findViewById(R.id.imageEvaluar)

        nombreEvaluar.text = nombre
        Picasso.get().load(imagen).into(imagenTaqueria)

        applyPadding(arrayOf(mLayoutSabor,mLayoutTam,mLayoutPersonal,mLayoutSalsas,mLayoutPrecio))

        //BOTONES SABOR

        btnVerySad1.setOnClickListener {
            animationButtons(btnVerySad1, arrayOf(btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
            evalSabor = 1
        }

        btnSad1.setOnClickListener {
            animationButtons(btnSad1, arrayOf(btnVerySad1,btnNeutro1,btnHappy1,btnVeryHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
            evalSabor = 2

        }

        btnNeutro1.setOnClickListener {
            animationButtons(btnNeutro1, arrayOf(btnVerySad1,btnSad1,btnHappy1,btnVeryHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
            evalSabor = 3
        }

        btnHappy1.setOnClickListener {
            animationButtons(btnHappy1, arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnVeryHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
            evalSabor = 4
        }

        btnVeryHappy1.setOnClickListener {
            animationButtons(btnVeryHappy1, arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
            evalSabor = 5

        }

        //BOTONES TAMAÑO

        btnVerySad2.setOnClickListener {
            animationButtons(btnVerySad2, arrayOf(btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
            evalTam = 1
        }

        btnSad2.setOnClickListener {
            animationButtons(btnSad2, arrayOf(btnVerySad2,btnNeutro2,btnHappy2,btnVeryHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
            evalTam = 2
        }

        btnNeutro2.setOnClickListener {
            animationButtons(btnNeutro2, arrayOf(btnVerySad2,btnSad2,btnHappy2,btnVeryHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
            evalTam = 3
        }

        btnHappy2.setOnClickListener {
            animationButtons(btnHappy2, arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnVeryHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
            evalTam = 4
        }

        btnVeryHappy2.setOnClickListener {
            animationButtons(btnVeryHappy2, arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
            evalTam = 5
        }

        //BOTONES ATENCION

        btnVerySad3.setOnClickListener {
            animationButtons(btnVerySad3, arrayOf(btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
            evalAtencion = 1
        }

        btnSad3.setOnClickListener {
            animationButtons(btnSad3, arrayOf(btnVerySad3,btnNeutro3,btnHappy3,btnVeryHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
            evalAtencion = 2

        }

        btnNeutro3.setOnClickListener {
            animationButtons(btnNeutro3, arrayOf(btnVerySad3,btnSad3,btnHappy3,btnVeryHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
            evalAtencion = 3
        }

        btnHappy3.setOnClickListener {
            animationButtons(btnHappy3, arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnVeryHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
            evalAtencion = 4
        }

        btnVeryHappy3.setOnClickListener {
            animationButtons(btnVeryHappy3, arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
            evalAtencion = 5
        }

        //BOTONES SALSAS

        btnVerySad4.setOnClickListener {
            animationButtons(btnVerySad4, arrayOf(btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
            evalSalsas = 1
        }

        btnSad4.setOnClickListener {
            animationButtons(btnSad4, arrayOf(btnVerySad4,btnNeutro4,btnHappy4,btnVeryHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
            evalSalsas = 2
        }

        btnNeutro4.setOnClickListener {
            animationButtons(btnNeutro4, arrayOf(btnVerySad4,btnSad4,btnHappy4,btnVeryHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
            evalSalsas = 3
        }

        btnHappy4.setOnClickListener {
            animationButtons(btnHappy4, arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnVeryHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
            evalSalsas = 4
        }

        btnVeryHappy4.setOnClickListener {
            animationButtons(btnVeryHappy4, arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
            evalSalsas = 5
        }

        //BOTONES PRECIO

        btnVerySad5.setOnClickListener {
            animationButtons(btnVerySad5, arrayOf(btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
            evalPrecio = 1
        }

        btnSad5.setOnClickListener {
            animationButtons(btnSad5, arrayOf(btnVerySad5,btnNeutro5,btnHappy5,btnVeryHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
            evalPrecio = 2
        }

        btnNeutro5.setOnClickListener {
            animationButtons(btnNeutro5, arrayOf(btnVerySad5,btnSad5,btnHappy5,btnVeryHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
            evalPrecio = 3
        }

        btnHappy5.setOnClickListener {
            animationButtons(btnHappy5, arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnVeryHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
            evalPrecio = 4
        }

        btnVeryHappy5.setOnClickListener {
            animationButtons(btnVeryHappy5, arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
            evalPrecio = 5
        }

        btnEvaluate.setOnClickListener {

            Log.e("SABOR:" , evalSabor.toString())
            Log.e("TAMAÑO:" , evalTam.toString())
            Log.e("ATENCIÓN:" , evalAtencion.toString())
            Log.e("SALSAS:" , evalSalsas.toString())
            Log.e("PRECIO:" , evalPrecio.toString())

            /*

            var builder = AlertDialog.Builder(this)
            builder.setTitle("Taqueria evaluada")
                .setMessage("La taqueria fue correctamente evaluada")
                .setNegativeButton("Aceptar") { dialog, button->dialog.dismiss()
                finish() }
                .show()

             */

        }

    }

    //Funcion para hacer la animación de los botones más grandes
    private fun animationButtons(actual: Button, unselected: Array<Button>){
        actual.layoutParams = LinearLayout.LayoutParams(dp2px(70), dp2px(70))

        for(i in unselected){
            i.layoutParams = LinearLayout.LayoutParams(dp2px(50), dp2px(50))
        }
    }

    //Funcion que aplica un padding en el LinearLayout que contiene a los botones
    private fun applyPadding(linear: Array<View>){
        for (i in linear){
            i.setPadding(dp2px(10),dp2px(0),dp2px(10),dp2px(0))
        }
    }

    //Funcion que aplica los margenes a los botones
    private fun myFunction(misBotones: Array<Button>){
        for(i in misBotones){
            setMargins(i)
        }
    }

    //Funcion para establecer los margenes de los botones
    private fun setMargins(view: View) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(10, 0, 10, 0)
            view.requestLayout()
        }
    }

    //Funcion para convertir de dp a px
    private fun dp2px(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    //Funcion para convertir de px a dp
    private fun px2dp(px: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val dp = px / (metrics.densityDpi / 160f)
        return dp.roundToInt().toFloat()
    }

}