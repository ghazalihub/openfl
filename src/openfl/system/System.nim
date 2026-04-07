type
  System* = ref object

var useCodePage*: bool = false

proc totalMemory*(): int = 0
proc totalMemoryNumber*(): float64 = 0.0
proc vmVersion*(): string = "1.0.0"

proc exit*(code: int) =
  quit(code)

proc gc*() =
  # GC control is different in ARC/ORC
  discard

proc setClipboard*(str: string) =
  # TODO: Implement with SDL2/Pixie
  discard
