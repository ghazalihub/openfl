import ../events/EventDispatcher
import ./SoundTransform

type
  SoundChannel* = ref object of EventDispatcher
    leftPeak*: float64
    rightPeak*: float64
    position*: float64
    soundTransform*: SoundTransform

proc newSoundChannel*(): SoundChannel =
  let self = SoundChannel(
    leftPeak: 1.0,
    rightPeak: 1.0,
    position: 0.0,
    soundTransform: newSoundTransform()
  )
  self.initEventDispatcher()
  return self

proc stop*(self: SoundChannel) =
  # TODO: implement with SDL2 Mixer
  discard
