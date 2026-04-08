import pixie
import ./InteractiveObject
import ./TextFormat

type
  TextField* = ref object of InteractiveObject
    text*: string
    defaultTextFormat*: TextFormat
    textColor*: uint32

proc initTextField*(self: TextField) =
  self.initInteractiveObject()
  self.text = ""
  self.textColor = 0x000000

proc newTextField*(): TextField =
  let self = TextField()
  self.initTextField()
  return self

proc render*(self: TextField): Image =
  if self.text == "": return newImage(1, 1)

  # Basic text rendering with Pixie
  # We should load a font here, but for now we use a placeholder logic
  let font = readFont("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf")
  font.size = if not self.defaultTextFormat.isNil and self.defaultTextFormat.size != 0: float32(self.defaultTextFormat.size) else: 12.0
  font.paint.color = color(0, 0, 0, 1) # Black for now

  let textImg = newImage(500, 100)
  textImg.fillText(font, self.text)
  return textImg
