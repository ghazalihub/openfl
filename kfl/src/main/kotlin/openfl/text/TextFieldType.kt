package openfl.text

enum class TextFieldType {
    DYNAMIC,
    INPUT;

    override fun toString(): String {
        return when (this) {
            DYNAMIC -> "dynamic"
            INPUT -> "input"
        }
    }

    companion object {
        fun fromString(value: String): TextFieldType? {
            return when (value) {
                "dynamic" -> DYNAMIC
                "input" -> INPUT
                else -> null
            }
        }
    }
}
