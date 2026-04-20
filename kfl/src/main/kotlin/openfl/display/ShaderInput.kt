package openfl.display

class ShaderInput<T> {
    var channels: Int = 0
    var height: Int = 0
    var input: T? = null
    var name: String = ""
    var smoothing: Boolean = false
    var width: Int = 0

    internal var index: Int = -1
    internal var __isUniform: Boolean = false
}
