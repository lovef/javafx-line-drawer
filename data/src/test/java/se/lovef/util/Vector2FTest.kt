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
class Vector2FTest {

    @Test fun equals() {
        Vector2F.ZERO isEqualTo Vector2F.ZERO
        Vector2F(0, 0) isEqualTo Vector2F.ZERO
        Vector2F(0, 0F) isEqualTo Vector2F.ZERO
        Vector2F(0F, 0) isEqualTo Vector2F.ZERO
        Vector2F(0F, 0F) isEqualTo Vector2F.ZERO

        Vector2F(5, 5) isNotEqualTo Vector2F.ZERO

        Vector2F(5, 5) isEqualTo Vector2F(5, 5)
        Vector2F(5, -5) isEqualTo Vector2F(5, -5)
    }

    @Test fun hash() {
        val set = HashSet<Int>()
        (0..9).forEach { x -> (0..9).forEach { y -> set.add(Vector2F(x, y).hashCode()) } }
        set.size isEqualTo 10 * 10 - 1 // Collision between (0, 0) and (2, 2)
    }

    @Test fun `add vector`() {
        Vector2F.ZERO + Vector2F.ZERO isEqualTo Vector2F.ZERO

        val a = Vector2F(1.2F, 3.4F)
        val b = Vector2F(5.6F, 7.8F)
        val sum = Vector2F(a.x + b.x, a.y + b.y)
        a + b isEqualTo sum
    }

    @Test fun `add scalar`() {
        Vector2F.ZERO + 0 isEqualTo Vector2F.ZERO
        Vector2F.ZERO + 0L isEqualTo Vector2F.ZERO
        Vector2F.ZERO + 0F isEqualTo Vector2F.ZERO
        Vector2F.ZERO + 0.0 isEqualTo Vector2F.ZERO
        Vector2F.ZERO + 1 isEqualTo Vector2F(1, 1)
        Vector2F(1, 2) + 1 isEqualTo Vector2F(2, 3)
    }

    @Test fun `minus vector`() {
        Vector2F.ZERO - Vector2F.ZERO isEqualTo Vector2F.ZERO

        val a = Vector2F(1.2F, 3.4F)
        val b = Vector2F(5.6F, 7.8F)
        val sum = Vector2F(a.x - b.x, a.y - b.y)
        a - b isEqualTo sum
    }

    @Test fun `minus scalar`() {
        Vector2F.ZERO - 0 isEqualTo Vector2F.ZERO
        Vector2F.ZERO - 0L isEqualTo Vector2F.ZERO
        Vector2F.ZERO - 0F isEqualTo Vector2F.ZERO
        Vector2F.ZERO - 0.0 isEqualTo Vector2F.ZERO
        Vector2F.ZERO - 1 isEqualTo Vector2F(-1, -1)
        Vector2F(1, 2) - 1 isEqualTo Vector2F(0, 1)
    }

    @Test fun `unary minus scalar`() {
        -Vector2F.ZERO isEqualTo Vector2F.ZERO
        -Vector2F(1, 2) isEqualTo Vector2F(-1, -2)
    }

    @Test fun `times scalar`() {
        Vector2F.X * 1 isEqualTo Vector2F.X
        Vector2F.Y * 1 isEqualTo Vector2F.Y
        Vector2F.X * 1L isEqualTo Vector2F.X
        Vector2F.X * 1F isEqualTo Vector2F.X
        Vector2F.X * 1.0 isEqualTo Vector2F.X
        Vector2F(1, 1) * -1 isEqualTo Vector2F(-1, -1)
        Vector2F(1, 1) * 2 isEqualTo Vector2F(2, 2)
        Vector2F(1, 2) * 2 isEqualTo Vector2F(2, 4)
    }

    @Test fun `divide scalar`() {
        Vector2F.X / 1 isEqualTo Vector2F.X
        Vector2F.Y / 1 isEqualTo Vector2F.Y
        Vector2F.X / 1L isEqualTo Vector2F.X
        Vector2F.X / 1F isEqualTo Vector2F.X
        Vector2F.X / 1.0 isEqualTo Vector2F.X
        Vector2F(1, 1) / -1 isEqualTo Vector2F(-1, -1)
        Vector2F(1, 1) / 2 isEqualTo Vector2F(0.5F, 0.5F)
        Vector2F(1, 2) / 2 isEqualTo Vector2F(0.5F, 1)
    }

    @Test fun `dot product`() {
        Vector2F(1, 1) dotProduct Vector2F(1, 1) isEqualTo 2F
        Vector2F(1, 2) dotProduct Vector2F(3, 4) isEqualTo 1F * 3F + 2F * 4F
    }

    @Test fun `cross product`() {
        Vector2F(1, 2) crossProduct Vector2F(3, 4) isEqualTo
                Point2D(1.0, 2.0).crossProduct(3.0, 4.0).z.toFloat()
    }

    @Test fun length() {
        Vector2F.ZERO.lengthSquared isEqualTo 0F
        Vector2F.ZERO.length isEqualTo 0.0
        Vector2F(1, 1).lengthSquared isEqualTo 2F
        Vector2F(1, 1).length isEqualTo Math.sqrt(2.0)
    }

    @Test fun unit() {
        Vector2F(1, 1).unit.length isCloseTo 1.0 tolerance  1e-7
        Vector2F(1, 1).unit.angle isCloseTo Math.PI / 4 tolerance  1e-7
        Vector2F(1,2).unit.length isCloseTo 1.0 tolerance  1e-7
        Vector2F(1,2).unit.angle isCloseTo Vector2F(1, 2).angle tolerance  1e-7
    }

    @Test fun angle() {
        Vector2F( 1,  0).angle isCloseTo Math.PI * 0 / 4 tolerance 1e-7
        Vector2F( 1,  1).angle isCloseTo Math.PI * 1 / 4 tolerance 1e-7
        Vector2F( 0,  1).angle isCloseTo Math.PI * 2 / 4 tolerance 1e-7
        Vector2F(-1,  1).angle isCloseTo Math.PI * 3 / 4 tolerance 1e-7
        Vector2F(-1,  0).angle isCloseTo Math.PI * 4 / 4 tolerance 1e-7
        Vector2F(-1, -1).angle isCloseTo Math.PI * 5 / 4 tolerance 1e-7
        Vector2F( 0, -1).angle isCloseTo Math.PI * 6 / 4 tolerance 1e-7
        Vector2F( 1, -1).angle isCloseTo Math.PI * 7 / 4 tolerance 1e-7
    }

    @Test fun `rotate left`() {
        Vector2F.X.left isEqualTo Vector2F.Y
        Vector2F.Y.left isEqualTo -Vector2F.X
        (-Vector2F.X).left isEqualTo -Vector2F.Y
        (-Vector2F.Y).left isEqualTo Vector2F.X

        Vector2F(3, 4).let { it.left.angleFrom(it) } isEqualTo Math.PI / 2
        Vector2F(-4, 3).let { it.left.angleFrom(it) } isEqualTo Math.PI / 2
        Vector2F(-3, -4).let { it.left.angleFrom(it) } isEqualTo Math.PI / 2
        Vector2F(4, -3).let { it.left.angleFrom(it) } isEqualTo Math.PI / 2
    }

    @Test fun `to string`() {
        Vector2F.X.toString() isEqualTo "(1,0)"
        Vector2F(1.5, 0).toString() isEqualTo "(1.5,0)"
        Vector2F(1.5, 0.00001).toString() isEqualTo "(1.5,${0.00001})"
    }
}

fun v(x: Number, y: Number) = Vector2F(x, y)
object v {
    val X = Vector2F.X
    val Y = Vector2F.Y
    val ZERO = Vector2F.ZERO
}
