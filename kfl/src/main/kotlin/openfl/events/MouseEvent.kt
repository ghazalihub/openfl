package openfl.events

import openfl.display.InteractiveObject
import openfl.geom.Point

class MouseEvent(
    type: String,
    bubbles: Boolean = true,
    cancelable: Boolean = false,
    var localX: Double = 0.0,
    var localY: Double = 0.0,
    var relatedObject: InteractiveObject? = null,
    var ctrlKey: Boolean = false,
    var altKey: Boolean = false,
    var shiftKey: Boolean = false,
    var buttonDown: Boolean = false,
    var delta: Int = 0,
    var commandKey: Boolean = false,
    var controlKey: Boolean = false,
    var clickCount: Int = 0
) : Event(type, bubbles, cancelable) {

    companion object {
        const val CLICK = "click"
        const val DOUBLE_CLICK = "doubleClick"
        const val MOUSE_DOWN = "mouseDown"
        const val MOUSE_MOVE = "mouseMove"
        const val MOUSE_OUT = "mouseOut"
        const val MOUSE_OVER = "mouseOver"
        const val MOUSE_UP = "mouseUp"
        const val MOUSE_WHEEL = "mouseWheel"
        const val ROLL_OUT = "rollOut"
        const val ROLL_OVER = "rollOver"
        const val RIGHT_CLICK = "rightClick"
        const val RIGHT_MOUSE_DOWN = "rightMouseDown"
        const val RIGHT_MOUSE_UP = "rightMouseUp"
        const val MIDDLE_CLICK = "middleClick"
        const val MIDDLE_MOUSE_DOWN = "middleMouseDown"
        const val MIDDLE_MOUSE_UP = "middleMouseUp"
        const val RELEASE_OUTSIDE = "releaseOutside"
    }

    var stageX: Double = Double.NaN
    var stageY: Double = Double.NaN
    internal var updateAfterEventFlag: Boolean = false

    override fun clone(): MouseEvent {
        val event = MouseEvent(type, bubbles, cancelable, localX, localY, relatedObject, ctrlKey, altKey, shiftKey, buttonDown, delta, commandKey, controlKey, clickCount)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        event.stageX = stageX
        event.stageY = stageY
        return event
    }

    override fun toString(): String {
        return formatToString("MouseEvent", "type", "bubbles", "cancelable", "localX", "localY", "relatedObject", "ctrlKey", "altKey", "shiftKey", "buttonDown", "delta")
    }

    fun updateAfterEvent() {
        updateAfterEventFlag = true
    }
}
