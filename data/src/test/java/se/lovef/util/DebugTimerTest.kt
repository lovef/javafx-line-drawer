package se.lovef.util

import org.junit.Test
import se.lovef.assert.isCloseTo
import se.lovef.assert.isEqualTo
import se.lovef.assert.isLessOrEqualTo

/**
 * Date: 2017-04-18
 * @author Love
 */
class DebugTimerTest {

    @Test fun `simple time measurement`() {
        DebugTimer().apply {
            Thread.sleep(5)
            5L isLessOrEqualTo time isLessOrEqualTo 6L
            Thread.sleep(5)
            10L isLessOrEqualTo time isLessOrEqualTo 12L
            start()
            Thread.sleep(5)
            5L isLessOrEqualTo time isLessOrEqualTo 6L
            Thread.sleep(5)
            10L isLessOrEqualTo time isLessOrEqualTo 12L
        }
    }

    @Test fun `named intervals`() {
        DebugTimer().apply {
            start("first")
            Thread.sleep(3)
            switchTo("second")
            Thread.sleep(5)
            stop()
            Thread.sleep(5)
            3L isLessOrEqualTo time("first") isLessOrEqualTo 4L
            5L isLessOrEqualTo time("second") isLessOrEqualTo 6L
            8L isLessOrEqualTo time isLessOrEqualTo 10L
        }
    }

    @Test fun `mean time`() {
        DebugTimer().apply {
            meanTime isEqualTo 0f
            (0..2).forEach {
                start()
                Thread.sleep(1)
                stop()
                Thread.sleep(2)
            }
            meanTime isCloseTo 1 tolerance 0.5
            start()
        }
    }


    @Test fun `interval mean time`() {
        DebugTimer().apply {
            meanTime("first") isEqualTo 0f
            meanTime("second") isEqualTo 0f
            (0..2).forEach {
                start("first")
                Thread.sleep(1)
                switchTo("second")
                Thread.sleep(2)
                stop()
                Thread.sleep(3)
            }
            meanTime("first") isCloseTo 1 tolerance 0.5
            meanTime("second") isCloseTo 2 tolerance 0.5
            meanTime isCloseTo 3 tolerance 0.5
        }
    }

    @Test fun `execute if`() {
        var count = 0
        DebugTimer().apply {
            executeIf(3) { count++ }
            count isEqualTo 0

            Thread.sleep(4)
            executeIf(3) { count++ }
            count isEqualTo 1
            executeIf(3) { count++ }
            count isEqualTo 1

            Thread.sleep(4)
            executeIf(3) { count++ }
            count isEqualTo 2
        }
    }
}
