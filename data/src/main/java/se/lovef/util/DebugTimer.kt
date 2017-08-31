package se.lovef.util

import java.util.*

private val currentTimeMillis get() = System.currentTimeMillis()

/**
 * Date: 2017-04-18
 * @author Love
 */
class DebugTimer {

    private class Interval(var start: Long = currentTimeMillis, var end: Long = -1L) {
        val time get() = (if (end < start) currentTimeMillis else end) - start
        var meanTime = 0f
            private set

        fun start(start: Long = currentTimeMillis) {
            this.start = start
            end = -1
        }

        fun stop(end: Long = currentTimeMillis) {
            this.end = end
            meanTime = (time + meanTime) / 2
        }
    }

    private val interval = Interval(currentTimeMillis)
    val start get() = interval.start
    val time get() = interval.time
    val meanTime get() = interval.meanTime

    private val intervals = LinkedHashMap<String, Interval>()
    private var currentInterval: String? = null

    fun start() {
        interval.start()
    }

    fun start(name: String) {
        start()
        startInterval(name, interval.start)
    }

    private fun startInterval(name: String, start: Long) {
        currentInterval = name
        intervals[name].let {
            if (it == null) {
                intervals[name] = Interval(start)
            } else {
                it.start(start)
            }
        }
    }

    fun stop() {
        interval.stop()
        currentInterval?.let { stop(it, interval.end) }
    }

    fun stop(name: String, end: Long = currentTimeMillis) {
        intervals[name]?.stop(end)
    }

    fun switchTo(name: String) {
        val end = currentTimeMillis
        currentInterval?.let { intervals[it]!!.stop(end) }
        startInterval(name, end)
    }

    fun time(name: String) = intervals[name]?.time ?: 0L

    fun meanTime(name: String) = intervals[name]?.meanTime ?: 0f

    private var lastExecution = start

    fun executeIf(period: Int, action: DebugTimer.() -> Unit) {
        val time = currentTimeMillis
        if (lastExecution + period < time) {
            action.invoke(this)
            lastExecution = time
        }
    }
}
