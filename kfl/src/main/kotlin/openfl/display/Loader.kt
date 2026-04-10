package openfl.display

import openfl.events.Event
import openfl.net.URLRequest
import openfl.system.LoaderContext

class LoaderInfo : openfl.events.EventDispatcher() {
    var bytesLoaded: Int = 0
    var bytesTotal: Int = 0
    var content: DisplayObject? = null
    var url: String? = null
    var contentType: String? = null

    companion object {
        fun create(loader: Loader?): LoaderInfo {
            return LoaderInfo()
        }
    }
}

class Loader : DisplayObjectContainer() {
    val contentLoaderInfo: LoaderInfo = LoaderInfo.create(this)
    var content: DisplayObject? = null
        private set

    fun load(request: URLRequest, context: LoaderContext? = null) {
        // Implementation
    }

    fun loadBytes(bytes: openfl.utils.ByteArray, context: LoaderContext? = null) {
        // Implementation
    }

    fun unload() {
        // Implementation
    }
}
