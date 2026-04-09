import ./Stage
import ./MovieClip

type
  Lib* = ref object

var current*: MovieClip = nil

proc getTimer*(): int =
  # TODO: implement
  return 0
