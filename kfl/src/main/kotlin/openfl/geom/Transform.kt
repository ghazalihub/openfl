package openfl.geom

import openfl.display.DisplayObject
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

class Transform(internal val __displayObject: DisplayObject) {

    var colorTransform: ColorTransform
        get() = __colorTransform.__clone()
        set(value) {
            if (!__colorTransform.__equals(value, false)) {
                __colorTransform.__copyFrom(value)
                __displayObject.alpha = value.alphaMultiplier
                __displayObject.__setRenderDirty()
            }
        }

    val concatenatedColorTransform: ColorTransform = ColorTransform()

    val concatenatedMatrix: Matrix
        get() = __displayObject.__getWorldTransform().clone()

    var matrix: Matrix
        get() = __displayObject.__transform.clone()
        set(value) {
            __hasMatrix = true
            __setTransform(value.a, value.b, value.c, value.d, value.tx, value.ty)
        }

    val pixelBounds: Rectangle = Rectangle()

    internal var __colorTransform: ColorTransform = ColorTransform()
    internal var __hasMatrix: Boolean = true

    internal fun __setTransform(a: Double, b: Double, c: Double, d: Double, tx: Double, ty: Double) {
        val transform = __displayObject.__transform
        if (transform.a == a && transform.b == b && transform.c == c && transform.d == d && transform.tx == tx && transform.ty == ty) {
            return
        }

        val scaleX = if (b == 0.0) a else sqrt(a * a + b * b)
        val scaleY = if (c == 0.0) d else sqrt(c * c + d * d)

        __displayObject.__scaleX = scaleX
        __displayObject.__scaleY = scaleY

        val rotation = (180.0 / PI) * atan2(d, c) - 90.0
        if (rotation != __displayObject.__rotation) {
            __displayObject.__rotation = rotation
            // Re-calc sine/cosine if needed, usually handled by setter
        }

        transform.a = a
        transform.b = b
        transform.c = c
        transform.d = d
        transform.tx = tx
        transform.ty = ty

        __displayObject.__setTransformDirty()
    }
}
