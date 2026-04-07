import ./BitmapData
import ../geom/Matrix

type
  Graphics* = ref object
    # TODO: Implement drawing commands and context

proc newGraphics*(): Graphics =
  return Graphics()

proc beginFill*(self: Graphics, color: uint32, alpha: float64 = 1.0) =
  # TODO: implement
  discard

proc endFill*(self: Graphics) =
  # TODO: implement
  discard

proc drawRect*(self: Graphics, x: float64, y: float64, width: float64, height: float64) =
  # TODO: implement
  discard

proc clear*(self: Graphics) =
  # TODO: implement
  discard
