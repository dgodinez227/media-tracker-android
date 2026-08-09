package edu.metrostate.ics342.mediatracker

import edu.metrostate.ics342.mediatracker.ui.library.canAddPriority
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

// tests 5 limit max limit for new priorities bonus feature
// Build is Successful
class PriorityLimitTest {

    @Test
    fun canAddPriority_whenFourItems_returnsTrue() {
        assertTrue(canAddPriority(4))
    }

    @Test
    fun canAddPriority_whenFiveItems_returnsFalse() {
        assertFalse(canAddPriority(5))
    }
}
