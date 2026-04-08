import sdl2
import ./Window
import ../events/EventDispatcher
import ../events/Event
import ../ui/Keyboard
import ../ui/KeyLocation

type
  Application* = ref object of EventDispatcher
    windows*: seq[Window]

proc newApplication*(): Application =
  discard sdl2.init(INIT_EVERYTHING)
  let self = Application(windows: @[])
  self.initEventDispatcher()
  return self

proc createWindow*(self: Application, width: int, height: int, title: string = "OpenFL"): Window =
  let win = newWindow(width, height, title)
  self.windows.add(win)
  return win

proc exec*(self: Application): int =
  var running = true
  var event = sdl2.defaultEvent

  while running:
    while pollEvent(event):
      case event.kind:
      case QuitEvent:
        running = false
      case KeyDown:
        # TODO: map to openfl KeyboardEvent
        discard
      case MouseButtonDown:
        # TODO: map to openfl MouseEvent
        discard
      else:
        discard

    # Simple render loop
    for win in self.windows:
      setRenderDrawColor(win.sdlRenderer, 255, 255, 255, 255) # Clear to white
      clear(win.sdlRenderer)

      # TODO: implement hierarchical rendering of the stage

      present(win.sdlRenderer)

    delay(16) # ~60fps

  return 0
