package mx.tec.tacu

import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_evaluation.*
import android.widget.LinearLayout
import kotlin.math.roundToInt
import android.view.ViewGroup
import android.view.View


class EvaluationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)

        val mLayoutSabor = findViewById<LinearLayout>(R.id.linearSabor)
        val mLayoutTam = findViewById<LinearLayout>(R.id.linearTam)
        val mLayoutPersonal = findViewById<LinearLayout>(R.id.linearAtencion)
        val mLayoutSalsas = findViewById<LinearLayout>(R.id.linearSalsas)
        val mLayoutPrecio = findViewById<LinearLayout>(R.id.linearPrecio)

        mLayoutSabor.setPadding(dp2px(10),dp2px(0),dp2px(10),dp2px(0))
        mLayoutTam.setPadding(dp2px(10),dp2px(0),dp2px(10),dp2px(0))
        mLayoutPersonal.setPadding(dp2px(10),dp2px(0),dp2px(10),dp2px(0))
        mLayoutSalsas.setPadding(dp2px(10),dp2px(0),dp2px(10),dp2px(0))
        mLayoutPrecio.setPadding(dp2px(10),dp2px(0),dp2px(10),dp2px(0))


        //BOTONES SABOR

        btnVerySad1.setOnClickListener {
            animationButtons(btnVerySad1, arrayOf(btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
        }

        btnSad1.setOnClickListener {
            animationButtons(btnSad1, arrayOf(btnVerySad1,btnNeutro1,btnHappy1,btnVeryHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))

        }

        btnNeutro1.setOnClickListener {
            animationButtons(btnNeutro1, arrayOf(btnVerySad1,btnSad1,btnHappy1,btnVeryHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
        }

        btnHappy1.setOnClickListener {
            animationButtons(btnHappy1, arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnVeryHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))
        }

        btnVeryHappy1.setOnClickListener {
            animationButtons(btnVeryHappy1, arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1))
            myFunction(arrayOf(btnVerySad1,btnSad1,btnNeutro1,btnHappy1,btnVeryHappy1))

        }

        //BOTONES TAMAÑO

        btnVerySad2.setOnClickListener {
            animationButtons(btnVerySad2, arrayOf(btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
        }

        btnSad2.setOnClickListener {
            animationButtons(btnSad2, arrayOf(btnVerySad2,btnNeutro2,btnHappy2,btnVeryHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
        }

        btnNeutro2.setOnClickListener {
            animationButtons(btnNeutro2, arrayOf(btnVerySad2,btnSad2,btnHappy2,btnVeryHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
        }

        btnHappy2.setOnClickListener {
            animationButtons(btnHappy2, arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnVeryHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
        }

        btnVeryHappy2.setOnClickListener {
            animationButtons(btnVeryHappy2, arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2))
            myFunction(arrayOf(btnVerySad2,btnSad2,btnNeutro2,btnHappy2,btnVeryHappy2))
        }

        //BOTONES ATENCION

        btnVerySad3.setOnClickListener {
            animationButtons(btnVerySad3, arrayOf(btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
        }

        btnSad3.setOnClickListener {
            animationButtons(btnSad3, arrayOf(btnVerySad3,btnNeutro3,btnHappy3,btnVeryHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))

        }

        btnNeutro3.setOnClickListener {
            animationButtons(btnNeutro3, arrayOf(btnVerySad3,btnSad3,btnHappy3,btnVeryHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
        }

        btnHappy3.setOnClickListener {
            animationButtons(btnHappy3, arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnVeryHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
        }

        btnVeryHappy3.setOnClickListener {
            animationButtons(btnVeryHappy3, arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3))
            myFunction(arrayOf(btnVerySad3,btnSad3,btnNeutro3,btnHappy3,btnVeryHappy3))
        }

        //BOTONES SALSAS

        btnVerySad4.setOnClickListener {
            animationButtons(btnVerySad4, arrayOf(btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
        }

        btnSad4.setOnClickListener {
            animationButtons(btnSad4, arrayOf(btnVerySad4,btnNeutro4,btnHappy4,btnVeryHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
        }

        btnNeutro4.setOnClickListener {
            animationButtons(btnNeutro4, arrayOf(btnVerySad4,btnSad4,btnHappy4,btnVeryHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
        }

        btnHappy4.setOnClickListener {
            animationButtons(btnHappy4, arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnVeryHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
        }

        btnVeryHappy4.setOnClickListener {
            animationButtons(btnVeryHappy4, arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4))
            myFunction(arrayOf(btnVerySad4,btnSad4,btnNeutro4,btnHappy4,btnVeryHappy4))
        }

        //BOTONES PRECIO

        btnVerySad5.setOnClickListener {
            animationButtons(btnVerySad5, arrayOf(btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
        }

        btnSad5.setOnClickListener {
            animationButtons(btnSad5, arrayOf(btnVerySad5,btnNeutro5,btnHappy5,btnVeryHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
        }

        btnNeutro5.setOnClickListener {
            animationButtons(btnNeutro5, arrayOf(btnVerySad5,btnSad5,btnHappy5,btnVeryHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
        }

        btnHappy5.setOnClickListener {
            animationButtons(btnHappy5, arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnVeryHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
        }

        btnVeryHappy5.setOnClickListener {
            animationButtons(btnVeryHappy5, arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5))
            myFunction(arrayOf(btnVerySad5,btnSad5,btnNeutro5,btnHappy5,btnVeryHappy5))
        }

    }

    private fun animationButtons(actual: Button, unselected: Array<Button>){
        actual.layoutParams = LinearLayout.LayoutParams(dp2px(70), dp2px(70))

        for(i in unselected){
            i.layoutParams = LinearLayout.LayoutParams(dp2px(50), dp2px(50))
        }
    }

    fun myFunction(misBotones: Array<Button>){
        for(i in misBotones){
            setMargins(i, 10, 0, 10, 0)
        }
    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }

    fun dp2px(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun px2dp(px: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val dp = px / (metrics.densityDpi / 160f)
        return dp.roundToInt().toFloat()
    }

}