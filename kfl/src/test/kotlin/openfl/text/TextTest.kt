package openfl.text

import kotlin.test.Test
import kotlin.test.assertEquals

class TextTest {
    @Test
    fun testTextField() {
        val tf = TextField()
        tf.text = "Hello World"
        assertEquals("Hello World", tf.text)
        assertEquals(11, tf.length)

        tf.appendText("!")
        assertEquals("Hello World!", tf.text)
    }

    @Test
    fun testTextFormat() {
        val tf = TextField()
        val format = TextFormat(font = "Arial", size = 20, color = 0xFF0000)
        tf.defaultTextFormat = format

        assertEquals(0xFF0000, tf.textColor)
        assertEquals("Arial", tf.defaultTextFormat.font)
        assertEquals(20, tf.defaultTextFormat.size)
    }
}
