import std/math
import ./Vector3D

type
  Matrix3D* = ref object
    rawData*: seq[float64]

proc newMatrix3D*(v: seq[float64] = @[]): Matrix3D =
  if v.len == 16:
    Matrix3D(rawData: v)
  else:
    Matrix3D(rawData: @[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0])

proc append*(self: Matrix3D, lhs: Matrix3D) =
  let m111 = self.rawData[0]
  let m121 = self.rawData[4]
  let m131 = self.rawData[8]
  let m141 = self.rawData[12]
  let m112 = self.rawData[1]
  let m122 = self.rawData[5]
  let m132 = self.rawData[9]
  let m142 = self.rawData[13]
  let m113 = self.rawData[2]
  let m123 = self.rawData[6]
  let m133 = self.rawData[10]
  let m143 = self.rawData[14]
  let m114 = self.rawData[3]
  let m124 = self.rawData[7]
  let m134 = self.rawData[11]
  let m144 = self.rawData[15]
  let m211 = lhs.rawData[0]
  let m221 = lhs.rawData[4]
  let m231 = lhs.rawData[8]
  let m241 = lhs.rawData[12]
  let m212 = lhs.rawData[1]
  let m222 = lhs.rawData[5]
  let m232 = lhs.rawData[9]
  let m242 = lhs.rawData[13]
  let m213 = lhs.rawData[2]
  let m223 = lhs.rawData[6]
  let m233 = lhs.rawData[10]
  let m243 = lhs.rawData[14]
  let m214 = lhs.rawData[3]
  let m224 = lhs.rawData[7]
  let m234 = lhs.rawData[11]
  let m244 = lhs.rawData[15]

  self.rawData[0] = m111 * m211 + m112 * m221 + m113 * m231 + m114 * m241
  self.rawData[1] = m111 * m212 + m112 * m222 + m113 * m232 + m114 * m242
  self.rawData[2] = m111 * m213 + m112 * m223 + m113 * m233 + m114 * m243
  self.rawData[3] = m111 * m214 + m112 * m224 + m113 * m234 + m114 * m244

  self.rawData[4] = m121 * m211 + m122 * m221 + m123 * m231 + m124 * m241
  self.rawData[5] = m121 * m212 + m122 * m222 + m123 * m232 + m124 * m242
  self.rawData[6] = m121 * m213 + m122 * m223 + m123 * m233 + m124 * m243
  self.rawData[7] = m121 * m214 + m122 * m224 + m123 * m234 + m124 * m244

  self.rawData[8] = m131 * m211 + m132 * m221 + m133 * m231 + m134 * m241
  self.rawData[9] = m131 * m212 + m132 * m222 + m133 * m232 + m134 * m242
  self.rawData[10] = m131 * m213 + m132 * m223 + m133 * m233 + m134 * m243
  self.rawData[11] = m131 * m214 + m132 * m224 + m133 * m234 + m134 * m244

  self.rawData[12] = m141 * m211 + m142 * m221 + m143 * m231 + m144 * m241
  self.rawData[13] = m141 * m212 + m142 * m222 + m143 * m232 + m144 * m242
  self.rawData[14] = m141 * m213 + m142 * m223 + m143 * m233 + m144 * m243
  self.rawData[15] = m141 * m214 + m142 * m224 + m143 * m234 + m144 * m244

proc identity*(self: Matrix3D) =
  self.rawData = @[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0]

proc appendRotation*(self: Matrix3D, degrees: float64, axis: Vector3D, pivotPoint: Vector3D = nil) =
  var tx, ty, tz: float64 = 0
  if not pivotPoint.isNil:
    tx = pivotPoint.x
    ty = pivotPoint.y
    tz = pivotPoint.z

  let radian = degrees * PI / 180.0
  let cosR = cos(radian)
  let sinR = sin(radian)
  var x = axis.x
  var y = axis.y
  var z = axis.z
  var x2 = x * x
  var y2 = y * y
  var z2 = z * z
  let ls = x2 + y2 + z2
  if ls != 0:
    let l = sqrt(ls)
    x /= l
    y /= l
    z /= l
    x2 /= ls
    y2 /= ls
    z2 /= ls

  let ccos = 1.0 - cosR
  let m = newMatrix3D()
  m.rawData[0] = x2 + (y2 + z2) * cosR
  m.rawData[1] = x * y * ccos + z * sinR
  m.rawData[2] = x * z * ccos - y * sinR
  m.rawData[4] = x * y * ccos - z * sinR
  m.rawData[5] = y2 + (x2 + z2) * cosR
  m.rawData[6] = y * z * ccos + x * sinR
  m.rawData[8] = x * z * ccos + y * sinR
  m.rawData[9] = y * z * ccos - x * sinR
  m.rawData[10] = z2 + (x2 + y2) * cosR
  m.rawData[12] = (tx * (y2 + z2) - x * (ty * y + tz * z)) * ccos + (ty * z - tz * y) * sinR
  m.rawData[13] = (ty * (x2 + z2) - y * (tx * x + tz * z)) * ccos + (tz * x - tx * z) * sinR
  m.rawData[14] = (tz * (x2 + y2) - z * (tx * x + ty * y)) * ccos + (tx * y - ty * x) * sinR
  self.append(m)

proc appendScale*(self: Matrix3D, xScale, yScale, zScale: float64) =
  self.append(newMatrix3D(@[xScale, 0.0, 0.0, 0.0, 0.0, yScale, 0.0, 0.0, 0.0, 0.0, zScale, 0.0, 0.0, 0.0, 0.0, 1.0]))

proc appendTranslation*(self: Matrix3D, x, y, z: float64) =
  self.rawData[12] += x
  self.rawData[13] += y
  self.rawData[14] += z

proc clone*(self: Matrix3D): Matrix3D =
  newMatrix3D(self.rawData)

proc determinant*(self: Matrix3D): float64 =
  let rd = self.rawData
  return 1.0 * ((rd[0] * rd[5] - rd[4] * rd[1]) * (rd[10] * rd[15] - rd[14] * rd[11]) -
         (rd[0] * rd[9] - rd[8] * rd[1]) * (rd[6] * rd[15] - rd[14] * rd[7]) +
         (rd[0] * rd[13] - rd[12] * rd[1]) * (rd[6] * rd[11] - rd[10] * rd[7]) +
         (rd[4] * rd[9] - rd[8] * rd[5]) * (rd[2] * rd[15] - rd[14] * rd[3]) -
         (rd[4] * rd[13] - rd[12] * rd[5]) * (rd[2] * rd[11] - rd[10] * rd[3]) +
         (rd[8] * rd[13] - rd[12] * rd[9]) * (rd[2] * rd[7] - rd[6] * rd[3]))

proc position*(self: Matrix3D): Vector3D =
  return newVector3D(self.rawData[12], self.rawData[13], self.rawData[14])

proc `position=`*(self: Matrix3D, val: Vector3D) =
  self.rawData[12] = val.x
  self.rawData[13] = val.y
  self.rawData[14] = val.z

proc invert*(self: Matrix3D): bool =
  var d = self.determinant()
  let invertable = abs(d) > 0.00000000001
  if invertable:
    d = 1.0 / d
    let m11 = self.rawData[0]; let m21 = self.rawData[4]; let m31 = self.rawData[8]; let m41 = self.rawData[12]
    let m12 = self.rawData[1]; let m22 = self.rawData[5]; let m32 = self.rawData[9]; let m42 = self.rawData[13]
    let m13 = self.rawData[2]; let m23 = self.rawData[6]; let m33 = self.rawData[10]; let m43 = self.rawData[14]
    let m14 = self.rawData[3]; let m24 = self.rawData[7]; let m34 = self.rawData[11]; let m44 = self.rawData[15]

    self.rawData[0] = d * (m22 * (m33 * m44 - m43 * m34) - m32 * (m23 * m44 - m43 * m24) + m42 * (m23 * m34 - m33 * m24))
    self.rawData[1] = -d * (m12 * (m33 * m44 - m43 * m34) - m32 * (m13 * m44 - m43 * m14) + m42 * (m13 * m34 - m33 * m14))
    self.rawData[2] = d * (m12 * (m23 * m44 - m43 * m24) - m22 * (m13 * m44 - m43 * m14) + m42 * (m13 * m24 - m23 * m14))
    self.rawData[3] = -d * (m12 * (m23 * m34 - m33 * m24) - m22 * (m13 * m34 - m33 * m14) + m32 * (m13 * m24 - m23 * m14))
    self.rawData[4] = -d * (m21 * (m33 * m44 - m43 * m34) - m31 * (m23 * m44 - m43 * m24) + m41 * (m23 * m34 - m33 * m24))
    self.rawData[5] = d * (m11 * (m33 * m44 - m43 * m34) - m31 * (m13 * m44 - m43 * m14) + m41 * (m13 * m34 - m33 * m14))
    self.rawData[6] = -d * (m11 * (m23 * m44 - m43 * m24) - m21 * (m13 * m44 - m43 * m14) + m41 * (m13 * m24 - m23 * m14))
    self.rawData[7] = d * (m11 * (m23 * m34 - m33 * m24) - m21 * (m13 * m34 - m33 * m14) + m31 * (m13 * m24 - m23 * m14))
    self.rawData[8] = d * (m21 * (m32 * m44 - m42 * m34) - m31 * (m22 * m44 - m42 * m24) + m41 * (m22 * m34 - m32 * m24))
    self.rawData[9] = -d * (m11 * (m32 * m44 - m42 * m34) - m31 * (m12 * m44 - m42 * m14) + m41 * (m12 * m34 - m32 * m14))
    self.rawData[10] = d * (m11 * (m22 * m44 - m42 * m24) - m21 * (m12 * m44 - m42 * m14) + m41 * (m12 * m24 - m22 * m14))
    self.rawData[11] = -d * (m11 * (m22 * m34 - m32 * m24) - m21 * (m12 * m34 - m32 * m14) + m31 * (m12 * m24 - m22 * m14))
    self.rawData[12] = -d * (m21 * (m32 * m43 - m42 * m33) - m31 * (m22 * m43 - m42 * m23) + m41 * (m22 * m33 - m32 * m23))
    self.rawData[13] = d * (m11 * (m32 * m43 - m42 * m33) - m31 * (m12 * m43 - m42 * m13) + m41 * (m12 * m33 - m32 * m13))
    self.rawData[14] = -d * (m11 * (m22 * m43 - m42 * m23) - m21 * (m12 * m43 - m42 * m13) + m41 * (m12 * m23 - m22 * m13))
    self.rawData[15] = d * (m11 * (m22 * m33 - m32 * m23) - m21 * (m12 * m33 - m32 * m13) + m31 * (m12 * m23 - m22 * m13))
  return invertable

proc transpose*(self: Matrix3D) =
  let o = self.rawData
  self.rawData = @[o[0], o[4], o[8], o[12], o[1], o[5], o[9], o[13], o[2], o[6], o[10], o[14], o[3], o[7], o[11], o[15]]
