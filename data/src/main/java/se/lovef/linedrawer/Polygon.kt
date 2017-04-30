package se.lovef.linedrawer

import se.lovef.util.Vector2d

/**
 * Date: 2016-11-27
 * @author Love
 */
class Polygon(val center: Vector2d,
              val radius: Double,
              val pointsCount: Int,
              startAngle: Double = Math.PI / 2) {

    val levelCount: Int
        get() = pointsCount / 2

    val points: List<Vector2d> = (0..pointsCount - 1).map {
        val angle = (it * 2 * Math.PI) / pointsCount + startAngle
        Vector2d(radius * Math.cos(angle), radius * Math.sin(angle)) + center
    }

    fun getLevelIterator(start: Int = 0, end: Int = levelCount -1) = LevelIterator(start, end)

    inner class LevelIterator(start: Int, val end: Int) : Iterator<Level> {

        init {
            if(start > end || start < 0 || end >= levelCount)
                throw IndexOutOfBoundsException()
        }

        private var current = start

        override fun hasNext() = current <= end

        override fun next() = getLevel(current).apply { current++ }

    }

    fun getLevel(/** Level index. Inner level has index 0 */ index: Int) = Level(index)

    inner class Level(/** Level index. Inner level has index 0 */ index: Int) : Iterator<Line> {

        val skips = levelCount - index

        init {
            if(index < 0 || index >= levelCount) throw IndexOutOfBoundsException()
        }

        private var current = 0

        override fun hasNext() = current < pointsCount

        override fun next(): Line = Line(current, current + skips).apply { current++ }
    }

    inner class Line(startIndex: Int, endIndex: Int) {
        val startIndex = startIndex % pointsCount
        val endIndex = endIndex % pointsCount

        val start: Vector2d
            get() = points[this.startIndex]
        val end: Vector2d
            get() = points[this.endIndex]
    }
}
