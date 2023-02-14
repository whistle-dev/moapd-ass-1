package dk.itu.moapd.scootersharing.rasni

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dk.itu.moapd.scootersharing.rasni.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    // A set of private constants used in this class .
    companion object {
        private val TAG = MainActivity::class.qualifiedName
        lateinit var ridesDB: RidesDB
    }

    // GUI variables .
    private val scooter: Scooter = Scooter("", "")

    // The binding object instance that is associated with this activity.
    private lateinit var mainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        // Singleton to share an object between the app activities .
        ridesDB = RidesDB.get(this)

        // Initialize the binding object instance associated with this activity.
        mainBinding = ActivityMainBinding.inflate(layoutInflater)



        with(mainBinding) {
            // Start ride click event
            startRide.setOnClickListener {
                val intent = Intent(this@MainActivity, StartRideActivity::class.java.apply {
                    intent.putExtra("scooter", "scooter")
                })
                startActivity(intent)
            }
            updateRide.setOnClickListener {
                val intent = Intent(this@MainActivity, UpdateRideActivity::class.java)
                startActivity(intent)
            }
            listRides.setOnClickListener {

                //TODO: list rides in a listview when pressed
            }
        }

        val view = mainBinding.root
        setContentView(view)

    }

}