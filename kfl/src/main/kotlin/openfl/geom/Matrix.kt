package openfl.geom

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.round

open class Matrix(
    var a: Double = 1.0,
    var b: Double = 0.0,
    var c: Double = 0.0,
    var d: Double = 1.0,
    var tx: Double = 0.0,
    var ty: Double = 0.0
) {
    fun clone(): Matrix {
        return Matrix(a, b, c, d, tx, ty)
    }

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

    fun copyColumnFrom(column: Int, vector3D: Vector3D) {
        when (column) {
            0 -> {
                a = vector3D.x
                b = vector3D.y
            }
            1 -> {
                c = vector3D.x
                d = vector3D.y
            }
            2 -> {
                tx = vector3D.x
                ty = vector3D.y
            }
            else -> throw IllegalArgumentException("Column $column out of bounds (2)")
        }
    }

    fun copyColumnTo(column: Int, vector3D: Vector3D) {
        when (column) {
            0 -> {
                vector3D.x = a
                vector3D.y = b
                vector3D.z = 0.0
            }
            1 -> {
                vector3D.x = c
                vector3D.y = d
                vector3D.z = 0.0
            }
            2 -> {
                vector3D.x = tx
                vector3D.y = ty
                vector3D.z = 1.0
            }
            else -> throw IllegalArgumentException("Column $column out of bounds (2)")
        }
    }

    fun copyFrom(sourceMatrix: Matrix) {
        a = sourceMatrix.a
        b = sourceMatrix.b
        c = sourceMatrix.c
        d = sourceMatrix.d
        tx = sourceMatrix.tx
        ty = sourceMatrix.ty
    }

    fun copyRowFrom(row: Int, vector3D: Vector3D) {
        when (row) {
            0 -> {
                a = vector3D.x
                c = vector3D.y
                tx = vector3D.z
            }
            1 -> {
                b = vector3D.x
                d = vector3D.y
                ty = vector3D.z
            }
            else -> throw IllegalArgumentException("Row $row out of bounds (2)")
        }
    }

    fun copyRowTo(row: Int, vector3D: Vector3D) {
        when (row) {
            0 -> {
                vector3D.x = a
                vector3D.y = c
                vector3D.z = tx
            }
            1 -> {
                vector3D.x = b
                vector3D.y = d
                vector3D.z = ty
            }
            else -> {
                vector3D.setTo(0.0, 0.0, 1.0)
            }
        }
    }

    fun createBox(scaleX: Double, scaleY: Double, rotation: Double = 0.0, tx: Double = 0.0, ty: Double = 0.0) {
        if (rotation != 0.0) {
            val cos = cos(rotation)
            val sin = sin(rotation)

            a = cos * scaleX
            b = sin * scaleY
            c = -sin * scaleX
            d = cos * scaleY
        } else {
            a = scaleX
            b = 0.0
            c = 0.0
            d = scaleY
        }

        this.tx = tx
        this.ty = ty
    }

    fun createGradientBox(width: Double, height: Double, rotation: Double = 0.0, tx: Double = 0.0, ty: Double = 0.0) {
        a = width / 1638.4
        d = height / 1638.4

        if (rotation != 0.0) {
            val cos = cos(rotation)
            val sin = sin(rotation)

            b = sin * d
            c = -sin * a
            a *= cos
            d *= cos
        } else {
            b = 0.0
            c = 0.0
        }

        this.tx = tx + width / 2
        this.ty = ty + height / 2
    }

    fun deltaTransformPoint(point: Point): Point {
        return Point(point.x * a + point.y * c, point.x * b + point.y * d)
    }

    fun deltaTransformPointToOutput(point: Point, output: Point?): Point {
        val result = output ?: Point()
        result.setTo(point.x * a + point.y * c, point.x * b + point.y * d)
        return result
    }

    fun equals(matrix: Matrix?): Boolean {
        return (matrix != null && tx == matrix.tx && ty == matrix.ty && a == matrix.a && b == matrix.b && c == matrix.c && d == matrix.d)
    }

    fun identity() {
        a = 1.0
        b = 0.0
        c = 0.0
        d = 1.0
        tx = 0.0
        ty = 0.0
    }

    fun invert(): Matrix {
        var norm = a * d - b * c

        if (norm == 0.0) {
            a = 0.0
            b = 0.0
            c = 0.0
            d = 0.0
            tx = -tx
            ty = -ty
        } else {
            norm = 1.0 / norm
            val a1 = d * norm
            d = a * norm
            a = a1
            b *= -norm
            c *= -norm

            val tx1 = -a * tx - c * ty
            ty = -b * tx - d * ty
            tx = tx1
        }

        return this
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
        a *= sx
        b *= sy
        c *= sx
        d *= sy
        tx *= sx
        ty *= sy
    }

    fun setTo(a: Double, b: Double, c: Double, d: Double, tx: Double, ty: Double) {
        this.a = a
        this.b = b
        this.c = c
        this.d = d
        this.tx = tx
        this.ty = ty
    }

    override fun toString(): String {
        return "matrix($a, $b, $c, $d, $tx, $ty)"
    }

    fun transformPoint(pos: Point): Point {
        return Point(__transformX(pos.x, pos.y), __transformY(pos.x, pos.y))
    }

    fun transformPointToOutput(pos: Point, output: Point?): Point {
        val result = output ?: Point()
        result.setTo(__transformX(pos.x, pos.y), __transformY(pos.x, pos.y))
        return result
    }

    fun translate(dx: Double, dy: Double) {
        tx += dx
        ty += dy
    }

    private fun __transformX(px: Double, py: Double): Double {
        return px * a + py * c + tx
    }

    private fun __transformY(px: Double, py: Double): Double {
        return px * b + py * d + ty
    }
}
