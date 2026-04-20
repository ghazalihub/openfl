package openfl.events

import openfl.ui.KeyLocation

class KeyboardEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var charCode: Int = 0,
    var keyCode: Int = 0,
    var keyLocation: KeyLocation = KeyLocation.STANDARD,
    var ctrlKey: Boolean = false,
    var altKey: Boolean = false,
    var shiftKey: Boolean = false,
    var controlKey: Boolean = false,
    var commandKey: Boolean = false
) : Event(type, bubbles, cancelable) {

    companion object {
        const val KEY_DOWN = "keyDown"
        const val KEY_UP = "keyUp"
    }

    internal var updateAfterEventFlag: Boolean = false

    override fun clone(): KeyboardEvent {
        val event = KeyboardEvent(type, bubbles, cancelable, charCode, keyCode, keyLocation, ctrlKey, altKey, shiftKey, controlKey, commandKey)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    override fun toString(): String {
        return formatToString("KeyboardEvent", "type", "bubbles", "cancelable", "charCode", "keyCode", "keyLocation", "ctrlKey", "altKey", "shiftKey")
    }

    fun updateAfterEvent() {
        updateAfterEventFlag = true
    }
}
