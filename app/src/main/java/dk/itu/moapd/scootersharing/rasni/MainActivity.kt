package dk.itu.moapd.scootersharing.rasni

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * The main activity used on startup of app
 *
 * This class uses the starts with the fragment MainFragment
 *
 */
class MainActivity : AppCompatActivity() {

    /**
     * override of onCreate
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