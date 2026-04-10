package openfl.display

enum class JointStyle {
    BEVEL,
    MITER,
    ROUND;

    companion object {
        fun fromString(value: String?): JointStyle = when (value?.lowercase()) {
            "bevel" -> BEVEL
            "miter" -> MITER
            "round" -> ROUND
            else -> ROUND
        }
    }
}
