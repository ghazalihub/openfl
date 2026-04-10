package openfl.events

open class TouchEvent(
    type: String,
    bubbles: Boolean = false,
    cancelable: Boolean = false,
    var touchPointID: Int = 0,
    var isPrimaryTouchPoint: Boolean = false,
    var localX: Double = 0.0,
    var localY: Double = 0.0,
    var sizeX: Double = 0.0,
    var sizeY: Double = 0.0,
    var pressure: Double = 0.0,
    var relatedObject: Any? = null,
    var ctrlKey: Boolean = false,
    var altKey: Boolean = false,
    var shiftKey: Boolean = false,
    var commandKey: Boolean = false,
    var controlKey: Boolean = false
) : Event(type, bubbles, cancelable) {
    var stageX: Double = 0.0
    var stageY: Double = 0.0

    override fun clone(): TouchEvent {
        val event = TouchEvent(type, bubbles, cancelable, touchPointID, isPrimaryTouchPoint, localX, localY, sizeX, sizeY, pressure, relatedObject, ctrlKey, altKey, shiftKey, commandKey, controlKey)
        event.target = target
        event.currentTarget = currentTarget
        event.eventPhase = eventPhase
        return event
    }

    companion object {
        const val TOUCH_BEGIN = "touchBegin"
        const val TOUCH_END = "touchEnd"
        const val TOUCH_MOVE = "touchMove"
        const val TOUCH_OUT = "touchOut"
        const val TOUCH_OVER = "touchOver"
        const val TOUCH_ROLL_OUT = "touchRollOut"
        const val TOUCH_ROLL_OVER = "touchRollOver"
        const val TOUCH_TAP = "touchTap"
    }
}
