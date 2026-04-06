import math

type
  Point* = ref object
    x*: float64
    y*: float64

proc newPoint*(x: float64 = 0, y: float64 = 0): Point =
  Point(x: x, y: y)

proc length*(self: Point): float64 =
  sqrt(self.x * self.x + self.y * self.y)

proc add*(self: Point, v: Point): Point =
  newPoint(self.x + v.x, self.y + v.y)

proc addToOutput*(self: Point, v: Point, output: Point): Point =
  if output != nil:
    output.x = self.x + v.x
    output.y = self.y + v.y
    return output
  return newPoint(self.x + v.x, self.y + v.y)

proc clone*(self: Point): Point =
  newPoint(self.x, self.y)

proc copyFrom*(self: Point, sourcePoint: Point) =
  self.x = sourcePoint.x
  self.y = sourcePoint.y

proc distance*(pt1: Point, pt2: Point): float64 =
  let dx = pt1.x - pt2.x
  let dy = pt1.y - pt2.y
  sqrt(dx * dx + dy * dy)

proc equals*(self: Point, toCompare: Point): bool =
  not toCompare.isNil and self.x == toCompare.x and self.y == toCompare.y

proc interpolate*(pt1: Point, pt2: Point, f: float64): Point =
  newPoint(pt2.x + f * (pt1.x - pt2.x), pt2.y + f * (pt1.y - pt2.y))

proc interpolateToOutput*(pt1: Point, pt2: Point, f: float64, output: Point): Point =
  if output != nil:
    output.x = pt2.x + f * (pt1.x - pt2.x)
    output.y = pt2.y + f * (pt1.y - pt2.y)
    return output
  return newPoint(pt2.x + f * (pt1.x - pt2.x), pt2.y + f * (pt1.y - pt2.y))

proc normalize*(self: Point, thickness: float64) =
  if self.x == 0 and self.y == 0:
    return
  else:
    let norm = thickness / sqrt(self.x * self.x + self.y * self.y)
    self.x *= norm
    self.y *= norm

proc offset*(self: Point, dx: float64, dy: float64) =
  self.x += dx
  self.y += dy

proc polar*(len: float64, angle: float64): Point =
  newPoint(len * cos(angle), len * sin(angle))

proc polarToOutput*(len: float64, angle: float64, output: Point): Point =
  if output != nil:
    output.x = len * cos(angle)
    output.y = len * sin(angle)
    return output
  return newPoint(len * cos(angle), len * sin(angle))

proc setTo*(self: Point, xa: float64, ya: float64) =
  self.x = xa
  self.y = ya

proc subtract*(self: Point, v: Point): Point =
  newPoint(self.x - v.x, self.y - v.y)

proc subtractToOutput*(self: Point, v: Point, output: Point): Point =
  if output != nil:
    output.x = self.x - v.x
    output.y = self.y - v.y
    return output
  return newPoint(self.x - v.x, self.y - v.y)

proc `$`*(self: Point): string =
  "(x=" & $self.x & ", y=" & $self.y & ")"
