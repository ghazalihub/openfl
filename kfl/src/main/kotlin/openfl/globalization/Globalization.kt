package openfl.globalization

class LocaleID(val name: String) {
    companion object {
        const val DEFAULT = "i-default"
    }
}

enum class LastOperationStatus {
    BUFFER_OVERFLOW_ERROR,
    ERROR_CODE_UNKNOWN,
    ILLEGAL_ARGUMENT_ERROR,
    INDEX_OUT_OF_BOUNDS_ERROR,
    INVALID_ATTR_VALUE,
    INVALID_FORMAT_ERROR,
    MEMORY_ALLOCATION_ERROR,
    NO_ERROR,
    NUMBER_PARSE_ERROR,
    PARSE_ERROR,
    TRUNCATED_CHAR_FOUND,
    UNEXPECTED_TOKEN,
    UNSUPPORTED_ERROR,
    USING_DEFAULT_WARNING,
    USING_FALLBACK_WARNING
}
