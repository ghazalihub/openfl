import ./DisplayObject

type
  InteractiveObject* = ref object of DisplayObject
    doubleClickEnabled*: bool
    mouseEnabled*: bool
    tabEnabledInternal: bool

proc initInteractiveObject*(self: InteractiveObject) =
  self.initDisplayObject()
  self.doubleClickEnabled = false
  self.mouseEnabled = true
  self.tabEnabledInternal = false

proc newInteractiveObject*(): InteractiveObject =
  let self = InteractiveObject()
  self.initInteractiveObject()
  return self
