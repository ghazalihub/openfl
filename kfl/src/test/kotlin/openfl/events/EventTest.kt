package openfl.events

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class EventTest {
    @Test
    fun testEvent() {
        val event = Event("test", true, true)
        assertEquals("test", event.type)
        assertTrue(event.bubbles)
        assertTrue(event.cancelable)
    }

    @Test
    fun testEventDispatcher() {
        val dispatcher = EventDispatcher()
        var called = false
        val listener = { event: Event ->
            called = true
            assertEquals("test", event.type)
        }

        dispatcher.addEventListener("test", listener)
        assertTrue(dispatcher.hasEventListener("test"))

        dispatcher.dispatchEvent(Event("test"))
        assertTrue(called)

        called = false
        dispatcher.removeEventListener("test", listener)
        assertFalse(dispatcher.hasEventListener("test"))
        dispatcher.dispatchEvent(Event("test"))
        assertFalse(called)
    }

    @Test
    fun testPriority() {
        val dispatcher = EventDispatcher()
        val order = mutableListOf<Int>()

        dispatcher.addEventListener("test", { order.add(1) }, priority = 1)
        dispatcher.addEventListener("test", { order.add(3) }, priority = 3)
        dispatcher.addEventListener("test", { order.add(2) }, priority = 2)

        dispatcher.dispatchEvent(Event("test"))
        assertEquals(listOf(3, 2, 1), order)
    }
}
