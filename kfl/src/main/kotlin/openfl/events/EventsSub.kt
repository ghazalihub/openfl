package openfl.events

class AccelerometerEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var timestamp: Double = 0.0,
    var accelerationX: Double = 0.0,
    var accelerationY: Double = 0.0,
    var accelerationZ: Double = 0.0
) : Event(type, bubbles, cancelable) {
    override fun clone(): AccelerometerEvent {
        val event = AccelerometerEvent(type, bubbles, cancelable, timestamp, accelerationX, accelerationY, accelerationZ)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    companion object {
        const val UPDATE = "update"
    }
}

class AsyncErrorEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    text: String = "",
    var error: Throwable? = null
) : ErrorEvent(type, bubbles, cancelable, text) {
    override fun clone(): AsyncErrorEvent {
        val event = AsyncErrorEvent(type, bubbles, cancelable, text, error)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    companion object {
        const val ASYNC_ERROR = "asyncError"
    }
}

class NetStatusEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var info: Map<String, Any?> = emptyMap()
) : Event(type, bubbles, cancelable) {
    override fun clone(): NetStatusEvent {
        val event = NetStatusEvent(type, bubbles, cancelable, info)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    companion object {
        const val NET_STATUS = "netStatus"
    }
}

class FocusEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var relatedObject: openfl.display.InteractiveObject? = null,
    var shiftKey: Boolean = false,
    var keyCode: Int = 0
) : Event(type, bubbles, cancelable) {
    override fun clone(): FocusEvent {
        val event = FocusEvent(type, bubbles, cancelable, relatedObject, shiftKey, keyCode)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    companion object {
        const val FOCUS_IN = "focusIn"
        const val FOCUS_OUT = "focusOut"
        const val KEY_FOCUS_CHANGE = "keyFocusChange"
        const val MOUSE_FOCUS_CHANGE = "mouseFocusChange"
    }
}
