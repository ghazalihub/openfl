import ../utils/ByteArray
import ../Vector

type
  VertexBuffer3D* = ref object

proc newVertexBuffer3D*(): VertexBuffer3D =
  return VertexBuffer3D()

proc uploadFromByteArray*(self: VertexBuffer3D, data: ByteArray, byteArrayOffset: int, startVertex: int, numVertices: int) =
  # TODO: implement
  discard

proc uploadFromVector*(self: VertexBuffer3D, data: Vector[float32], startVertex: int, numVertices: int) =
  # TODO: implement
  discard

proc dispose*(self: VertexBuffer3D) =
  # TODO: implement
  discard
