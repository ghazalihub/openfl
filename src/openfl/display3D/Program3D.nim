import ../utils/ByteArray

type
  Program3D* = ref object

proc newProgram3D*(): Program3D =
  return Program3D()

proc upload*(self: Program3D, vertexProgram: ByteArray, fragmentProgram: ByteArray) =
  # TODO: implement
  discard

proc dispose*(self: Program3D) =
  # TODO: implement
  discard
