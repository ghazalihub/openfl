import ./Event
import ./ErrorEvent

type
  IOErrorEvent* = ref object of ErrorEvent

proc newIOErrorEvent*(typeStr: string, bubbles: bool = true, cancelable: bool = false, text: string = "", id: int = 0): IOErrorEvent =
  let self = IOErrorEvent()
  self.`type` = typeStr
  self.bubbles = bubbles
  self.cancelable = cancelable
  self.text = text
  self.errorID = id
  return self

method clone*(self: IOErrorEvent): Event =
  let event = newIOErrorEvent(self.`type`, self.bubbles, self.cancelable, self.text, self.errorID)
  event.eventPhase = self.eventPhase
  event.target = self.target
  event.currentTarget = self.currentTarget
  return event

method toString*(self: IOErrorEvent): string =
  "[IOErrorEvent type=" & self.`type` & " bubbles=" & $self.bubbles & " cancelable=" & $self.cancelable & " text=" & self.text & " errorID=" & $self.errorID & "]"

const
  IO_ERROR* = "ioError"
