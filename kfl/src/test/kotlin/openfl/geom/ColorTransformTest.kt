package openfl.geom

import kotlin.test.Test
import kotlin.test.assertEquals

class ColorTransformTest {
    @Test
    fun testColorTransform() {
        val ct = ColorTransform()
        ct.color = 0xFF0000
        assertEquals(0xFF0000, ct.color)
        assertEquals(0.0, ct.redMultiplier)
        assertEquals(255.0, ct.redOffset)

        val ct2 = ColorTransform(redMultiplier = 0.5, redOffset = 10.0)
        ct.concat(ct2)
        // second.redOffset * redMultiplier + redOffset
        // 10.0 * 0.0 + 255.0 = 255.0
        assertEquals(255.0, ct.redOffset)
        assertEquals(0.0, ct.redMultiplier)
    }
}
