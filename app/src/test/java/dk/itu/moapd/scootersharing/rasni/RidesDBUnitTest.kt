package dk.itu.moapd.scootersharing.rasni

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(AndroidJUnit4::class)
class RidesDBUnitTest {

    @Test
    fun testRidesDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val ridesDB = RidesDB(context)
        val rides = ridesDB.getRidesList()
        assertEquals(3, rides.size)
        ridesDB.addScooter("CPH004", "Fields", 0)
        assertEquals(4, rides.size)
        ridesDB.deleteScooter("CPH004")
        assertEquals(3, rides.size)
        ridesDB.updateCurrentScooter("Fields", 0)
        assertEquals("CPH003", ridesDB.getCurrentScooter().name)
        assertEquals("Fields", ridesDB.getCurrentScooter().location)
        assertEquals(0, ridesDB.getCurrentScooter().timestamp)
    }
}
