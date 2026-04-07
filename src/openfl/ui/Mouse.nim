import ./MouseCursor

type
  Mouse* = ref object

var cursor*: MouseCursor = MouseCursor.auto

proc hide*() =
  # TODO: implement with SDL2
  discard

proc show*() =
  # TODO: implement with SDL2
  discard
