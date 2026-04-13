package openfl.media

import openfl.events.EventDispatcher

object SoundMixer {
    var soundTransform: SoundTransform = SoundTransform()
    var bufferTime: Int = 1000

    fun stopAll() {

    }
}

class SoundLoaderContext(var bufferTime: Double = 1000.0, var checkPolicyFile: Boolean = false)

class Video(var width: Int = 320, var height: Int = 240) : openfl.display.DisplayObject() {
    fun attachNetStream(netStream: openfl.net.NetStream?) {

    }

    fun clear() {

    }
}
