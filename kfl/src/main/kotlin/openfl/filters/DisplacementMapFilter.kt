package openfl.filters

import openfl.display.BitmapData
import openfl.display.DisplayObjectRenderer
import openfl.display.Shader
import openfl.geom.Point
import openfl.geom.Rectangle

class DisplacementMapFilter(
    var mapBitmap: BitmapData? = null,
    var mapPoint: Point? = null,
    var componentX: Int = 0,
    var componentY: Int = 0,
    var scaleX: Double = 0.0,
    var scaleY: Double = 0.0,
    var mode: DisplacementMapFilterMode = DisplacementMapFilterMode.WRAP,
    var color: Int = 0,
    var alpha: Double = 0.0
) : BitmapFilter() {
    override fun clone(): BitmapFilter {
        return DisplacementMapFilter(mapBitmap, mapPoint?.clone(), componentX, componentY, scaleX, scaleY, mode, color, alpha)
    }

    override fun __applyFilter(bitmapData: BitmapData, sourceBitmapData: BitmapData, sourceRect: Rectangle, destPoint: Point): BitmapData {
        return bitmapData
    }
}

enum class DisplacementMapFilterMode {
    CLAMP,
    COLOR,
    IGNORE,
    WRAP
}
