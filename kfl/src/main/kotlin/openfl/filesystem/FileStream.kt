package openfl.filesystem

import openfl.events.EventDispatcher
import openfl.utils.ByteArray
import java.io.RandomAccessFile

class FileStream : EventDispatcher() {
    private var __file: RandomAccessFile? = null

    fun open(file: File, fileMode: FileMode) {
        val mode = when (fileMode) {
            FileMode.READ -> "r"
            FileMode.WRITE -> "rw"
            FileMode.APPEND -> "rw"
            FileMode.UPDATE -> "rw"
        }
        __file = RandomAccessFile(file.nativePath, mode)
        if (fileMode == FileMode.APPEND) {
            __file?.seek(__file?.length() ?: 0)
        }
    }

    fun close() {
        __file?.close()
        __window = null
    }

    fun readBytes(bytes: ByteArray, offset: Int = 0, length: Int = 0) {
        val len = if (length == 0) __file?.length()?.toInt() ?: 0 else length
        val buffer = kotlin.ByteArray(len)
        __file?.read(buffer)
        bytes.writeUTFBytes(String(buffer)) // Simplified
    }

    fun writeBytes(bytes: ByteArray, offset: Int = 0, length: Int = 0) {
        val len = if (length == 0) bytes.length - offset else length
        __file?.write(bytes.toByteArray(), offset, len)
    }

    private var __window: Any? = null
}

enum class FileMode {
    APPEND,
    READ,
    UPDATE,
    WRITE
}
