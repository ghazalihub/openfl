package openfl.filesystem

import openfl.events.EventDispatcher
import java.io.File as JFile

class File(path: String? = null) : EventDispatcher() {
    var nativePath: String = path ?: ""
        private set
    var exists: Boolean = false
        get() = JFile(nativePath).exists()
        private set
    var isDirectory: Boolean = false
        get() = JFile(nativePath).isDirectory
        private set

    fun resolvePath(path: String): File {
        val f = JFile(nativePath, path)
        return File(f.absolutePath)
    }

    companion object {
        val applicationDirectory: File get() = File(System.getProperty("user.dir"))
        val applicationStorageDirectory: File get() = File(System.getProperty("user.home") + "/.kfl/storage")
        val desktopDirectory: File get() = File(System.getProperty("user.home") + "/Desktop")
        val documentsDirectory: File get() = File(System.getProperty("user.home") + "/Documents")
        val userDirectory: File get() = File(System.getProperty("user.home"))
    }
}
