package openfl.geom

class ColorTransform(
    var redMultiplier: Double = 1.0,
    var greenMultiplier: Double = 1.0,
    var blueMultiplier: Double = 1.0,
    var alphaMultiplier: Double = 1.0,
    var redOffset: Double = 0.0,
    var greenOffset: Double = 0.0,
    var blueOffset: Double = 0.0,
    var alphaOffset: Double = 0.0
) {
    var color: Int
        get() = ((redOffset.toInt() shl 16) or (greenOffset.toInt() shl 8) or blueOffset.toInt())
        set(value) {
            redOffset = ((value shr 16) and 0xFF).toDouble()
            greenOffset = ((value shr 8) and 0xFF).toDouble()
            blueOffset = (value and 0xFF).toDouble()
            redMultiplier = 0.0
            greenMultiplier = 0.0
            blueMultiplier = 0.0
        }

    fun concat(second: ColorTransform) {
        redOffset += second.redOffset * redMultiplier
        greenOffset += second.greenOffset * greenMultiplier
        blueOffset += second.blueOffset * blueMultiplier
        alphaOffset += second.alphaOffset * alphaMultiplier

        redMultiplier *= second.redMultiplier
        greenMultiplier *= second.greenMultiplier
        blueMultiplier *= second.blueMultiplier
        alphaMultiplier *= second.alphaMultiplier
    }

    override fun toString(): String {
        return "(redMultiplier=$redMultiplier, greenMultiplier=$greenMultiplier, blueMultiplier=$blueMultiplier, alphaMultiplier=$alphaMultiplier, redOffset=$redOffset, greenOffset=$greenOffset, blueOffset=$blueOffset, alphaOffset=$alphaOffset)"
    }

    internal fun __clone(): ColorTransform {
        return ColorTransform(redMultiplier, greenMultiplier, blueMultiplier, alphaMultiplier, redOffset, greenOffset, blueOffset, alphaOffset)
    }

    internal fun __copyFrom(ct: ColorTransform) {
        redMultiplier = ct.redMultiplier
        greenMultiplier = ct.greenMultiplier
        blueMultiplier = ct.blueMultiplier
        alphaMultiplier = ct.alphaMultiplier
        redOffset = ct.redOffset
        greenOffset = ct.greenOffset
        blueOffset = ct.blueOffset
        alphaOffset = ct.alphaOffset
    }

    internal fun __identity() {
        redMultiplier = 1.0
        greenMultiplier = 1.0
        blueMultiplier = 1.0
        alphaMultiplier = 1.0
        redOffset = 0.0
        greenOffset = 0.0
        blueOffset = 0.0
        alphaOffset = 0.0
    }

    internal fun __equals(ct: ColorTransform?, ignoreAlphaMultiplier: Boolean): Boolean {
        return (ct != null &&
                redMultiplier == ct.redMultiplier &&
                greenMultiplier == ct.greenMultiplier &&
                blueMultiplier == ct.blueMultiplier &&
                (ignoreAlphaMultiplier || alphaMultiplier == ct.alphaMultiplier) &&
                redOffset == ct.redOffset &&
                greenOffset == ct.greenOffset &&
                blueOffset == ct.blueOffset &&
                alphaOffset == ct.alphaOffset)
    }
}
