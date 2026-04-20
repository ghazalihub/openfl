package openfl.filters

enum class BitmapFilterType {
    FULL,
    INNER,
    OUTER;

    companion object {
        fun fromInt(value: Int): BitmapFilterType? = values().getOrNull(value)
    }
}
