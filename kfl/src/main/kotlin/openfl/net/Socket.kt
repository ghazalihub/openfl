package openfl.net

import openfl.events.EventDispatcher
import openfl.events.IOErrorEvent
import openfl.events.ProgressEvent
import openfl.events.Event
import openfl.utils.ByteArray
import openfl.utils.Endian
import java.io.*
import java.net.Socket as JSocket

class Socket(host: String? = null, port: Int = 0) : EventDispatcher() {
    var bytesAvailable: Int = 0
        get() = __inputStream?.available() ?: 0
        private set
    var connected: Boolean = false
        get() = __socket?.isConnected ?: false
        private set
    var endian: Endian = Endian.BIG_ENDIAN
    var timeout: Int = 0
        set(value) {
            field = value
            __socket?.soTimeout = value
        }

    private var __socket: JSocket? = null
    private var __inputStream: InputStream? = null
    private var __outputStream: OutputStream? = null

    init {
        if (host != null && port > 0) {
            connect(host, port)
        }
    }

    fun connect(host: String, port: Int) {
        kotlin.concurrent.thread {
            try {
                __socket = JSocket(host, port)
                __inputStream = __socket?.getInputStream()
                __outputStream = __socket?.getOutputStream()
                dispatchEvent(Event(Event.CONNECT))
            } catch (e: Exception) {
                dispatchEvent(IOErrorEvent(IOErrorEvent.IO_ERROR, false, false, e.message ?: "Unknown Error"))
            }
        }
    }

    fun close() {
        __socket?.close()
        __socket = null
        connected = false
        dispatchEvent(Event(Event.CLOSE))
    }

    fun flush() {
        __outputStream?.flush()
    }

    fun writeByte(value: Int) {
        __outputStream?.write(value)
    }

    fun writeBytes(bytes: ByteArray, offset: Int = 0, length: Int = 0) {
        val len = if (length == 0) bytes.length - offset else length
        __outputStream?.write(bytes.toByteArray(), offset, len)
    }

    fun writeUTFBytes(value: String) {
        __outputStream?.write(value.toByteArray(Charsets.UTF_8))
    }

    fun readByte(): Int {
        return __inputStream?.read() ?: 0
    }

    fun readBytes(bytes: ByteArray, offset: Int = 0, length: Int = 0) {
        val len = if (length == 0) bytesAvailable else length
        val buffer = kotlin.ByteArray(len)
        val read = __inputStream?.read(buffer) ?: 0
        if (read > 0) {
            val oldPos = bytes.position
            bytes.position = offset
            val ba = kotlin.ByteArray(read)
            System.arraycopy(buffer, 0, ba, 0, read)
            bytes.writeUTFBytes(String(ba)) // Still slightly simplified
            bytes.position = oldPos
        }
    }
}
