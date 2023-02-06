package dk.itu.moapd.scootersharing.rasni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.view.get
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            logScooter(it)
        }

    }


    fun logScooter(view: View) {
        val scooterName = findViewById<TextInputLayout>(R.id.ScooterName)
        val scooterLoc = findViewById<TextInputLayout>(R.id.ScooterLocation)
        val scooterNameText = scooterName.editText?.text.toString()
        val scooterLocText = scooterLoc.editText?.text.toString()
        Log.d("Scooter", "Scooter name: $scooterNameText")
        Log.d("Scooter", "Scooter location: $scooterLocText")
    }
}
