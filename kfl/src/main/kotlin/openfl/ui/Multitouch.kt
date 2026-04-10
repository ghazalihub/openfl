package openfl.ui

object Multitouch {
    var inputMode: MultitouchInputMode = MultitouchInputMode.NONE
    val maxTouchPoints: Int = 0 // Update based on platform
    val supportedGestures: Array<String>? = null
    val supportsGestureEvents: Boolean = false
    val supportsTouchEvents: Boolean = false
}
