package dk.itu.moapd.scootersharing.rasni

import android.icu.text.SimpleDateFormat
import java.util.*

data class Scooter(

    var name: String,
    var location: String,
    var timestamp: Long = System.currentTimeMillis()
) {
    override fun toString(): String {
        return "[Scooter] $name is placed at $location ."
    }
    fun getTimestamp(): String {
        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(timestamp))
        return timestamp.toString()
    }

}
