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
        val w = window ?: return 1
        w.create()

        val context = org.jetbrains.skia.DirectContext.makeGL()
        val renderTarget = org.jetbrains.skia.BackendRenderTarget.makeGL(
            w.width, w.height, 0, 8,
            org.jetbrains.skia.FramebufferFormat.GR_GL_RGBA8
        )
        val surface = org.jetbrains.skia.Surface.makeFromBackendRenderTarget(
            context, renderTarget,
            org.jetbrains.skia.SurfaceOrigin.BOTTOM_LEFT,
            org.jetbrains.skia.SurfaceColorFormat.RGBA_8888,
            org.jetbrains.skia.ColorSpace.sRGB
        ) ?: throw RuntimeException("Could not create Skia surface")

        while (!w.shouldClose()) {
            w.render(context, surface)
            dispatchEvent(Event(Event.ENTER_FRAME))
        }

        w.destroy()
        return 0
    }

    companion object {
        private var __current: Application? = null
        val current: Application? get() = __current
    }
}
