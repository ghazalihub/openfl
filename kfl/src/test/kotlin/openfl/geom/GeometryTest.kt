package openfl.geom

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GeometryTest {
    @Test
    fun testPoint() {
        val p1 = Point(10.0, 20.0)
        val p2 = Point(5.0, 5.0)
        val p3 = p1.add(p2)
        assertEquals(15.0, p3.x)
        assertEquals(25.0, p3.y)
    }

    @Test
    fun testRectangle() {
        val rect = Rectangle(0.0, 0.0, 100.0, 100.0)
        assertTrue(rect.contains(50.0, 50.0))
        assertEquals(100.0, rect.right)
        assertEquals(100.0, rect.bottom)

        val rect2 = Rectangle(50.0, 50.0, 100.0, 100.0)
        val intersect = rect.intersection(rect2)
        assertEquals(50.0, intersect.x)
        assertEquals(50.0, intersect.y)
        assertEquals(50.0, intersect.width)
        assertEquals(50.0, intersect.height)
    }

    @Test
    fun testMatrix() {
        val matrix = Matrix()
        matrix.scale(2.0, 2.0)
        matrix.translate(10.0, 20.0)

        val p = Point(5.0, 5.0)
        val p2 = matrix.transformPoint(p)

        // (5 * 2) + 10 = 20
        // (5 * 2) + 20 = 30
        assertEquals(20.0, p2.x)
        assertEquals(30.0, p2.y)
    }
}
