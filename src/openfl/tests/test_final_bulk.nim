import ../Vector
import ../Assets
import ../Memory
import ../display3D/Context3D
import ../display3D/VertexBuffer3D
import ../display3D/IndexBuffer3D
import ../filesystem/File
import ../utils/ByteArray

proc testVector() =
  echo "Testing Vector..."
  let vec = newVector[int](3)
  assert vec.length == 3
  vec[0] = 10
  vec[1] = 20
  vec[2] = 30
  assert vec[1] == 20

  discard vec.push(40)
  assert vec.length == 4
  assert vec[3] == 40

  let popped = vec.pop()
  assert popped == 40
  assert vec.length == 3

  let shifted = vec.shift()
  assert shifted == 10
  assert vec.length == 2
  assert vec[0] == 20

  vec.unshift(5)
  assert vec[0] == 5
  assert vec.length == 3

  assert vec.indexOf(20) == 1
  assert vec.lastIndexOf(30) == 2

  var sum = 0
  for val in vec:
    sum += val
  assert sum == 5 + 20 + 30
  echo "Vector test passed!"

proc testDisplay3D() =
  echo "Testing Display3D..."
  let ctx = newContext3D()
  assert ctx.driverInfo == "Pixie/Software"

  let vb = ctx.createVertexBuffer(100, 3)
  let ib = ctx.createIndexBuffer(300)
  assert not vb.isNil
  assert not ib.isNil
  echo "Display3D test passed!"

proc testFilesystem() =
  echo "Testing Filesystem..."
  let f = newFile("/tmp") # Standard path
  echo "File nativePath: ", f.nativePath
  let resolved = f.resolvePath("test.txt")
  assert resolved.nativePath.contains("test.txt")
  echo "Filesystem test passed!"

testVector()
testDisplay3D()
testFilesystem()
echo "All final bulk tests passed!"
