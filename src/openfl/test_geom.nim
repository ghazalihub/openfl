import ./geom/Point
import ./geom/Rectangle
import ./geom/Matrix

proc testPoint() =
  echo "Testing Point..."
  let p1 = newPoint(10, 20)
  let p2 = newPoint(30, 40)
  let p3 = p1.add(p2)
  echo "p1: ", $p1
  echo "p2: ", $p2
  echo "p1 + p2: ", $p3
  assert p3.x == 40
  assert p3.y == 60
  echo "Point tests passed!"

proc testRectangle() =
  echo "Testing Rectangle..."
  let r1 = newRectangle(0, 0, 100, 100)
  echo "r1: ", $r1
  assert r1.right == 100
  assert r1.bottom == 100

  let r2 = newRectangle(50, 50, 100, 100)
  let inter = r1.intersection(r2)
  echo "Intersection of r1 and r2: ", $inter
  assert inter.x == 50
  assert inter.y == 50
  assert inter.width == 50
  assert inter.height == 50

  echo "Rectangle tests passed!"

proc testMatrix() =
  echo "Testing Matrix..."
  let m = newMatrix()
  m.translate(10, 20)
  let p = newPoint(0, 0)
  let pTransformed = m.transformPoint(p)
  echo "Point (0,0) transformed by translate(10,20): ", $pTransformed
  assert pTransformed.x == 10
  assert pTransformed.y == 20

  m.identity()
  m.scale(2, 2)
  let p2 = newPoint(10, 10)
  let p2Transformed = m.transformPoint(p2)
  echo "Point (10,10) transformed by scale(2,2): ", $p2Transformed
  assert p2Transformed.x == 20
  assert p2Transformed.y == 20

  echo "Matrix tests passed!"

testPoint()
testRectangle()
testMatrix()
echo "All tests passed!"
