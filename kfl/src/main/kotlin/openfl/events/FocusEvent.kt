package openfl.events

import openfl.display.InteractiveObject

class FocusEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var relatedObject: InteractiveObject? = null,
    var shiftKey: Boolean = false,
    var keyCode: Int = 0
) : Event(type, bubbles, cancelable) {

    companion object {
        const val FOCUS_IN = "focusIn"
        const val FOCUS_OUT = "focusOut"
        const val KEY_FOCUS_CHANGE = "keyFocusChange"
        const val MOUSE_FOCUS_CHANGE = "mouseFocusChange"
    }

    override fun clone(): FocusEvent {
        val event = FocusEvent(type, bubbles, cancelable, relatedObject, shiftKey, keyCode)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    override fun toString(): String {
        return formatToString("FocusEvent", "type", "bubbles", "cancelable", "relatedObject", "shiftKey", "keyCode")
    }
}
