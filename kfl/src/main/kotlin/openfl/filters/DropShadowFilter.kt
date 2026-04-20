package openfl.filters

import openfl.display.BitmapData
import openfl.display.DisplayObjectRenderer
import openfl.display.Shader
import openfl.geom.Point
import openfl.geom.Rectangle
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

class DropShadowFilter(
    distance: Double = 4.0,
    angle: Double = 45.0,
    color: Int = 0,
    alpha: Double = 1.0,
    blurX: Double = 4.0,
    blurY: Double = 4.0,
    strength: Double = 1.0,
    quality: Int = 1,
    inner: Boolean = false,
    knockout: Boolean = false,
    hideObject: Boolean = false
) : BitmapFilter() {
    private var __alpha: Double = alpha
    private var __angle: Double = angle
    private var __blurX: Double = blurX
    private var __blurY: Double = blurY
    private var __color: Int = color
    private var __distance: Double = distance
    private var __hideObject: Boolean = hideObject
    private var __horizontalPasses: Int = 0
    private var __inner: Boolean = inner
    private var __knockout: Boolean = knockout
    private var __offsetX: Double = 0.0
    private var __offsetY: Double = 0.0
    private var __quality: Int = quality
    private var __strength: Double = strength
    private var __verticalPasses: Int = 0

    var alpha: Double
        get() = __alpha
        set(value) {
            if (value != __alpha) __renderDirty = true
            __alpha = value
        }

    var angle: Double
        get() = __angle
        set(value) {
            if (value != __angle) {
                __angle = value
                __renderDirty = true
                __updateSize()
            }
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

    var distance: Double
        get() = __distance
        set(value) {
            if (value != __distance) {
                __distance = value
                __renderDirty = true
                __updateSize()
            }
        }

    var hideObject: Boolean
        get() = __hideObject
        set(value) {
            if (value != __hideObject) __renderDirty = true
            __hideObject = value
        }

    var inner: Boolean
        get() = __inner
        set(value) {
            if (value != __inner) __renderDirty = true
            __inner = value
        }

    var knockout: Boolean
        get() = __knockout
        set(value) {
            if (value != __knockout) __renderDirty = true
            __knockout = value
        }

    var quality: Int
        get() = __quality
        set(value) {
            if (value != __quality) __renderDirty = true
            __quality = value
            __calculateNumShaderPasses()
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
        return DropShadowFilter(
            __distance, __angle, __color, __alpha, __blurX, __blurY,
            __strength, __quality, __inner, __knockout, __hideObject
        )
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
        __offsetX = __distance * cos(__angle * Math.PI / 180.0)
        __offsetY = __distance * sin(__angle * Math.PI / 180.0)
        __topExtension = ceil((if (__offsetY < 0) -__offsetY else 0.0) + __blurY).toInt()
        __bottomExtension = ceil((if (__offsetY > 0) __offsetY else 0.0) + __blurY).toInt()
        __leftExtension = ceil((if (__offsetX < 0) -__offsetX else 0.0) + __blurX).toInt()
        __rightExtension = ceil((if (__offsetX > 0) __offsetX else 0.0) + __blurX).toInt()
        __calculateNumShaderPasses()
    }

    private fun __calculateNumShaderPasses() {
        __horizontalPasses = if (__blurX <= 0) 0 else (Math.round(__blurX * (__quality.toDouble() / 4.0)) + 1).toInt()
        __verticalPasses = if (__blurY <= 0) 0 else (Math.round(__blurY * (__quality.toDouble() / 4.0)) + 1).toInt()
        __numShaderPasses = __horizontalPasses + __verticalPasses + (if (__inner) 2 else 1)
    }
}
