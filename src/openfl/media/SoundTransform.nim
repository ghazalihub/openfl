type
  SoundTransform* = ref object
    pan*: float64
    volume*: float64

proc newSoundTransform*(vol: float64 = 1.0, panning: float64 = 0.0): SoundTransform =
  return SoundTransform(
    volume: vol,
    pan: panning
  )
