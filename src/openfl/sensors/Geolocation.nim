import ../events/EventDispatcher

type
  Geolocation* = ref object of EventDispatcher
    muted*: bool

proc newGeolocation*(): Geolocation =
  let self = Geolocation(muted: false)
  self.initEventDispatcher()
  return self

var isSupported*: bool = false
