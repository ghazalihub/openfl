import ./utils/ByteArray

type
  Memory* = ref object

proc select*(bytes: ByteArray) =
  # TODO: implement byte-level memory access
  discard

proc getByte*(addr: int): int = 0
proc getDouble*(addr: int): float64 = 0.0
proc getFloat*(addr: int): float32 = 0.0
proc getInt*(addr: int): int = 0
proc getShort*(addr: int): int = 0

proc setByte*(addr: int, v: int) = discard
proc setDouble*(addr: int, v: float64) = discard
proc setFloat*(addr: int, v: float32) = discard
proc setInt*(addr: int, v: int) = discard
proc setShort*(addr: int, v: int) = discard
