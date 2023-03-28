package dk.itu.moapd.scootersharing.rasni

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dk.itu.moapd.scootersharing.rasni.model.Scooter
import java.util.*

class RidesDB(context: Context) {
    private val rides = ArrayList<Scooter>()

    private val ridesRef = FirebaseDatabase.getInstance("https://scootersharing-c2eb4-default-rtdb.europe-west1.firebasedatabase.app").getReference("rides")

    companion object : RidesDBHolder<RidesDB, Context>(::RidesDB)

    init {
        ridesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val updatedRides = ArrayList<Scooter>()
                for (child in snapshot.children) {
                    val scooter = child.getValue(Scooter::class.java) ?: continue
                    scooter.id = child.key ?: continue
                    updatedRides.add(scooter)
                }
                rides.clear()
                rides.addAll(updatedRides)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancelled event
                Log.e("RidesDB", "Failed to retrieve ride IDs from database", error.toException())
            }
        })
    }


    fun getRidesList(): List<Scooter> {
        return rides
    }

    fun addScooter(name: String, location: String, timestamp: Long) {
        val key = ridesRef.push().key ?: return
        ridesRef.child(key).setValue(Scooter(name, location, timestamp))
    }

    fun deleteScooter(name: String) {
        val scooter = rides.find { it.name == name } ?: return
        ridesRef.child(scooter.id).removeValue()
    }

    fun updateCurrentScooter(location: String, timestamp: Long) {
        val scooter = getCurrentScooter()
        ridesRef.child(scooter.id).child("location").setValue(location)
        ridesRef.child(scooter.id).child("timestamp").setValue(timestamp)
    }


    fun getCurrentScooter(): Scooter {
        return rides[rides.size - 1]
    }

    fun getCurrentScooterInfo(): String {
        return getCurrentScooter().toString()
    }

    /**
     * Generate a random timestamp in the last 365 days .
     *
     * @return A random timestamp in the last year .
     */
    private fun randomDate(): Long {
        val random = Random()
        val now = System.currentTimeMillis()
        val year = random.nextDouble() * 1000 * 60 * 60 * 24 * 365
        return now - year.toLong()
    }
}

open class RidesDBHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator

    @Volatile
    private var instance: T? = null
    fun get(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null)
            return checkInstance
        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null)
                checkInstanceAgain
            else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}