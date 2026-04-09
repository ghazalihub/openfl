import ../Vector
import ../geom/Matrix3D
import ./VertexBuffer3D
import ./IndexBuffer3D
import ./Program3D

type
  Context3D* = ref object
    driverInfo*: string

proc newContext3D*(): Context3D =
  return Context3D(driverInfo: "Pixie/Software")

proc clear*(self: Context3D, red: float64 = 0.0, green: float64 = 0.0, blue: float64 = 0.0, alpha: float64 = 1.0, depth: float64 = 1.0, stencil: int = 0, mask: int = 0xFFFFFFFF) =
  # TODO: implement
  discard

proc configureBackBuffer*(self: Context3D, width: int, height: int, antiAlias: int, enableDepthAndStencil: bool = true, wantBestResolution: bool = false, wantBestResolutionOnBrowserZoom: bool = false) =
  # TODO: implement
  discard

proc createVertexBuffer*(self: Context3D, numVertices: int, data32PerVertex: int, bufferUsage: string = "staticDraw"): VertexBuffer3D =
  return VertexBuffer3D()

proc createIndexBuffer*(self: Context3D, numIndices: int, bufferUsage: string = "staticDraw"): IndexBuffer3D =
  return IndexBuffer3D()

proc createProgram*(self: Context3D): Program3D =
  return Program3D()

proc drawTriangles*(self: Context3D, indexBuffer: IndexBuffer3D, firstIndex: int = 0, numTriangles: int = -1) =
  # TODO: implement
  discard

proc present*(self: Context3D) =
  # TODO: implement
  discard

proc setProgram*(self: Context3D, program: Program3D) =
  # TODO: implement
  discard

proc setProgramConstantsFromMatrix*(self: Context3D, programType: string, firstRegister: int, matrix: Matrix3D, transposedMatrix: bool = false) =
  # TODO: implement
  discard

proc setVertexBufferAt*(self: Context3D, index: int, buffer: VertexBuffer3D, bufferOffset: int = 0, format: string = "float4") =
  # TODO: implement
  discard
