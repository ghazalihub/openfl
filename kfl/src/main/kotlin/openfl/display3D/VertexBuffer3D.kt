package openfl.display3D

import org.lwjgl.opengl.GL15.*
import java.nio.FloatBuffer

class VertexBuffer3D(val __numVertices: Int, val __data32PerVertex: Int) {
    internal val __id: Int = glGenBuffers()

    fun uploadFromVector(data: FloatArray, startVertex: Int, numVertices: Int) {
        glBindBuffer(GL_ARRAY_BUFFER, __id)
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW)
    }

    fun dispose() {
        glDeleteBuffers(__id)
    }
}
