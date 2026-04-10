package openfl.display

import openfl.display._internal.IBitmapDrawableType
import openfl.events.Event
import openfl.events.EventDispatcher
import openfl.events.EventPhase
import openfl.geom.Matrix
import openfl.geom.Point
import openfl.geom.Rectangle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

abstract class DisplayObject : EventDispatcher(), IBitmapDrawable {

    var alpha: Double
        get() = __alpha
        set(value) {
            val v = if (value.isNaN()) 0.0 else if (value > 1.0) 1.0 else if (value < 0.0) 0.0 else value
            if (v != __alpha) __setRenderDirty()
            __alpha = v
        }

    var blendMode: BlendMode = BlendMode.NORMAL
        set(value) {
            if (value != field) __setRenderDirty()
            field = value
        }

    var cacheAsBitmap: Boolean = false
        set(value) {
            if (value != field) __setRenderDirty()
            field = value
        }

    open var height: Double
        get() {
            val rect = Rectangle()
            __getLocalBounds(rect)
            return rect.height
        }
        set(value) {
            val rect = Rectangle()
            val matrix = Matrix()
            __getBounds(rect, matrix)
            if (value != rect.height) {
                scaleY = value / rect.height
            } else {
                scaleY = 1.0
            }
        }

    var mask: DisplayObject? = null
        set(value) {
            if (value === field) return
            // In OpenFL/Flash, a mask can only belong to one object
            // field?.__maskTarget = null
            field = value
            // value?.__maskTarget = this
            __setRenderDirty()
        }

    var mouseX: Double = 0.0
        get() = 0.0 // Needs Stage reference
        private set

    var mouseY: Double = 0.0
        get() = 0.0 // Needs Stage reference
        private set

    var name: String = ""

    var stage: Any? = null // Placeholder for Stage
        internal set

    var parent: DisplayObjectContainer? = null
        internal set

    var rotation: Double
        get() = __rotation
        set(value) {
            var v = value % 360.0
            if (v > 180.0) v -= 360.0 else if (v < -180.0) v += 360.0
            if (v != __rotation) {
                __rotation = v
                val radians = v * (PI / 180.0)
                __rotationSine = sin(radians)
                __rotationCosine = cos(radians)
                __transform.a = __rotationCosine * __scaleX
                __transform.b = __rotationSine * __scaleX
                __transform.c = -__rotationSine * __scaleY
                __transform.d = __rotationCosine * __scaleY
                __setTransformDirty()
            }
        }

    var scaleX: Double
        get() = __scaleX
        set(value) {
            if (value != __scaleX) {
                __scaleX = value
                if (__rotation == 0.0) {
                    if (value != __transform.a) __setTransformDirty()
                    __transform.a = value
                } else {
                    val a = __rotationCosine * value
                    val b = __rotationSine * value
                    if (__transform.a != a || __transform.b != b) __setTransformDirty()
                    __transform.a = a
                    __transform.b = b
                }
            }
        }

    var scaleY: Double
        get() = __scaleY
        set(value) {
            if (value != __scaleY) {
                __scaleY = value
                if (__rotation == 0.0) {
                    if (value != __transform.d) __setTransformDirty()
                    __transform.d = value
                } else {
                    val c = -__rotationSine * value
                    val d = __rotationCosine * value
                    if (__transform.d != d || __transform.c != c) __setTransformDirty()
                    __transform.c = c
                    __transform.d = d
                }
            }
        }

    var visible: Boolean = true
        set(value) {
            if (value != field) __setRenderDirty()
            field = value
        }

    open var width: Double
        get() {
            val rect = Rectangle()
            __getLocalBounds(rect)
            return rect.width
        }
        set(value) {
            val rect = Rectangle()
            val matrix = Matrix()
            __getBounds(rect, matrix)
            if (value != rect.width) {
                scaleX = value / rect.width
            } else {
                scaleX = 1.0
            }
        }

    var x: Double
        get() = __transform.tx
        set(value) {
            if (value != __transform.tx) __setTransformDirty()
            __transform.tx = value
        }

    var y: Double
        get() = __transform.ty
        set(value) {
            if (value != __transform.ty) __setTransformDirty()
            __transform.ty = value
        }

    // Internal Properties
    internal var __alpha: Double = 1.0
    override var __blendMode: BlendMode = BlendMode.NORMAL
    override var __drawableType: IBitmapDrawableType = IBitmapDrawableType.DISPLAY_OBJECT
    override var __isMask: Boolean = false
    override var __renderable: Boolean = true
    override var __renderTransform: Matrix = Matrix()
    override var __transform: Matrix = Matrix()
    override var __worldAlpha: Double = 1.0
    override var __worldTransform: Matrix = Matrix()
    override var __mask: DisplayObject? = null
    override var __scrollRect: Rectangle? = null

    internal var __rotation: Double = 0.0
    internal var __rotationSine: Double = 0.0
    internal var __rotationCosine: Double = 1.0
    internal var __scaleX: Double = 1.0
    internal var __scaleY: Double = 1.0
    internal var __transformDirty: Boolean = false
    internal var __renderDirty: Boolean = false

    fun getBounds(targetCoordinateSpace: DisplayObject?): Rectangle {
        val matrix = Matrix()
        if (targetCoordinateSpace != null && targetCoordinateSpace !== this) {
            matrix.copyFrom(__getWorldTransform())
            val targetMatrix = targetCoordinateSpace.__getWorldTransform().clone()
            targetMatrix.invert()
            matrix.concat(targetMatrix)
        }
        val bounds = Rectangle()
        __getBounds(bounds, matrix)
        return bounds
    }

    fun globalToLocal(pos: Point): Point {
        val result = Point()
        __getRenderTransform()
        result.x = __renderTransform.tx // Simplification for now
        // TODO: Full inverse transform
        return result
    }

    fun localToGlobal(pos: Point): Point {
        return __getRenderTransform().transformPoint(pos)
    }

    override fun __getBounds(rect: Rectangle, matrix: Matrix) {
        // To be overridden
    }

    internal fun __getLocalBounds(rect: Rectangle) {
        __getBounds(rect, __transform)
        rect.x -= __transform.tx
        rect.y -= __transform.ty
    }

    override fun __update(transformOnly: Boolean, updateChildren: Boolean) {
        __renderable = visible && __scaleX != 0.0 && __scaleY != 0.0 && !__isMask
        __updateTransforms()
        __transformDirty = false
    }

    override fun __updateTransforms(overrideTransform: Matrix?) {
        val local = overrideTransform ?: __transform
        if (parent != null) {
            __calculateAbsoluteTransform(local, parent!!.__worldTransform, __worldTransform)
            __calculateAbsoluteTransform(local, parent!!.__renderTransform, __renderTransform)
        } else {
            __worldTransform.copyFrom(local)
            __renderTransform.copyFrom(local)
        }
    }

    internal fun __getRenderTransform(): Matrix {
        __getWorldTransform()
        return __renderTransform
    }

    internal fun __getWorldTransform(): Matrix {
        if (__transformDirty) {
            __update(true, false)
        }
        return __worldTransform
    }

    internal fun __setRenderDirty() {
        if (!__renderDirty) {
            __renderDirty = true
            parent?.__setRenderDirty()
        }
    }

    internal fun __setTransformDirty() {
        if (!__transformDirty) {
            __transformDirty = true
            __setRenderDirty()
        }
    }

    private fun __calculateAbsoluteTransform(local: Matrix, parentTransform: Matrix, target: Matrix) {
        target.a = local.a * parentTransform.a + local.b * parentTransform.c
        target.b = local.a * parentTransform.b + local.b * parentTransform.d
        target.c = local.c * parentTransform.a + local.d * parentTransform.c
        target.d = local.c * parentTransform.b + local.d * parentTransform.d
        target.tx = local.tx * parentTransform.a + local.ty * parentTransform.c + parentTransform.tx
        target.ty = local.tx * parentTransform.b + local.ty * parentTransform.d + parentTransform.ty
    }

    override fun __dispatchEvent(event: Event): Boolean {
        val parent = if (event.bubbles) this.parent else null
        val result = super.__dispatchEvent(event)
        if (event.isCanceled) return true
        if (parent != null && parent !== this) {
            event.eventPhase = EventPhase.BUBBLING_PHASE
            parent.__dispatchEvent(event)
        }
        return result
    }
}
