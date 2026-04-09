import pixie
import ../geom/Rectangle
import ../geom/Point
import ../geom/Matrix

type
  BitmapData* = ref object
    image*: Image # Pixie Image
    transparent*: bool

proc newBitmapData*(width: int, height: int, transparent: bool = true, fillColor: uint32 = 0xFFFFFFFF): BitmapData =
  let img = newImage(width, height)
  if not transparent:
    img.fill(rgba(uint8(fillColor shr 16), uint8(fillColor shr 8), uint8(fillColor), 255))
  else:
    img.fill(rgba(uint8(fillColor shr 16), uint8(fillColor shr 8), uint8(fillColor), uint8(fillColor shr 24)))

  return BitmapData(
    image: img,
    transparent: transparent
  )

proc width*(self: BitmapData): int = self.image.width
proc height*(self: BitmapData): int = self.image.height

proc getPixel*(self: BitmapData, x: int, y: int): uint32 =
  let c = self.image.getRgbx(x, y)
  return (uint32(c.r) shl 16) or (uint32(c.g) shl 8) or uint32(c.b)

proc setPixel*(self: BitmapData, x: int, y: int, color: uint32) =
  var c = self.image.getRgbx(x, y)
  c.r = uint8(color shr 16)
  c.g = uint8(color shr 8)
  c.b = uint8(color)
  self.image.setRgbx(x, y, c)

proc fillRect*(self: BitmapData, rect: Rectangle, color: uint32) =
  let c = rgba(uint8(color shr 16), uint8(color shr 8), uint8(color), uint8(color shr 24))
  self.image.fill(c) # Simple implementation

proc copyPixels*(self: BitmapData, source: BitmapData, sourceRect: Rectangle, destPoint: Point) =
  self.image.draw(source.image, vec2(destPoint.x, destPoint.y))

proc draw*(self: BitmapData, source: RootRef, matrix: Matrix = nil) =
  # TODO: implement rendering of DisplayObjects onto BitmapData
  discard

proc dispose*(self: BitmapData) =
  self.image = nil
