package openfl.text

class TextLineMetrics(
    var ascent: Double = 0.0,
    var descent: Double = 0.0,
    var height: Double = 0.0,
    var leading: Double = 0.0,
    var width: Double = 0.0,
    var x: Double = 0.0
)

enum class AntiAliasType {
    ADVANCED,
    NORMAL
}

enum class GridFitType {
    NONE,
    PIXEL,
    SUBPIXEL
}

class Font(val name: String? = null) {
    var fontName: String? = name
    var fontStyle: FontStyle = FontStyle.REGULAR
    var fontType: FontType = FontType.EMBEDDED

    companion object {
        fun enumerateFonts(enumerateDeviceFonts: Boolean = false): Array<Font> {
            return emptyArray()
        }
    }
}

enum class FontStyle {
    BOLD,
    BOLD_ITALIC,
    ITALIC,
    REGULAR
}

enum class FontType {
    DEVICE,
    EMBEDDED,
    EMBEDDED_CFF
}
