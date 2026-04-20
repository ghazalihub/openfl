package openfl.display

enum class CapsStyle {
    NONE,
    ROUND,
    SQUARE;

    companion object {
        fun fromString(value: String?): CapsStyle = when (value?.lowercase()) {
            "none" -> NONE
            "round" -> ROUND
            "square" -> SQUARE
            else -> ROUND
        }
    }
}
