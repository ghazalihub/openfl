package openfl.events

class IOErrorEvent(
    type: String,
    bubbles: Boolean = true,
    cancelable: Boolean = false,
    text: String = "",
    id: Int = 0
) : ErrorEvent(type, bubbles, cancelable, text, id) {

    companion object {
        const val IO_ERROR = "ioError"
    }

    override fun clone(): IOErrorEvent {
        val event = IOErrorEvent(type, bubbles, cancelable, text, errorID)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    override fun toString(): String {
        return formatToString("IOErrorEvent", "type", "bubbles", "cancelable", "text", "errorID")
    }
}
