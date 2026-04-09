import ../events/EventDispatcher

type
  NetConnection* = ref object of EventDispatcher
    connected*: bool

proc newNetConnection*(): NetConnection =
  let self = NetConnection(connected: false)
  self.initEventDispatcher()
  return self

proc connect*(self: NetConnection, command: string, args: varargs[RootRef]) =
  # TODO: implement
  self.connected = true
