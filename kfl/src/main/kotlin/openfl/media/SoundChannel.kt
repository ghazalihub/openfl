package openfl.media

import openfl.events.EventDispatcher

class SoundChannel(val sound: Sound, var soundTransform: SoundTransform) : EventDispatcher() {
    var leftPeak: Double = 0.0
    var rightPeak: Double = 0.0
    var position: Double = 0.0

    fun stop() {
        // Implementation would stop the specific Clip/AL Source
    }
}
