import sdl2, sdl2/mixer
import ../events/EventDispatcher
import ../net/URLRequest
import ./SoundChannel
import ./SoundTransform

type
  Sound* = ref object of EventDispatcher
    chunk: ChunkPtr
    url*: string

proc newSound*(stream: URLRequest = nil, context: RootRef = nil): Sound =
  let self = Sound()
  self.initEventDispatcher()
  if not stream.isNil:
    self.url = stream.url
    self.chunk = loadWAV(self.url)
  return self

proc play*(self: Sound, startTime: float64 = 0.0, loops: int = 0, sndTransform: SoundTransform = nil): SoundChannel =
  let channel = playChannel(-1, self.chunk, int32(loops))
  let sc = newSoundChannel()
  # TODO: link sc to SDL channel
  return sc

proc load*(self: Sound, stream: URLRequest, context: RootRef = nil) =
  self.url = stream.url
  self.chunk = loadWAV(self.url)

proc close*(self: Sound) =
  if not self.chunk.isNil:
    freeChunk(self.chunk)
    self.chunk = nil
