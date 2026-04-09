import ../../events/EventDispatcher

type
  TextureBase* = ref object of EventDispatcher

proc initTextureBase*(self: TextureBase) =
  self.initEventDispatcher()

proc dispose*(self: TextureBase) =
  # TODO: implement
  discard
