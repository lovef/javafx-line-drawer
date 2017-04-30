package se.lovef.util

import javafx.geometry.Point2D
import org.junit.Test
import se.lovef.assert.isCloseTo
import se.lovef.assert.isEqualTo
import se.lovef.assert.isNotEqualTo
import java.util.*

/**
 * Date: 2017-03-28
 * @author Love
 */
class Vector2dTest {

    @Test fun equals() {
        v.ZERO isEqualTo v.ZERO
        v(0, 0) isEqualTo v.ZERO

        v(5, 5) isNotEqualTo v.ZERO

        v(5, 5) isEqualTo v(5, 5)
        v(5, -5) isEqualTo v(5, -5)
    }

    @Test fun hash() {
        val set = HashSet<Int>()
        (0..9).forEach { x -> (0..9).forEach { y -> set.add(v(x, y).hashCode()) } }
        set.size isEqualTo 10 * 10 - 1 // Collision between (0, 0) and (2, 2)
    }

    @Test fun `add vector`() {
        v.ZERO + v.ZERO isEqualTo v.ZERO

        val a = v(1.2, 3.4)
        val b = v(5.6, 7.8)
        val sum = v(a.x + b.x, a.y + b.y)
        a + b isEqualTo sum
    }

    @Test fun `add scalar`() {
        v.ZERO + 0 isEqualTo v.ZERO
        v.ZERO + 0L isEqualTo v.ZERO
        v.ZERO + 0F isEqualTo v.ZERO
        v.ZERO + 0.0 isEqualTo v.ZERO
        v.ZERO + 1 isEqualTo v(1, 1)
        v(1, 2) + 1 isEqualTo v(2, 3)
    }

    @Test fun `minus vector`() {
        v.ZERO - v.ZERO isEqualTo v.ZERO

        val a = v(1.2, 3.4)
        val b = v(5.6, 7.8)
        val sum = v(a.x - b.x, a.y - b.y)
        a - b isEqualTo sum
    }

    @Test fun `minus scalar`() {
        v.ZERO - 0 isEqualTo v.ZERO
        v.ZERO - 0L isEqualTo v.ZERO
        v.ZERO - 0F isEqualTo v.ZERO
        v.ZERO - 0.0 isEqualTo v.ZERO
        v.ZERO - 1 isEqualTo v(-1, -1)
        v(1, 2) - 1 isEqualTo v(0, 1)
    }

    @Test fun `unary minus scalar`() {
        -v.ZERO isEqualTo v.ZERO
        -v(1, 2) isEqualTo v(-1, -2)
    }

    @Test fun `times scalar`() {
        v.X * 1 isEqualTo v.X
        v.Y * 1 isEqualTo v.Y
        v.X * 1L isEqualTo v.X
        v.X * 1F isEqualTo v.X
        v.X * 1.0 isEqualTo v.X
        v(1, 1) * -1 isEqualTo v(-1, -1)
        v(1, 1) * 2 isEqualTo v(2, 2)
        v(1, 2) * 2 isEqualTo v(2, 4)
    }

    @Test fun `divide scalar`() {
        v.X / 1 isEqualTo v.X
        v.Y / 1 isEqualTo v.Y
        v.X / 1L isEqualTo v.X
        v.X / 1F isEqualTo v.X
        v.X / 1.0 isEqualTo v.X
        v(1, 1) / -1 isEqualTo v(-1, -1)
        v(1, 1) / 2 isEqualTo v(0.5, 0.5)
        v(1, 2) / 2 isEqualTo v(0.5, 1)
    }

    @Test fun `dot product`() {
        v(1, 1) dotProduct v(1, 1) isEqualTo 2.0
        v(1, 2) dotProduct v(3, 4) isEqualTo 1.0 * 3.0 + 2.0 * 4.0
    }

    @Test fun `cross product`() {
        v(1, 2) crossProduct v(3, 4) isEqualTo
                Point2D(1.0, 2.0).crossProduct(3.0, 4.0).z
    }

    @Test fun length() {
        v.ZERO.lengthSquared isEqualTo 0.0
        v.ZERO.length isEqualTo 0.0
        v(1, 1).lengthSquared isEqualTo 2.0
        v(1, 1).length isEqualTo Math.sqrt(2.0)
    }

    @Test fun unit() {
        v(1, 1).unit.length isCloseTo 1.0 tolerance  1e-7
        v(1, 1).unit.angle isCloseTo Math.PI / 4 tolerance  1e-7
        v(1,2).unit.length isCloseTo 1.0 tolerance  1e-7
        v(1,2).unit.angle isCloseTo v(1, 2).angle tolerance  1e-7
    }

    @Test fun angle() {
        v( 1,  0).angle isCloseTo Math.PI * 0 / 4 tolerance 1e-7
        v( 1,  1).angle isCloseTo Math.PI * 1 / 4 tolerance 1e-7
        v( 0,  1).angle isCloseTo Math.PI * 2 / 4 tolerance 1e-7
        v(-1,  1).angle isCloseTo Math.PI * 3 / 4 tolerance 1e-7
        v(-1,  0).angle isCloseTo Math.PI * 4 / 4 tolerance 1e-7
        v(-1, -1).angle isCloseTo Math.PI * 5 / 4 tolerance 1e-7
        v( 0, -1).angle isCloseTo Math.PI * 6 / 4 tolerance 1e-7
        v( 1, -1).angle isCloseTo Math.PI * 7 / 4 tolerance 1e-7
    }

    @Test fun `rotate left`() {
        v.X.left isEqualTo v.Y
        v.Y.left isEqualTo -v.X
        (-v.X).left isEqualTo -v.Y
        (-v.Y).left isEqualTo v.X

        v(3, 4).let { it.left.angleFrom(it) } isEqualTo Math.PI / 2
        v(-4, 3).let { it.left.angleFrom(it) } isEqualTo Math.PI / 2
        v(-3, -4).let { it.left.angleFrom(it) } isEqualTo Math.PI / 2
        v(4, -3).let { it.left.angleFrom(it) } isEqualTo Math.PI / 2
    }

    @Test fun `to string`() {
        v.X.toString() isEqualTo "(1,0)"
        v(1.5, 0).toString() isEqualTo "(1.5,0)"
        v(1.5, 0.00001).toString() isEqualTo "(1.5,${0.00001})"
    }
}

object v {
    val X = Vector2d.X
    val Y = Vector2d.Y
    val ZERO = Vector2d.ZERO

    operator fun invoke(x: Number, y: Number) = Vector2d(x.toDouble(), y.toDouble())
}
