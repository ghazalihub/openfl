import ./InteractiveObject

type
  DisplayObjectContainer* = ref object of InteractiveObject
    children*: seq[DisplayObject]
    mouseChildren*: bool

proc initDisplayObjectContainer*(self: DisplayObjectContainer) =
  self.initInteractiveObject()
  self.children = @[]
  self.mouseChildren = true

proc newDisplayObjectContainer*(): DisplayObjectContainer =
  let self = DisplayObjectContainer()
  self.initDisplayObjectContainer()
  return self

proc addChild*(self: DisplayObjectContainer, child: DisplayObject): DisplayObject =
  if child.isNil: return nil
  if not child.parent.isNil:
    discard cast[DisplayObjectContainer](child.parent).removeChild(child)

  self.children.add(child)
  child.parent = self
  return child

proc removeChild*(self: DisplayObjectContainer, child: DisplayObject): DisplayObject =
  if child.isNil: return nil
  let index = self.children.find(child)
  if index != -1:
    self.children.delete(index)
    child.parent = nil
    return child
  return nil

proc numChildren*(self: DisplayObjectContainer): int =
  return self.children.len

proc getChildAt*(self: DisplayObjectContainer, index: int): DisplayObject =
  if index >= 0 and index < self.children.len:
    return self.children[index]
  return nil
