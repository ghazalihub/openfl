import sdl2
import ./Stage

type
  Window* = ref object
    sdlWindow*: WindowPtr
    sdlRenderer*: RendererPtr
    stage*: Stage

proc newWindow*(width: int, height: int, title: string = "OpenFL"): Window =
  let sdlWin = createWindow(title, SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, int32(width), int32(height), SDL_WINDOW_SHOWN)
  let sdlRen = createRenderer(sdlWin, -1, Renderer_Accelerated or Renderer_PresentVsync)
  let stage = newStage(width, height)

  return Window(
    sdlWindow: sdlWin,
    sdlRenderer: sdlRen,
    stage: stage
  )

proc close*(self: Window) =
  destroyRenderer(self.sdlRenderer)
  destroyWindow(self.sdlWindow)
