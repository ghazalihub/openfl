package openfl.external

object ExternalInterface {
    var available: Boolean = false
        private set
    var marshallExceptions: Boolean = false

    fun addCallback(functionName: String, closure: (Array<Any?>) -> Any?) {
        // ... implementation
    }

    fun call(functionName: String, vararg arguments: Any?): Any? {
        return null
    }
}
