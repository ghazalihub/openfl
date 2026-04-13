package openfl.display3D

import openfl.display.BitmapData
import openfl.geom.Matrix3D
import openfl.geom.Rectangle
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.*

class Context3D {
    var driverInfo: String = "OpenGL"
        private set

    private var __currentProgram: Program3D? = null

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
        __currentProgram = program
        if (program != null) {
            glUseProgram(program.__id)
        } else {
            glUseProgram(0)
        }
    }

    fun setVertexBufferAt(index: Int, buffer: VertexBuffer3D?, bufferOffset: Int = 0, format: Context3DVertexBufferFormat = Context3DVertexBufferFormat.FLOAT_4) {
        if (buffer != null) {
            glBindBuffer(GL_ARRAY_BUFFER, buffer.__id)
            glEnableVertexAttribArray(index)
            val size = when (format) {
                Context3DVertexBufferFormat.FLOAT_4 -> 4
                Context3DVertexBufferFormat.FLOAT_3 -> 3
                Context3DVertexBufferFormat.FLOAT_2 -> 2
                Context3DVertexBufferFormat.FLOAT_1 -> 1
                else -> 4
            }
            glVertexAttribPointer(index, size, GL_FLOAT, false, buffer.__data32PerVertex * 4, bufferOffset.toLong() * 4)
        } else {
            glDisableVertexAttribArray(index)
        }
    }

    fun setBlendFactors(sourceFactor: Context3DBlendFactor, destinationFactor: Context3DBlendFactor) {
        glEnable(GL_BLEND)
        glBlendFunc(__getGLBlendFactor(sourceFactor), __getGLBlendFactor(destinationFactor))
    }

    private fun __getGLBlendFactor(factor: Context3DBlendFactor): Int = when (factor) {
        Context3DBlendFactor.ONE -> GL_ONE
        Context3DBlendFactor.ZERO -> GL_ZERO
        Context3DBlendFactor.SOURCE_ALPHA -> GL_SRC_ALPHA
        Context3DBlendFactor.ONE_MINUS_SOURCE_ALPHA -> GL_ONE_MINUS_SRC_ALPHA
        Context3DBlendFactor.DESTINATION_ALPHA -> GL_DST_ALPHA
        Context3DBlendFactor.ONE_MINUS_DESTINATION_ALPHA -> GL_ONE_MINUS_DST_ALPHA
        else -> GL_ONE
    }

    fun setCulling(triangleFaceToCull: Context3DTriangleFace) {
        if (triangleFaceToCull == Context3DTriangleFace.NONE) {
            glDisable(GL_CULL_FACE)
        } else {
            glEnable(GL_CULL_FACE)
            glCullFace(when (triangleFaceToCull) {
                Context3DTriangleFace.BACK -> GL_BACK
                Context3DTriangleFace.FRONT -> GL_FRONT
                Context3DTriangleFace.FRONT_AND_BACK -> GL_FRONT_AND_BACK
                else -> GL_BACK
            })
        }
    }

    fun setDepthTest(depthMask: Boolean, passCompareMode: Context3DCompareMode) {
        glDepthMask(depthMask)
        glDepthFunc(when (passCompareMode) {
            Context3DCompareMode.ALWAYS -> GL_ALWAYS
            Context3DCompareMode.EQUAL -> GL_EQUAL
            Context3DCompareMode.GREATER -> GL_GREATER
            Context3DCompareMode.LESS -> GL_LESS
            Context3DCompareMode.NEVER -> GL_NEVER
            else -> GL_LESS
        })
    }

    internal fun __dispose() {

    }
}
