package openfl.display

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class DisplayTest {
    @Test
    fun testDisplayObjectContainer() {
        val parent = Sprite()
        val child1 = Shape()
        val child2 = Shape()

        parent.addChild(child1)
        parent.addChildAt(child2, 0)

        assertEquals(2, parent.numChildren)
        assertEquals(child2, parent.getChildAt(0))
        assertEquals(child1, parent.getChildAt(1))
        assertTrue(parent.contains(child1))

        parent.removeChild(child1)
        assertEquals(1, parent.numChildren)
        assertFalse(parent.contains(child1))
        assertEquals(null, child1.parent)
    }

    @Test
    fun testTransforms() {
        val sprite = Sprite()
        sprite.x = 100.0
        sprite.y = 50.0
        sprite.scaleX = 2.0

        assertEquals(100.0, sprite.x)
        assertEquals(50.0, sprite.y)
        assertEquals(2.0, sprite.scaleX)

        // Internal matrix check
        val matrix = sprite.__transform
        assertEquals(2.0, matrix.a)
        assertEquals(100.0, matrix.tx)
        assertEquals(50.0, matrix.ty)
    }
}
