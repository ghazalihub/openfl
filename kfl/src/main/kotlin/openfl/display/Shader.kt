package openfl.display

import openfl.utils.ByteArray

open class Shader(var byteCode: ByteArray? = null) {
    var data: ShaderData = ShaderData(byteCode)
    var precisionHint: ShaderPrecision = ShaderPrecision.FULL

    var glFragmentSource: String? = null
        set(value) {
            if (value != field) __glSourceDirty = true
            field = value
        }

    var glVertexSource: String? = null
        set(value) {
            if (value != field) __glSourceDirty = true
            field = value
        }

    internal var __glSourceDirty: Boolean = true
    internal var __numPasses: Int = 1

    // LWJGL/OpenGL program reference would go here
    internal var __glProgram: Int = 0

    init {
        if (byteCode != null) {
            // Process bytecode
        }
    }

    internal open fun __init() {
        if (glFragmentSource != null && glVertexSource != null && (__glProgram == 0 || __glSourceDirty)) {
            __initGL()
        }
    }

    internal open fun __initGL() {
        val vs = org.lwjgl.opengl.GL20.glCreateShader(org.lwjgl.opengl.GL20.GL_VERTEX_SHADER)
        org.lwjgl.opengl.GL20.glShaderSource(vs, glVertexSource!!)
        org.lwjgl.opengl.GL20.glCompileShader(vs)

        val fs = org.lwjgl.opengl.GL20.glCreateShader(org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER)
        org.lwjgl.opengl.GL20.glShaderSource(fs, glFragmentSource!!)
        org.lwjgl.opengl.GL20.glCompileShader(fs)

        __glProgram = org.lwjgl.opengl.GL20.glCreateProgram()
        org.lwjgl.opengl.GL20.glAttachShader(__glProgram, vs)
        org.lwjgl.opengl.GL20.glAttachShader(__glProgram, fs)
        org.lwjgl.opengl.GL20.glLinkProgram(__glProgram)

        __glSourceDirty = false
    }

    internal open fun __update() {
        // Uniform updates would happen here based on ShaderData
    }

    internal open fun __enable() {
        __init()
        if (__glProgram != 0) {
            org.lwjgl.opengl.GL20.glUseProgram(__glProgram)
        }
    }

    internal open fun __disable() {
        if (__glProgram != 0) {
            org.lwjgl.opengl.GL20.glUseProgram(0)
        }
    }
}
