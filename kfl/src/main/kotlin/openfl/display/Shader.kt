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
        // LWJGL shader compilation logic
        __glSourceDirty = false
    }

    internal open fun __update() {
        // Upload uniforms
    }

    internal open fun __enable() {
        __init()
        if (__glProgram != 0) {
            // glUseProgram
        }
    }

    internal open fun __disable() {
        if (__glProgram != 0) {
            // glUseProgram(0)
        }
    }
}
