package se.lovef.util

import org.junit.Test
import se.lovef.assert.isEqualTo

/**
 * Date: 2017-04-14
 * @author Love
 */
class NumberExtensionKtTest {

    var sqrtCount = 0

    fun sqrt(d: Double): Double {
        sqrtCount++
        return Math.sqrt(d)
    }

    @Test fun squared() {
        2.toByte().squared isEqualTo 2.toByte() * 2.toByte()
        2.squared isEqualTo 2 * 2
        2L.squared isEqualTo 2L * 2L
        2f.squared isEqualTo 2f * 2f
        2.0.squared isEqualTo 2.0 * 2.0
        var i = 1
        (++i).squared isEqualTo i * i
        sqrtCount = 0
        sqrt(4.0).squared isEqualTo 4.0
        sqrtCount isEqualTo 1
    }
}
