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
        // Implementation for loading from bytes (decoding PNG/JPEG)
        try {
            val bis = java.io.ByteArrayInputStream(bytes.toByteArray())
            val bufferedImage = javax.imageio.ImageIO.read(bis)
            if (bufferedImage != null) {
                val bitmapData = BitmapData.fromBufferedImage(bufferedImage)
                val bitmap = Bitmap(bitmapData)
                content = bitmap
                addChild(bitmap)
                contentLoaderInfo.dispatchEvent(Event(Event.COMPLETE))
            }
        } catch (e: Exception) {
            contentLoaderInfo.dispatchEvent(IOErrorEvent(IOErrorEvent.IO_ERROR, text = e.message ?: "Decoding error"))
        }
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
