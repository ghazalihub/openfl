package openfl.filters

import openfl.display.BitmapData
import openfl.display.DisplayObjectRenderer
import openfl.display.Shader
import openfl.geom.Point
import openfl.geom.Rectangle

class ConvolutionFilter(
    var columns: Int = 0,
    var rows: Int = 0,
    matrix: DoubleArray? = null,
    var divisor: Double = 1.0,
    var bias: Double = 0.0,
    var preserveAlpha: Boolean = true,
    var clamp: Boolean = true,
    var color: Int = 0,
    var alpha: Double = 0.0
) : BitmapFilter() {
    private var __matrix: DoubleArray = matrix ?: DoubleArray(columns * rows)

    var matrix: DoubleArray
        get() = __matrix.copyOf()
        set(value) {
            __matrix = value
            __renderDirty = true
        }

    override fun clone(): BitmapFilter {
        return ConvolutionFilter(columns, rows, __matrix.copyOf(), divisor, bias, preserveAlpha, clamp, color, alpha)
    }

    override fun __applyFilter(bitmapData: BitmapData, sourceBitmapData: BitmapData, sourceRect: Rectangle, destPoint: Point): BitmapData {
        return bitmapData
    }
}
