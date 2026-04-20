package openfl.display

enum class InterpolationMethod {
    LINEAR_RGB,
    RGB;

    override fun toString(): String {
        return when (this) {
            LINEAR_RGB -> "linearRGB"
            RGB -> "rgb"
        }
    }

    companion object {
        fun fromString(value: String): InterpolationMethod? {
            return when (value) {
                "linearRGB" -> LINEAR_RGB
                "rgb" -> RGB
                else -> null
            }
        }
    }
}
