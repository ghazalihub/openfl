import ../events/EventDispatcher

type
  NativeApplication* = ref object of EventDispatcher
    applicationID*: string
    publisherID*: string
    runtimePatchLevel*: int
    runtimeVersion*: string

proc initNativeApplication*(self: NativeApplication) =
  self.initEventDispatcher()
  self.applicationID = ""
  self.publisherID = ""
  self.runtimeVersion = "1.0.0"

var nativeApplicationInstance: NativeApplication = nil

proc nativeApplication*(): NativeApplication =
  if nativeApplicationInstance.isNil:
    nativeApplicationInstance = NativeApplication()
    nativeApplicationInstance.initNativeApplication()
  return nativeApplicationInstance

proc exit*(self: NativeApplication, errorCode: int = 0) =
  # TODO: Implement proper shutdown
  quit(errorCode)
