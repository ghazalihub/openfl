import ./events/Event
import ./events/IOErrorEvent

proc testEvents() =
  echo "Testing Event..."
  let e = newEvent(COMPLETE, false, false)
  echo "Event: ", $e
  assert e.`type` == "complete"
  assert e.bubbles == false

  echo "Testing IOErrorEvent..."
  let ioe = newIOErrorEvent(IO_ERROR, true, false, "File not found", 404)
  echo "IOErrorEvent: ", $ioe
  assert ioe.`type` == "ioError"
  assert ioe.bubbles == true
  assert ioe.text == "File not found"
  assert ioe.errorID == 404

  let ioeClone = IOErrorEvent(ioe.clone())
  echo "IOErrorEvent Clone: ", $ioeClone
  assert ioeClone.`type` == ioe.`type`
  assert ioeClone.text == ioe.text
  assert ioeClone.errorID == ioe.errorID

  echo "Event tests passed!"

testEvents()
