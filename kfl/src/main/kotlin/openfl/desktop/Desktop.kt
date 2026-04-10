package openfl.desktop

import openfl.events.EventDispatcher
import openfl.events.InvokeEvent
import openfl.display.NativeWindow

object NativeApplication : EventDispatcher() {
    var activeWindow: NativeWindow? = null
        private set
    var openedWindows: Array<NativeWindow> = emptyArray()
        private set

    fun exit(errorCode: Int = 0) {
        System.exit(errorCode)
    }
}

object Clipboard {
    var generalClipboard: Clipboard = Clipboard()
        private set

    fun clear() {}
    fun getData(format: String, transferMode: String = "original"): Any? = null
    fun setData(format: String, data: Any, serializable: Boolean = true): Boolean = true
}

class Clipboard()

object ClipboardFormats {
    const val BITMAP_FORMAT = "air:bitmap"
    const val FILE_LIST_FORMAT = "air:file list"
    const val HTML_FORMAT = "air:html"
    const val RICH_TEXT_FORMAT = "air:rtf"
    const val TEXT_FORMAT = "air:text"
    const val URL_FORMAT = "air:url"
}
