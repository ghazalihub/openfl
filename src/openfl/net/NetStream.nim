import ../events/EventDispatcher
import ./NetConnection

type
  NetStream* = ref object of EventDispatcher
    client*: RootRef
    time*: float64

proc newNetStream*(connection: NetConnection): NetStream =
  let self = NetStream(time: 0.0)
  self.initEventDispatcher()
  return self

proc play*(self: NetStream, url: string) =
  # TODO: implement video/audio streaming
  discard

proc pause*(self: NetStream) = discard
proc resume*(self: NetStream) = discard
proc seek*(self: NetStream, offset: float64) = discard
proc close*(self: NetStream) = discard
