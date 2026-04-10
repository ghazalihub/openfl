package openfl.display

import openfl.display._internal.IBitmapDrawableType
import openfl.geom.ColorTransform
import openfl.geom.Matrix
import openfl.geom.Point
import openfl.geom.Rectangle
import org.jetbrains.skia.Bitmap as SkiaBitmap
import org.jetbrains.skia.*

open class BitmapData(
    val width: Int,
    val height: Int,
    val transparent: Boolean = true,
    fillColor: Long = 0xFFFFFFFFL
) : IBitmapDrawable {

    val rect: Rectangle = Rectangle(0.0, 0.0, width.toDouble(), height.toDouble())

    internal var __skiaBitmap: SkiaBitmap = SkiaBitmap()

    override var __blendMode: BlendMode = BlendMode.NORMAL
    override var __drawableType: IBitmapDrawableType = IBitmapDrawableType.BITMAP_DATA
    override var __isMask: Boolean = false
    override var __renderable: Boolean = true
    override var __renderTransform: Matrix = Matrix()
    override var __transform: Matrix = Matrix()
    override var __worldAlpha: Double = 1.0
    override var __worldTransform: Matrix = Matrix()
    override var __mask: DisplayObject? = null
    override var __scrollRect: Rectangle? = null

    init {
        if (width > 0 && height > 0) {
            val colorInfo = ColorInfo(
                ColorType.N32,
                if (transparent) ColorAlphaType.PREMUL else ColorAlphaType.OPAQUE,
                ColorSpace.sRGB
            )
            __skiaBitmap.allocPixels(ImageInfo(colorInfo, width, height))

            val canvas = Canvas(__skiaBitmap)
            val fill = if (transparent) fillColor else (0xFF000000L or (fillColor and 0xFFFFFFL))
            canvas.clear(fill.toInt())
        }
    }

    fun dispose() {
        __skiaBitmap.close()
    }

    fun fillRect(rect: Rectangle, color: Int) {
        val canvas = Canvas(__skiaBitmap)
        canvas.drawRect(Rect.makeXYWH(rect.x.toFloat(), rect.y.toFloat(), rect.width.toFloat(), rect.height.toFloat()), Paint().apply { this.color = color })
    }

    fun copyPixels(
        sourceBitmapData: BitmapData,
        sourceRect: Rectangle,
        destPoint: Point,
        alphaBitmapData: BitmapData? = null,
        alphaPoint: Point? = null,
        mergeAlpha: Boolean = false
    ) {
        val canvas = Canvas(__skiaBitmap)
        val image = Image.makeFromBitmap(sourceBitmapData.__skiaBitmap)
        canvas.drawImageRect(
            image,
            Rect.makeXYWH(sourceRect.x.toFloat(), sourceRect.y.toFloat(), sourceRect.width.toFloat(), sourceRect.height.toFloat()),
            Rect.makeXYWH(destPoint.x.toFloat(), destPoint.y.toFloat(), sourceRect.width.toFloat(), sourceRect.height.toFloat())
        )
    }

    fun draw(
        source: IBitmapDrawable,
        matrix: Matrix? = null,
        colorTransform: ColorTransform? = null,
        blendMode: BlendMode? = null,
        clipRect: Rectangle? = null,
        smoothing: Boolean = false
    ) {
        val canvas = Canvas(__skiaBitmap)
        canvas.save()

        if (clipRect != null) {
            canvas.clipRect(Rect.makeXYWH(clipRect.x.toFloat(), clipRect.y.toFloat(), clipRect.width.toFloat(), clipRect.height.toFloat()))
        }

        if (matrix != null) {
            val skiaMatrix = Matrix33(
                matrix.a.toFloat(), matrix.c.toFloat(), matrix.tx.toFloat(),
                matrix.b.toFloat(), matrix.d.toFloat(), matrix.ty.toFloat(),
                0f, 0f, 1f
            )
            canvas.concat(skiaMatrix)
        }

        when (source) {
            is BitmapData -> {
                val image = Image.makeFromBitmap(source.__skiaBitmap)
                canvas.drawImage(image, 0f, 0f)
            }
            is DisplayObject -> {
                __renderDisplayObject(source, canvas)
            }
        }
        canvas.restore()
    }

    private fun __renderDisplayObject(displayObject: DisplayObject, canvas: Canvas) {
        if (!displayObject.visible || displayObject.alpha <= 0) return

        canvas.save()

        val matrix = displayObject.__transform
        val skiaMatrix = Matrix33(
            matrix.a.toFloat(), matrix.c.toFloat(), matrix.tx.toFloat(),
            matrix.b.toFloat(), matrix.d.toFloat(), matrix.ty.toFloat(),
            0f, 0f, 1f
        )
        canvas.concat(skiaMatrix)

        // Render graphics
        when (displayObject) {
            is Sprite -> displayObject.graphics.__draw(canvas)
            is Shape -> displayObject.graphics.__draw(canvas)
            is Bitmap -> {
                displayObject.bitmapData?.let { bmd ->
                    canvas.drawImage(Image.makeFromBitmap(bmd.__skiaBitmap), 0f, 0f)
                }
            }
        }

        // Render children
        if (displayObject is DisplayObjectContainer) {
            for (child in displayObject.__children) {
                __renderDisplayObject(child, canvas)
            }
        }

        canvas.restore()
    }

    fun getPixel(x: Int, y: Int): Int {
        return __skiaBitmap.getColor(x, y) and 0xFFFFFF
    }

    fun getPixel32(x: Int, y: Int): Int {
        return __skiaBitmap.getColor(x, y)
    }

    fun setPixel(x: Int, y: Int, color: Int) {
        val alpha = if (transparent) (getPixel32(x, y) shr 24) and 0xFF else 0xFF
        val argb = (alpha shl 24) or (color and 0xFFFFFF)
        __skiaBitmap.erase(argb, Rectangle(x.toDouble(), y.toDouble(), 1.0, 1.0).toIntRect())
    }

    fun setPixel32(x: Int, y: Int, color: Int) {
        __skiaBitmap.erase(color, Rectangle(x.toDouble(), y.toDouble(), 1.0, 1.0).toIntRect())
    }

    override fun __getBounds(rect: Rectangle, matrix: Matrix) {
        val bounds = Rectangle(0.0, 0.0, width.toDouble(), height.toDouble())
        // bounds.__transform(bounds, matrix)
        rect.union(bounds)
    }

    override fun __update(transformOnly: Boolean, updateChildren: Boolean) {}

    override fun __updateTransforms(overrideTransform: Matrix?) {
        if (overrideTransform == null) {
            __worldTransform.identity()
        } else {
            __worldTransform.copyFrom(overrideTransform)
        }
        __renderTransform.copyFrom(__worldTransform)
    }

    private fun Rectangle.toIntRect(): IRect {
        return IRect.makeXYWH(x.toInt(), y.toInt(), width.toInt(), height.toInt())
    }
}
