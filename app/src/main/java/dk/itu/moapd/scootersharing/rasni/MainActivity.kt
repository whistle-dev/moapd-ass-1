package dk.itu.moapd.scootersharing.rasni

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    // A set of private constants used in this class .
    companion object {
        private val TAG = MainActivity::class.qualifiedName
    }

    // GUI variables .
    private lateinit var scooterName: EditText
    private lateinit var scooterLocation: EditText
    private lateinit var startRideButton: Button
    private val scooter: Scooter = Scooter("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Edit texts
        scooterName = findViewById(R.id.ScooterName)
        scooterLocation = findViewById(R.id.ScooterLocation)


        // Buttons
        startRideButton = findViewById(R.id.button)
        startRideButton.setOnClickListener {
            if (scooterName.text.isNotEmpty() && scooterLocation.text.isNotEmpty()) {

                // Update the object attributes
                val name = scooterName.text.toString().trim()
                val location = scooterLocation.text.toString().trim()
                scooter.setName(name)
                scooter.setLocation(location)

                // Reset the text fields and update the UI.
                scooterName.text.clear()
                scooterLocation.text.clear()
                showMessage()
            }
        }
    }

    // Print a message in the ‘Logcat ‘ system
    private fun showMessage() {
        Log.d(TAG, scooter.toString())
    }

    // Gesture Detector to remove the focus from the text fields
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is TextInputEditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}