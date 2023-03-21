package dk.itu.moapd.scootersharing.rasni.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dk.itu.moapd.scootersharing.rasni.R
import dk.itu.moapd.scootersharing.rasni.controller.MainFragment
import dk.itu.moapd.scootersharing.rasni.controller.WelcomeFragment

/**
 * The main activity used on startup of app
 *
 * This class uses the starts with the fragment MainFragment
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.qualifiedName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        FirebaseAuth.getInstance().setLanguageCode("en")

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, WelcomeFragment())
            .commit()
    }
}