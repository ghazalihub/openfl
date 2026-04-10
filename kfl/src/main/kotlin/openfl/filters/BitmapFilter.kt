package openfl.filters

import openfl.display.BitmapData
import openfl.display.BlendMode
import openfl.display.DisplayObjectRenderer
import openfl.display.Shader
import openfl.geom.Point
import openfl.geom.Rectangle

open class BitmapFilter {
    internal var __bottomExtension: Int = 0
    internal var __leftExtension: Int = 0
    internal var __needSecondBitmapData: Boolean = true
    internal var __numShaderPasses: Int = 0
    internal var __preserveObject: Boolean = false
    internal var __renderDirty: Boolean = false
    internal var __rightExtension: Int = 0
    internal var __shaderBlendMode: BlendMode = BlendMode.NORMAL
    internal var __smooth: Boolean = true
    internal var __topExtension: Int = 0

    open fun clone(): BitmapFilter {
        return BitmapFilter()
    }

    internal open fun __applyFilter(bitmapData: BitmapData, sourceBitmapData: BitmapData, sourceRect: Rectangle, destPoint: Point): BitmapData {
        return sourceBitmapData
    }

    internal open fun __initShader(renderer: DisplayObjectRenderer?, pass: Int, sourceBitmapData: BitmapData): Shader? {
        return null
    }
}
