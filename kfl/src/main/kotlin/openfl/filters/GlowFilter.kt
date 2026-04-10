package openfl.filters

import openfl.display.BitmapData
import openfl.display.DisplayObjectRenderer
import openfl.display.Shader
import openfl.geom.Point
import openfl.geom.Rectangle
import kotlin.math.ceil

class GlowFilter(
    color: Int = 0xFF0000,
    alpha: Double = 1.0,
    blurX: Double = 6.0,
    blurY: Double = 6.0,
    strength: Double = 2.0,
    quality: Int = 1,
    inner: Boolean = false,
    knockout: Boolean = false
) : BitmapFilter() {
    private var __alpha: Double = alpha
    private var __blurX: Double = blurX
    private var __blurY: Double = blurY
    private var __color: Int = color
    private var __horizontalPasses: Int = 0
    private var __inner: Boolean = inner
    private var __knockout: Boolean = knockout
    private var __quality: Int = quality
    private var __strength: Double = strength
    private var __verticalPasses: Int = 0

    var alpha: Double
        get() = __alpha
        set(value) {
            if (value != __alpha) __renderDirty = true
            __alpha = value
        }

    var blurX: Double
        get() = __blurX
        set(value) {
            if (value != __blurX) {
                __blurX = value
                __renderDirty = true
                __updateSize()
            }
        }

    var blurY: Double
        get() = __blurY
        set(value) {
            if (value != __blurY) {
                __blurY = value
                __renderDirty = true
                __updateSize()
            }
        }

    var color: Int
        get() = __color
        set(value) {
            if (value != __color) __renderDirty = true
            __color = value
        }

    var inner: Boolean
        get() = __inner
        set(value) {
            if (value != __inner) {
                __renderDirty = true
                __calculateNumShaderPasses()
            }
            __inner = value
        }

    var knockout: Boolean
        get() = __knockout
        set(value) {
            if (value != __knockout) {
                __renderDirty = true
                __calculateNumShaderPasses()
            }
            __knockout = value
        }

    var quality: Int
        get() = __quality
        set(value) {
            if (value != __quality) {
                __renderDirty = true
                __calculateNumShaderPasses()
            }
            __quality = value
        }

    var strength: Double
        get() = __strength
        set(value) {
            if (value != __strength) __renderDirty = true
            __strength = value
        }

    init {
        __updateSize()
        __needSecondBitmapData = true
        __preserveObject = true
        __renderDirty = true
    }

    override fun clone(): BitmapFilter {
        return GlowFilter(__color, __alpha, __blurX, __blurY, __strength, __quality, __inner, __knockout)
    }

    override fun __applyFilter(
        bitmapData: BitmapData,
        sourceBitmapData: BitmapData,
        sourceRect: Rectangle,
        destPoint: Point
    ): BitmapData {
        return bitmapData
    }

    override fun __initShader(renderer: DisplayObjectRenderer?, pass: Int, sourceBitmapData: BitmapData): Shader? {
        return null
    }

    private fun __updateSize() {
        __leftExtension = if (__blurX > 0) ceil(__blurX * 1.5).toInt() else 0
        __rightExtension = __leftExtension
        __topExtension = if (__blurY > 0) ceil(__blurY * 1.5).toInt() else 0
        __bottomExtension = __topExtension
        __calculateNumShaderPasses()
    }

    private fun __calculateNumShaderPasses() {
        __horizontalPasses = if (__blurX <= 0) 0 else (Math.round(__blurX * (__quality.toDouble() / 4.0)) + 1).toInt()
        __verticalPasses = if (__blurY <= 0) 0 else (Math.round(__blurY * (__quality.toDouble() / 4.0)) + 1).toInt()
        __numShaderPasses = __horizontalPasses + __verticalPasses + (if (__inner) 2 else 1)
    }
}
