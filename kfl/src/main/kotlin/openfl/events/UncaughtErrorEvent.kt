package openfl.events

open class UncaughtErrorEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var error: Any? = null
) : ErrorEvent(type, bubbles, cancelable) {
    override fun clone(): UncaughtErrorEvent {
        val event = UncaughtErrorEvent(type, bubbles, cancelable, error)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    companion object {
        const val UNCAUGHT_ERROR = "uncaughtError"
    }
}
