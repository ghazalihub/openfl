package openfl.filters

import openfl.display.BitmapData
import openfl.display.DisplayObjectRenderer
import openfl.display.Shader
import openfl.geom.Point
import openfl.geom.Rectangle
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.roundToInt

class BlurFilter(blurX: Double = 4.0, blurY: Double = 4.0, quality: Int = 1) : BitmapFilter() {
    private var __blurX: Double = 0.0
    private var __blurY: Double = 0.0
    private var __horizontalPasses: Int = 0
    private var __quality: Int = 0
    private var __verticalPasses: Int = 0

    var blurX: Double
        get() = __blurX
        set(value) {
            if (value != __blurX) {
                __blurX = value
                __renderDirty = true
                val p = __padFor(value)
                __leftExtension = p
                __rightExtension = p
            }
        }

    var blurY: Double
        get() = __blurY
        set(value) {
            if (value != __blurY) {
                __blurY = value
                __renderDirty = true
                val p = __padFor(value)
                __topExtension = p
                __bottomExtension = p
            }
        }

    var quality: Int
        get() = __quality
        set(value) {
            __horizontalPasses = if (__blurX <= 0) 0 else (Math.round(__blurX * (value.toDouble() / 4.0)) + 1).toInt()
            __verticalPasses = if (__blurY <= 0) 0 else (Math.round(__blurY * (value.toDouble() / 4.0)) + 1).toInt()
            __numShaderPasses = __horizontalPasses + __verticalPasses
            if (value != __quality) __renderDirty = true
            __quality = value
            blurX = __blurX
            blurY = __blurY
        }

    init {
        this.blurX = blurX
        this.blurY = blurY
        this.quality = quality
        __needSecondBitmapData = true
        __preserveObject = false
        __renderDirty = true
    }

    override fun clone(): BitmapFilter {
        return BlurFilter(__blurX, __blurY, __quality)
    }

    override fun __applyFilter(
        bitmapData: BitmapData,
        sourceBitmapData: BitmapData,
        sourceRect: Rectangle,
        destPoint: Point
    ): BitmapData {
        // Skia-based blur would happen here or in the renderer
        return bitmapData
    }

    override fun __initShader(renderer: DisplayObjectRenderer?, pass: Int, sourceBitmapData: BitmapData): Shader? {

        return null
    }

    private fun __padFor(value: Double): Int {
        if (value <= 0) return 0
        val passes = if (__quality > 0) __quality else 1
        val reach = value * passes * 3.0
        return ceil(reach).toInt() + 2
    }
}
