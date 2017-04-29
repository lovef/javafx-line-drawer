package se.lovef.util

/**
 * Date: 2017-03-28
 * @author Love
 */
class Vector2F(val x: Float, val y: Float) {
    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())

    override fun equals(other: Any?) = this === other ||
            other is Vector2F && x == other.x && y == other.y

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    operator fun plus(other: Vector2F) = Vector2F(x + other.x, y + other.y)
    operator fun plus(scalar: Float) = Vector2F(x + scalar, y + scalar)
    operator fun plus(scalar: Number) = Vector2F(x + scalar.toFloat(), y + scalar.toFloat())

    operator fun minus(other: Vector2F) = Vector2F(x - other.x, y - other.y)
    operator fun minus(scalar: Float) = Vector2F(x - scalar, y - scalar)
    operator fun minus(scalar: Number) = Vector2F(x - scalar.toFloat(), y - scalar.toFloat())

    operator fun unaryMinus() = Vector2F(-x, -y)

    operator fun times(scalar: Float) = Vector2F(x * scalar, y * scalar)
    operator fun times(scalar: Number) = Vector2F(x * scalar.toFloat(), y * scalar.toFloat())

    operator fun div(scalar: Float) = Vector2F(x / scalar, y / scalar)
    operator fun div(scalar: Number) = Vector2F(x / scalar.toFloat(), y / scalar.toFloat())

    infix fun dotProduct(other: Vector2F) = x * other.x + y * other.y
    infix fun crossProduct(other: Vector2F) = x * other.y - y * other.x

    val lengthSquared by lazy { x * x + y * y }
    val length by lazy { Math.sqrt(lengthSquared.toDouble()) }
    val unit by lazy { this / length }

    val angle by lazy { angleFrom(X) }

    fun angleFrom(other: Vector2F) = if (crossProduct(other) <= 0)
        Math.acos((unit dotProduct other.unit).toDouble())
    else
        2 * Math.PI - Math.acos((unit dotProduct other.unit).toDouble())

    val left by lazy { Vector2F(-y, x) }

    override fun toString() = "(${x.format()},${y.format()})"

    companion object {
        @JvmStatic val ZERO = Vector2F(0, 0)
        @JvmStatic val X = Vector2F(1, 0)
        @JvmStatic val Y = Vector2F(0, 1)
    }
}

private fun Float.format() = toInt().let { if (it.toFloat() == this) it.toString() else this.toString() }
