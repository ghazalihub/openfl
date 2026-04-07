import ../events/EventDispatcher
import ../net/URLRequest
import ./SoundChannel
import ./SoundTransform

type
  Sound* = ref object of EventDispatcher
    bytesLoaded*: int
    bytesTotal*: int
    id3*: RootRef # ID3Info stub
    isBuffering*: bool
    isStreaming*: bool
    length*: float64
    url*: string

proc newSound*(stream: URLRequest = nil, context: RootRef = nil): Sound =
  let self = Sound(
    bytesLoaded: 0,
    bytesTotal: 0,
    isBuffering: false,
    isStreaming: false,
    length: 0.0
  )
  self.initEventDispatcher()
  if not stream.isNil:
    self.url = stream.url
    # TODO: Load logic
  return self

proc play*(self: Sound, startTime: float64 = 0.0, loops: int = 0, sndTransform: SoundTransform = nil): SoundChannel =
  # TODO: implement with SDL2 Mixer
  return newSoundChannel()

proc close*(self: Sound) =
  # TODO: implement
  discard

proc load*(self: Sound, stream: URLRequest, context: RootRef = nil) =
  self.url = stream.url
  # TODO: implement loading logic
  discard
