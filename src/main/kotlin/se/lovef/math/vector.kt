package se.lovef.math

import com.sun.javafx.geom.Vec2d

/*
 * Date: 2016-11-27
 * @author Love
 */

operator fun Vec2d.unaryPlus() = this

operator fun Vec2d.unaryMinus() = Vec2d(-x, -y)

operator fun Vec2d.plus(v: Vec2d) = Vec2d(x + v.x, y + v.y)

operator fun Vec2d.minus(v: Vec2d) = this + (-v)

operator fun Vec2d.times(d: Double) = Vec2d(x * d, y * d)

operator fun Vec2d.div(d: Double) = Vec2d(x / d, y / d)

val Vec2d.length: Double get() = Math.sqrt(x * x + y * y)