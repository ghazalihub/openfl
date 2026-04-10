package openfl.errors

open class Error(var message: String? = "", var errorID: Int = 0) : RuntimeException(message)
class ArgumentError(message: String? = "") : Error(message)
class EOFError(message: String? = "") : Error(message)
class IOError(message: String? = "") : Error(message)
class IllegalOperationError(message: String? = "") : Error(message)
class RangeError(message: String? = "") : Error(message)
class SecurityError(message: String? = "") : Error(message)
class TypeError(message: String? = "") : Error(message)
class UnboundError(message: String? = "") : Error(message)
