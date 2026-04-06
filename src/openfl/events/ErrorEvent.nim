import ./Event
import ./TextEvent

type
  ErrorEvent* = ref object of TextEvent
    errorID*: int

proc newErrorEvent*(typeStr: string, bubbles: bool = false, cancelable: bool = false, text: string = "", id: int = 0): ErrorEvent =
  let self = ErrorEvent(errorID: id)
  self.`type` = typeStr
  self.bubbles = bubbles
  self.cancelable = cancelable
  self.text = text
  return self

method clone*(self: ErrorEvent): Event =
  let event = newErrorEvent(self.`type`, self.bubbles, self.cancelable, self.text, self.errorID)
  event.eventPhase = self.eventPhase
  event.target = self.target
  event.currentTarget = self.currentTarget
  return event

method toString*(self: ErrorEvent): string =
  "[ErrorEvent type=" & self.`type` & " bubbles=" & $self.bubbles & " cancelable=" & $self.cancelable & " text=" & self.text & " errorID=" & $self.errorID & "]"

const
  ERROR* = "error"
