package openfl.utils

enum class Endian {
    BIG_ENDIAN,
    LITTLE_ENDIAN;

    override fun toString(): String {
        return when (this) {
            BIG_ENDIAN -> "bigEndian"
            LITTLE_ENDIAN -> "littleEndian"
        }
    }

    companion object {
        fun fromString(value: String): Endian? {
            return when (value) {
                "bigEndian" -> BIG_ENDIAN
                "littleEndian" -> LITTLE_ENDIAN
                else -> null
            }
        }
    }
}
