import std/math
import ./BitmapFilter
import ./BitmapFilterQuality

type
  GlowFilter* = ref object of BitmapFilter
    alphaInternal: float64
    blurXInternal: float64
    blurYInternal: float64
    colorInternal: int
    innerInternal: bool
    knockoutInternal: bool
    qualityInternal: int
    strengthInternal: float64
    horizontalPasses: int
    verticalPasses: int

proc updateSize(self: GlowFilter) =
  self.leftExtension = if self.blurXInternal > 0: int(ceil(self.blurXInternal * 1.5)) else: 0
  self.rightExtension = self.leftExtension
  self.topExtension = if self.blurYInternal > 0: int(ceil(self.blurYInternal * 1.5)) else: 0
  self.bottomExtension = self.topExtension

  self.horizontalPasses = if self.blurXInternal <= 0: 0 else: int(round(self.blurXInternal * (float64(self.qualityInternal) / 4.0))) + 1
  self.verticalPasses = if self.blurYInternal <= 0: 0 else: int(round(self.blurYInternal * (float64(self.qualityInternal) / 4.0))) + 1
  self.numShaderPasses = self.horizontalPasses + self.verticalPasses + (if self.innerInternal: 2 else: 1)

proc alpha*(self: GlowFilter): float64 = self.alphaInternal
proc `alpha=`*(self: GlowFilter, value: float64) =
  if value != self.alphaInternal: self.renderDirty = true
  self.alphaInternal = value

proc blurX*(self: GlowFilter): float64 = self.blurXInternal
proc `blurX=`*(self: GlowFilter, value: float64) =
  if value != self.blurXInternal:
    self.blurXInternal = value
    self.renderDirty = true
    self.updateSize()

proc blurY*(self: GlowFilter): float64 = self.blurYInternal
proc `blurY=`*(self: GlowFilter, value: float64) =
  if value != self.blurYInternal:
    self.blurYInternal = value
    self.renderDirty = true
    self.updateSize()

proc color*(self: GlowFilter): int = self.colorInternal
proc `color=`*(self: GlowFilter, value: int) =
  if value != self.colorInternal: self.renderDirty = true
  self.colorInternal = value

proc inner*(self: GlowFilter): bool = self.innerInternal
proc `inner=`*(self: GlowFilter, value: bool) =
  if value != self.innerInternal:
    self.renderDirty = true
    self.innerInternal = value
    self.updateSize()

proc knockout*(self: GlowFilter): bool = self.knockoutInternal
proc `knockout=`*(self: GlowFilter, value: bool) =
  if value != self.knockoutInternal:
    self.renderDirty = true
    self.knockoutInternal = value
    self.updateSize()

proc quality*(self: GlowFilter): int = self.qualityInternal
proc `quality=`*(self: GlowFilter, value: int) =
  if value != self.qualityInternal:
    self.renderDirty = true
    self.qualityInternal = value
    self.updateSize()

proc strength*(self: GlowFilter): float64 = self.strengthInternal
proc `strength=`*(self: GlowFilter, value: float64) =
  if value != self.strengthInternal: self.renderDirty = true
  self.strengthInternal = value

proc newGlowFilter*(color: int = 0xFF0000, alpha: float64 = 1, blurX: float64 = 6, blurY: float64 = 6, strength: float64 = 2, quality: int = 1, inner: bool = false, knockout: bool = false): GlowFilter =
  let self = GlowFilter(
    colorInternal: color,
    alphaInternal: alpha,
    blurXInternal: blurX,
    blurYInternal: blurY,
    strengthInternal: strength,
    innerInternal: inner,
    knockoutInternal: knockout,
    qualityInternal: quality,
    needSecondBitmapData: true,
    preserveObject: true,
    renderDirty: true
  )
  self.updateSize()
  return self

method clone*(self: GlowFilter): BitmapFilter =
  return newGlowFilter(self.colorInternal, self.alphaInternal, self.blurXInternal, self.blurYInternal, self.strengthInternal, self.qualityInternal, self.innerInternal, self.knockoutInternal)
