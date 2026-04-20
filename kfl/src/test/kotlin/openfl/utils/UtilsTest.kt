package openfl.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertNotNull

class UtilsTest {
    @Test
    fun testObjectPool() {
        var count = 0
        val pool = ObjectPool<String>(create = { "item" + (count++) })

        val item1 = pool.get()
        assertEquals("item0", item1)
        assertEquals(1, pool.activeObjects)

        pool.release(item1!!)
        assertEquals(0, pool.activeObjects)
        assertEquals(1, pool.inactiveObjects)

        val item2 = pool.get()
        assertEquals("item0", item2)
        assertEquals(1, pool.activeObjects)
        assertEquals(0, pool.inactiveObjects)
    }

    @Test
    fun testTimer() {
        // Since we are in a unit test, we might want to avoid long waits
        // But let's check basic properties
        val timer = Timer(100.0, 5)
        assertEquals(100.0, timer.delay)
        assertEquals(5, timer.repeatCount)
        assertEquals(0, timer.currentCount)
    }
}
