package openfl.filters

import openfl.display.BitmapData
import openfl.display.DisplayObjectRenderer
import openfl.display.Shader
import openfl.geom.Point
import openfl.geom.Rectangle
import org.jetbrains.skia.ColorFilter

class ColorMatrixFilter(matrix: DoubleArray? = null) : BitmapFilter() {
    private var __matrix: DoubleArray = matrix ?: doubleArrayOf(
        1.0, 0.0, 0.0, 0.0, 0.0,
        0.0, 1.0, 0.0, 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0, 0.0,
        0.0, 0.0, 0.0, 1.0, 0.0
    )

    var matrix: DoubleArray
        get() = __matrix.copyOf()
        set(value) {
            __matrix = value
            __renderDirty = true
        }

    init {
        __numShaderPasses = 1
        __needSecondBitmapData = false
    }

    override fun clone(): BitmapFilter {
        return ColorMatrixFilter(__matrix.copyOf())
    }

    override fun __applyFilter(
        bitmapData: BitmapData,
        sourceBitmapData: BitmapData,
        sourceRect: Rectangle,
        destPoint: Point
    ): BitmapData {
        val m = __matrix
        // Skia takes 4x5 row-major matrix
        val skiaMatrix = floatArrayOf(
            m[0].toFloat(), m[1].toFloat(), m[2].toFloat(), m[3].toFloat(), m[4].toFloat(),
            m[5].toFloat(), m[6].toFloat(), m[7].toFloat(), m[8].toFloat(), m[9].toFloat(),
            m[10].toFloat(), m[11].toFloat(), m[12].toFloat(), m[13].toFloat(), m[14].toFloat(),
            m[15].toFloat(), m[16].toFloat(), m[17].toFloat(), m[18].toFloat(), m[19].toFloat()
        )
        val filter = ColorFilter.makeMatrix(skiaMatrix)
        // Apply filter via Skia Canvas in BitmapData...
        return bitmapData
    }
}
