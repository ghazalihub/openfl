package openfl.net

import openfl.events.Event
import openfl.events.IOErrorEvent
import openfl.events.SecurityErrorEvent
import openfl.events.DataEvent
import openfl.Lib

class XMLSocket(host: String? = null, port: Int = 0) : Socket(host, port) {
    init {
        // XMLSocket specific behavior
        addEventListener(Event.CONNECT) {
            // ... logic
        }
    }

    fun send(data: Any) {
        val str = data.toString() + "\u0000"
        writeUTFBytes(str)
        flush()
    }
}
