import ./TextFormatAlign

type
  TextFormat* = ref object
    align*: TextFormatAlign
    blockIndent*: int
    boldInternal: bool
    bullet*: bool
    color*: int
    fontInternal: string
    indent*: int
    italicInternal: bool
    kerning*: bool
    leading*: int
    leftMargin*: int
    letterSpacing*: float64
    rightMargin*: int
    sizeInternal: int
    strikethrough*: bool
    tabStops*: seq[int]
    target*: string
    underline*: bool
    url*: string
    cacheKey: string

proc toCacheKey(self: TextFormat): string =
  self.cacheKey = self.fontInternal & $self.sizeInternal & $self.boldInternal & $self.italicInternal
  return self.cacheKey

proc font*(self: TextFormat): string = self.fontInternal
proc `font=`*(self: TextFormat, value: string) =
  if self.fontInternal != value:
    self.fontInternal = value
    discard self.toCacheKey()

proc size*(self: TextFormat): int = self.sizeInternal
proc `size=`*(self: TextFormat, value: int) =
  if self.sizeInternal != value:
    self.sizeInternal = value
    discard self.toCacheKey()

proc bold*(self: TextFormat): bool = self.boldInternal
proc `bold=`*(self: TextFormat, value: bool) =
  if self.boldInternal != value:
    self.boldInternal = value
    discard self.toCacheKey()

proc italic*(self: TextFormat): bool = self.italicInternal
proc `italic=`*(self: TextFormat, value: bool) =
  if self.italicInternal != value:
    self.italicInternal = value
    discard self.toCacheKey()

proc newTextFormat*(font: string = "", size: int = 12, color: int = 0, bold: bool = false, italic: bool = false, underline: bool = false, url: string = "", target: string = "", align: TextFormatAlign = TextFormatAlign.LEFT, leftMargin: int = 0, rightMargin: int = 0, indent: int = 0, leading: int = 0): TextFormat =
  let self = TextFormat(
    fontInternal: font,
    sizeInternal: size,
    color: color,
    boldInternal: bold,
    italicInternal: italic,
    underline: underline,
    url: url,
    target: target,
    align: align,
    leftMargin: leftMargin,
    rightMargin: rightMargin,
    indent: indent,
    leading: leading,
    tabStops: @[]
  )
  discard self.toCacheKey()
  return self

proc clone*(self: TextFormat): TextFormat =
  let newFormat = newTextFormat(self.fontInternal, self.sizeInternal, self.color, self.boldInternal, self.italicInternal, self.underline, self.url, self.target)
  newFormat.align = self.align
  newFormat.leftMargin = self.leftMargin
  newFormat.rightMargin = self.rightMargin
  newFormat.indent = self.indent
  newFormat.leading = self.leading
  newFormat.blockIndent = self.blockIndent
  newFormat.bullet = self.bullet
  newFormat.kerning = self.kerning
  newFormat.letterSpacing = self.letterSpacing
  newFormat.tabStops = self.tabStops
  newFormat.strikethrough = self.strikethrough
  newFormat.cacheKey = self.cacheKey
  return newFormat
