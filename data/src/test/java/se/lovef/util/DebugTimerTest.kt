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
            var start = currentMillis
            Thread.sleep(5)
            var period = currentMillis - start
            period isLessOrEqualTo time isLessOrEqualTo period + 1

            Thread.sleep(5)
            period = currentMillis - start
            period isLessOrEqualTo time isLessOrEqualTo period + 1

            start()
            start = currentMillis
            Thread.sleep(5)
            period = currentMillis - start
            period isLessOrEqualTo time isLessOrEqualTo period + 1

            Thread.sleep(5)
            period = currentMillis - start
            period isLessOrEqualTo time isLessOrEqualTo period + 1
        }
    }

    @Test fun `named intervals`() {
        DebugTimer().apply {
            start("first")
            val firstStart = currentMillis
            Thread.sleep(3)
            switchTo("second")
            val secondStart = currentMillis
            Thread.sleep(5)
            stop()
            val stop = currentMillis
            Thread.sleep(5)
            secondStart - firstStart isCloseTo time("first") tolerance 1
            stop - secondStart isCloseTo time("second") tolerance 1
            stop - firstStart isCloseTo time tolerance 1
        }
    }

    @Test fun `mean time`() {
        DebugTimer().apply {
            meanTime isEqualTo 0f
            var control = 0.0
            (0 until 5).forEach {
                start()
                val start = currentMillis
                Thread.sleep(3)
                control = (currentMillis - start + control) / 2
                stop()
                Thread.sleep(3)
            }
            meanTime isCloseTo control tolerance 0.2
        }
    }


    @Test fun `interval mean time`() {
        DebugTimer().apply {
            meanTime("first") isEqualTo 0f
            meanTime("second") isEqualTo 0f
            var firstControl = 0.0
            var secondControl = 0.0
            (0 until 5).forEach {
                start("first")
                val firstStart = currentMillis
                Thread.sleep(1)
                firstControl = (currentMillis - firstStart + firstControl) / 2
                switchTo("second")
                val secondStart = currentMillis
                Thread.sleep(2)
                secondControl = (currentMillis - secondStart + secondControl) / 2
                stop()
                Thread.sleep(3)
            }
            meanTime("first") isCloseTo firstControl tolerance 0.2
            meanTime("second") isCloseTo secondControl tolerance 0.2
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

    val currentMillis get() = System.currentTimeMillis()
}
