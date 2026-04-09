type
  ColorTransform* = ref object
    alphaMultiplier*: float64
    alphaOffset*: float64
    blueMultiplier*: float64
    blueOffset*: float64
    greenMultiplier*: float64
    greenOffset*: float64
    redMultiplier*: float64
    redOffset*: float64

proc newColorTransform*(redMultiplier: float64 = 1, greenMultiplier: float64 = 1, blueMultiplier: float64 = 1, alphaMultiplier: float64 = 1, redOffset: float64 = 0, greenOffset: float64 = 0, blueOffset: float64 = 0, alphaOffset: float64 = 0): ColorTransform =
  ColorTransform(
    redMultiplier: redMultiplier,
    greenMultiplier: greenMultiplier,
    blueMultiplier: blueMultiplier,
    alphaMultiplier: alphaMultiplier,
    redOffset: redOffset,
    greenOffset: greenOffset,
    blueOffset: blueOffset,
    alphaOffset: alphaOffset
  )

proc color*(self: ColorTransform): int =
  (int(self.redOffset) shl 16) or (int(self.greenOffset) shl 8) or int(self.blueOffset)

proc `color=`*(self: ColorTransform, value: int) =
  self.redOffset = float64((value shr 16) and 0xFF)
  self.greenOffset = float64((value shr 8) and 0xFF)
  self.blueOffset = float64(value and 0xFF)
  self.redMultiplier = 0
  self.greenMultiplier = 0
  self.blueMultiplier = 0

proc concat*(self: ColorTransform, second: ColorTransform) =
  self.redOffset = second.redOffset * self.redMultiplier + self.redOffset
  self.greenOffset = second.greenOffset * self.greenMultiplier + self.greenOffset
  self.blueOffset = second.blueOffset * self.blueMultiplier + self.blueOffset
  self.alphaOffset = second.alphaOffset * self.alphaMultiplier + self.alphaOffset

  self.redMultiplier *= second.redMultiplier
  self.greenMultiplier *= second.greenMultiplier
  self.blueMultiplier *= second.blueMultiplier
  self.alphaMultiplier *= second.alphaMultiplier

proc `$`*(self: ColorTransform): string =
  "(redMultiplier=" & $self.redMultiplier & ", greenMultiplier=" & $self.greenMultiplier & ", blueMultiplier=" & $self.blueMultiplier & ", alphaMultiplier=" & $self.alphaMultiplier & ", redOffset=" & $self.redOffset & ", greenOffset=" & $self.greenOffset & ", blueOffset=" & $self.blueOffset & ", alphaOffset=" & $self.alphaOffset & ")"

proc clone*(self: ColorTransform): ColorTransform =
  newColorTransform(self.redMultiplier, self.greenMultiplier, self.blueMultiplier, self.alphaMultiplier, self.redOffset, self.greenOffset, self.blueOffset, self.alphaOffset)

proc copyFrom*(self: ColorTransform, ct: ColorTransform) =
  self.redMultiplier = ct.redMultiplier
  self.greenMultiplier = ct.greenMultiplier
  self.blueMultiplier = ct.blueMultiplier
  self.alphaMultiplier = ct.alphaMultiplier
  self.redOffset = ct.redOffset
  self.greenOffset = ct.greenOffset
  self.blueOffset = ct.blueOffset
  self.alphaOffset = ct.alphaOffset

proc combine*(self: ColorTransform, ct: ColorTransform) =
  self.redMultiplier *= ct.redMultiplier
  self.greenMultiplier *= ct.greenMultiplier
  self.blueMultiplier *= ct.blueMultiplier
  self.alphaMultiplier *= ct.alphaMultiplier
  self.redOffset += ct.redOffset
  self.greenOffset += ct.greenOffset
  self.blueOffset += ct.blueOffset
  self.alphaOffset += ct.alphaOffset

proc identity*(self: ColorTransform) =
  self.redMultiplier = 1
  self.greenMultiplier = 1
  self.blueMultiplier = 1
  self.alphaMultiplier = 1
  self.redOffset = 0
  self.greenOffset = 0
  self.blueOffset = 0
  self.alphaOffset = 0

proc invert*(self: ColorTransform) =
  self.redMultiplier = if self.redMultiplier != 0: 1.0 / self.redMultiplier else: 1.0
  self.greenMultiplier = if self.greenMultiplier != 0: 1.0 / self.greenMultiplier else: 1.0
  self.blueMultiplier = if self.blueMultiplier != 0: 1.0 / self.blueMultiplier else: 1.0
  self.alphaMultiplier = if self.alphaMultiplier != 0: 1.0 / self.alphaMultiplier else: 1.0
  self.redOffset = -self.redOffset
  self.greenOffset = -self.greenOffset
  self.blueOffset = -self.blueOffset
  self.alphaOffset = -self.alphaOffset

proc equals*(self: ColorTransform, ct: ColorTransform, ignoreAlphaMultiplier: bool = false): bool =
  not ct.isNil and
    self.redMultiplier == ct.redMultiplier and
    self.greenMultiplier == ct.greenMultiplier and
    self.blueMultiplier == ct.blueMultiplier and
    (ignoreAlphaMultiplier or self.alphaMultiplier == ct.alphaMultiplier) and
    self.redOffset == ct.redOffset and
    self.greenOffset == ct.greenOffset and
    self.blueOffset == ct.blueOffset and
    self.alphaOffset == ct.alphaOffset

proc isDefault*(self: ColorTransform, ignoreAlphaMultiplier: bool = false): bool =
  if ignoreAlphaMultiplier:
    self.redMultiplier == 1 and
    self.greenMultiplier == 1 and
    self.blueMultiplier == 1 and
    self.redOffset == 0 and
    self.greenOffset == 0 and
    self.blueOffset == 0 and
    self.alphaOffset == 0
  else:
    self.redMultiplier == 1 and
    self.greenMultiplier == 1 and
    self.blueMultiplier == 1 and
    self.alphaMultiplier == 1 and
    self.redOffset == 0 and
    self.greenOffset == 0 and
    self.blueOffset == 0 and
    self.alphaOffset == 0
