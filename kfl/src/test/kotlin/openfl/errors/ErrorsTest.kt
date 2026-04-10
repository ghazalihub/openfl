package openfl.errors

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class ErrorsTest {
    @Test
    fun testErrors() {
        val error = Error("message", 123)
        assertTrue(error is Exception)
        assertTrue(error.message == "message")
        assertTrue(error.errorID == 123)

        assertFailsWith<ArgumentError> {
            throw ArgumentError("bad arg")
        }
    }
}
