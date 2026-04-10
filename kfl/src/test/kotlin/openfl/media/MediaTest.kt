package openfl.media

import kotlin.test.Test
import kotlin.test.assertEquals

class MediaTest {
    @Test
    fun testSoundTransform() {
        val st = SoundTransform(0.5, -1.0)
        assertEquals(0.5, st.volume)
        assertEquals(-1.0, st.pan)

        val st2 = st.clone()
        assertEquals(st.volume, st2.volume)
        assertEquals(st.pan, st2.pan)
    }
}
