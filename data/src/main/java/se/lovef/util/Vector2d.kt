package se.lovef.util

/**
 * Date: 2017-03-28
 * @author Love
 */
class Vector2d(val x: Double, val y: Double) {

    override fun equals(other: Any?) = this === other ||
            other is Vector2d && x == other.x && y == other.y

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    operator fun plus(other: Vector2d) = Vector2d(x + other.x, y + other.y)
    operator fun plus(scalar: Double) = Vector2d(x + scalar, y + scalar)
    operator fun plus(scalar: Number) = Vector2d(x + scalar.toDouble(), y + scalar.toDouble())

    operator fun minus(other: Vector2d) = Vector2d(x - other.x, y - other.y)
    operator fun minus(scalar: Double) = Vector2d(x - scalar, y - scalar)
    operator fun minus(scalar: Number) = Vector2d(x - scalar.toDouble(), y - scalar.toDouble())

    operator fun unaryMinus() = Vector2d(-x, -y)

    operator fun times(scalar: Double) = Vector2d(x * scalar, y * scalar)
    operator fun times(scalar: Number) = Vector2d(x * scalar.toDouble(), y * scalar.toDouble())

    operator fun div(scalar: Double) = Vector2d(x / scalar, y / scalar)
    operator fun div(scalar: Number) = Vector2d(x / scalar.toDouble(), y / scalar.toDouble())

    infix fun dotProduct(other: Vector2d) = x * other.x + y * other.y
    infix fun crossProduct(other: Vector2d) = x * other.y - y * other.x

    val lengthSquared by lazy { x * x + y * y }
    val length by lazy { Math.sqrt(lengthSquared) }
    val unit by lazy { this / length }

    val angle by lazy { angleFrom(X) }

    fun angleFrom(other: Vector2d) = if (crossProduct(other) <= 0)
        Math.acos((unit dotProduct other.unit))
    else
        2 * Math.PI - Math.acos((unit dotProduct other.unit))

    val left by lazy { Vector2d(-y, x) }

    override fun toString() = "(${x.format()},${y.format()})"

    companion object {
        @JvmStatic val ZERO = Vector2d(0.0, 0.0)
        @JvmStatic val X = Vector2d(1.0, 0.0)
        @JvmStatic val Y = Vector2d(0.0, 1.0)
    }
}

private fun Double.format() = toInt().let { if (it.toDouble() == this) it.toString() else this.toString() }
