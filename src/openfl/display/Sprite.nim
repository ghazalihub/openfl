import ./DisplayObjectContainer

type
  Sprite* = ref object of DisplayObjectContainer
    buttonMode*: bool
    useHandCursor*: bool

proc initSprite*(self: Sprite) =
  self.initDisplayObjectContainer()
  self.buttonMode = false
  self.useHandCursor = false

proc newSprite*(): Sprite =
  let self = Sprite()
  self.initSprite()
  return self
