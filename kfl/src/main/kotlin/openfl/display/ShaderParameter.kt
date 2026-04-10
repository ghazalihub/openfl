package openfl.display

class ShaderParameter<T> {
    var name: String = ""
    var type: ShaderParameterType? = null
    var value: Array<T>? = null

    internal var index: Int = -1
    internal var __arrayLength: Int = 0
    internal var __isBool: Boolean = false
    internal var __isFloat: Boolean = false
    internal var __isInt: Boolean = false
    internal var __isUniform: Boolean = false
    internal var __length: Int = 0
}
