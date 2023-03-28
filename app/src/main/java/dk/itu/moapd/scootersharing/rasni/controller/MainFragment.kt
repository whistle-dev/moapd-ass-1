package dk.itu.moapd.scootersharing.rasni.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import dk.itu.moapd.scootersharing.rasni.*
import dk.itu.moapd.scootersharing.rasni.databinding.FragmentMainBinding
import dk.itu.moapd.scootersharing.rasni.model.Scooter

/**

* This class represents the main fragment of the Scooter Sharing app. It displays a list of rides
* and provides buttons to start a new ride, update an existing ride, and toggle the visibility of the
* rides list. The rides are stored in an SQLite database and are retrieved and displayed using a ListView
* and a custom adapter.
* @property TAG A private constant that represents the name of the class.
* @property ridesDB A lateinit variable that represents the SQLite database used to store the rides.
* @property scooter A private variable that represents a Scooter object.
* @property mainBinding A private variable that represents the FragmentMainBinding object associated with this fragment.
* @property listView A private variable that represents the ListView used to display the list of rides.
 */

class MainFragment : Fragment() {

    // A set of private constants used in this class .
    companion object {
        private val TAG = MainFragment::class.qualifiedName
        private lateinit var auth: FirebaseAuth

        // Singleton to share an object between the app activities .
        lateinit var ridesDB: RidesDB
    }

    // GUI variables .
    private val scooter: Scooter = Scooter("", "")

    // The binding object instance that is associated with this activity.
    private lateinit var mainBinding: FragmentMainBinding

    // RecyclerView for rides
    private lateinit var recyclerView: RecyclerView


    /**
     * This method is called when the fragment is created. It initializes the rides database.
     *
     * @param savedInstanceState A Bundle object that contains previously saved data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        super.onCreate(savedInstanceState)

        // Singleton to share an object between the app activities .
        ridesDB = RidesDB.get(requireContext())

    }


    /**
     * This method is called when the fragment is created. It inflates the layout, creates a ListView
     * adapter, and returns the root view.
     *
     * @param inflater A LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container A ViewGroup object that is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle object that contains previously saved data.
     * @return The root View of the inflated layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)

        // Get a reference to the RecyclerView in the layout with binding
        recyclerView = mainBinding.recyclerView

        // Create an adapter for the RecyclerView
        val adapter = CustomArrayAdapter(ridesDB.getRidesList())
        recyclerView.adapter = adapter

        // Set the layout manager for the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        return mainBinding.root
    }


    /**
     * This method is called after the view has been created. It sets up the click listeners for the
     * start ride, update ride, and list rides buttons. It also sets up the visibility of the rides list.
     *
     * @param view The View returned by onCreateView().
     * @param savedInstanceState A Bundle object that contains previously saved data.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainBinding.apply {
            // Start ride click event
            startRide.setOnClickListener {
                val fragment = StartRideFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            updateRide.setOnClickListener {
                val fragment = UpdateRideFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            listRides.setOnClickListener {
                if (recyclerView.visibility == View.GONE) {
                    recyclerView.visibility = View.VISIBLE
                    listRides.text = "HIDE RIDES"
                    listRides.iconSize = 80
                    listRides.textSize = 14F
                    listRides.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.baseline_arrow_drop_up_24
                    )
                } else {
                    recyclerView.visibility = View.GONE
                    listRides.text = "SHOW RIDES"
                    listRides.iconSize = 80
                    listRides.textSize = 14F
                    listRides.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.baseline_arrow_drop_down_24
                    )
                }
            }
            logout.setOnClickListener {
                auth = FirebaseAuth.getInstance()
                auth.signOut()
                val fragment = WelcomeFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
                Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()

            }
        }
    }

}