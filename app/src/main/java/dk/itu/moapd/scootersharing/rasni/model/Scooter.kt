package dk.itu.moapd.scootersharing.rasni.model

import android.icu.text.SimpleDateFormat
import java.util.*

data class Scooter(
    var name: String = "",
    var location: String = "",
    var timestamp: Long = System.currentTimeMillis(),
    var id: String = UUID.randomUUID().toString().substring(0, 5)
) {
    constructor() : this("", "", System.currentTimeMillis(), "")
    override fun toString(): String {
        return "[Scooter] $name is placed at $location ."
    }
    fun formattedTimestamp(): String {
        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(timestamp))
        return timestamp.toString()
    }
}

