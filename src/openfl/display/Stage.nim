import ./DisplayObjectContainer
import ./StageScaleMode
import ./StageAlign
import ./StageDisplayState

type
  Stage* = ref object of DisplayObjectContainer
    align*: StageAlign
    displayState*: StageDisplayState
    frameRate*: float64
    scaleMode*: StageScaleMode
    stageWidth*: int
    stageHeight*: int

proc initStage*(self: Stage, width: int = 0, height: int = 0) =
  self.initDisplayObjectContainer()
  self.stageWidth = width
  self.stageHeight = height
  self.frameRate = 60.0
  self.scaleMode = StageScaleMode.showAll
  self.align = StageAlign.topLeft

proc newStage*(width: int = 0, height: int = 0): Stage =
  let self = Stage()
  self.initStage(width, height)
  return self
