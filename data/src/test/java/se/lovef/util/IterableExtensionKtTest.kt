package se.lovef.util

import org.junit.Test
import se.lovef.assert.isEqualTo

/**
 * Date: 2017-04-13
 * @author Love
 */
class IterableExtensionKtTest {

    @Test fun `reduce adjacent elements`() {
        emptyList<Int>().reduceAdjacentElements { a, _ -> a } isEqualTo emptyList()
        listOf(1).reduceAdjacentElements { a, _ -> a } isEqualTo emptyList()
        listOf(1, 2).reduceAdjacentElements { a, _ -> a } isEqualTo listOf(1)
        listOf(1, 2, 3).reduceAdjacentElements { a, _ -> a } isEqualTo listOf(1, 2)
        listOf(1, 2, 3).reduceAdjacentElements { _, b -> b } isEqualTo listOf(2, 3)
    }
}
