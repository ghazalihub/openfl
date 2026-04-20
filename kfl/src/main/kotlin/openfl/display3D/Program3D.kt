package openfl.display3D

import org.lwjgl.opengl.GL20.*

class Program3D {
    internal val __id: Int = glCreateProgram()

    fun upload(vertexShader: String, fragmentShader: String) {
        val vs = __compileShader(GL_VERTEX_SHADER, vertexShader)
        val fs = __compileShader(GL_FRAGMENT_SHADER, fragmentShader)

        glAttachShader(__id, vs)
        glAttachShader(__id, fs)
        glLinkProgram(__id)

        if (glGetProgrami(__id, GL_LINK_STATUS) == GL_FALSE) {
            throw RuntimeException("Program link error: " + glGetProgramInfoLog(__id))
        }
    }

    private fun __compileShader(type: Int, source: String): Int {
        val shader = glCreateShader(type)
        glShaderSource(shader, source)
        glCompileShader(shader)
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw RuntimeException("Shader compile error: " + glGetShaderInfoLog(shader))
        }
        return shader
    }

    fun dispose() {
        glDeleteProgram(__id)
    }
}
