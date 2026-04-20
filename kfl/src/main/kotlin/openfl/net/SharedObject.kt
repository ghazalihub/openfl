package openfl.net

import openfl.events.EventDispatcher
import openfl.events.NetStatusEvent
import openfl.events.Event
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class SharedObject : EventDispatcher() {
    var data: MutableMap<String, Any?> = mutableMapOf()
        private set
    var size: Int = 0
        get() = data.size // Simplified
        private set

    private var __name: String = ""
    private var __localPath: String = ""

    fun clear() {
        data.clear()
        val file = __getFile()
        if (file.exists()) file.delete()
    }

    fun flush(minDiskSpace: Int = 0): SharedObjectFlushStatus {
        try {
            val file = __getFile()
            file.parentFile.mkdirs()
            ObjectOutputStream(FileOutputStream(file)).use { it.writeObject(data) }
            return SharedObjectFlushStatus.FLUSHED
        } catch (e: Exception) {
            return SharedObjectFlushStatus.PENDING
        }
    }

    private fun __getFile(): File {
        val appData = System.getProperty("user.home") + "/.kfl/sharedObjects/"
        return File(appData + __localPath + "/" + __name + ".sol")
    }

    companion object {
        fun getLocal(name: String, localPath: String? = null, secure: Boolean = false): SharedObject {
            val so = SharedObject()
            so.__name = name
            so.__localPath = localPath ?: "default"

            val file = so.__getFile()
            if (file.exists()) {
                try {
                    ObjectInputStream(FileInputStream(file)).use {
                        so.data = it.readObject() as MutableMap<String, Any?>
                    }
                } catch (e: Exception) {}
            }
            return so
        }
    }
}

enum class SharedObjectFlushStatus {
    FLUSHED,
    PENDING
}
