import ../display/BlendMode

type
  BitmapFilter* = ref object of RootObj
    bottomExtension*: int
    leftExtension*: int
    needSecondBitmapData*: bool
    numShaderPasses*: int
    preserveObject*: bool
    renderDirty*: bool
    rightExtension*: int
    shaderBlendMode*: BlendMode
    smooth*: bool
    topExtension*: int

proc newBitmapFilter*(): BitmapFilter =
  return BitmapFilter(
    bottomExtension: 0,
    leftExtension: 0,
    needSecondBitmapData: true,
    numShaderPasses: 0,
    preserveObject: false,
    rightExtension: 0,
    shaderBlendMode: BlendMode.NORMAL,
    topExtension: 0,
    smooth: true
  )

method clone*(self: BitmapFilter): BitmapFilter {.base.} =
  return newBitmapFilter()
