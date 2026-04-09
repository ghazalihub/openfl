import ../utils/ByteArray
import ../Vector

type
  IndexBuffer3D* = ref object

proc newIndexBuffer3D*(): IndexBuffer3D =
  return IndexBuffer3D()

proc uploadFromByteArray*(self: IndexBuffer3D, data: ByteArray, byteArrayOffset: int, startOffset: int, count: int) =
  # TODO: implement
  discard

proc uploadFromVector*(self: IndexBuffer3D, data: Vector[uint16], startOffset: int, count: int) =
  # TODO: implement
  discard

proc dispose*(self: IndexBuffer3D) =
  # TODO: implement
  discard
