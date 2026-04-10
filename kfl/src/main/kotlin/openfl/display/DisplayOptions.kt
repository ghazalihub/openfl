package openfl.display

class JPEGEncoderOptions(var quality: Int = 80)
class PNGEncoderOptions(var fastCompression: Boolean = false)

enum class PixelSnapping {
    ALWAYS,
    AUTO,
    NEVER
}
