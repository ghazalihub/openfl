type
  ExternalInterface* = ref object

var marshallExceptions*: bool = false
var available*: bool = false # Browser dependent

proc addCallback*(functionName: string, closure: proc (args: seq[RootRef]): RootRef) =
  # TODO: implement for JS target or native bridge
  discard

proc call*(functionName: string, args: varargs[RootRef]): RootRef =
  # TODO: implement for JS target or native bridge
  return nil
