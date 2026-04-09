import ./Event

type
  TextEvent* = ref object of Event
    text*: string

proc newTextEvent*(typeStr: string, bubbles: bool = false, cancelable: bool = false, text: string = ""): TextEvent =
  let self = TextEvent(text: text)
  self.`type` = typeStr
  self.bubbles = bubbles
  self.cancelable = cancelable
  return self

method clone*(self: TextEvent): Event =
  let event = newTextEvent(self.`type`, self.bubbles, self.cancelable, self.text)
  event.eventPhase = self.eventPhase
  event.target = self.target
  event.currentTarget = self.currentTarget
  return event

method toString*(self: TextEvent): string =
  "[TextEvent type=" & self.`type` & " bubbles=" & $self.bubbles & " cancelable=" & $self.cancelable & " text=" & self.text & "]"

const
  LINK* = "link"
  TEXT_INPUT* = "textInput"
