package openfl.display

import openfl.geom.Matrix
import openfl.geom.Rectangle

open class Shape : DisplayObject() {

    val graphics: Graphics by lazy { Graphics(this) }

    init {
        __drawableType = openfl.display._internal.IBitmapDrawableType.DISPLAY_OBJECT
    }

    override fun __getBounds(rect: Rectangle, matrix: Matrix) {
        super.__getBounds(rect, matrix)
        // Add graphics bounds here when implemented
    }
}
