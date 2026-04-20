package openfl.display3D.textures

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.*

class CubeTexture(val size: Int) : TextureBase() {
    fun uploadFromBitmapData(source: openfl.display.BitmapData, side: Int, miplevel: Int = 0) {
        glBindTexture(GL_TEXTURE_CUBE_MAP, __id)
        val target = GL_TEXTURE_CUBE_MAP_POSITIVE_X + side
        // upload data
    }
}
