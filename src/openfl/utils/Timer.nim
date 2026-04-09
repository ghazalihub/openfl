import std/math
import std/asyncdispatch
import ../events/EventDispatcher
import ../events/TimerEvent as openfl_TimerEvent
import ../errors/Error

type
  Timer* = ref object of EventDispatcher
    currentCountInternal: int
    delayInternal: float64
    repeatCountInternal: int
    runningInternal: bool

proc currentCount*(self: Timer): int = self.currentCountInternal
proc running*(self: Timer): bool = self.runningInternal

proc stop*(self: Timer)
proc start*(self: Timer)

proc delay*(self: Timer): float64 = self.delayInternal
proc `delay=`*(self: Timer, value: float64) =
  if value.isNaN or value < 0:
    raise newError("The delay specified is negative or not a finite number")
  self.delayInternal = value
  if self.runningInternal:
    self.stop()
    self.start()

proc repeatCount*(self: Timer): int = self.repeatCountInternal
proc `repeatCount=`*(self: Timer, value: int) =
  if self.runningInternal and value != 0 and value <= self.currentCountInternal:
    self.stop()
  self.repeatCountInternal = value

proc newTimer*(delay: float64, repeatCount: int = 0): Timer =
  if delay.isNaN or delay < 0:
    raise newError("The delay specified is negative or not a finite number")

  let self = Timer()
  # Initializing EventDispatcher parts
  self.initEventDispatcher()

  self.delayInternal = delay
  self.repeatCountInternal = repeatCount
  self.runningInternal = false
  self.currentCountInternal = 0
  return self

proc stop*(self: Timer) =
  self.runningInternal = false

proc timerLoop(self: Timer) {.async.} =
  while self.runningInternal:
    await sleepAsync(int(self.delayInternal))
    if not self.runningInternal: break

    self.currentCountInternal += 1

    if self.repeatCountInternal > 0 and self.currentCountInternal >= self.repeatCountInternal:
      self.stop()
      discard self.dispatchEvent(openfl_TimerEvent.newTimerEvent(openfl_TimerEvent.TIMER))
      discard self.dispatchEvent(openfl_TimerEvent.newTimerEvent(openfl_TimerEvent.TIMER_COMPLETE))
    else:
      discard self.dispatchEvent(openfl_TimerEvent.newTimerEvent(openfl_TimerEvent.TIMER))

proc start*(self: Timer) =
  if not self.runningInternal:
    self.runningInternal = true
    asyncCheck self.timerLoop()

proc reset*(self: Timer) =
  if self.runningInternal:
    self.stop()
  self.currentCountInternal = 0
