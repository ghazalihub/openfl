package openfl.geom

import kotlin.math.sqrt
import kotlin.math.acos

open class Vector3D(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
    var w: Double = 0.0
) {
    val length: Double
        get() = sqrt(x * x + y * y + z * z)

    val lengthSquared: Double
        get() = x * x + y * y + z * z

    fun add(a: Vector3D): Vector3D {
        return Vector3D(x + a.x, y + a.y, z + a.z)
    }

    fun addToOutput(a: Vector3D, output: Vector3D?): Vector3D {
        val result = output ?: Vector3D()
        result.setTo(x + a.x, y + a.y, z + a.z)
        return result
    }

    fun clone(): Vector3D {
        return Vector3D(x, y, z, w)
    }

    fun copyFrom(sourceVector3D: Vector3D) {
        x = sourceVector3D.x
        y = sourceVector3D.y
        z = sourceVector3D.z
    }

    fun crossProduct(a: Vector3D): Vector3D {
        return Vector3D(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x, 1.0)
    }

    fun crossProductToOutput(a: Vector3D, output: Vector3D?): Vector3D {
        val result = output ?: Vector3D()
        result.setTo(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x)
        result.w = 1.0
        return result
    }

    fun decrementBy(a: Vector3D) {
        x -= a.x
        y -= a.y
        z -= a.z
    }

    fun dotProduct(a: Vector3D): Double {
        return x * a.x + y * a.y + z * a.z
    }

    fun equals(toCompare: Vector3D, allFour: Boolean = false): Boolean {
        return x == toCompare.x && y == toCompare.y && z == toCompare.z && (!allFour || w == toCompare.w)
    }

    fun incrementBy(a: Vector3D) {
        x += a.x
        y += a.y
        z += a.z
    }

    fun nearEquals(toCompare: Vector3D, tolerance: Double, allFour: Boolean = false): Boolean {
        return Math.abs(x - toCompare.x) < tolerance &&
                Math.abs(y - toCompare.y) < tolerance &&
                Math.abs(z - toCompare.z) < tolerance &&
                (!allFour || Math.abs(w - toCompare.w) < tolerance)
    }

    fun negate() {
        x *= -1.0
        y *= -1.0
        z *= -1.0
    }

    fun normalize(): Double {
        val l = length
        if (l != 0.0) {
            x /= l
            y /= l
            z /= l
        }
        return l
    }

    fun project() {
        x /= w
        y /= w
        z /= w
    }

    fun scaleBy(s: Double) {
        x *= s
        y *= s
        z *= s
    }

    fun setTo(xa: Double, ya: Double, za: Double) {
        x = xa
        y = ya
        z = za
    }

    fun subtract(a: Vector3D): Vector3D {
        return Vector3D(x - a.x, y - a.y, z - a.z)
    }

    fun subtractToOutput(a: Vector3D, output: Vector3D?): Vector3D {
        val result = output ?: Vector3D()
        result.setTo(x - a.x, y - a.y, z - a.z)
        return result
    }

    override fun toString(): String {
        return "Vector3D($x, $y, $z)"
    }

    companion object {
        val X_AXIS: Vector3D get() = Vector3D(1.0, 0.0, 0.0)
        val Y_AXIS: Vector3D get() = Vector3D(0.0, 1.0, 0.0)
        val Z_AXIS: Vector3D get() = Vector3D(0.0, 0.0, 1.0)

        fun angleBetween(a: Vector3D, b: Vector3D): Double {
            var dot = a.dotProduct(b)
            val la = a.length
            val lb = b.length
            if (la != 0.0) dot /= la
            if (lb != 0.0) dot /= lb
            return acos(dot)
        }

        fun distance(pt1: Vector3D, pt2: Vector3D): Double {
            val dx = pt2.x - pt1.x
            val dy = pt2.y - pt1.y
            val dz = pt2.z - pt1.z
            return sqrt(dx * dx + dy * dy + dz * dz)
        }
    }
}
