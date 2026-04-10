package openfl.display

import openfl.display._internal.IBitmapDrawableType
import openfl.geom.Matrix
import openfl.geom.Rectangle

class Bitmap(
    var bitmapData: BitmapData? = null,
    var pixelSnapping: PixelSnapping = PixelSnapping.AUTO,
    var smoothing: Boolean = false
) : DisplayObject() {

    init {
        __drawableType = IBitmapDrawableType.DISPLAY_OBJECT
    }

    override var height: Double
        get() = (bitmapData?.height?.toDouble() ?: 0.0) * __scaleY
        set(value) {
            val h = bitmapData?.height?.toDouble() ?: 0.0
            scaleY = if (h != 0.0) value / h else 1.0
        }

    override var width: Double
        get() = (bitmapData?.width?.toDouble() ?: 0.0) * __scaleX
        set(value) {
            val w = bitmapData?.width?.toDouble() ?: 0.0
            scaleX = if (w != 0.0) value / w else 1.0
        }

    override fun __getBounds(rect: Rectangle, matrix: Matrix) {
        val bounds = Rectangle()
        bitmapData?.let {
            bounds.setTo(0.0, 0.0, it.width.toDouble(), it.height.toDouble())
        }
        // bounds.__transform(bounds, matrix)
        rect.union(bounds)
    }
}

enum class PixelSnapping {
    ALWAYS,
    AUTO,
    NEVER
}
