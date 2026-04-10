package openfl.system

object Capabilities {
    val cpuArchitecture: String
        get() = java.lang.System.getProperty("os.arch")

    val language: String
        get() = java.util.Locale.getDefault().language

    val manufacturer: String = "kfl"

    val os: String
        get() = java.lang.System.getProperty("os.name") + " " + java.lang.System.getProperty("os.version")

    val playerType: String = "Desktop"

    val screenDPI: Double = 72.0 // Placeholder

    val screenResolutionX: Double = 1920.0 // Placeholder
    val screenResolutionY: Double = 1080.0 // Placeholder

    val version: String = "WIN 0,0,0,0"
}

object System {
    val totalMemory: Long
        get() = Runtime.getRuntime().totalMemory()

    fun exit(code: Int) {
        kotlin.system.exitProcess(code)
    }

    fun gc() {
        java.lang.System.gc()
    }

    fun setClipboard(string: String) {
        // Implementation with java.awt.datatransfer.Clipboard if needed
    }
}
