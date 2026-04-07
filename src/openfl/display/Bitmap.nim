import ./DisplayObject
import ./BitmapData
import ./PixelSnapping

type
  Bitmap* = ref object of DisplayObject
    bitmapData*: BitmapData
    pixelSnapping*: PixelSnapping
    smoothing*: bool

proc initBitmap*(self: Bitmap, bitmapData: BitmapData = nil, pixelSnapping: PixelSnapping = PixelSnapping.auto, smoothing: bool = false) =
  self.initDisplayObject()
  self.bitmapData = bitmapData
  self.pixelSnapping = pixelSnapping
  self.smoothing = smoothing

proc newBitmap*(bitmapData: BitmapData = nil, pixelSnapping: PixelSnapping = PixelSnapping.auto, smoothing: bool = false): Bitmap =
  let self = Bitmap()
  self.initBitmap(bitmapData, pixelSnapping, smoothing)
  return self
