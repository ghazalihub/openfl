package openfl.display

import openfl.display._internal.IBitmapDrawableType
import openfl.geom.Matrix
import openfl.geom.Rectangle

open class Sprite : DisplayObjectContainer() {

    var buttonMode: Boolean = false
    var useHandCursor: Boolean = true

    // Graphics object placeholder
    // val graphics: Graphics by lazy { Graphics(this) }

    init {
        __drawableType = IBitmapDrawableType.SPRITE
    }

    override fun __getBounds(rect: Rectangle, matrix: Matrix) {
        super.__getBounds(rect, matrix)
        // Add graphics bounds here when implemented
    }
}
