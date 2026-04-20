package openfl.events

open class TextEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var text: String = ""
) : Event(type, bubbles, cancelable) {

    companion object {
        const val LINK = "link"
        const val TEXT_INPUT = "textInput"
    }

    override fun clone(): TextEvent {
        val event = TextEvent(type, bubbles, cancelable, text)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    override fun toString(): String {
        return formatToString("TextEvent", "type", "bubbles", "cancelable", "text")
    }
}
