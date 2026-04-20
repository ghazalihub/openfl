package openfl.events

class SecurityErrorEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    text: String = "",
    id: Int = 0
) : ErrorEvent(type, bubbles, cancelable, text, id) {

    companion object {
        const val SECURITY_ERROR = "securityError"
    }

    override fun clone(): SecurityErrorEvent {
        val event = SecurityErrorEvent(type, bubbles, cancelable, text, errorID)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    override fun toString(): String {
        return formatToString("SecurityErrorEvent", "type", "bubbles", "cancelable", "text", "errorID")
    }
}
