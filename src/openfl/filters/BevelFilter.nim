import std/math
import ./BitmapFilter
import ./BitmapFilterQuality
import ./BitmapFilterType

type
  BevelFilter* = ref object of BitmapFilter
    blurXInternal: float64
    blurYInternal: float64
    distanceInternal: float64
    angleInternal: float64
    highlightColorInternal: uint32
    highlightAlphaInternal: float64
    shadowColorInternal: uint32
    shadowAlphaInternal: float64
    qualityInternal: int
    strengthInternal: float64
    typeInternal: BitmapFilterType
    knockoutInternal: bool
    horizontalPasses: int
    verticalPasses: int

proc updateSize(self: BevelFilter) =
  let offsetX = if self.typeInternal != BitmapFilterType.INNER: int(ceil(self.distanceInternal * cos(self.angleInternal * PI / 180.0))) else: 0
  let offsetY = if self.typeInternal != BitmapFilterType.INNER: int(ceil(self.distanceInternal * sin(self.angleInternal * PI / 180.0))) else: 0
  self.topExtension = int(ceil((if offsetY < 0: float64(-offsetY) else: 0.0) + self.blurYInternal))
  self.bottomExtension = int(ceil((if offsetY > 0: float64(offsetY) else: 0.0) + self.blurYInternal))
  self.leftExtension = int(ceil((if offsetX < 0: float64(-offsetX) else: 0.0) + self.blurXInternal))
  self.rightExtension = int(ceil((if offsetX > 0: float64(offsetX) else: 0.0) + self.blurXInternal))

proc blurX*(self: BevelFilter): float64 = self.blurXInternal
proc `blurX=`*(self: BevelFilter, value: float64) =
  let val = clamp(value, 0.0, 255.0)
  if val != self.blurXInternal:
    self.blurXInternal = val
    self.renderDirty = true
    self.updateSize()

proc blurY*(self: BevelFilter): float64 = self.blurYInternal
proc `blurY=`*(self: BevelFilter, value: float64) =
  let val = clamp(value, 0.0, 255.0)
  if val != self.blurYInternal:
    self.blurYInternal = val
    self.renderDirty = true
    self.updateSize()

proc distance*(self: BevelFilter): float64 = self.distanceInternal
proc `distance=`*(self: BevelFilter, value: float64) =
  if value != self.distanceInternal:
    self.distanceInternal = value
    self.updateSize()
    self.renderDirty = true

proc angle*(self: BevelFilter): float64 = self.angleInternal
proc `angle=`*(self: BevelFilter, value: float64) =
  if value != self.angleInternal:
    self.angleInternal = value
    self.updateSize()
    self.renderDirty = true

proc highlightColor*(self: BevelFilter): uint32 = self.highlightColorInternal
proc `highlightColor=`*(self: BevelFilter, value: uint32) =
  let val = if value > 0xFFFFFF: 0xFFFFFF'u32 else: value
  if val != self.highlightColorInternal:
    self.highlightColorInternal = val
    self.renderDirty = true

proc highlightAlpha*(self: BevelFilter): float64 = self.highlightAlphaInternal
proc `highlightAlpha=`*(self: BevelFilter, value: float64) =
  let val = clamp(value, 0.0, 1.0)
  if val != self.highlightAlphaInternal:
    self.highlightAlphaInternal = val
    self.renderDirty = true

proc shadowColor*(self: BevelFilter): uint32 = self.shadowColorInternal
proc `shadowColor=`*(self: BevelFilter, value: uint32) =
  let val = if value > 0xFFFFFF: 0xFFFFFF'u32 else: value
  if val != self.shadowColorInternal:
    self.shadowColorInternal = val
    self.renderDirty = true

proc shadowAlpha*(self: BevelFilter): float64 = self.shadowAlphaInternal
proc `shadowAlpha=`*(self: BevelFilter, value: float64) =
  let val = clamp(value, 0.0, 1.0)
  if val != self.shadowAlphaInternal:
    self.shadowAlphaInternal = val
    self.renderDirty = true

proc quality*(self: BevelFilter): int = self.qualityInternal
proc `quality=`*(self: BevelFilter, value: int) =
  let val = clamp(value, 1, 15)
  self.horizontalPasses = if self.blurXInternal <= 0: 0 else: int(round(self.blurXInternal * (float64(val) / 4.0)))
  self.verticalPasses = if self.blurYInternal <= 0: 0 else: int(round(self.blurYInternal * (float64(val) / 4.0)))
  self.numShaderPasses = self.horizontalPasses + self.verticalPasses + 1
  if val != self.qualityInternal: self.renderDirty = true
  self.qualityInternal = val

proc strength*(self: BevelFilter): float64 = self.strengthInternal
proc `strength=`*(self: BevelFilter, value: float64) =
  let val = clamp(value, 1.0, 255.0)
  if val != self.strengthInternal:
    self.strengthInternal = val
    self.renderDirty = true

proc `type`*(self: BevelFilter): BitmapFilterType = self.typeInternal
proc `type=`*(self: BevelFilter, value: BitmapFilterType) =
  if value != self.typeInternal:
    self.typeInternal = value
    self.renderDirty = true

proc knockout*(self: BevelFilter): bool = self.knockoutInternal
proc `knockout=`*(self: BevelFilter, value: bool) =
  if value != self.knockoutInternal:
    self.knockoutInternal = value
    self.renderDirty = true

proc newBevelFilter*(distance: float64 = 4.0, angle: float64 = 45, highlightColor: uint32 = 0xFFFFFF, highlightAlpha: float64 = 1.0, shadowColor: uint32 = 0x000000, shadowAlpha: float64 = 1.0, blurX: float64 = 4.0, blurY: float64 = 4.0, strength: float64 = 1, quality: int = 1, filterType: BitmapFilterType = BitmapFilterType.INNER, knockout: bool = false): BevelFilter =
  let self = BevelFilter(
    distanceInternal: distance,
    angleInternal: angle,
    highlightColorInternal: highlightColor,
    highlightAlphaInternal: highlightAlpha,
    shadowColorInternal: shadowColor,
    shadowAlphaInternal: shadowAlpha,
    blurXInternal: blurX,
    blurYInternal: blurY,
    strengthInternal: strength,
    qualityInternal: quality,
    typeInternal: filterType,
    knockoutInternal: knockout,
    needSecondBitmapData: true,
    preserveObject: true,
    renderDirty: true
  )
  self.updateSize()
  return self

method clone*(self: BevelFilter): BitmapFilter =
  return newBevelFilter(self.distanceInternal, self.angleInternal, self.highlightColorInternal, self.highlightAlphaInternal, self.shadowColorInternal, self.shadowAlphaInternal, self.blurXInternal, self.blurYInternal, self.strengthInternal, self.qualityInternal, self.typeInternal, self.knockoutInternal)
