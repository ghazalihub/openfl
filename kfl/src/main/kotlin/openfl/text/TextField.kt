package openfl.text

import openfl.display.InteractiveObject
import openfl.events.Event
import openfl.geom.Matrix
import openfl.geom.Rectangle

class TextField : InteractiveObject() {

    var autoSize: String = "none" // Simplified for now
    var background: Boolean = false
    var backgroundColor: Int = 0xFFFFFF
    var border: Boolean = false
    var borderColor: Int = 0x000000

    var defaultTextFormat: TextFormat
        get() = __textFormat.clone()
        set(value) {
            __textFormat.__merge(value)
            __setRenderDirty()
        }

    var displayAsPassword: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                __setRenderDirty()
            }
        }

    var embedFonts: Boolean = false

    var htmlText: String
        get() = if (__isHTML) __htmlText else __text
        set(value) {
            __isHTML = true
            __htmlText = value
            __text = value // Simplified: Should parse HTML
            __setRenderDirty()
        }

    val length: Int
        get() = __text.length

    var maxChars: Int = 0
    var multiline: Boolean = false
    var selectable: Boolean = true

    var text: String
        get() = __text
        set(value) {
            __isHTML = false
            __text = value
            __setRenderDirty()
        }

    var textColor: Int
        get() = __textFormat.color ?: 0
        set(value) {
            __textFormat.color = value
            __setRenderDirty()
        }

    var type: TextFieldType = TextFieldType.DYNAMIC
    var wordWrap: Boolean = false

    private var __text: String = ""
    private var __htmlText: String = ""
    private var __isHTML: Boolean = false
    private var __textFormat: TextFormat = TextFormat("Times New Roman", 12, 0x000000)
    private var __width: Double = 100.0
    private var __height: Double = 100.0

    init {
        __drawableType = openfl.display._internal.IBitmapDrawableType.TEXT_FIELD
        __width = 100.0
        __height = 100.0
    }

    fun appendText(newText: String) {
        __text += newText
        __setRenderDirty()
    }

    fun getTextFormat(beginIndex: Int = -1, endIndex: Int = -1): TextFormat {
        return __textFormat.clone() // Simplified
    }

    fun setTextFormat(format: TextFormat, beginIndex: Int = -1, endIndex: Int = -1) {
        __textFormat.__merge(format)
        __setRenderDirty()
    }

    override fun __getBounds(rect: Rectangle, matrix: Matrix) {
        val bounds = Rectangle(0.0, 0.0, __width, __height)
        // matrix.transformRect(bounds) // Needs matrix transform implementation
        rect.union(bounds)
    }
}
