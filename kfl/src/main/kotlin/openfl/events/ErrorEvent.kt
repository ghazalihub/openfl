package openfl.events

open class ErrorEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    text: String = "",
    val errorID: Int = 0
) : TextEvent(type, bubbles, cancelable, text) {

    companion object {
        const val ERROR = "error"
    }

    override fun clone(): ErrorEvent {
        val event = ErrorEvent(type, bubbles, cancelable, text, errorID)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    override fun toString(): String {
        return formatToString("ErrorEvent", "type", "bubbles", "cancelable", "text", "errorID")
    }
}
