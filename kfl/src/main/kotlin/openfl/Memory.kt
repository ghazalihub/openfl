package openfl

import openfl.utils.ByteArray

object Memory {
    private var __byteArray: ByteArray? = null

    fun select(ba: ByteArray?) {
        __byteArray = ba
    }

    fun getByte(addr: Int): Int {
        val pos = __byteArray?.position ?: 0
        __byteArray?.position = addr
        val result = __byteArray?.readByte() ?: 0
        __byteArray?.position = pos
        return result
    }

    fun setByte(addr: Int, v: Int) {
        val pos = __byteArray?.position ?: 0
        __byteArray?.position = addr
        __byteArray?.writeByte(v)
        __byteArray?.position = pos
    }

    fun getInt(addr: Int): Int {
        val pos = __byteArray?.position ?: 0
        __byteArray?.position = addr
        val result = __byteArray?.readInt() ?: 0
        __byteArray?.position = pos
        return result
    }

    fun setInt(addr: Int, v: Int) {
        val pos = __byteArray?.position ?: 0
        __byteArray?.position = addr
        __byteArray?.writeInt(v)
        __byteArray?.position = pos
    }

    fun getDouble(addr: Int): Double {
        val pos = __byteArray?.position ?: 0
        __byteArray?.position = addr
        val result = __byteArray?.readDouble() ?: 0.0
        __byteArray?.position = pos
        return result
    }

    fun setDouble(addr: Int, v: Double) {
        val pos = __byteArray?.position ?: 0
        __byteArray?.position = addr
        __byteArray?.writeDouble(v)
        __byteArray?.position = pos
    }
}
