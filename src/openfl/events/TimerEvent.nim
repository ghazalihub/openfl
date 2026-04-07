import ./Event

type
  TimerEvent* = ref object of Event

const
  TIMER* = "timer"
  TIMER_COMPLETE* = "timerComplete"

proc newTimerEvent*(typeStr: string, bubbles: bool = false, cancelable: bool = false): TimerEvent =
  let self = TimerEvent()
  self.`type` = typeStr
  self.bubbles = bubbles
  self.cancelable = cancelable
  self.kindPreventDefaultFlag = false # Reusing for updateAfterEvent if needed, or add new field
  return self

method clone*(self: TimerEvent): Event =
  let event = newTimerEvent(self.`type`, self.bubbles, self.cancelable)
  event.target = self.target
  event.currentTarget = self.currentTarget
  event.eventPhase = self.eventPhase
  return event

method toString*(self: TimerEvent): string =
  "[TimerEvent type=" & self.`type` & " bubbles=" & $self.bubbles & " cancelable=" & $self.cancelable & "]"

proc updateAfterEvent*(self: TimerEvent) =
  # self.updateAfterEventFlag = true
  discard
