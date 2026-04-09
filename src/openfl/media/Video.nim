import ../display/DisplayObject
import ../net/NetStream

type
  Video* = ref object of DisplayObject
    deblocking*: int
    smoothing*: bool
    videoHeight*: int
    videoWidth*: int

proc initVideo*(self: Video, width: int = 320, height: int = 240) =
  self.initDisplayObject()
  self.videoWidth = width
  self.videoHeight = height
  self.smoothing = false
  self.deblocking = 0

proc newVideo*(width: int = 320, height: int = 240): Video =
  let self = Video()
  self.initVideo(width, height)
  return self

proc attachNetStream*(self: Video, netStream: NetStream) =
  # TODO: implement video rendering
  discard

proc clear*(self: Video) =
  # TODO: implement
  discard
