package openfl.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder

open class ByteArray(capacity: Int = 0) : IDataInput, IDataOutput {
    private var __buffer: ByteBuffer = ByteBuffer.allocate(maxOf(capacity, 1)).order(ByteOrder.BIG_ENDIAN)

    override var endian: Endian
        get() = if (__buffer.order() == ByteOrder.BIG_ENDIAN) Endian.BIG_ENDIAN else Endian.LITTLE_ENDIAN
        set(value) {
            __buffer.order(if (value == Endian.BIG_ENDIAN) ByteOrder.BIG_ENDIAN else ByteOrder.LITTLE_ENDIAN)
        }

    override var objectEncoding: Int = 3

    val length: Int get() = __buffer.limit()
    var position: Int
        get() = __buffer.position()
        set(value) { __buffer.position(value) }

    override val bytesAvailable: Int get() = __buffer.limit() - __buffer.position()

    override fun writeBoolean(value: Boolean) = writeByte(if (value) 1 else 0)
    override fun writeByte(value: Int) = __ensureCapacity(1).also { __buffer.put(value.toByte()) }
    override fun writeBytes(bytes: ByteArray, offset: Int, length: Int) {
        val len = if (length == 0) bytes.length - offset else length
        __ensureCapacity(len)
        __buffer.put(bytes.toByteArray(), offset, len)
    }
    override fun writeDouble(value: Double) = __ensureCapacity(8).also { __buffer.putDouble(value) }
    override fun writeFloat(value: Float) = __ensureCapacity(4).also { __buffer.putFloat(value) }
    override fun writeInt(value: Int) = __ensureCapacity(4).also { __buffer.putInt(value) }
    override fun writeMultiByte(value: String, charSet: String) {
        val bytes = value.toByteArray(Charsets.forName(charSet))
        __ensureCapacity(bytes.size).also { __buffer.put(bytes) }
    }
    override fun writeObject(value: Any?) {
        // Simple serialization placeholder for desktop
    }
    override fun writeShort(value: Int) = __ensureCapacity(2).also { __buffer.putShort(value.toShort()) }
    override fun writeUnsignedInt(value: Long) = __ensureCapacity(4).also { __buffer.putInt(value.toInt()) }
    override fun writeUTF(value: String) {
        val bytes = value.toByteArray(Charsets.UTF_8)
        writeShort(bytes.size)
        __ensureCapacity(bytes.size).also { __buffer.put(bytes) }
    }
    override fun writeUTFBytes(value: String) {
        val bytes = value.toByteArray(Charsets.UTF_8)
        __ensureCapacity(bytes.size).also { __buffer.put(bytes) }
    }

    override fun readBoolean(): Boolean = readByte() != 0
    override fun readByte(): Int = __buffer.get().toInt()
    override fun readBytes(bytes: ByteArray, offset: Int, length: Int) {
        val len = if (length == 0) bytesAvailable else length
        val buffer = kotlin.ByteArray(len)
        __buffer.get(buffer)
        val oldPos = bytes.position
        bytes.position = offset
        bytes.writeBytes(ByteArray.fromByteArray(buffer), 0, len)
        bytes.position = oldPos
    }
    override fun readDouble(): Double = __buffer.double
    override fun readFloat(): Float = __buffer.float
    override fun readInt(): Int = __buffer.getInt()
    override fun readMultiByte(length: Int, charSet: String): String {
        val bytes = kotlin.ByteArray(length)
        __buffer.get(bytes)
        return String(bytes, Charsets.forName(charSet))
    }
    override fun readObject(): Any? = null
    override fun readShort(): Int = __buffer.short.toInt()
    override fun readUnsignedByte(): Int = __buffer.get().toInt() and 0xFF
    override fun readUnsignedInt(): Long = __buffer.int.toLong() and 0xFFFFFFFFL
    override fun readUnsignedShort(): Int = __buffer.short.toInt() and 0xFFFF
    override fun readUTF(): String {
        val len = readUnsignedShort()
        return readUTFBytes(len)
    }
    override fun readUTFBytes(length: Int): String {
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
