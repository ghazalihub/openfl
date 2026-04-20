package openfl.events

class ActivityEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var activating: Boolean = false
) : Event(type, bubbles, cancelable) {

    companion object {
        const val ACTIVITY = "activity"
    }

    override fun clone(): ActivityEvent {
        val event = ActivityEvent(type, bubbles, cancelable, activating)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    override fun toString(): String {
        return formatToString("ActivityEvent", "type", "bubbles", "cancelable", "activating")
    }
}
