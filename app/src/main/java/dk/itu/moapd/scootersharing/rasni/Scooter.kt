package dk.itu.moapd.scootersharing.rasni

    data class Scooter(var name: String, var location: String, var timestamp: Long = System.currentTimeMillis()) {
        override fun toString(): String {
            return "[Scooter] $name is placed at $location ."
        }
        fun getTimestamp(): String {
            return timestamp.toString()
        }

    }
