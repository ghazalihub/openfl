import sdl2
import ./Window
import ../events/EventDispatcher

type
  Application* = ref object of EventDispatcher
    windows*: seq[Window]

proc newApplication*(): Application =
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
      if event.kind == QuitEvent:
        running = false
        break

    # Simple render loop
    for win in self.windows:
      setRenderDrawColor(win.sdlRenderer, 0, 0, 0, 255)
      clear(win.sdlRenderer)
      # TODO: Render stage
      present(win.sdlRenderer)

    delay(16) # ~60fps

  return 0
