package openfl.system

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertNotNull

class SystemTest {
    @Test
    fun testCapabilities() {
        assertNotNull(Capabilities.os)
        assertNotNull(Capabilities.language)
        assertEquals("kfl", Capabilities.manufacturer)
    }

    @Test
    fun testSystem() {
        assertTrue(System.totalMemory > 0)
    }

    private fun assertEquals(expected: String, actual: String) {
        kotlin.test.assertEquals(expected, actual)
    }
}
