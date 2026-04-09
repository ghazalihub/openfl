package openfl.utils

enum class CompressionAlgorithm {
    DEFLATE,
    LZMA,
    ZLIB;

    override fun toString(): String {
        return when (this) {
            DEFLATE -> "deflate"
            LZMA -> "lzma"
            ZLIB -> "zlib"
        }
    }

    companion object {
        fun fromString(value: String): CompressionAlgorithm? {
            return when (value) {
                "deflate" -> DEFLATE
                "lzma" -> LZMA
                "zlib" -> ZLIB
                else -> null
            }
        }
    }
}
