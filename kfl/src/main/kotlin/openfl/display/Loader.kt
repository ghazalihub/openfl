package openfl.display

import openfl.events.Event
import openfl.events.IOErrorEvent
import openfl.utils.Assets
import kotlinx.coroutines.*

open class Loader : DisplayObjectContainer() {
    var content: DisplayObject? = null
        private set
    var contentLoaderInfo: LoaderInfo = LoaderInfo()
        private set

    init {
        contentLoaderInfo.loader = this
    }

    fun load(request: openfl.net.URLRequest, context: openfl.system.LoaderContext? = null) {
        val url = request.url ?: return
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val bitmapData = Assets.loadBitmapData(url)
                if (bitmapData != null) {
                    val bitmap = Bitmap(bitmapData)
                    content = bitmap
                    addChild(bitmap)
                    contentLoaderInfo.dispatchEvent(Event(Event.COMPLETE))
                } else {
                    contentLoaderInfo.dispatchEvent(IOErrorEvent(IOErrorEvent.IO_ERROR, text = "Failed to load $url"))
                }
            } catch (e: Exception) {
                contentLoaderInfo.dispatchEvent(IOErrorEvent(IOErrorEvent.IO_ERROR, text = e.message ?: "Unknown error"))
            }
        }
    }

    fun loadBytes(bytes: openfl.utils.ByteArray, context: openfl.system.LoaderContext? = null) {
        // ... Implementation for loading from bytes
    }

    fun unload() {
        if (content != null) {
            removeChild(content!!)
            content = null
        }
    }

    fun unloadAndStop(gc: Boolean = true) {
        unload()
    }
}
