package openfl.text

class TextFormat(
    var font: String? = null,
    var size: Int? = null,
    var color: Int? = null,
    var bold: Boolean? = null,
    var italic: Boolean? = null,
    var underline: Boolean? = null,
    var url: String? = null,
    var target: String? = null,
    var align: TextFormatAlign? = null,
    var leftMargin: Int? = null,
    var rightMargin: Int? = null,
    var indent: Int? = null,
    var leading: Int? = null
) {
    var blockIndent: Int? = null
    var bullet: Boolean? = null
    var kerning: Boolean? = null
    var letterSpacing: Double? = null
    var tabStops: Array<Int>? = null
    var strikethrough: Boolean? = null

    fun clone(): TextFormat {
        val newFormat = TextFormat(font, size, color, bold, italic, underline, url, target, align, leftMargin, rightMargin, indent, leading)
        newFormat.blockIndent = blockIndent
        newFormat.bullet = bullet
        newFormat.kerning = kerning
        newFormat.letterSpacing = letterSpacing
        newFormat.tabStops = tabStops
        newFormat.strikethrough = strikethrough
        return newFormat
    }

    internal fun __merge(format: TextFormat) {
        if (format.font != null) font = format.font
        if (format.size != null) size = format.size
        if (format.color != null) color = format.color
        if (format.bold != null) bold = format.bold
        if (format.italic != null) italic = format.italic
        if (format.underline != null) underline = format.underline
        if (format.url != null) url = format.url
        if (format.target != null) target = format.target
        if (format.align != null) align = format.align
        if (format.leftMargin != null) leftMargin = format.leftMargin
        if (format.rightMargin != null) rightMargin = format.rightMargin
        if (format.indent != null) indent = format.indent
        if (format.leading != null) leading = format.leading
        if (format.blockIndent != null) blockIndent = format.blockIndent
        if (format.bullet != null) bullet = format.bullet
        if (format.kerning != null) kerning = format.kerning
        if (format.letterSpacing != null) letterSpacing = format.letterSpacing
        if (format.tabStops != null) tabStops = format.tabStops
        if (format.strikethrough != null) strikethrough = format.strikethrough
    }
}
