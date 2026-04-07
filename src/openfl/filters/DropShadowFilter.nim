import std/math
import ./BitmapFilter
import ./BitmapFilterQuality

type
  DropShadowFilter* = ref object of BitmapFilter
    alphaInternal: float64
    angleInternal: float64
    blurXInternal: float64
    blurYInternal: float64
    colorInternal: int
    distanceInternal: float64
    hideObjectInternal: bool
    innerInternal: bool
    knockoutInternal: bool
    qualityInternal: int
    strengthInternal: float64
    offsetX: float64
    offsetY: float64
    horizontalPasses: int
    verticalPasses: int

proc updateSize(self: DropShadowFilter) =
  self.offsetX = self.distanceInternal * cos(self.angleInternal * PI / 180.0)
  self.offsetY = self.distanceInternal * sin(self.angleInternal * PI / 180.0)
  self.topExtension = int(ceil((if self.offsetY < 0: -self.offsetY else: 0) + self.blurYInternal))
  self.bottomExtension = int(ceil((if self.offsetY > 0: self.offsetY else: 0) + self.blurYInternal))
  self.leftExtension = int(ceil((if self.offsetX < 0: -self.offsetX else: 0) + self.blurXInternal))
  self.rightExtension = int(ceil((if self.offsetX > 0: self.offsetX else: 0) + self.blurXInternal))
  self.horizontalPasses = if self.blurXInternal <= 0: 0 else: int(round(self.blurXInternal * (float64(self.qualityInternal) / 4.0))) + 1
  self.verticalPasses = if self.blurYInternal <= 0: 0 else: int(round(self.blurYInternal * (float64(self.qualityInternal) / 4.0))) + 1
  self.numShaderPasses = self.horizontalPasses + self.verticalPasses + (if self.innerInternal: 2 else: 1)

proc alpha*(self: DropShadowFilter): float64 = self.alphaInternal
proc `alpha=`*(self: DropShadowFilter, value: float64) =
  if value != self.alphaInternal: self.renderDirty = true
  self.alphaInternal = value

proc angle*(self: DropShadowFilter): float64 = self.angleInternal
proc `angle=`*(self: DropShadowFilter, value: float64) =
  if value != self.angleInternal:
    self.angleInternal = value
    self.renderDirty = true
    self.updateSize()

proc blurX*(self: DropShadowFilter): float64 = self.blurXInternal
proc `blurX=`*(self: DropShadowFilter, value: float64) =
  if value != self.blurXInternal:
    self.blurXInternal = value
    self.renderDirty = true
    self.updateSize()

proc blurY*(self: DropShadowFilter): float64 = self.blurYInternal
proc `blurY=`*(self: DropShadowFilter, value: float64) =
  if value != self.blurYInternal:
    self.blurYInternal = value
    self.renderDirty = true
    self.updateSize()

proc color*(self: DropShadowFilter): int = self.colorInternal
proc `color=`*(self: DropShadowFilter, value: int) =
  if value != self.colorInternal: self.renderDirty = true
  self.colorInternal = value

proc distance*(self: DropShadowFilter): float64 = self.distanceInternal
proc `distance=`*(self: DropShadowFilter, value: float64) =
  if value != self.distanceInternal:
    self.distanceInternal = value
    self.renderDirty = true
    self.updateSize()

proc hideObject*(self: DropShadowFilter): bool = self.hideObjectInternal
proc `hideObject=`*(self: DropShadowFilter, value: bool) =
  if value != self.hideObjectInternal: self.renderDirty = true
  self.hideObjectInternal = value

proc inner*(self: DropShadowFilter): bool = self.innerInternal
proc `inner=`*(self: DropShadowFilter, value: bool) =
  if value != self.innerInternal: self.renderDirty = true
  self.innerInternal = value

proc knockout*(self: DropShadowFilter): bool = self.knockoutInternal
proc `knockout=`*(self: DropShadowFilter, value: bool) =
  if value != self.knockoutInternal: self.renderDirty = true
  self.knockoutInternal = value

proc quality*(self: DropShadowFilter): int = self.qualityInternal
proc `quality=`*(self: DropShadowFilter, value: int) =
  if value != self.qualityInternal: self.renderDirty = true
  self.qualityInternal = value
  self.updateSize()

proc strength*(self: DropShadowFilter): float64 = self.strengthInternal
proc `strength=`*(self: DropShadowFilter, value: float64) =
  if value != self.strengthInternal: self.renderDirty = true
  self.strengthInternal = value

proc newDropShadowFilter*(distance: float64 = 4, angle: float64 = 45, color: int = 0, alpha: float64 = 1, blurX: float64 = 4, blurY: float64 = 4, strength: float64 = 1, quality: int = 1, inner: bool = false, knockout: bool = false, hideObject: bool = false): DropShadowFilter =
  let self = DropShadowFilter(
    distanceInternal: distance,
    angleInternal: angle,
    colorInternal: color,
    alphaInternal: alpha,
    blurXInternal: blurX,
    blurYInternal: blurY,
    strengthInternal: strength,
    qualityInternal: quality,
    innerInternal: inner,
    knockoutInternal: knockout,
    hideObjectInternal: hideObject,
    needSecondBitmapData: true,
    preserveObject: true,
    renderDirty: true
  )
  self.updateSize()
  return self

method clone*(self: DropShadowFilter): BitmapFilter =
  return newDropShadowFilter(self.distanceInternal, self.angleInternal, self.colorInternal, self.alphaInternal, self.blurXInternal, self.blurYInternal, self.strengthInternal, self.qualityInternal, self.innerInternal, self.knockoutInternal, self.hideObjectInternal)
