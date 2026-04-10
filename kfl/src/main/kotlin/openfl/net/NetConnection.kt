package openfl.net

import openfl.events.EventDispatcher
import openfl.events.NetStatusEvent
import openfl.events.IOErrorEvent
import openfl.events.Event

open class NetConnection : EventDispatcher() {
    var connected: Boolean = false
        private set
    var client: Any? = null
    var objectEncoding: Int = 3
    var proxyType: String = "none"
    var uri: String? = null
        private set

    fun connect(command: String?, vararg arguments: Any?) {
        uri = command
        connected = true
        dispatchEvent(NetStatusEvent(NetStatusEvent.NET_STATUS, false, false, mapOf("code" to "NetConnection.Connect.Success")))
    }

    fun close() {
        connected = false
    }

    fun call(command: String, responder: Responder? = null, vararg arguments: Any?) {
        // ... AMF call implementation
    }
}

class Responder(val result: (Any?) -> Unit, val status: ((Any?) -> Unit)? = null)
