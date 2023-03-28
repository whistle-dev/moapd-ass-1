package dk.itu.moapd.scootersharing.rasni.controller

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.scootersharing.rasni.R
import dk.itu.moapd.scootersharing.rasni.RidesDB
import dk.itu.moapd.scootersharing.rasni.controller.MainFragment.Companion.ridesDB
import dk.itu.moapd.scootersharing.rasni.model.Scooter

class CustomArrayAdapter(private val data: List<Scooter>) :
    RecyclerView.Adapter<CustomArrayAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val location: TextView = itemView.findViewById(R.id.location)
        val timestamp: TextView = itemView.findViewById(R.id.timestamp)
        val delete: TextView = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_ride_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scooter = data[position]

        // Singleton to share an object between the app activities.
        ridesDB = RidesDB.get(holder.itemView.context)

        holder.name.text = scooter.name
        holder.location.text = scooter.location
        holder.timestamp.text = scooter.formattedTimestamp()
        holder.delete.setOnClickListener {
            //open a dialog to confirm delete ride
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Delete ride")
                .setMessage("Are you sure you want to delete${scooter.name}?")
                .setPositiveButton("Yes") { dialog, which ->
                    //delete ride
                    ridesDB.deleteScooter(scooter.name)

                    //update recyclerview
                    notifyDataSetChanged()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    //delete button


    override fun getItemCount(): Int {
        return data.size
    }
}
