package openfl.text

enum class TextFormatAlign {
    CENTER,
    END,
    JUSTIFY,
    LEFT,
    RIGHT,
    START;

    override fun toString(): String {
        return when (this) {
            CENTER -> "center"
            END -> "end"
            JUSTIFY -> "justify"
            LEFT -> "left"
            RIGHT -> "right"
            START -> "start"
        }
    }

    companion object {
        fun fromString(value: String): TextFormatAlign? {
            return when (value) {
                "center" -> CENTER
                "end" -> END
                "justify" -> JUSTIFY
                "left" -> LEFT
                "right" -> RIGHT
                "start" -> START
                else -> null
            }
        }
    }
}
