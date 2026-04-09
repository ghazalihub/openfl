import ./FontStyle
import ./FontType

type
  Font* = ref object
    fontNameInternal: string
    fontStyle*: FontStyle
    fontType*: FontType

proc fontName*(self: Font): string = self.fontNameInternal
proc `fontName=`*(self: Font, value: string) = self.fontNameInternal = value

proc newFont*(name: string = ""): Font =
  return Font(fontNameInternal: name, fontStyle: FontStyle.REGULAR, fontType: FontType.DEVICE)

var registeredFonts: seq[Font] = @[]

proc enumerateFonts*(enumerateDeviceFonts: bool = false): seq[Font] =
  return registeredFonts

proc registerFont*(font: Font) =
  registeredFonts.add(font)
