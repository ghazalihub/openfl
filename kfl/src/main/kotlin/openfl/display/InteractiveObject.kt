package openfl.display

import openfl.events.Event
import openfl.geom.Rectangle

abstract class InteractiveObject : DisplayObject() {

    var doubleClickEnabled: Boolean = false
    var mouseEnabled: Boolean = true
    var tabEnabled: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                dispatchEvent(Event(Event.TAB_ENABLED_CHANGE, true, false))
            }
        }

    var tabIndex: Int = -1
        set(value) {
            if (field != value) {
                field = value
                dispatchEvent(Event(Event.TAB_INDEX_CHANGE, true, false))
            }
        }

    init {
        __drawableType = openfl.display._internal.IBitmapDrawableType.DISPLAY_OBJECT
    }
}
