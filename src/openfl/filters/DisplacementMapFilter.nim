import ./BitmapFilter
import ./DisplacementMapFilterMode
import ../geom/Point

type
  DisplacementMapFilter* = ref object of BitmapFilter
    alphaInternal: float64
    colorInternal: int
    componentXInternal: int
    componentYInternal: int
    mapBitmapInternal: RootRef # Placeholder for BitmapData
    mapPointInternal: Point
    modeInternal: DisplacementMapFilterMode
    scaleXInternal: float64
    scaleYInternal: float64

proc alpha*(self: DisplacementMapFilter): float64 = self.alphaInternal
proc `alpha=`*(self: DisplacementMapFilter, value: float64) =
  if value != self.alphaInternal: self.renderDirty = true
  self.alphaInternal = value

proc color*(self: DisplacementMapFilter): int = self.colorInternal
proc `color=`*(self: DisplacementMapFilter, value: int) =
  if value != self.colorInternal: self.renderDirty = true
  self.colorInternal = value

proc componentX*(self: DisplacementMapFilter): int = self.componentXInternal
proc `componentX=`*(self: DisplacementMapFilter, value: int) =
  if value != self.componentXInternal: self.renderDirty = true
  self.componentXInternal = value

proc componentY*(self: DisplacementMapFilter): int = self.componentYInternal
proc `componentY=`*(self: DisplacementMapFilter, value: int) =
  if value != self.componentYInternal: self.renderDirty = true
  self.componentYInternal = value

proc scaleX*(self: DisplacementMapFilter): float64 = self.scaleXInternal
proc `scaleX=`*(self: DisplacementMapFilter, value: float64) =
  if value != self.scaleXInternal: self.renderDirty = true
  self.scaleXInternal = value

proc scaleY*(self: DisplacementMapFilter): float64 = self.scaleYInternal
proc `scaleY=`*(self: DisplacementMapFilter, value: float64) =
  if value != self.scaleYInternal: self.renderDirty = true
  self.scaleYInternal = value

proc mapBitmap*(self: DisplacementMapFilter): RootRef = self.mapBitmapInternal
proc `mapBitmap=`*(self: DisplacementMapFilter, value: RootRef) =
  if value != self.mapBitmapInternal: self.renderDirty = true
  self.mapBitmapInternal = value

proc mapPoint*(self: DisplacementMapFilter): Point = self.mapPointInternal
proc `mapPoint=`*(self: DisplacementMapFilter, value: Point) =
  if value != self.mapPointInternal: self.renderDirty = true
  self.mapPointInternal = value

proc mode*(self: DisplacementMapFilter): DisplacementMapFilterMode = self.modeInternal
proc `mode=`*(self: DisplacementMapFilter, value: DisplacementMapFilterMode) =
  if value != self.modeInternal: self.renderDirty = true
  self.modeInternal = value

proc newDisplacementMapFilter*(mapBitmap: RootRef = nil, mapPoint: Point = nil, componentX: int = 0, componentY: int = 0, scaleX: float64 = 0.0, scaleY: float64 = 0.0, mode: DisplacementMapFilterMode = DisplacementMapFilterMode.WRAP, color: int = 0, alpha: float64 = 0.0): DisplacementMapFilter =
  let self = DisplacementMapFilter(
    mapBitmapInternal: mapBitmap,
    mapPointInternal: if mapPoint.isNil: newPoint() else: mapPoint,
    componentXInternal: componentX,
    componentYInternal: componentY,
    scaleXInternal: scaleX,
    scaleYInternal: scaleY,
    modeInternal: mode,
    colorInternal: color,
    alphaInternal: alpha,
    needSecondBitmapData: true,
    preserveObject: false,
    renderDirty: true
  )
  self.numShaderPasses = 1
  return self

method clone*(self: DisplacementMapFilter): BitmapFilter =
  return newDisplacementMapFilter(self.mapBitmapInternal, self.mapPointInternal.clone(), self.componentXInternal, self.componentYInternal, self.scaleXInternal, self.scaleYInternal, self.modeInternal, self.colorInternal, self.alphaInternal)
