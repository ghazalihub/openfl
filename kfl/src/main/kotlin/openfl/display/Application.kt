package openfl.display

import openfl.Lib
import openfl.events.Event

open class Application {
    var window: Window? = null
        internal set

    init {
        if (__current == null) {
            __current = this
        }
    }

    open fun createWindow(width: Int, height: Int, title: String): Window {
        val window = Window(this, width, height, title)
        this.window = window
        Lib.__init(window.stage!!)
        return window
    }

    open fun exec(): Int {
        window?.let { w ->
            w.create()
            // Main loop logic is usually handled by the platform-specific Application implementation
            // Here we'd enter the GLFW loop
        }
        return 0
    }

    companion object {
        private var __current: Application? = null
        val current: Application? get() = __current
    }
}
