import std/math

type
  Vector3D* = ref object
    w*: float64
    x*: float64
    y*: float64
    z*: float64

proc newVector3D*(x: float64 = 0, y: float64 = 0, z: float64 = 0, w: float64 = 0): Vector3D =
  Vector3D(x: x, y: y, z: z, w: w)

proc X_AXIS*(): Vector3D = newVector3D(1, 0, 0)
proc Y_AXIS*(): Vector3D = newVector3D(0, 1, 0)
proc Z_AXIS*(): Vector3D = newVector3D(0, 0, 1)

proc length*(self: Vector3D): float64 =
  sqrt(self.x * self.x + self.y * self.y + self.z * self.z)

proc lengthSquared*(self: Vector3D): float64 =
  self.x * self.x + self.y * self.y + self.z * self.z

proc add*(self: Vector3D, a: Vector3D): Vector3D =
  newVector3D(self.x + a.x, self.y + a.y, self.z + a.z)

proc addToOutput*(self: Vector3D, a: Vector3D, output: Vector3D): Vector3D =
  if not output.isNil:
    output.x = self.x + a.x
    output.y = self.y + a.y
    output.z = self.z + a.z
    return output
  newVector3D(self.x + a.x, self.y + a.y, self.z + a.z)

proc dotProduct*(self: Vector3D, a: Vector3D): float64 =
  return self.x * a.x + self.y * a.y + self.z * a.z

proc angleBetween*(a: Vector3D, b: Vector3D): float64 =
  let la = a.length
  let lb = b.length
  var dot = a.dotProduct(b)

  if la != 0:
    dot /= la
  if lb != 0:
    dot /= lb

  return arccos(dot)

proc clone*(self: Vector3D): Vector3D =
  newVector3D(self.x, self.y, self.z, self.w)

proc copyFrom*(self: Vector3D, sourceVector3D: Vector3D) =
  self.x = sourceVector3D.x
  self.y = sourceVector3D.y
  self.z = sourceVector3D.z

proc crossProduct*(self: Vector3D, a: Vector3D): Vector3D =
  newVector3D(self.y * a.z - self.z * a.y, self.z * a.x - self.x * a.z, self.x * a.y - self.y * a.x, 1)

proc crossProductToOutput*(self: Vector3D, a: Vector3D, output: Vector3D): Vector3D =
  if not output.isNil:
    output.x = self.y * a.z - self.z * a.y
    output.y = self.z * a.x - self.x * a.z
    output.z = self.x * a.y - self.y * a.x
    output.w = 1
    return output
  newVector3D(self.y * a.z - self.z * a.y, self.z * a.x - self.x * a.z, self.x * a.y - self.y * a.x, 1)

proc decrementBy*(self: Vector3D, a: Vector3D) =
  self.x -= a.x
  self.y -= a.y
  self.z -= a.z

proc distance*(pt1: Vector3D, pt2: Vector3D): float64 =
  let dx = pt2.x - pt1.x
  let dy = pt2.y - pt1.y
  let dz = pt2.z - pt1.z
  sqrt(dx * dx + dy * dy + dz * dz)

proc equals*(self: Vector3D, toCompare: Vector3D, allFour: bool = false): bool =
  not toCompare.isNil and self.x == toCompare.x and self.y == toCompare.y and self.z == toCompare.z and (not allFour or self.w == toCompare.w)

proc incrementBy*(self: Vector3D, a: Vector3D) =
  self.x += a.x
  self.y += a.y
  self.z += a.z

proc nearEquals*(self: Vector3D, toCompare: Vector3D, tolerance: float64, allFour: bool = false): bool =
  not toCompare.isNil and
    abs(self.x - toCompare.x) < tolerance and
    abs(self.y - toCompare.y) < tolerance and
    abs(self.z - toCompare.z) < tolerance and
    (not allFour or abs(self.w - toCompare.w) < tolerance)

proc negate*(self: Vector3D) =
  self.x *= -1
  self.y *= -1
  self.z *= -1

proc normalize*(self: Vector3D): float64 =
  let l = self.length
  if l != 0:
    self.x /= l
    self.y /= l
    self.z /= l
  return l

proc project*(self: Vector3D) =
  self.x /= self.w
  self.y /= self.w
  self.z /= self.w

proc scaleBy*(self: Vector3D, s: float64) =
  self.x *= s
  self.y *= s
  self.z *= s

proc setTo*(self: Vector3D, xa: float64, ya: float64, za: float64) =
  self.x = xa
  self.y = ya
  self.z = za

proc subtract*(self: Vector3D, a: Vector3D): Vector3D =
  newVector3D(self.x - a.x, self.y - a.y, self.z - a.z)

proc subtractToOutput*(self: Vector3D, a: Vector3D, output: Vector3D): Vector3D =
  if not output.isNil:
    output.x = self.x - a.x
    output.y = self.y - a.y
    output.z = self.z - a.z
    return output
  newVector3D(self.x - a.x, self.y - a.y, self.z - a.z)

proc `$`*(self: Vector3D): string =
  "Vector3D(" & $self.x & ", " & $self.y & ", " & $self.z & ")"
