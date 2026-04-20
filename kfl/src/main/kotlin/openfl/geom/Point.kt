package openfl.geom

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

open class Point(var x: Double = 0.0, var y: Double = 0.0) {

    val length: Double
        get() = sqrt(x * x + y * y)

    fun add(v: Point): Point {
        return Point(v.x + x, v.y + y)
    }

    fun addToOutput(v: Point, output: Point?): Point {
        val result = output ?: Point()
        result.setTo(v.x + x, v.y + y)
        return result
    }

    fun clone(): Point {
        return Point(x, y)
    }

    fun copyFrom(sourcePoint: Point) {
        x = sourcePoint.x
        y = sourcePoint.y
    }

    fun equals(toCompare: Point?): Boolean {
        return toCompare != null && toCompare.x == x && toCompare.y == y
    }

    fun normalize(thickness: Double) {
        if (x == 0.0 && y == 0.0) {
            return
        } else {
            val norm = thickness / length
            x *= norm
            y *= norm
        }
    }

    fun offset(dx: Double, dy: Double) {
        x += dx
        y += dy
    }

    fun setTo(xa: Double, ya: Double) {
        x = xa
        y = ya
    }

    fun subtract(v: Point): Point {
        return Point(x - v.x, y - v.y)
    }

    fun subtractToOutput(v: Point, output: Point?): Point {
        val result = output ?: Point()
        result.setTo(x - v.x, y - v.y)
        return result
    }

    override fun toString(): String {
        return "(x=$x, y=$y)"
    }

    companion object {
        fun distance(pt1: Point, pt2: Point): Double {
            val dx = pt1.x - pt2.x
            val dy = pt1.y - pt2.y
            return sqrt(dx * dx + dy * dy)
        }

        fun interpolate(pt1: Point, pt2: Point, f: Double): Point {
            return Point(pt2.x + f * (pt1.x - pt2.x), pt2.y + f * (pt1.y - pt2.y))
        }

        fun interpolateToOutput(pt1: Point, pt2: Point, f: Double, output: Point?): Point {
            val result = output ?: Point()
            result.setTo(pt2.x + f * (pt1.x - pt2.x), pt2.y + f * (pt1.y - pt2.y))
            return result
        }

        fun polar(len: Double, angle: Double): Point {
            return Point(len * cos(angle), len * sin(angle))
        }

        fun polarToOutput(len: Double, angle: Double, output: Point?): Point {
            val result = output ?: Point()
            result.setTo(len * cos(angle), len * sin(angle))
            return result
        }
    }
}
