package dk.itu.moapd.scootersharing.rasni

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dk.itu.moapd.scootersharing.rasni.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    // A set of private constants used in this class .
    companion object {
        private val TAG = MainActivity::class.qualifiedName
    }

    // GUI variables .
    private val scooter: Scooter = Scooter("", "")

    // The binding object instance that is associated with this activity.
    private lateinit var mainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        // Initialize the binding object instance associated with this activity.
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

    }

}