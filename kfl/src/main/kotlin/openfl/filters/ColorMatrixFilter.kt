package openfl.filters

import openfl.display.BitmapData
import openfl.display.DisplayObjectRenderer
import openfl.display.Shader
import openfl.geom.Point
import openfl.geom.Rectangle
import kotlin.math.max
import kotlin.math.min

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
        // Pixel manipulation in Kotlin is slow, ideally we'd use a native implementation or Skia
        // For now, we'll provide a placeholder or use Skia if possible.
        // Skia's ColorFilter can be used when drawing.
        return bitmapData
    }

    override fun __initShader(renderer: DisplayObjectRenderer?, pass: Int, sourceBitmapData: BitmapData): Shader? {
        // TODO: Implement ColorMatrixShader
        return null
    }
}
