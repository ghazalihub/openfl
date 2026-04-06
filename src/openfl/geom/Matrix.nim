import math
import ./Point

type
  Matrix* = ref object
    a*: float64
    b*: float64
    c*: float64
    d*: float64
    tx*: float64
    ty*: float64

proc newMatrix*(a: float64 = 1, b: float64 = 0, c: float64 = 0, d: float64 = 1, tx: float64 = 0, ty: float64 = 0): Matrix =
  Matrix(a: a, b: b, c: c, d: d, tx: tx, ty: ty)

proc clone*(self: Matrix): Matrix =
  newMatrix(self.a, self.b, self.c, self.d, self.tx, self.ty)

proc concat*(self: Matrix, m: Matrix) =
  let a1 = self.a * m.a + self.b * m.c
  self.b = self.a * m.b + self.b * m.d
  self.a = a1

  let c1 = self.c * m.a + self.d * m.c
  self.d = self.c * m.b + self.d * m.d
  self.c = c1

  let tx1 = self.tx * m.a + self.ty * m.c + m.tx
  self.ty = self.tx * m.b + self.ty * m.d + m.ty
  self.tx = tx1

proc copyFrom*(self: Matrix, sourceMatrix: Matrix) =
  self.a = sourceMatrix.a
  self.b = sourceMatrix.b
  self.c = sourceMatrix.c
  self.d = sourceMatrix.d
  self.tx = sourceMatrix.tx
  self.ty = sourceMatrix.ty

proc createBox*(self: Matrix, scaleX: float64, scaleY: float64, rotation: float64 = 0, tx: float64 = 0, ty: float64 = 0) =
  if rotation != 0:
    let cosRotation = cos(rotation)
    let sinRotation = sin(rotation)

    self.a = cosRotation * scaleX
    self.b = sinRotation * scaleY
    self.c = -sinRotation * scaleX
    self.d = cosRotation * scaleY
  else:
    self.a = scaleX
    self.b = 0
    self.c = 0
    self.d = scaleY

  self.tx = tx
  self.ty = ty

proc createGradientBox*(self: Matrix, width: float64, height: float64, rotation: float64 = 0, tx: float64 = 0, ty: float64 = 0) =
  self.a = width / 1638.4
  self.d = height / 1638.4

  if rotation != 0:
    let cosRotation = cos(rotation)
    let sinRotation = sin(rotation)

    self.b = sinRotation * self.d
    self.c = -sinRotation * self.a
    self.a *= cosRotation
    self.d *= cosRotation
  else:
    self.b = 0
    self.c = 0

  self.tx = tx + width / 2
  self.ty = ty + height / 2

proc deltaTransformPoint*(self: Matrix, point: Point): Point =
  newPoint(point.x * self.a + point.y * self.c, point.x * self.b + point.y * self.d)

proc deltaTransformPointToOutput*(self: Matrix, point: Point, output: Point): Point =
  if output != nil:
    output.setTo(point.x * self.a + point.y * self.c, point.x * self.b + point.y * self.d)
    return output
  return newPoint(point.x * self.a + point.y * self.c, point.x * self.b + point.y * self.d)

proc equals*(self: Matrix, matrix: Matrix): bool =
  not matrix.isNil and self.tx == matrix.tx and self.ty == matrix.ty and self.a == matrix.a and self.b == matrix.b and self.c == matrix.c and self.d == matrix.d

proc identity*(self: Matrix) =
  self.a = 1
  self.b = 0
  self.c = 0
  self.d = 1
  self.tx = 0
  self.ty = 0

proc invert*(self: Matrix): Matrix =
  var norm = self.a * self.d - self.b * self.c

  if norm == 0:
    self.a = 0
    self.b = 0
    self.c = 0
    self.d = 0
    self.tx = -self.tx
    self.ty = -self.ty
  else:
    norm = 1.0 / norm
    let a1 = self.d * norm
    self.d = self.a * norm
    self.a = a1
    self.b *= -norm
    self.c *= -norm

    let tx1 = -self.a * self.tx - self.c * self.ty
    self.ty = -self.b * self.tx - self.d * self.ty
    self.tx = tx1

  return self

proc rotate*(self: Matrix, theta: float64) =
  let cosTheta = cos(theta)
  let sinTheta = sin(theta)

  let a1 = self.a * cosTheta - self.b * sinTheta
  self.b = self.a * sinTheta + self.b * cosTheta
  self.a = a1

  let c1 = self.c * cosTheta - self.d * sinTheta
  self.d = self.c * sinTheta + self.d * cosTheta
  self.c = c1

  let tx1 = self.tx * cosTheta - self.ty * sinTheta
  self.ty = self.tx * sinTheta + self.ty * cosTheta
  self.tx = tx1

proc scale*(self: Matrix, sx: float64, sy: float64) =
  self.a *= sx
  self.b *= sy
  self.c *= sx
  self.d *= sy
  self.tx *= sx
  self.ty *= sy

proc setTo*(self: Matrix, a: float64, b: float64, c: float64, d: float64, tx: float64, ty: float64) =
  self.a = a
  self.b = b
  self.c = c
  self.d = d
  self.tx = tx
  self.ty = ty

proc `$`*(self: Matrix): string =
  "matrix(" & $self.a & ", " & $self.b & ", " & $self.c & ", " & $self.d & ", " & $self.tx & ", " & $self.ty & ")"

proc transformPoint*(self: Matrix, pos: Point): Point =
  newPoint(pos.x * self.a + pos.y * self.c + self.tx, pos.x * self.b + pos.y * self.d + self.ty)

proc transformPointToOutput*(self: Matrix, pos: Point, output: Point): Point =
  if output != nil:
    output.setTo(pos.x * self.a + pos.y * self.c + self.tx, pos.x * self.b + pos.y * self.d + self.ty)
    return output
  return newPoint(pos.x * self.a + pos.y * self.c + self.tx, pos.x * self.b + pos.y * self.d + self.ty)

proc translate*(self: Matrix, dx: float64, dy: float64) =
  self.tx += dx
  self.ty += dy
