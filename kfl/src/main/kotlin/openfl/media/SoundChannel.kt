package openfl.media

import openfl.events.EventDispatcher
import openfl.events.Event

class SoundChannel(
    private val __sound: Sound,
    private var __soundTransform: SoundTransform = SoundTransform()
) : EventDispatcher() {

    var leftPeak: Double = 1.0
    var rightPeak: Double = 1.0

    var position: Double = 0.0
        get() = field // Placeholder
        set(value) { field = value }

    var soundTransform: SoundTransform
        get() = __soundTransform.clone()
        set(value) {
            __soundTransform = value.clone()
            // Apply to native audio source
        }

    fun stop() {
        // Stop native audio source
    }
}
