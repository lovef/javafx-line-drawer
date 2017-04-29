package se.lovef.util

import org.junit.Test
import se.lovef.assert.isEqualTo

/**
 * Date: 2017-04-12
 * @author Love
 */
class ComparableExtensionKtTest {

    @Test fun `at least`() {
        1.atLeast(2) isEqualTo 2
        2.atLeast(1) isEqualTo 2
        null.atLeast(2) isEqualTo 2
    }

    @Test fun `at least or null`() {
        1.atLeastOrNull(2) isEqualTo 2
        2.atLeastOrNull(1) isEqualTo 2
        null.atLeastOrNull(2) isEqualTo 2
        1.atLeastOrNull(null) isEqualTo 1
        null.atLeastOrNull(null) isEqualTo null
    }

    @Test fun `at most`() {
        1.atMost(2) isEqualTo 1
        2.atMost(1) isEqualTo 1
        null.atMost(1) isEqualTo 1
    }

    @Test fun `at most or null`() {
        1.atMostOrNull(2) isEqualTo 1
        2.atMostOrNull(1) isEqualTo 1
        null.atMostOrNull(1) isEqualTo 1
        1.atMostOrNull(null) isEqualTo 1
        null.atMostOrNull(null) isEqualTo null
    }
}
