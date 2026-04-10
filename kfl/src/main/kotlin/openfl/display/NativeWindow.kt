package openfl.display

import openfl.events.EventDispatcher
import openfl.events.NativeWindowBoundsEvent
import openfl.events.NativeWindowDisplayStateEvent
import openfl.geom.Rectangle

class NativeWindow(val initOptions: NativeWindowInitOptions? = null) : EventDispatcher() {
    var bounds: Rectangle = Rectangle(0.0, 0.0, 800.0, 600.0)
    var title: String = ""
        set(value) {
            field = value
            __window?.title = value
        }
    var visible: Boolean = true

    internal var __window: Window? = null

    fun activate() {
        // ... focus window
    }

    fun close() {
        __window?.destroy()
    }

    fun maximize() {
        // ... maximize window
    }

    fun minimize() {
        // ... minimize window
    }

    fun restore() {
        // ... restore window
    }
}

class NativeWindowInitOptions {
    var owner: NativeWindow? = null
    var renderMode: String = "auto"
    var resizable: Boolean = true
    var maximizable: Boolean = true
    var minimizable: Boolean = true
    var systemChrome: String = "standard"
    var transparent: Boolean = false
    var type: String = "normal"
}
