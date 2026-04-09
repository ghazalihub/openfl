import pixie
import ./BitmapData
import ../geom/Matrix

type
  Graphics* = ref object
    context*: Context
    image*: Image
    fillColor: Color
    hasFill: bool

proc newGraphics*(width: int = 1, height: int = 1): Graphics =
  let img = newImage(width, height)
  return Graphics(
    image: img,
    context: newContext(img),
    hasFill: false
  )

proc beginFill*(self: Graphics, color: uint32, alpha: float64 = 1.0) =
  self.fillColor = rgba(
    uint8((color shr 16) and 0xFF),
    uint8((color shr 8) and 0xFF),
    uint8(color and 0xFF),
    uint8(alpha * 255)
  )
  self.hasFill = true

proc endFill*(self: Graphics) =
  self.hasFill = false

proc drawRect*(self: Graphics, x: float64, y: float64, width: float64, height: float64) =
  if self.hasFill:
    self.context.fillStyle = self.fillColor
    self.context.fillRect(rect(x, y, width, height))

proc drawCircle*(self: Graphics, x: float64, y: float64, radius: float64) =
  if self.hasFill:
    self.context.fillStyle = self.fillColor
    self.context.fillCircle(circle(vec2(x, y), radius))

proc lineTo*(self: Graphics, x: float64, y: float64) =
  self.context.lineTo(vec2(x, y))

proc moveTo*(self: Graphics, x: float64, y: float64) =
  self.context.moveTo(vec2(x, y))

proc clear*(self: Graphics) =
  self.image.fill(rgba(0, 0, 0, 0))
  self.hasFill = false
