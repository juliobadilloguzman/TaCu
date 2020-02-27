package mx.tec.tacu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar

class MyProgressBar : AppCompatActivity() {

    companion object {
        lateinit var loading: ProgressBar
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_progress_bar)

        loading = findViewById(R.id.progressBar)

    }
}
