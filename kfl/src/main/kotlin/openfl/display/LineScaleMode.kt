package openfl.display

enum class LineScaleMode {
    HORIZONTAL,
    NONE,
    NORMAL,
    VERTICAL;

    companion object {
        fun fromString(value: String?): LineScaleMode = when (value?.lowercase()) {
            "horizontal" -> HORIZONTAL
            "none" -> NONE
            "normal" -> NORMAL
            "vertical" -> VERTICAL
            else -> NORMAL
        }
    }
}
