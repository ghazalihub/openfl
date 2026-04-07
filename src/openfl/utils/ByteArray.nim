import ./Endian
import ./IDataInput
import ./IDataOutput
import std/streams

type
  ByteArray* = ref object of RootObj
    data*: string # Using string as backing storage for convenience
    position*: int
    endian*: Endian
    objectEncoding*: int

proc newByteArray*(): ByteArray =
  return ByteArray(
    data: "",
    position: 0,
    endian: BIG_ENDIAN,
    objectEncoding: 3 # AMF3
  )

proc bytesAvailable*(self: ByteArray): int =
  return self.data.len - self.position

proc length*(self: ByteArray): int =
  return self.data.len

proc `length=`*(self: ByteArray, value: int) =
  if value > self.data.len:
    self.data.setLen(value)
  elif value < self.data.len:
    self.data.setLen(value)
  if self.position > value:
    self.position = value

proc clear*(self: ByteArray) =
  self.data = ""
  self.position = 0

# Reading methods
proc readBoolean*(self: ByteArray): bool =
  if self.bytesAvailable() < 1: return false
  let res = self.data[self.position] != '\0'
  self.position += 1
  return res

proc readByte*(self: ByteArray): int =
  if self.bytesAvailable() < 1: return 0
  let res = int(int8(self.data[self.position]))
  self.position += 1
  return res

proc readUnsignedByte*(self: ByteArray): int =
  if self.bytesAvailable() < 1: return 0
  let res = int(uint8(self.data[self.position]))
  self.position += 1
  return res

proc readUTF*(self: ByteArray): string =
  # Very simplified: read uint16 length, then bytes
  if self.bytesAvailable() < 2: return ""
  # Assume BIG_ENDIAN for UTF
  let len = (int(uint8(self.data[self.position])) shl 8) or int(uint8(self.data[self.position + 1]))
  self.position += 2
  if self.bytesAvailable() < len: return ""
  let res = self.data[self.position ..< self.position + len]
  self.position += len
  return res

# Writing methods
proc writeByte*(self: ByteArray, value: int) =
  let b = char(uint8(value and 0xFF))
  if self.position < self.data.len:
    self.data[self.position] = b
  else:
    self.data.add(b)
  self.position += 1

proc writeUTF*(self: ByteArray, value: string) =
  let len = value.len
  self.writeByte((len shr 8) and 0xFF)
  self.writeByte(len and 0xFF)
  for c in value:
    self.writeByte(int(c))

proc toString*(self: ByteArray): string =
  return self.data
