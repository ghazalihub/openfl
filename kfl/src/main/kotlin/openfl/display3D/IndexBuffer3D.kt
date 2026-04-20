package openfl.display3D

import org.lwjgl.opengl.GL15.*

class IndexBuffer3D(val __numIndices: Int) {
    internal val __id: Int = glGenBuffers()

    fun uploadFromVector(data: ShortArray, startOffset: Int, numIndices: Int) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, __id)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW)
    }

    fun dispose() {
        glDeleteBuffers(__id)
    }
}
