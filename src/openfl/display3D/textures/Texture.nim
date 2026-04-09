import ./TextureBase
import ../../display/BitmapData
import ../../utils/ByteArray

type
  Texture* = ref object of TextureBase

proc newTexture*(): Texture =
  let self = Texture()
  self.initTextureBase()
  return self

proc uploadFromBitmapData*(self: Texture, source: BitmapData, miplevel: int = 0) =
  # TODO: implement
  discard

proc uploadFromByteArray*(self: Texture, data: ByteArray, byteArrayOffset: int, miplevel: int = 0) =
  # TODO: implement
  discard

proc uploadCompressedTextureFromByteArray*(self: Texture, data: ByteArray, byteArrayOffset: int, async: bool = false) =
  # TODO: implement
  discard
