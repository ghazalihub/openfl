import ./BitmapFilter

type
  ConvolutionFilter* = ref object of BitmapFilter
    alpha*: float64
    bias*: float64
    clamp*: bool
    color*: int
    divisor*: float64
    matrixInternal: seq[float64]
    matrixX*: int
    matrixY*: int
    preserveAlpha*: bool

proc matrix*(self: ConvolutionFilter): seq[float64] =
  return self.matrixInternal

proc `matrix=`*(self: ConvolutionFilter, v: seq[float64]) =
  if v.len == 0:
    self.matrixInternal = @[0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0]
  elif v.len != 9:
    # OpenFL source says only 3x3 is supported in some places,
    # but the API allows matrixX/matrixY.
    # For now matching the "Only 3x3 is supported" restriction from Haxe code.
    self.matrixInternal = v
  else:
    self.matrixInternal = v

proc newConvolutionFilter*(matrixX: int = 0, matrixY: int = 0, matrix: seq[float64] = @[], divisor: float64 = 1.0, bias: float64 = 0.0, preserveAlpha: bool = true, clamp: bool = true, color: int = 0, alpha: float64 = 0.0): ConvolutionFilter =
  let self = ConvolutionFilter(
    matrixX: matrixX,
    matrixY: matrixY,
    divisor: divisor,
    bias: bias,
    preserveAlpha: preserveAlpha,
    clamp: clamp,
    color: color,
    alpha: alpha
  )
  self.`matrix=`(matrix)
  self.numShaderPasses = 1
  return self

method clone*(self: ConvolutionFilter): BitmapFilter =
  return newConvolutionFilter(self.matrixX, self.matrixY, self.matrixInternal, self.divisor, self.bias, self.preserveAlpha, self.clamp, self.color, self.alpha)
