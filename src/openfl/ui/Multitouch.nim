import ./MultitouchInputMode

type
  Multitouch* = ref object

var inputMode*: MultitouchInputMode = MultitouchInputMode.NONE
var maxTouchPoints*: int = 10
var supportsTouchEvents*: bool = true
var supportsGestureEvents*: bool = false
