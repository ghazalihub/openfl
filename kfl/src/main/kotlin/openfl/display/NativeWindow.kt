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
        __window?.let { org.lwjgl.glfw.GLFW.glfwFocusWindow(it.handle) }
    }

    fun close() {
        __window?.destroy()
    }

    fun maximize() {
        __window?.let { org.lwjgl.glfw.GLFW.glfwMaximizeWindow(it.handle) }
    }

    fun minimize() {
        __window?.let { org.lwjgl.glfw.GLFW.glfwIconifyWindow(it.handle) }
    }

    fun restore() {
        __window?.let { org.lwjgl.glfw.GLFW.glfwRestoreWindow(it.handle) }
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
