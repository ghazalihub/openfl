package openfl.errors

open class Error(message: String? = "", var errorID: Int = 0) : Exception(message)

class ArgumentError(message: String? = "") : Error(message)
class IllegalOperationError(message: String? = "") : Error(message)
class IOError(message: String? = "") : Error(message)
class RangeError(message: String? = "") : Error(message)
class SecurityError(message: String? = "") : Error(message)
class TypeError(message: String? = "") : Error(message)
