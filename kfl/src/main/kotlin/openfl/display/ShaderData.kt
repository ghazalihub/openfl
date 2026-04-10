package openfl.display

import openfl.utils.ByteArray

class ShaderData(code: ByteArray? = null) {
    // Dynamic fields are handled via a map or by generated subclasses
    private val __fields = mutableMapOf<String, Any>()

    operator fun get(name: String): Any? = __fields[name]
    operator fun set(name: String, value: Any) {
        __fields[name] = value
    }
}
