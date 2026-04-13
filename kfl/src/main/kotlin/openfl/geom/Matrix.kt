package openfl.geom

import kotlin.math.*

class Matrix(
    var a: Double = 1.0,
    var b: Double = 0.0,
    var c: Double = 0.0,
    var d: Double = 1.0,
    var tx: Double = 0.0,
    var ty: Double = 0.0
) {
    fun clone(): Matrix = Matrix(a, b, c, d, tx, ty)

    fun concat(m: Matrix) {
        val a1 = a * m.a + b * m.c
        b = a * m.b + b * m.d
        a = a1
        val c1 = c * m.a + d * m.c
        d = c * m.b + d * m.d
        c = c1
        val tx1 = tx * m.a + ty * m.c + m.tx
        ty = tx * m.b + ty * m.d + m.ty
        tx = tx1
    }

    fun identity() {
        a = 1.0; b = 0.0; c = 0.0; d = 1.0; tx = 0.0; ty = 0.0
    }

    fun invert() {
        val norm = a * d - b * c
        if (norm == 0.0) {
            a = 0.0; b = 0.0; c = 0.0; d = 0.0; tx = -tx; ty = -ty
        } else {
            val invNorm = 1.0 / norm
            val a1 = d * invNorm
            d = a * invNorm
            a = a1
            b *= -invNorm
            c *= -invNorm
            val tx1 = -a * tx - c * ty
            ty = -b * tx - d * ty
            tx = tx1
        }
    }

    fun rotate(theta: Double) {
        val cos = cos(theta)
        val sin = sin(theta)
        val a1 = a * cos - b * sin
        b = a * sin + b * cos
        a = a1
        val c1 = c * cos - d * sin
        d = c * sin + d * cos
        c = c1
        val tx1 = tx * cos - ty * sin
        ty = tx * sin + ty * cos
        tx = tx1
    }

    fun scale(sx: Double, sy: Double) {
        a *= sx; b *= sy; c *= sx; d *= sy; tx *= sx; ty *= sy
    }

    fun translate(dx: Double, dy: Double) {
        tx += dx; ty += dy
    }

    fun transformPoint(pos: Point): Point = Point(pos.x * a + pos.y * c + tx, pos.x * b + pos.y * d + ty)

    fun copyFrom(other: Matrix) {
        a = other.a; b = other.b; c = other.c; d = other.d; tx = other.tx; ty = other.ty
    }

    override fun toString(): String = "matrix($a, $b, $c, $d, $tx, $ty)"

    companion object {
        val __pool = openfl.utils.ObjectPool({ Matrix() }, { it.identity() })
    }
}
