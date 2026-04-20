package openfl.text

import openfl.display.DisplayObject
import openfl.display.InteractiveObject
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Font as SkiaFont
import org.jetbrains.skia.Paint
import org.jetbrains.skia.TextLine
import org.jetbrains.skia.Typeface
import org.jetbrains.skia.shaper.Shaper

class TextField : InteractiveObject() {
    var text: String = ""
        set(value) {
            field = value
            __dirty = true
            __setRenderDirty()
        }

    var defaultTextFormat: TextFormat? = null
        set(value) {
            field = value
            __dirty = true
            __setRenderDirty()
        }

    var autoSize: TextFieldAutoSize = TextFieldAutoSize.NONE
    var wordWrap: Boolean = false
    var multiline: Boolean = false

    private var __dirty = true
    private var __textLines = mutableListOf<TextLine>()
    private val __paint = Paint().apply { isAntiAlias = true }
    private val __skiaFont = SkiaFont(Typeface.makeDefault(), 12f)

    override fun __update(transformOnly: Boolean, updateChildren: Boolean) {
        if (__dirty) {
            __layout()
            __dirty = false
        }
        super.__update(transformOnly, updateChildren)
    }

    private fun __layout() {
        __textLines.clear()
        val format = defaultTextFormat
        if (format != null) {
            __skiaFont.size = (format.size ?: 12).toFloat()
            __paint.color = (format.color ?: 0x000000) or 0xFF000000.toInt()
        }

        if (text.isNotEmpty()) {
            if (multiline || wordWrap) {
                // Simplified multi-line
                val lines = text.split("\n")
                for (line in lines) {
                    __textLines.add(TextLine.make(line, __skiaFont))
                }
            } else {
                __textLines.add(TextLine.make(text, __skiaFont))
            }
        }
    }

    internal fun __draw(canvas: Canvas) {
        var yOffset = __skiaFont.metrics.capHeight
        for (line in __textLines) {
            canvas.drawTextLine(line, 0f, yOffset, __paint)
            yOffset += __skiaFont.metrics.height
        }
    }
}

enum class TextFieldAutoSize {
    CENTER,
    LEFT,
    NONE,
    RIGHT
}
