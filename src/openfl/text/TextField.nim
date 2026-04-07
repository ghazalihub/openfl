import ../display/InteractiveObject
import ./TextFormat
import ./TextFieldType
import ./TextFieldAutoSize
import ./AntiAliasType
import ./GridFitType

type
  TextField* = ref object of InteractiveObject
    antiAliasType*: AntiAliasType
    autoSize*: TextFieldAutoSize
    background*: bool
    backgroundColor*: int
    border*: bool
    borderColor*: int
    condenseWhite*: bool
    defaultTextFormatInternal: TextFormat
    displayAsPassword*: bool
    embedFonts*: bool
    gridFitType*: GridFitType
    htmlTextInternal: string
    maxChars*: int
    mouseWheelEnabled*: bool
    multiline*: bool
    restrict*: string
    scrollH*: int
    scrollV*: int
    selectable*: bool
    sharpness*: float64
    textInternal: string
    textColorInternal: int
    typeInternal: TextFieldType
    wordWrap*: bool

proc text*(self: TextField): string = self.textInternal
proc `text=`*(self: TextField, value: string) =
  self.textInternal = value
  # TODO: trigger layout update

proc htmlText*(self: TextField): string = self.htmlTextInternal
proc `htmlText=`*(self: TextField, value: string) =
  self.htmlTextInternal = value
  # TODO: parse HTML and update textInternal

proc defaultTextFormat*(self: TextField): TextFormat = self.defaultTextFormatInternal
proc `defaultTextFormat=`*(self: TextField, value: TextFormat) =
  self.defaultTextFormatInternal = value

proc textColor*(self: TextField): int = self.textColorInternal
proc `textColor=`*(self: TextField, value: int) =
  self.textColorInternal = value

proc `type`*(self: TextField): TextFieldType = self.typeInternal
proc `type=`*(self: TextField, value: TextFieldType) =
  self.typeInternal = value

proc newTextField*(): TextField =
  let self = TextField(
    antiAliasType: AntiAliasType.NORMAL,
    autoSize: TextFieldAutoSize.NONE,
    background: false,
    backgroundColor: 0xFFFFFF,
    border: false,
    borderColor: 0x000000,
    condenseWhite: false,
    displayAsPassword: false,
    embedFonts: false,
    gridFitType: GridFitType.PIXEL,
    maxChars: 0,
    mouseWheelEnabled: true,
    multiline: false,
    scrollH: 0,
    scrollV: 1,
    selectable: true,
    sharpness: 0,
    textInternal: "",
    htmlTextInternal: "",
    typeInternal: TextFieldType.DYNAMIC,
    wordWrap: false
  )
  self.initInteractiveObject()
  return self

proc appendText*(self: TextField, text: string) =
  self.textInternal &= text

proc getTextFormat*(self: TextField, beginIndex: int = -1, endIndex: int = -1): TextFormat =
  return self.defaultTextFormatInternal.clone()

proc setTextFormat*(self: TextField, format: TextFormat, beginIndex: int = -1, endIndex: int = -1) =
  # TODO: handle ranges
  self.defaultTextFormatInternal = format
