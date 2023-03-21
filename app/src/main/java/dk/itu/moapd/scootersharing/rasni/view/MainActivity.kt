package dk.itu.moapd.scootersharing.rasni.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dk.itu.moapd.scootersharing.rasni.R
import dk.itu.moapd.scootersharing.rasni.controller.MainFragment

/**
 * The main activity used on startup of app
 *
 * This class uses the starts with the fragment MainFragment
 */
class MainActivity : AppCompatActivity() {

    /**
     * Override of onCreate
     *
     * Sets up the contentView and sets up the MainFragment with a transaction
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .commit()
        }

}