import ./display/BitmapData
import ./utils/ByteArray
import ./events/EventDispatcher

type
  Assets* = ref object

proc getBitmapData*(id: string, useCache: bool = true): BitmapData =
  # TODO: Implement asset loading
  return nil

proc getBytes*(id: string): ByteArray =
  # TODO: Implement asset loading
  return nil

proc getText*(id: string): string =
  # TODO: Implement asset loading
  return ""

proc exists*(id: string, typeStr: string = nil): bool =
  # TODO: Implement asset check
  return false
