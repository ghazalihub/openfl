import ../events/EventDispatcher
import ../geom/Matrix
import ../geom/Rectangle
import ../geom/Transform
import ../display/BlendMode
import ../filters/BitmapFilter

type
  DisplayObject* = ref object of EventDispatcher
    alphaInternal: float64
    blendMode*: BlendMode
    cacheAsBitmap*: bool
    filtersInternal: seq[BitmapFilter]
    mask*: DisplayObject
    name*: string
    parent*: RootRef # DisplayObjectContainer
    rotationInternal: float64
    scaleXInternal: float64
    scaleYInternal: float64
    visibleInternal: bool
    transformInternal: Transform
    # Internal state
    kindTransform: Matrix
    kindRenderDirty*: bool
    kindTransformDirty: bool

proc x*(self: DisplayObject): float64 = self.kindTransform.tx
proc `x=`*(self: DisplayObject, value: float64) =
  self.kindTransform.tx = value
  self.kindTransformDirty = true

proc y*(self: DisplayObject): float64 = self.kindTransform.ty
proc `y=`*(self: DisplayObject, value: float64) =
  self.kindTransform.ty = value
  self.kindTransformDirty = true

proc initDisplayObject*(self: DisplayObject) =
  self.initEventDispatcher()
  self.alphaInternal = 1.0
  self.blendMode = BlendMode.NORMAL
  self.visibleInternal = true
  self.scaleXInternal = 1.0
  self.scaleYInternal = 1.0
  self.kindTransform = newMatrix()
  self.name = ""

proc newDisplayObject*(): DisplayObject =
  let self = DisplayObject()
  self.initDisplayObject()
  return self
