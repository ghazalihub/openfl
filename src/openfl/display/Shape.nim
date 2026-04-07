import ./DisplayObject

type
  Shape* = ref object of DisplayObject

proc initShape*(self: Shape) =
  self.initDisplayObject()

proc newShape*(): Shape =
  let self = Shape()
  self.initShape()
  return self
