package openfl.display3D

import openfl.display.BitmapData
import openfl.geom.Matrix3D
import openfl.geom.Rectangle
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.*

class Context3D {
    var driverInfo: String = "OpenGL"
        private set

    fun clear(red: Double = 0.0, green: Double = 0.0, blue: Double = 0.0, alpha: Double = 1.0, depth: Double = 1.0, stencil: Int = 0, mask: Int = 0xFFFFFF) {
        glClearColor(red.toFloat(), green.toFloat(), blue.toFloat(), alpha.toFloat())
        glClearDepth(depth)
        glClearStencil(stencil)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT or GL_STENCIL_BUFFER_BIT)
    }

    fun configureBackBuffer(width: Int, height: Int, antiAlias: Int, enableDepthAndStencil: Boolean = true, wantsBestResolution: Boolean = false, wantsBestResolutionOnBrowserRetina: Boolean = false) {
        glViewport(0, 0, width, height)
    }

    fun createVertexBuffer(numVertices: Int, data32PerVertex: Int): VertexBuffer3D {
        return VertexBuffer3D(numVertices, data32PerVertex)
    }

    fun createIndexBuffer(numIndices: Int): IndexBuffer3D {
        return IndexBuffer3D(numIndices)
    }

    fun createProgram(): Program3D {
        return Program3D()
    }

    fun drawTriangles(indexBuffer: IndexBuffer3D, firstIndex: Int = 0, numTriangles: Int = -1) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.__id)
        val count = if (numTriangles < 0) indexBuffer.__numIndices else numTriangles * 3
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_SHORT, firstIndex.toLong() * 2)
    }

    fun present() {
        // Handled by window swap buffers
    }

    fun setProgram(program: Program3D?) {
        if (program != null) {
            glUseProgram(program.__id)
        } else {
            glUseProgram(0)
        }
    }

    fun setVertexBufferAt(index: Int, buffer: VertexBuffer3D?, bufferOffset: Int = 0, format: String = "float4") {
        if (buffer != null) {
            glBindBuffer(GL_ARRAY_BUFFER, buffer.__id)
            glEnableVertexAttribArray(index)
            val size = when (format) {
                "float4" -> 4
                "float3" -> 3
                "float2" -> 2
                "float1" -> 1
                else -> 4
            }
            glVertexAttribPointer(index, size, GL_FLOAT, false, buffer.__data32PerVertex * 4, bufferOffset.toLong() * 4)
        } else {
            glDisableVertexAttribArray(index)
        }
    }
}
