package openfl.display

import openfl.events.EventDispatcher
import openfl.events.Event
import openfl.system.ApplicationDomain
import openfl.utils.ByteArray

class LoaderInfo : EventDispatcher() {
    var applicationDomain: ApplicationDomain? = null
        private set
    var bytes: ByteArray? = null
        private set
    var bytesLoaded: Int = 0
        private set
    var bytesTotal: Int = 0
        private set
    var content: DisplayObject? = null
        private set
    var contentType: String? = null
        private set
    var frameRate: Double = 60.0
        private set
    var height: Int = 0
        private set
    var loader: Loader? = null
        internal set
    var loaderURL: String? = null
        private set
    var parameters: Map<String, String>? = null
        private set
    var parentSandboxBridge: Any? = null
    var sameDomain: Boolean = true
        private set
    var sharedEvents: EventDispatcher? = null
        private set
    var url: String? = null
        private set
    var width: Int = 0
        private set

    companion object {
        fun getLoaderInfoByDefinition(obj: Any): LoaderInfo? {
            return null
        }
    }
}
