package openfl.media

class SoundTransform(
    var volume: Double = 1.0,
    var pan: Double = 0.0
) {
    var leftToLeft: Double = 1.0
    var leftToRight: Double = 0.0
    var rightToLeft: Double = 0.0
    var rightToRight: Double = 1.0

    fun clone(): SoundTransform {
        val st = SoundTransform(volume, pan)
        st.leftToLeft = leftToLeft
        st.leftToRight = leftToRight
        st.rightToLeft = rightToLeft
        st.rightToRight = rightToRight
        return st
    }
}
