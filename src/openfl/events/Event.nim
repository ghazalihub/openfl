import ./EventPhase

type
  Event* = ref object of RootObj
    bubbles*: bool
    cancelable*: bool
    currentTarget*: RootRef
    eventPhase*: EventPhase
    target*: RootRef
    `type`*: string
    isCanceled: bool
    isCanceledNow: bool
    preventDefaultFlag: bool

const
  ACTIVATE* = "activate"
  ADDED* = "added"
  ADDED_TO_STAGE* = "addedToStage"
  CANCEL* = "cancel"
  CHANGE* = "change"
  CLEAR* = "clear"
  CLOSING* = "closing"
  CLOSE* = "close"
  COMPLETE* = "complete"
  CONNECT* = "connect"
  CONTEXT3D_CREATE* = "context3DCreate"
  COPY* = "copy"
  CUT* = "cut"
  DEACTIVATE* = "deactivate"
  ENTER_FRAME* = "enterFrame"
  EXIT_FRAME* = "exitFrame"
  EXITING* = "exiting"
  FRAME_CONSTRUCTED* = "frameConstructed"
  FRAME_LABEL* = "frameLabel"
  FULLSCREEN* = "fullScreen"
  ID3* = "id3"
  INIT* = "init"
  MOUSE_LEAVE* = "mouseLeave"
  OPEN* = "open"
  PASTE* = "paste"
  REMOVED* = "removed"
  REMOVED_FROM_STAGE* = "removedFromStage"
  RENDER* = "render"
  RESIZE* = "resize"
  SCROLL* = "scroll"
  SELECT* = "select"
  SELECT_ALL* = "selectAll"
  SOUND_COMPLETE* = "soundComplete"
  TAB_CHILDREN_CHANGE* = "tabChildrenChange"
  TAB_ENABLED_CHANGE* = "tabEnabledChange"
  TAB_INDEX_CHANGE* = "tabIndexChange"
  TEXTURE_READY* = "textureReady"
  UNLOAD* = "unload"

proc newEvent*(typeStr: string, bubbles: bool = false, cancelable: bool = false): Event =
  Event(
    `type`: typeStr,
    bubbles: bubbles,
    cancelable: cancelable,
    eventPhase: EventPhase.AT_TARGET
  )

method clone*(self: Event): Event {.base.} =
  let event = newEvent(self.`type`, self.bubbles, self.cancelable)
  event.eventPhase = self.eventPhase
  event.target = self.target
  event.currentTarget = self.currentTarget
  return event

proc isDefaultPrevented*(self: Event): bool =
  self.preventDefaultFlag

proc preventDefault*(self: Event) =
  if self.cancelable:
    self.preventDefaultFlag = true

proc stopImmediatePropagation*(self: Event) =
  self.isCanceled = true
  self.isCanceledNow = true

proc stopPropagation*(self: Event) =
  self.isCanceled = true

method toString*(self: Event): string {.base.} =
  "[Event type=" & self.`type` & " bubbles=" & $self.bubbles & " cancelable=" & $self.cancelable & "]"

proc `$`*(self: Event): string =
  self.toString()
