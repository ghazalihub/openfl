import std/math
import ./BitmapFilter
import ./BitmapFilterQuality

type
  BlurFilter* = ref object of BitmapFilter
    blurXInternal: float64
    blurYInternal: float64
    qualityInternal: int
    horizontalPasses: int
    verticalPasses: int

proc padFor(self: BlurFilter, value: float64): int =
  if value <= 0: return 0
  let passes = if self.qualityInternal > 0: self.qualityInternal else: 1
  let reach = value * float64(passes) * 3.0 # Simplified for Nim port, matching lime logic
  return int(ceil(reach)) + 2

proc blurX*(self: BlurFilter): float64 =
  return self.blurXInternal

proc `blurX=`*(self: BlurFilter, value: float64) =
  if value != self.blurXInternal:
    self.blurXInternal = value
    self.renderDirty = true
    let p = self.padFor(value)
    self.leftExtension = p
    self.rightExtension = p

proc blurY*(self: BlurFilter): float64 =
  return self.blurYInternal

proc `blurY=`*(self: BlurFilter, value: float64) =
  if value != self.blurYInternal:
    self.blurYInternal = value
    self.renderDirty = true
    let p = self.padFor(value)
    self.topExtension = p
    self.bottomExtension = p

proc quality*(self: BlurFilter): int =
  return self.qualityInternal

proc `quality=`*(self: BlurFilter, value: int) =
  self.horizontalPasses = if self.blurXInternal <= 0: 0 else: int(round(self.blurXInternal * (float64(value) / 4.0))) + 1
  self.verticalPasses = if self.blurYInternal <= 0: 0 else: int(round(self.blurYInternal * (float64(value) / 4.0))) + 1
  self.numShaderPasses = self.horizontalPasses + self.verticalPasses

  if value != self.qualityInternal:
    self.renderDirty = true
  self.qualityInternal = value
  self.`blurX=`(self.blurXInternal)
  self.`blurY=`(self.blurYInternal)

proc newBlurFilter*(blurX: float64 = 4, blurY: float64 = 4, quality: int = 1): BlurFilter =
  let self = BlurFilter()
  self.blurXInternal = blurX
  self.blurYInternal = blurY
  self.qualityInternal = quality
  self.needSecondBitmapData = true
  self.preserveObject = false
  self.renderDirty = true
  self.`quality=`(quality)
  return self

method clone*(self: BlurFilter): BitmapFilter =
  return newBlurFilter(self.blurXInternal, self.blurYInternal, self.qualityInternal)
