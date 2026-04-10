package openfl.net

import openfl.events.EventDispatcher
import openfl.events.NetStatusEvent
import openfl.events.IOErrorEvent
import openfl.events.Event
import openfl.media.SoundTransform

open class NetStream(val connection: NetConnection) : EventDispatcher() {
    var bufferLength: Double = 0.0
        private set
    var bufferTime: Double = 0.1
    var bytesLoaded: Int = 0
        private set
    var bytesTotal: Int = 0
        private set
    var checkPolicyFile: Boolean = true
    var client: Any? = null
    var currentFPS: Double = 0.0
        private set
    var liveDelay: Double = 0.0
        private set
    var objectEncoding: Int = 3
    var paused: Boolean = false
        private set
    var soundTransform: SoundTransform = SoundTransform()
    var time: Double = 0.0
        private set

    fun play(url: String, vararg arguments: Any?) {
        // ... play logic using Ktor/skia
    }

    fun pause() {
        paused = true
    }

    fun resume() {
        paused = false
    }

    fun close() {
        // ... implementation
    }

    fun seek(offset: Double) {
        // ... implementation
    }

    fun togglePause() {
        paused = !paused
    }
}
