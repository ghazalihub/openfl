package openfl.display

enum class SpreadMethod {
    PAD,
    REFLECT,
    REPEAT;

    override fun toString(): String {
        return when (this) {
            PAD -> "pad"
            REFLECT -> "reflect"
            REPEAT -> "repeat"
        }
    }

    companion object {
        fun fromString(value: String): SpreadMethod? {
            return when (value) {
                "pad" -> PAD
                "reflect" -> REFLECT
                "repeat" -> REPEAT
                else -> null
            }
        }
    }
}
