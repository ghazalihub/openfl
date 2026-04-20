package openfl.display

import openfl.events.Event
import openfl.events.MouseEvent
import openfl.events.KeyboardEvent
import openfl.ui.Keyboard
import openfl.text.TextField
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.jetbrains.skia.*
import openfl.Lib

class Window(val application: Application, val width: Int, val height: Int, var title: String) {

    internal var handle: Long = 0
    var stage: Stage? = null

    private var __mouseX: Double = 0.0
    private var __mouseY: Double = 0.0
    private var __mouseDown: Boolean = false

    fun create() {
        if (!glfwInit()) throw RuntimeException("Unable to initialize GLFW")

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        handle = glfwCreateWindow(width, height, title, 0, 0)
        if (handle == 0L) throw RuntimeException("Failed to create the GLFW window")

        glfwMakeContextCurrent(handle)
        GL.createCapabilities()

        stage = Stage()
        stage!!.__resize(width, height)

        __setupCallbacks()

        glfwShowWindow(handle)
    }

    private fun __setupCallbacks() {
        glfwSetMouseButtonCallback(handle) { _, button, action, mods ->
            val type = if (action == GLFW_PRESS) MouseEvent.MOUSE_DOWN else MouseEvent.MOUSE_UP
            __mouseDown = (action == GLFW_PRESS)

            val event = MouseEvent(type, true, true, __mouseX, __mouseY)
            event.buttonDown = __mouseDown
            event.shiftKey = (mods and GLFW_MOD_SHIFT) != 0
            event.ctrlKey = (mods and GLFW_MOD_CONTROL) != 0
            event.altKey = (mods and GLFW_MOD_ALT) != 0

            stage?.__mouseX = __mouseX
            stage?.__mouseY = __mouseY
            stage?.dispatchEvent(event)

            if (action == GLFW_RELEASE) {
                stage?.dispatchEvent(MouseEvent(MouseEvent.CLICK, true, true, __mouseX, __mouseY))
            }
        }

        glfwSetCursorPosCallback(handle) { _, xpos, ypos ->
            __mouseX = xpos
            __mouseY = ypos

            stage?.__mouseX = __mouseX
            stage?.__mouseY = __mouseY

            val type = MouseEvent.MOUSE_MOVE
            val event = MouseEvent(type, true, true, __mouseX, __mouseY)
            event.buttonDown = __mouseDown
            stage?.dispatchEvent(event)
        }

        glfwSetKeyCallback(handle) { _, key, scancode, action, mods ->
            val type = when (action) {
                GLFW_PRESS -> KeyboardEvent.KEY_DOWN
                GLFW_RELEASE -> KeyboardEvent.KEY_UP
                else -> null
            }

            if (type != null) {
                val keyCode = __convertGLFWKey(key)
                val charCode = Keyboard.__getCharCode(keyCode, (mods and GLFW_MOD_SHIFT) != 0, false)

                val event = KeyboardEvent(type, true, true, charCode, keyCode)
                event.shiftKey = (mods and GLFW_MOD_SHIFT) != 0
                event.ctrlKey = (mods and GLFW_MOD_CONTROL) != 0
                event.altKey = (mods and GLFW_MOD_ALT) != 0

                stage?.dispatchEvent(event)
            }
        }

        glfwSetWindowSizeCallback(handle) { _, w, h ->
            stage?.__resize(w, h)
        }
    }

    private fun __convertGLFWKey(key: Int): Int {
        return when (key) {
            GLFW_KEY_SPACE -> Keyboard.SPACE
            GLFW_KEY_ENTER -> Keyboard.ENTER
            GLFW_KEY_BACKSPACE -> Keyboard.BACKSPACE
            GLFW_KEY_TAB -> Keyboard.TAB
            GLFW_KEY_ESCAPE -> Keyboard.ESCAPE
            GLFW_KEY_LEFT -> Keyboard.LEFT
            GLFW_KEY_RIGHT -> Keyboard.RIGHT
            GLFW_KEY_UP -> Keyboard.UP
            GLFW_KEY_DOWN -> Keyboard.DOWN
            in GLFW_KEY_A..GLFW_KEY_Z -> key - GLFW_KEY_A + Keyboard.A
            in GLFW_KEY_0..GLFW_KEY_9 -> key - GLFW_KEY_0 + Keyboard.NUMBER_0
            else -> key
        }
    }

    fun render(context: DirectContext, surface: Surface) {
        val canvas = surface.canvas
        canvas.clear(stage?.color ?: 0xFFFFFFFF.toInt())

        stage?.__update(false, true)
        __renderDisplayObject(stage!!, canvas)

        context.flush()
        glfwSwapBuffers(handle)
        glfwPollEvents()
    }

    private fun __renderDisplayObject(displayObject: DisplayObject, canvas: Canvas) {
        if (!displayObject.visible || displayObject.alpha <= 0) return

        canvas.save()

        val matrix = displayObject.__transform
        val skiaMatrix = Matrix33(
            matrix.a.toFloat(), matrix.c.toFloat(), matrix.tx.toFloat(),
            matrix.b.toFloat(), matrix.d.toFloat(), matrix.ty.toFloat(),
            0f, 0f, 1f
        )
        canvas.concat(skiaMatrix)

        // Render graphics/content
        when (displayObject) {
            is Sprite -> displayObject.graphics.__draw(canvas)
            is Shape -> displayObject.graphics.__draw(canvas)
            is Bitmap -> {
                displayObject.bitmapData?.let { bmd ->
                    canvas.drawImage(Image.makeFromBitmap(bmd.__skiaBitmap), 0f, 0f)
                }
            }
            is TextField -> displayObject.__draw(canvas)
            is Tilemap -> displayObject.__draw(canvas)
        }

        // Render children
        if (displayObject is DisplayObjectContainer) {
            for (child in displayObject.__children) {
                __renderDisplayObject(child, canvas)
            }
        }

        canvas.restore()
    }

    fun shouldClose(): Boolean = glfwWindowShouldClose(handle)

    fun destroy() {
        glfwDestroyWindow(handle)
        glfwTerminate()
    }
}
