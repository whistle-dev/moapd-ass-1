package dk.itu.moapd.scootersharing.rasni

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import dk.itu.moapd.scootersharing.rasni.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    // A set of private constants used in this class .
    companion object {
        private val TAG = MainFragment::class.qualifiedName
        lateinit var ridesDB: RidesDB
    }

    // GUI variables .
    private val scooter: Scooter = Scooter("", "")

    // The binding object instance that is associated with this activity.
    private lateinit var mainBinding: FragmentMainBinding

    // Listview for rides
    private lateinit var listView: android.widget.ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        super.onCreate(savedInstanceState)

        // Singleton to share an object between the app activities .
        ridesDB = RidesDB.get(requireContext())

        // Initialize the binding object instance associated with this activity.
        mainBinding = FragmentMainBinding.inflate(layoutInflater)

        // Get a reference to the ListView in the layout with binding
        listView = mainBinding.listView

        // Create an adapter for the ListView
        listView.adapter = CustomArrayAdapter( requireContext(), R.layout.list_ride_item, ridesDB.getRidesList())

        with(mainBinding) {
            // Start ride click event
            startRide.setOnClickListener {
                val intent = Intent(context, StartRideActivity::class.java.apply {
                })
                startActivity(intent)
            }
            updateRide.setOnClickListener {
                val intent = Intent(context, UpdateRideActivity::class.java)
                startActivity(intent)
            }
            listRides.setOnClickListener {
                mainBinding.listRides.setOnClickListener {

                    if (listView.visibility == View.GONE){
                        listView.visibility = View.VISIBLE
                    }else{
                        listView.visibility = View.GONE
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}