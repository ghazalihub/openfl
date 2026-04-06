import ../events/Event
import ../events/EventDispatcher
import ../events/MouseEvent

proc testDispatcher() =
  echo "Testing EventDispatcher..."
  let dispatcher = newEventDispatcher()
  var clicked = false

  let listener = proc (e: Event) =
    echo "Click listener called with event: ", $e
    clicked = true
    let me = MouseEvent(e)
    assert me.localX == 100
    assert me.localY == 200

  dispatcher.addEventListener(CLICK, listener)

  let mouseEvent = newMouseEvent(CLICK, true, false, 100, 200)
  discard dispatcher.dispatchEvent(mouseEvent)

  assert clicked == true
  echo "EventDispatcher tests passed!"

testDispatcher()
