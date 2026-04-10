package openfl.sensors

import openfl.events.EventDispatcher
import openfl.events.AccelerometerEvent

object Accelerometer : EventDispatcher() {
    var isSupported: Boolean = false
        private set
    var muted: Boolean = false
        private set

    fun setRequestedUpdateInterval(interval: Double) {
        // ...
    }
}

object Geolocation : EventDispatcher() {
    var isSupported: Boolean = false
        private set
    var muted: Boolean = false
        private set

    fun setRequestedUpdateInterval(interval: Double) {
        // ...
    }
}
