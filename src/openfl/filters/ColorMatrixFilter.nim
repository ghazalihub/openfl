import ./BitmapFilter

type
  ColorMatrixFilter* = ref object of BitmapFilter
    matrixInternal: seq[float64]

proc matrix*(self: ColorMatrixFilter): seq[float64] =
  return self.matrixInternal

proc `matrix=`*(self: ColorMatrixFilter, value: seq[float64]) =
  if value.len == 0:
    self.matrixInternal = @[1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0]
  else:
    self.matrixInternal = value

proc newColorMatrixFilter*(matrix: seq[float64] = @[]): ColorMatrixFilter =
  let self = ColorMatrixFilter()
  self.`matrix=`(matrix)
  self.numShaderPasses = 1
  self.needSecondBitmapData = false
  return self

method clone*(self: ColorMatrixFilter): BitmapFilter =
  return newColorMatrixFilter(self.matrixInternal)
