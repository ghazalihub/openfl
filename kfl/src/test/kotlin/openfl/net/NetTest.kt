package openfl.net

import kotlin.test.Test
import kotlin.test.assertEquals

class NetTest {
    @Test
    fun testURLRequest() {
        val request = URLRequest("https://example.com")
        assertEquals("https://example.com", request.url)
        assertEquals(URLRequestMethod.GET, request.method)

        request.method = URLRequestMethod.POST
        assertEquals("POST", request.method)
    }
}
