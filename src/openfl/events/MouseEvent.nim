import std/math
import ./Event

type
  MouseEvent* = ref object of Event
    altKey*: bool
    buttonDown*: bool
    commandKey*: bool
    controlKey*: bool
    clickCount*: int
    ctrlKey*: bool
    delta*: int
    isRelatedObjectInaccessible*: bool
    localX*: float64
    localY*: float64
    relatedObject*: RootRef
    shiftKey*: bool
    stageX*: float64
    stageY*: float64
    updateAfterEventFlag: bool

const
  CLICK* = "click"
  DOUBLE_CLICK* = "doubleClick"
  MIDDLE_CLICK* = "middleClick"
  MIDDLE_MOUSE_DOWN* = "middleMouseDown"
  MIDDLE_MOUSE_UP* = "middleMouseUp"
  MOUSE_DOWN* = "mouseDown"
  MOUSE_MOVE* = "mouseMove"
  MOUSE_OUT* = "mouseOut"
  MOUSE_OVER* = "mouseOver"
  MOUSE_UP* = "mouseUp"
  MOUSE_WHEEL* = "mouseWheel"
  RELEASE_OUTSIDE* = "releaseOutside"
  RIGHT_CLICK* = "rightClick"
  RIGHT_MOUSE_DOWN* = "rightMouseDown"
  RIGHT_MOUSE_UP* = "rightMouseUp"
  ROLL_OUT* = "rollOut"
  ROLL_OVER* = "rollOver"

proc newMouseEvent*(typeStr: string, bubbles: bool = true, cancelable: bool = false, localX: float64 = 0, localY: float64 = 0, relatedObject: RootRef = nil, ctrlKey: bool = false, altKey: bool = false, shiftKey: bool = false, buttonDown: bool = false, delta: int = 0, commandKey: bool = false, controlKey: bool = false, clickCount: int = 0): MouseEvent =
  let self = MouseEvent(
    shiftKey: shiftKey,
    altKey: altKey,
    ctrlKey: ctrlKey,
    relatedObject: relatedObject,
    delta: delta,
    localX: localX,
    localY: localY,
    buttonDown: buttonDown,
    commandKey: commandKey,
    controlKey: controlKey,
    clickCount: clickCount,
    isRelatedObjectInaccessible: false,
    stageX: NaN,
    stageY: NaN,
    updateAfterEventFlag: false
  )
  self.`type` = typeStr
  self.bubbles = bubbles
  self.cancelable = cancelable
  return self

method clone*(self: MouseEvent): Event =
  let event = newMouseEvent(self.`type`, self.bubbles, self.cancelable, self.localX, self.localY, self.relatedObject, self.ctrlKey, self.altKey, self.shiftKey, self.buttonDown, self.delta, self.commandKey, self.controlKey, self.clickCount)
  event.target = self.target
  event.currentTarget = self.currentTarget
  event.eventPhase = self.eventPhase
  event.stageX = self.stageX
  event.stageY = self.stageY
  return event

method toString*(self: MouseEvent): string =
  "[MouseEvent type=" & self.`type` & " bubbles=" & $self.bubbles & " cancelable=" & $self.cancelable & " localX=" & $self.localX & " localY=" & $self.localY & "]"

proc updateAfterEvent*(self: MouseEvent) =
  self.updateAfterEventFlag = true
