import std/asyncdispatch
import ../errors/Error
import ../errors/ArgumentError
import ../filters/BlurFilter
import ../filters/GlowFilter
import ../filters/DropShadowFilter
import ../utils/Timer
import ../events/TimerEvent as openfl_TimerEvent
import ../events/Event
import ../events/EventDispatcher

proc testErrors() =
  echo "Testing Errors..."
  let err = newArgumentError("Invalid argument")
  echo "Error name: ", err.kindName
  echo "Error message: ", err.msg
  assert err.kindName == "ArgumentError"
  assert err.msg == "Invalid argument"

proc testFilters() =
  echo "Testing Filters..."
  let blur = newBlurFilter(10, 10, 2)
  echo "Blur blurX: ", blur.blurX
  assert blur.blurX == 10

  let glow = newGlowFilter(0xFF0000, 1.0, 5, 5)
  echo "Glow color: ", glow.color
  assert glow.color == 0xFF0000

  let drop = newDropShadowFilter(5, 45, 0x000000, 0.5)
  echo "Drop shadow distance: ", drop.distance
  assert drop.distance == 5

proc testTimer() {.async.} =
  echo "Testing Timer..."
  let timer = newTimer(100, 2)
  var count = 0
  timer.addEventListener(openfl_TimerEvent.TIMER, proc(e: Event) =
    count += 1
    echo "Timer tick: ", count
  )

  var complete = false
  timer.addEventListener(openfl_TimerEvent.TIMER_COMPLETE, proc(e: Event) =
    complete = true
    echo "Timer complete!"
  )

  timer.start()
  await sleepAsync(300)

  assert count == 2
  assert complete == true
  echo "Timer tests passed!"

proc runAll() {.async.} =
  testErrors()
  testFilters()
  await testTimer()
  echo "All bulk port tests passed!"

waitFor runAll()
