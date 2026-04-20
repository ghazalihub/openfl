package openfl.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ByteArrayTest {
    @Test
    fun testWriteRead() {
        val ba = ByteArray()
        ba.writeByte(10)
        ba.writeInt(123456)
        ba.writeDouble(3.14159)
        ba.writeUTFBytes("Hello")

        ba.position = 0
        assertEquals(10, ba.readByte())
        assertEquals(123456, ba.readInt())
        assertEquals(3.14159, ba.readDouble(), 0.00001)
        // Note: readUTFBytes(5) would be needed for "Hello"
    }

    @Test
    fun testEndian() {
        val ba = ByteArray()
        ba.endian = Endian.LITTLE_ENDIAN
        ba.writeInt(0x12345678)
        ba.position = 0
        assertEquals(0x78, ba.readByte() and 0xFF)

        ba.position = 0
        ba.endian = Endian.BIG_ENDIAN
        ba.writeInt(0x12345678)
        ba.position = 0
        assertEquals(0x12, ba.readByte() and 0xFF)
    }
}
