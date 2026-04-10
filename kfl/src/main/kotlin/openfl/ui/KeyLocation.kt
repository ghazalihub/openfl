package openfl.ui

enum class KeyLocation(val value: Int) {
    STANDARD(0),
    LEFT(1),
    RIGHT(2),
    NUM_PAD(3);

    companion object {
        fun fromInt(value: Int): KeyLocation? = values().find { it.value == value }
    }
}
