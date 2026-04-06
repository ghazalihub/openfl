import ../geom/ColorTransform
import ../geom/Vector3D
import ../geom/Matrix3D

proc testColorTransform() =
  echo "Testing ColorTransform..."
  let ct = newColorTransform(1, 1, 1, 1, 255, 0, 0, 0)
  echo "ct color: ", ct.color
  assert ct.color == 0xFF0000

  ct.color = 0x00FF00
  echo "ct color after set: ", ct.color
  assert ct.color == 0x00FF00
  assert ct.redMultiplier == 0
  assert ct.greenMultiplier == 0
  assert ct.blueMultiplier == 0
  assert ct.greenOffset == 255

  echo "ColorTransform tests passed!"

proc testVector3D() =
  echo "Testing Vector3D..."
  let v1 = newVector3D(1, 2, 3)
  let v2 = newVector3D(4, 5, 6)
  let v3 = v1.add(v2)
  echo "v1 + v2 = ", $v3
  assert v3.x == 5
  assert v3.y == 7
  assert v3.z == 9

  let dot = v1.dotProduct(v2)
  echo "v1 dot v2 = ", dot
  assert dot == (1*4 + 2*5 + 3*6)

  echo "Vector3D tests passed!"

proc testMatrix3D() =
  echo "Testing Matrix3D..."
  let m = newMatrix3D()
  m.appendTranslation(10, 20, 30)
  let pos = m.position
  echo "m position: ", $pos
  assert pos.x == 10
  assert pos.y == 20
  assert pos.z == 30

  m.identity()
  m.appendScale(2, 2, 2)
  echo "m scaled: ", m.rawData
  assert m.rawData[0] == 2
  assert m.rawData[5] == 2
  assert m.rawData[10] == 2

  echo "Matrix3D tests passed!"

testColorTransform()
testVector3D()
testMatrix3D()
echo "All extra geom tests passed!"
