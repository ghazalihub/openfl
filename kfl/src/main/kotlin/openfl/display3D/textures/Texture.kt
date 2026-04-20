package openfl.display3D.textures

import openfl.display.BitmapData
import openfl.utils.ByteArray
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*

class Texture(val width: Int, val height: Int) : TextureBase() {
    fun uploadFromBitmapData(source: BitmapData, miplevel: Int = 0) {
        glBindTexture(GL_TEXTURE_2D, __id)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

        val info = source.__skiaBitmap.info
        val pixels = source.__skiaBitmap.readPixels(info, source.width * 4, 0, 0)
        if (pixels != null) {
            val buffer = java.nio.ByteBuffer.allocateDirect(pixels.size).put(pixels).flip()
            glTexImage2D(GL_TEXTURE_2D, miplevel, GL_RGBA, source.width, source.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
        }
    }

    fun uploadFromByteArray(data: ByteArray, byteArrayOffset: Int, miplevel: Int = 0) {
        glBindTexture(GL_TEXTURE_2D, __id)
        val buffer = java.nio.ByteBuffer.allocateDirect(data.length).put(data.toByteArray()).flip()
        buffer.position(byteArrayOffset)
        glTexImage2D(GL_TEXTURE_2D, miplevel, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
    }
}
