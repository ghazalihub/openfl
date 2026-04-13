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
        __file = null
    }

    fun readBytes(bytes: ByteArray, offset: Int = 0, length: Int = 0) {
        val len = if (length == 0) (bytesAvailable().toInt()) else length
        val buffer = kotlin.ByteArray(len)
        __file?.read(buffer)
        bytes.position = offset
        bytes.writeBytes(ByteArray.fromByteArray(buffer))
    }

    fun writeBytes(bytes: ByteArray, offset: Int = 0, length: Int = 0) {
        val len = if (length == 0) bytes.length - offset else length
        __file?.write(bytes.toByteArray(), offset, len)
    }

    fun bytesAvailable(): Long {
        return (__file?.length() ?: 0) - (__file?.filePointer ?: 0)
    }
}

enum class FileMode {
    APPEND,
    READ,
    UPDATE,
    WRITE
}
