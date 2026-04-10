package openfl.display3D.textures

import openfl.events.EventDispatcher
import org.lwjgl.opengl.GL11.*

abstract class TextureBase : EventDispatcher() {
    internal var __id: Int = glGenTextures()

    open fun dispose() {
        if (__id != 0) {
            glDeleteTextures(__id)
            __id = 0
        }
    }
}
