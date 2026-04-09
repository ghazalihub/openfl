package openfl.display

import openfl.display._internal.IBitmapDrawableType
import openfl.geom.Matrix
import openfl.geom.Rectangle

interface IBitmapDrawable {
    var __blendMode: BlendMode
    var __drawableType: IBitmapDrawableType
    var __isMask: Boolean
    var __renderable: Boolean
    var __renderTransform: Matrix
    var __transform: Matrix
    var __worldAlpha: Double
    var __worldTransform: Matrix

    fun __getBounds(rect: Rectangle, matrix: Matrix)
    fun __update(transformOnly: Boolean, updateChildren: Boolean)
    fun __updateTransforms(overrideTransform: Matrix? = null)

    var __mask: DisplayObject?
    var __scrollRect: Rectangle?
}
