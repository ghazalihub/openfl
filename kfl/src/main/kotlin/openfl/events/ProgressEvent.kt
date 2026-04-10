package openfl.events

class ProgressEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var bytesLoaded: Double = 0.0,
    var bytesTotal: Double = 0.0
) : Event(type, bubbles, cancelable) {

    companion object {
        const val PROGRESS = "progress"
        const val SOCKET_DATA = "socketData"
    }

    override fun clone(): ProgressEvent {
        val event = ProgressEvent(type, bubbles, cancelable, bytesLoaded, bytesTotal)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    override fun toString(): String {
        return formatToString("ProgressEvent", "type", "bubbles", "cancelable", "bytesLoaded", "bytesTotal")
    }
}
