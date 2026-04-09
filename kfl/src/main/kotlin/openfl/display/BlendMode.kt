package openfl.display

enum class BlendMode {
    ADD,
    ALPHA,
    DARKEN,
    DIFFERENCE,
    ERASE,
    HARDLIGHT,
    INVERT,
    LAYER,
    LIGHTEN,
    MULTIPLY,
    NORMAL,
    OVERLAY,
    SCREEN,
    SHADER,
    SUBTRACT;

    override fun toString(): String {
        return when (this) {
            ADD -> "add"
            ALPHA -> "alpha"
            DARKEN -> "darken"
            DIFFERENCE -> "difference"
            ERASE -> "erase"
            HARDLIGHT -> "hardlight"
            INVERT -> "invert"
            LAYER -> "layer"
            LIGHTEN -> "lighten"
            MULTIPLY -> "multiply"
            NORMAL -> "normal"
            OVERLAY -> "overlay"
            SCREEN -> "screen"
            SHADER -> "shader"
            SUBTRACT -> "subtract"
        }
    }

    companion object {
        fun fromString(value: String): BlendMode? {
            return when (value) {
                "add" -> ADD
                "alpha" -> ALPHA
                "darken" -> DARKEN
                "difference" -> DIFFERENCE
                "erase" -> ERASE
                "hardlight" -> HARDLIGHT
                "invert" -> INVERT
                "layer" -> LAYER
                "lighten" -> LIGHTEN
                "multiply" -> MULTIPLY
                "normal" -> NORMAL
                "overlay" -> OVERLAY
                "screen" -> SCREEN
                "shader" -> SHADER
                "subtract" -> SUBTRACT
                else -> null
            }
        }
    }
}
