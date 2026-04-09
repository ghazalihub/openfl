import ../net/NetConnection
import ../net/NetStream
import ../media/Video
import ../ui/GameInput
import ../display3D/Context3DProgramType

proc testNet() =
  echo "Testing Net..."
  let nc = newNetConnection()
  nc.connect(nil)
  assert nc.connected

  let ns = newNetStream(nc)
  assert not ns.isNil
  echo "Net test passed!"

proc testMedia() =
  echo "Testing Media..."
  let video = newVideo(640, 480)
  assert video.videoWidth == 640
  echo "Media test passed!"

proc testUI() =
  echo "Testing UI..."
  let gi = newGameInput()
  assert not gi.isNil
  echo "UI test passed!"

testNet()
testMedia()
testUI()
echo "All bulk API tests passed!"
