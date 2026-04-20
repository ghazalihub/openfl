package openfl.display

enum class GradientType {
    LINEAR,
    RADIAL;

    override fun toString(): String {
        return when (this) {
            LINEAR -> "linear"
            RADIAL -> "radial"
        }
    }

    companion object {
        fun fromString(value: String): GradientType? {
            return when (value) {
                "linear" -> LINEAR
                "radial" -> RADIAL
                else -> null
            }
        }
    }
}
