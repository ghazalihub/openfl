package openfl.display

import openfl.geom.Matrix
import openfl.geom.Rectangle
import org.jetbrains.skia.*

class Graphics(internal val __owner: DisplayObject) {

    private val __commands = mutableListOf<(Canvas) -> Unit>()
    private var __visible = false
    private var __dirty = true

    private var __currentPaint = Paint().apply {
        isAntiAlias = true
    }

    private var __currentPath = Path()

    fun beginFill(color: Int, alpha: Double = 1.0) {
        val argb = ((alpha * 255).toInt() shl 24) or (color and 0xFFFFFF)
        val paintCopy = __currentPaint.makeClone()
        __commands.add {
            __currentPaint.mode = PaintMode.FILL
            __currentPaint.color = argb
        }
        __visible = true
    }

    fun clear() {
        __commands.clear()
        __visible = false
        __dirty = true
        __currentPath = Path()
    }

    fun drawCircle(x: Double, y: Double, radius: Double) {
        val paintCopy = __currentPaint.makeClone()
        __commands.add { canvas ->
            canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), paintCopy)
        }
        __dirty = true
        __visible = true
    }

    fun drawEllipse(x: Double, y: Double, width: Double, height: Double) {
        val rect = Rect.makeXYWH(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
        val paintCopy = __currentPaint.makeClone()
        __commands.add { canvas ->
            canvas.drawOval(rect, paintCopy)
        }
        __dirty = true
        __visible = true
    }

    fun drawRect(x: Double, y: Double, width: Double, height: Double) {
        val rect = Rect.makeXYWH(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
        val paintCopy = __currentPaint.makeClone()
        __commands.add { canvas ->
            canvas.drawRect(rect, paintCopy)
        }
        __dirty = true
        __visible = true
    }

    fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, ellipseWidth: Double, ellipseHeight: Double? = null) {
        val eh = ellipseHeight ?: ellipseWidth
        val rect = RRect.makeXYWH(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), ellipseWidth.toFloat(), eh.toFloat())
        val paintCopy = __currentPaint.makeClone()
        __commands.add { canvas ->
            canvas.drawRRect(rect, paintCopy)
        }
        __dirty = true
        __visible = true
    }

    fun endFill() {
        // No-op in Skia, but marks end of fill block
    }

    fun lineStyle(
        thickness: Double? = null,
        color: Int = 0,
        alpha: Double = 1.0,
        pixelHinting: Boolean = false,
        scaleMode: LineScaleMode = LineScaleMode.NORMAL,
        caps: CapsStyle? = null,
        joints: JointStyle? = null,
        miterLimit: Double = 3.0
    ) {
        if (thickness == null) {
            __commands.add { __currentPaint.strokeWidth = 0f }
            return
        }
        val argb = ((alpha * 255).toInt() shl 24) or (color and 0xFFFFFF)

        val skiaCaps = when (caps) {
            CapsStyle.NONE -> PaintStrokeCap.BUTT
            CapsStyle.ROUND -> PaintStrokeCap.ROUND
            CapsStyle.SQUARE -> PaintStrokeCap.SQUARE
            else -> PaintStrokeCap.ROUND
        }

        val skiaJoints = when (joints) {
            JointStyle.BEVEL -> PaintStrokeJoin.BEVEL
            JointStyle.MITER -> PaintStrokeJoin.MITER
            JointStyle.ROUND -> PaintStrokeJoin.ROUND
            else -> PaintStrokeJoin.ROUND
        }

        __commands.add {
            __currentPaint.mode = PaintMode.STROKE
            __currentPaint.color = argb
            __currentPaint.strokeWidth = thickness.toFloat()
            __currentPaint.strokeCap = skiaCaps
            __currentPaint.strokeJoin = skiaJoints
            __currentPaint.strokeMiter = miterLimit.toFloat()
        }
        __visible = true
    }

    fun lineTo(x: Double, y: Double) {
        __currentPath.lineTo(x.toFloat(), y.toFloat())
        val pathCopy = Path().addPath(__currentPath)
        val paintCopy = __currentPaint.makeClone()
        __commands.add { canvas ->
            canvas.drawPath(pathCopy, paintCopy)
        }
        __dirty = true
    }

    fun moveTo(x: Double, y: Double) {
        __currentPath.moveTo(x.toFloat(), y.toFloat())
    }

    fun curveTo(controlX: Double, controlY: Double, anchorX: Double, anchorY: Double) {
        __currentPath.quadTo(controlX.toFloat(), controlY.toFloat(), anchorX.toFloat(), anchorY.toFloat())
        val pathCopy = Path().addPath(__currentPath)
        val paintCopy = __currentPaint.makeClone()
        __commands.add { canvas ->
            canvas.drawPath(pathCopy, paintCopy)
        }
        __dirty = true
    }

    internal fun __draw(canvas: Canvas) {
        for (command in __commands) {
            command(canvas)
        }
    }
}
