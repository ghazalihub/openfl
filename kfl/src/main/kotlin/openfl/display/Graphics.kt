package openfl.display

import openfl.geom.Matrix
import openfl.geom.Rectangle
import org.jetbrains.skia.*

class Graphics(internal val __owner: DisplayObject) {

    private val __commands = mutableListOf<(Canvas) -> Unit>()
    private var __visible = false
    private var __dirty = true

    private var __fillPaint: Paint? = null
    private var __strokePaint: Paint? = null
    private var __currentPath = Path()

    fun beginBitmapFill(bitmap: BitmapData, matrix: Matrix? = null, repeat: Boolean = true, smooth: Boolean = false) {
        val shader = Shader.makeWithColorFilter(
            org.jetbrains.skia.Image.makeFromBitmap(bitmap.__skiaBitmap).makeShader(
                if (repeat) FilterTileMode.REPEAT else FilterTileMode.CLAMP,
                if (repeat) FilterTileMode.REPEAT else FilterTileMode.CLAMP,
                SamplingMode.DEFAULT,
                matrix?.let { m ->
                    org.jetbrains.skia.Matrix33(
                        m.a.toFloat(), m.c.toFloat(), m.tx.toFloat(),
                        m.b.toFloat(), m.d.toFloat(), m.ty.toFloat(),
                        0f, 0f, 1f
                    )
                }
            ),
            null
        )
        __fillPaint = Paint().apply {
            isAntiAlias = true
            mode = PaintMode.FILL
            this.shader = shader
        }
        __visible = true
    }

    fun beginFill(color: Int, alpha: Double = 1.0) {
        val argb = ((alpha * 255).toInt() shl 24) or (color and 0xFFFFFF)
        __fillPaint = Paint().apply {
            isAntiAlias = true
            mode = PaintMode.FILL
            this.color = argb
        }
        __visible = true
    }

    fun beginGradientFill(
        type: GradientType,
        colors: Array<Int>,
        alphas: Array<Double>,
        ratios: Array<Int>,
        matrix: Matrix? = null,
        spreadMethod: SpreadMethod = SpreadMethod.PAD,
        interpolationMethod: InterpolationMethod = InterpolationMethod.RGB,
        focalPointRatio: Double = 0.0
    ) {
        val skiaColors = IntArray(colors.size) { i ->
            ((alphas[i] * 255).toInt() shl 24) or (colors[i] and 0xFFFFFF)
        }
        val skiaRatios = FloatArray(ratios.size) { i -> ratios[i].toFloat() / 255f }

        val localMatrix = matrix?.let { m ->
            org.jetbrains.skia.Matrix33(
                m.a.toFloat(), m.c.toFloat(), m.tx.toFloat(),
                m.b.toFloat(), m.d.toFloat(), m.ty.toFloat(),
                0f, 0f, 1f
            )
        }

        val shader = when (type) {
            GradientType.LINEAR -> {
                org.jetbrains.skia.Shader.makeLinearGradient(
                    -819.2f, 0f, 819.2f, 0f,
                    skiaColors, skiaRatios,
                    when (spreadMethod) {
                        SpreadMethod.PAD -> FilterTileMode.CLAMP
                        SpreadMethod.REFLECT -> FilterTileMode.MIRROR
                        SpreadMethod.REPEAT -> FilterTileMode.REPEAT
                    },
                    0,
                    localMatrix
                )
            }
            GradientType.RADIAL -> {
                org.jetbrains.skia.Shader.makeRadialGradient(
                    0f, 0f, 819.2f,
                    skiaColors, skiaRatios,
                    when (spreadMethod) {
                        SpreadMethod.PAD -> FilterTileMode.CLAMP
                        SpreadMethod.REFLECT -> FilterTileMode.MIRROR
                        SpreadMethod.REPEAT -> FilterTileMode.REPEAT
                    },
                    0,
                    localMatrix
                )
            }
        }

        __fillPaint = Paint().apply {
            isAntiAlias = true
            mode = PaintMode.FILL
            this.shader = shader
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
        val fill = __fillPaint?.makeClone()
        val stroke = __strokePaint?.makeClone()
        __commands.add { canvas ->
            fill?.let { canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), it) }
            stroke?.let { canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), it) }
        }
        __dirty = true
        __visible = true
    }

    fun drawEllipse(x: Double, y: Double, width: Double, height: Double) {
        val rect = Rect.makeXYWH(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
        val fill = __fillPaint?.makeClone()
        val stroke = __strokePaint?.makeClone()
        __commands.add { canvas ->
            fill?.let { canvas.drawOval(rect, it) }
            stroke?.let { canvas.drawOval(rect, it) }
        }
        __dirty = true
        __visible = true
    }

    fun drawRect(x: Double, y: Double, width: Double, height: Double) {
        val rect = Rect.makeXYWH(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
        val fill = __fillPaint?.makeClone()
        val stroke = __strokePaint?.makeClone()
        __commands.add { canvas ->
            fill?.let { canvas.drawRect(rect, it) }
            stroke?.let { canvas.drawRect(rect, it) }
        }
        __dirty = true
        __visible = true
    }

    fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, ellipseWidth: Double, ellipseHeight: Double? = null) {
        val eh = ellipseHeight ?: ellipseWidth
        val rect = RRect.makeXYWH(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), ellipseWidth.toFloat(), eh.toFloat())
        val fill = __fillPaint?.makeClone()
        val stroke = __strokePaint?.makeClone()
        __commands.add { canvas ->
            fill?.let { canvas.drawRRect(rect, it) }
            stroke?.let { canvas.drawRRect(rect, it) }
        }
        __dirty = true
        __visible = true
    }

    fun endFill() {
        __fillPaint = null
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
            __strokePaint = null
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

        __strokePaint = Paint().apply {
            isAntiAlias = true
            mode = PaintMode.STROKE
            this.color = argb
            strokeWidth = thickness.toFloat()
            strokeCap = skiaCaps
            strokeJoin = skiaJoints
            strokeMiter = miterLimit.toFloat()
        }
        __visible = true
    }

    fun lineTo(x: Double, y: Double) {
        __currentPath.lineTo(x.toFloat(), y.toFloat())
        val pathCopy = Path().addPath(__currentPath)
        val fill = __fillPaint?.makeClone()
        val stroke = __strokePaint?.makeClone()
        __commands.add { canvas ->
            fill?.let { canvas.drawPath(pathCopy, it) }
            stroke?.let { canvas.drawPath(pathCopy, it) }
        }
        __dirty = true
    }

    fun moveTo(x: Double, y: Double) {
        __currentPath.moveTo(x.toFloat(), y.toFloat())
    }

    fun curveTo(controlX: Double, controlY: Double, anchorX: Double, anchorY: Double) {
        __currentPath.quadTo(controlX.toFloat(), controlY.toFloat(), anchorX.toFloat(), anchorY.toFloat())
        val pathCopy = Path().addPath(__currentPath)
        val fill = __fillPaint?.makeClone()
        val stroke = __strokePaint?.makeClone()
        __commands.add { canvas ->
            fill?.let { canvas.drawPath(pathCopy, it) }
            stroke?.let { canvas.drawPath(pathCopy, it) }
        }
        __dirty = true
    }

    fun cubicCurveTo(controlX1: Double, controlY1: Double, controlX2: Double, controlY2: Double, anchorX: Double, anchorY: Double) {
        __currentPath.cubicTo(controlX1.toFloat(), controlY1.toFloat(), controlX2.toFloat(), controlY2.toFloat(), anchorX.toFloat(), anchorY.toFloat())
        val pathCopy = Path().addPath(__currentPath)
        val fill = __fillPaint?.makeClone()
        val stroke = __strokePaint?.makeClone()
        __commands.add { canvas ->
            fill?.let { canvas.drawPath(pathCopy, it) }
            stroke?.let { canvas.drawPath(pathCopy, it) }
        }
        __dirty = true
    }

    internal fun __draw(canvas: Canvas) {
        for (command in __commands) {
            command(canvas)
        }
    }
}
