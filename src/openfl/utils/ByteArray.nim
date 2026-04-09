import ./Endian
import ./IDataInput
import ./IDataOutput
import std/streams
import std/endians

type
  ByteArray* = ref object of RootObj
    data*: string
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

proc readShort*(self: ByteArray): int =
  if self.bytesAvailable() < 2: return 0
  var val: int16
  copyMem(addr val, addr self.data[self.position], 2)
  if self.endian == BIG_ENDIAN:
    var res: int16
    bigEndian16(addr res, addr val)
    val = res
  self.position += 2
  return int(val)

proc readInt*(self: ByteArray): int =
  if self.bytesAvailable() < 4: return 0
  var val: int32
  copyMem(addr val, addr self.data[self.position], 4)
  if self.endian == BIG_ENDIAN:
    var res: int32
    bigEndian32(addr res, addr val)
    val = res
  self.position += 4
  return int(val)

proc readDouble*(self: ByteArray): float64 =
  if self.bytesAvailable() < 8: return 0.0
  var val: float64
  copyMem(addr val, addr self.data[self.position], 8)
  if self.endian == BIG_ENDIAN:
    var res: float64
    bigEndian64(addr res, addr val)
    val = res
  self.position += 8
  return val

proc readUTF*(self: ByteArray): string =
  let len = self.readShort()
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

proc writeInt*(self: ByteArray, value: int) =
  var val = int32(value)
  if self.endian == BIG_ENDIAN:
    var swapped: int32
    bigEndian32(addr swapped, addr val)
    val = swapped
  let bytes = cast[array[4, char]](val)
  for b in bytes: self.writeByte(int(b))

proc writeDouble*(self: ByteArray, value: float64) =
  var val = value
  if self.endian == BIG_ENDIAN:
    var swapped: float64
    bigEndian64(addr swapped, addr val)
    val = swapped
  let bytes = cast[array[8, char]](val)
  for b in bytes: self.writeByte(int(b))

proc writeUTF*(self: ByteArray, value: string) =
  let len = value.len
  self.writeInt(len) # Simplified to int for now
  for c in value:
    self.writeByte(int(c))

proc toString*(self: ByteArray): string =
  return self.data
