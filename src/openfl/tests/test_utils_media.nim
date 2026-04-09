import ../utils/ByteArray
import ../utils/Endian
import ../utils/Dictionary
import ../ui/Keyboard
import ../ui/Mouse
import ../ui/MouseCursor
import ../media/Sound
import ../media/SoundTransform
import ../external/ExternalInterface
import std/tables

proc testUtils() =
  echo "Testing Utils..."
  let ba = newByteArray()
  ba.endian = BIG_ENDIAN
  ba.writeUTF("OpenFL-Nim")
  ba.position = 0
  let readVal = ba.readUTF()
  assert readVal == "OpenFL-Nim"
  echo "ByteArray read/write UTF: ", readVal

  let dict = newDictionary[string, int]()
  dict["test"] = 42
  assert dict["test"] == 42
  echo "Dictionary test passed!"

proc testUI() =
  echo "Testing UI..."
  assert Keyboard.A == 65
  Mouse.cursor = MouseCursor.hand
  assert Mouse.cursor == MouseCursor.hand
  echo "UI test passed!"

proc testMedia() =
  echo "Testing Media..."
  let st = newSoundTransform(0.5, -1.0)
  assert st.volume == 0.5
  assert st.pan == -1.0

  let snd = newSound()
  assert snd.bytesTotal == 0
  echo "Media test passed!"

proc testExternal() =
  echo "Testing External..."
  assert not ExternalInterface.available
  echo "External test passed!"

testUtils()
testUI()
testMedia()
testExternal()
echo "All utils/media/ui/external tests passed!"
