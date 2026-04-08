import ../events/EventDispatcher

type
  Accelerometer* = ref object of EventDispatcher
    muted*: bool

proc newAccelerometer*(): Accelerometer =
  let self = Accelerometer(muted: false)
  self.initEventDispatcher()
  return self

var isSupported*: bool = false
