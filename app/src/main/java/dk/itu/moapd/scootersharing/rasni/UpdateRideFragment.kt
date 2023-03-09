package dk.itu.moapd.scootersharing.rasni

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dk.itu.moapd.scootersharing.rasni.databinding.FragmentUpdateRideBinding

class UpdateRideFragment : Fragment() {

    // A set of private constants used in this class .
    companion object {
        lateinit var ridesDB: RidesDB
    }

    // GUI variables .
    private val scooter: Scooter = Scooter("", "")

    // The binding object instance that is associated with this activity.
    private lateinit var mainBinding: FragmentUpdateRideBinding

    // Gesture detector to hide keyboard when the user taps outside of an EditText.
    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        super.onCreate(savedInstanceState)

        // Singleton to share an object between the app activities .
        ridesDB = RidesDB.get(requireContext())

        // Create a gesture detector and override its onTouchEvent method.
        gestureDetector = GestureDetectorCompat(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                // Return true to indicate that we're interested in this touch event.
                return true
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                // Hide the keyboard when the user taps outside of an EditText.
                val focusView = requireActivity().currentFocus
                if (focusView != null) {
                    val rect = Rect()
                    focusView.getGlobalVisibleRect(rect)
                    if (!rect.contains(e.rawX.toInt(), e.rawY.toInt())) {
                        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(focusView.windowToken, 0)
                        focusView.clearFocus()
                    }
                }
                return true
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainBinding = FragmentUpdateRideBinding.inflate(inflater, container, false)

        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the latest scooter from the database
        val latestScooter = ridesDB.getCurrentScooter()

        // Set the text of the first text field to the name of the latest scooter
        mainBinding.ScooterName.setText(latestScooter.name)

        mainBinding.apply {
            Button.setOnClickListener {
                if (ScooterName.text!!.isNotEmpty() && ScooterLocation.text!!.isNotEmpty()) {

                    // Update the object attributes
                    val location = ScooterLocation.text.toString().trim()

                    scooter.name = ScooterName.text.toString().trim()
                    scooter.location = location
                    scooter.timestamp = System.currentTimeMillis()

                    ridesDB.updateCurrentScooter(scooter.location, scooter.timestamp)

                    showMessage()

                    // Navigate back to the main fragment
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            // Set the gesture detector on the root view of the fragment's layout.
            root.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
        }
    }

    // Print a message in the ‘Logcat ‘ system
    private fun showMessage() {
        val snack = Snackbar.make(mainBinding.root, scooter.toString(), Snackbar.LENGTH_LONG)
        snack.duration = 5000
        snack.setAction("OK") {}
        snack.setBackgroundTint(resources.getColor(R.color.purple_200))
        snack.show()
    }

}