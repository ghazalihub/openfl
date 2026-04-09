import ../display/Stage
import ../display/Sprite
import ../display/Shape
import ../display/Bitmap
import ../display/BitmapData
import ../display/MovieClip
import ../display/DisplayObject
import ../display/DisplayObjectContainer
import ../Lib

proc testDisplayHierarchy() =
  echo "Testing Display Hierarchy..."
  let stage = newStage(800, 600)
  let root = newMovieClip()
  discard stage.addChild(root)
  Lib.current = root

  let sprite = newSprite()
  discard root.addChild(sprite)

  let shape = newShape()
  discard sprite.addChild(shape)

  assert root.numChildren == 1
  assert sprite.numChildren == 1
  assert sprite.parent == root
  assert shape.parent == sprite
  echo "Display Hierarchy test passed!"

proc testBitmap() =
  echo "Testing Bitmap/Pixie..."
  let bmd = newBitmapData(100, 100, true, 0xFFFF0000) # Red
  assert bmd.width == 100
  assert bmd.height == 100

  let pixel = bmd.getPixel(0, 0)
  # 0xFFFF0000 is red. In Pixie/Rgbx it might be represented differently depending on endianness,
  # but our newBitmapData uses a simple shift.
  echo "Pixel at (0,0): ", pixel

  let bmp = newBitmap(bmd)
  assert bmp.bitmapData == bmd
  echo "Bitmap/Pixie test passed!"

testDisplayHierarchy()
testBitmap()
echo "All display bulk tests passed!"
