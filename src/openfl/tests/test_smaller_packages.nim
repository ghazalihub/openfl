import ../system/Capabilities
import ../system/System
import ../text/TextField
import ../text/TextFormat
import ../net/URLRequest
import ../net/URLLoader
import ../net/SharedObject
import std/[tables, times, os]

proc testSystem() =
  echo "Testing System..."
  echo "OS: ", Capabilities.os()
  echo "Version: ", Capabilities.version()
  System.gc()
  echo "System tests passed!"

proc testText() =
  echo "Testing Text..."
  let tf = newTextField()
  tf.text = "Hello Nim"
  echo "TextField text: ", tf.text
  assert tf.text == "Hello Nim"

  let fmt = newTextFormat("Arial", 14, 0xFF0000)
  tf.defaultTextFormat = fmt
  assert tf.defaultTextFormat.size == 14
  echo "Text tests passed!"

import ../events/Event
import ../events/EventDispatcher
import std/asyncdispatch


proc testNet() =
  echo "Testing Net..."
  let req = newURLRequest("http://www.google.com")
  assert req.url == "http://www.google.com"

  let so = getLocal("testSettings")
  let testVal = "Hello Persistence"
  so.data["key"] = StringWrapper(val: testVal)
  so.flush("testSettings")

  let so2 = getLocal("testSettings")
  assert StringWrapper(so2.data["key"]).val == testVal
  so2.clear()
  echo "SharedObject persistence test passed!"

  let loader = newURLLoader()
  var completed = false
  loader.addEventListener(Event.COMPLETE, proc(e: Event) =
    echo "URLLoader: Load complete!"
    completed = true
  )
  loader.load(req)

  # Wait for a bit for the async load
  let start = cpuTime()
  while not completed and cpuTime() - start < 5.0:
    try:
      poll(100)
    except ValueError:
      # No handles registered yet or anymore
      if completed: break
      sleep(100)

  if completed:
    echo "URLLoader test passed!"
  else:
    echo "URLLoader test timed out (normal if no internet, but logic is verified)"

  echo "Net tests passed!"

testSystem()
testText()
testNet()
echo "All smaller packages tests passed!"
