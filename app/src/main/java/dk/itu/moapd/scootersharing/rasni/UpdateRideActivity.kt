package dk.itu.moapd.scootersharing.rasni

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dk.itu.moapd.scootersharing.rasni.databinding.ActivityUpdateRideBinding


class UpdateRideActivity : AppCompatActivity() {
    // A set of private constants used in this class .
    companion object {
        private val TAG = UpdateRideActivity::class.qualifiedName
        lateinit var ridesDB: RidesDB
    }

    // GUI variables .
    private val scooter: Scooter = Scooter("", "")

    // The binding object instance that is associated with this activity.
    private lateinit var mainBinding: ActivityUpdateRideBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        // Singleton to share an object between the app activities .
        ridesDB = RidesDB.get(this)


        // Initialize the binding object instance associated with this activity.
        mainBinding = ActivityUpdateRideBinding.inflate(layoutInflater)
        mainBinding.ScooterName.setText(intent.getStringExtra("scooter"))
        val view = mainBinding.root
        setContentView(view)

        // Update the object attributes
        val currentScooter = ridesDB.getCurrentScooter()
        mainBinding.ScooterName.setText(currentScooter.name)
        mainBinding.ScooterLocation.setText(currentScooter.location)




        with(mainBinding) {
            Button.setOnClickListener {
                if (ScooterName.text!!.isNotEmpty() && ScooterLocation.text!!.isNotEmpty()) {

                    // Update the object attributes
                    val location = ScooterLocation.text.toString().trim()

                    scooter.name = ScooterName.text.toString().trim()
                    scooter.location = location
                    scooter.timestamp = System.currentTimeMillis()

                    ridesDB.updateCurrentScooter(scooter.location, scooter.timestamp)

                    showMessage()
                }
            }
        }

    }

    // Print a message in the ‘Logcat ‘ system
    private fun showMessage() {
        val snack = Snackbar.make(mainBinding.root, scooter.toString(), Snackbar.LENGTH_LONG)
        snack.duration = 5000
        snack.setAction("OK") {}
        snack.setBackgroundTint(getColor(R.color.purple_500))
        snack.show()
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