package openfl.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder

open class ByteArray(capacity: Int = 0) {
    private var __buffer: ByteBuffer = ByteBuffer.allocate(maxOf(capacity, 1)).order(ByteOrder.BIG_ENDIAN)

    var endian: Endian
        get() = if (__buffer.order() == ByteOrder.BIG_ENDIAN) Endian.BIG_ENDIAN else Endian.LITTLE_ENDIAN
        set(value) {
            __buffer.order(if (value == Endian.BIG_ENDIAN) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN)
        }

    val length: Int get() = __buffer.limit()
    var position: Int
        get() = __buffer.position()
        set(value) { __buffer.position(value) }

    val bytesAvailable: Int get() = __buffer.limit() - __buffer.position()

    fun writeByte(value: Int) = __ensureCapacity(1).also { __buffer.put(value.toByte()) }
    fun writeInt(value: Int) = __ensureCapacity(4).also { __buffer.putInt(value) }
    fun writeDouble(value: Double) = __ensureCapacity(8).also { __buffer.putDouble(value) }
    fun writeUTFBytes(value: String) {
        val bytes = value.toByteArray(Charsets.UTF_8)
        __ensureCapacity(bytes.size).also { __buffer.put(bytes) }
    }

    fun readByte(): Int = __buffer.get().toInt()
    fun readInt(): Int = __buffer.getInt()
    fun readDouble(): Double = __buffer.double
    fun readUTFBytes(length: Int): String {
        val bytes = kotlin.ByteArray(length)
        __buffer.get(bytes)
        return String(bytes, Charsets.UTF_8)
    }

    private fun __ensureCapacity(additional: Int) {
        if (__buffer.position() + additional > __buffer.capacity()) {
            val newCapacity = maxOf(__buffer.capacity() * 2, __buffer.position() + additional)
            val newBuffer = ByteBuffer.allocate(newCapacity).order(__buffer.order())
            val pos = __buffer.position()
            val limit = __buffer.limit()
            __buffer.position(0)
            __buffer.limit(limit)
            newBuffer.put(__buffer)
            newBuffer.position(pos)
            newBuffer.limit(maxOf(limit, pos + additional))
            __buffer = newBuffer
        } else if (__buffer.position() + additional > __buffer.limit()) {
            __buffer.limit(__buffer.position() + additional)
        }
    }

    fun toByteArray(): kotlin.ByteArray {
        val pos = __buffer.position()
        val limit = __buffer.limit()
        val arr = kotlin.ByteArray(limit)
        __buffer.position(0)
        __buffer.get(arr)
        __buffer.position(pos)
        return arr
    }

    companion object {
        fun fromByteArray(bytes: kotlin.ByteArray): openfl.utils.ByteArray {
            val ba = openfl.utils.ByteArray(bytes.size)
            ba.__buffer.put(bytes)
            ba.__buffer.position(0)
            ba.__buffer.limit(bytes.size)
            return ba
        }
    }
}
