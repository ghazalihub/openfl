package openfl.display

import org.jetbrains.skia.DirectContext
import org.jetbrains.skia.Surface
import org.jetbrains.skia.BackendRenderTarget
import org.jetbrains.skia.FramebufferFormat
import org.jetbrains.skia.SurfaceColorFormat
import org.jetbrains.skia.SurfaceOrigin
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.*

class Application {

    private val windows = mutableListOf<Window>()
    private var context: DirectContext? = null

    fun createWindow(width: Int, height: Int, title: String): Window {
        val window = Window(this, width, height, title)
        window.create()
        windows.add(window)
        return window
    }

    fun exec() {
        context = DirectContext.makeGL()

        while (windows.isNotEmpty()) {
            val iterator = windows.iterator()
            while (iterator.hasNext()) {
                val window = iterator.next()
                if (window.shouldClose()) {
                    window.destroy()
                    iterator.remove()
                    continue
                }

                val fbId = glGetInteger(GL_FRAMEBUFFER_BINDING)
                val renderTarget = BackendRenderTarget.makeGL(
                    window.width, window.height, 0, 8, fbId, FramebufferFormat.GR_GL_RGBA8
                )
                val surface = Surface.makeFromBackendRenderTarget(
                    context!!, renderTarget, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat.RGBA_8888, null
                )

                if (surface != null) {
                    window.render(context!!, surface)
                    surface.close()
                }
                renderTarget.close()
            }
        }

        context?.close()
    }
}
