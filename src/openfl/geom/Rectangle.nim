import ./Point
import ./Matrix

type
  Rectangle* = ref object
    x*: float64
    y*: float64
    width*: float64
    height*: float64

proc newRectangle*(x: float64 = 0, y: float64 = 0, width: float64 = 0, height: float64 = 0): Rectangle =
  Rectangle(x: x, y: y, width: width, height: height)

proc right*(self: Rectangle): float64 =
  self.x + self.width

proc `right=`*(self: Rectangle, value: float64) =
  self.width = value - self.x

proc bottom*(self: Rectangle): float64 =
  self.y + self.height

proc `bottom=`*(self: Rectangle, value: float64) =
  self.height = value - self.y

proc left*(self: Rectangle): float64 =
  self.x

proc `left=`*(self: Rectangle, value: float64) =
  self.width -= value - self.x
  self.x = value

proc top*(self: Rectangle): float64 =
  self.y

proc `top=`*(self: Rectangle, value: float64) =
  self.height -= value - self.y
  self.y = value

proc topLeft*(self: Rectangle): Point =
  newPoint(self.x, self.y)

proc `topLeft=`*(self: Rectangle, p: Point) =
  self.x = p.x
  self.y = p.y

proc bottomRight*(self: Rectangle): Point =
  newPoint(self.x + self.width, self.y + self.height)

proc `bottomRight=`*(self: Rectangle, p: Point) =
  self.width = p.x - self.x
  self.height = p.y - self.y

proc size*(self: Rectangle): Point =
  newPoint(self.width, self.height)

proc `size=`*(self: Rectangle, p: Point) =
  self.width = p.x
  self.height = p.y

proc clone*(self: Rectangle): Rectangle =
  newRectangle(self.x, self.y, self.width, self.height)

proc contains*(self: Rectangle, x: float64, y: float64): bool =
  x >= self.x and y >= self.y and x < self.right and y < self.bottom

proc containsPoint*(self: Rectangle, point: Point): bool =
  self.contains(point.x, point.y)

proc containsRect*(self: Rectangle, rect: Rectangle): bool =
  if rect.width <= 0 or rect.height <= 0:
    rect.x > self.x and rect.y > self.y and rect.right < self.right and rect.bottom < self.bottom
  else:
    rect.x >= self.x and rect.y >= self.y and rect.right <= self.right and rect.bottom <= self.bottom

proc copyFrom*(self: Rectangle, sourceRect: Rectangle) =
  self.x = sourceRect.x
  self.y = sourceRect.y
  self.width = sourceRect.width
  self.height = sourceRect.height

proc equals*(self: Rectangle, toCompare: Rectangle): bool =
  if self == toCompare: return true
  not toCompare.isNil and self.x == toCompare.x and self.y == toCompare.y and self.width == toCompare.width and self.height == toCompare.height

proc inflate*(self: Rectangle, dx: float64, dy: float64) =
  self.x -= dx
  self.width += dx * 2
  self.y -= dy
  self.height += dy * 2

proc inflatePoint*(self: Rectangle, point: Point) =
  self.inflate(point.x, point.y)

proc intersection*(self: Rectangle, toIntersect: Rectangle): Rectangle =
  let x0 = if self.x < toIntersect.x: toIntersect.x else: self.x
  let x1 = if self.right > toIntersect.right: toIntersect.right else: self.right

  if x1 <= x0:
    return newRectangle()

  let y0 = if self.y < toIntersect.y: toIntersect.y else: self.y
  let y1 = if self.bottom > toIntersect.bottom: toIntersect.bottom else: self.bottom

  if y1 <= y0:
    return newRectangle()

  newRectangle(x0, y0, x1 - x0, y1 - y0)

proc setTo*(self: Rectangle, xa: float64, ya: float64, widtha: float64, heighta: float64)

proc intersectionToOutput*(self: Rectangle, toIntersect: Rectangle, output: Rectangle): Rectangle =
  var res = if output.isNil: newRectangle() else: output

  let x0 = if self.x < toIntersect.x: toIntersect.x else: self.x
  let x1 = if self.right > toIntersect.right: toIntersect.right else: self.right

  if x1 <= x0:
    res.setTo(0.0, 0.0, 0.0, 0.0)
    return res

  let y0 = if self.y < toIntersect.y: toIntersect.y else: self.y
  let y1 = if self.bottom > toIntersect.bottom: toIntersect.bottom else: self.bottom

  if y1 <= y0:
    res.setTo(0.0, 0.0, 0.0, 0.0)
    return res

  res.setTo(x0, y0, x1 - x0, y1 - y0)
  res

proc intersects*(self: Rectangle, toIntersect: Rectangle): bool =
  let x0 = if self.x < toIntersect.x: toIntersect.x else: self.x
  let x1 = if self.right > toIntersect.right: toIntersect.right else: self.right

  if x1 <= x0:
    return false

  let y0 = if self.y < toIntersect.y: toIntersect.y else: self.y
  let y1 = if self.bottom > toIntersect.bottom: toIntersect.bottom else: self.bottom

  y1 > y0

proc isEmpty*(self: Rectangle): bool =
  self.width <= 0 or self.height <= 0

proc offset*(self: Rectangle, dx: float64, dy: float64) =
  self.x += dx
  self.y += dy

proc offsetPoint*(self: Rectangle, point: Point) =
  self.x += point.x
  self.y += point.y

proc setEmpty*(self: Rectangle) =
  self.x = 0
  self.y = 0
  self.width = 0
  self.height = 0

proc setTo*(self: Rectangle, xa: float64, ya: float64, widtha: float64, heighta: float64) =
  self.x = xa
  self.y = ya
  self.width = widtha
  self.height = heighta

proc `$`*(self: Rectangle): string =
  "(x=" & $self.x & ", y=" & $self.y & ", width=" & $self.width & ", height=" & $self.height & ")"

proc union*(self: Rectangle, toUnion: Rectangle): Rectangle =
  if self.width == 0 or self.height == 0:
    return toUnion.clone()
  elif toUnion.width == 0 or toUnion.height == 0:
    return self.clone()

  let x0 = if self.x > toUnion.x: toUnion.x else: self.x
  let x1 = if self.right < toUnion.right: toUnion.right else: self.right
  let y0 = if self.y > toUnion.y: toUnion.y else: self.y
  let y1 = if self.bottom < toUnion.bottom: toUnion.bottom else: self.bottom

  newRectangle(x0, y0, x1 - x0, y1 - y0)

proc unionToOutput*(self: Rectangle, toUnion: Rectangle, output: Rectangle): Rectangle =
  var res = if output.isNil: newRectangle() else: output

  if self.width == 0 or self.height == 0:
    res.setTo(toUnion.x, toUnion.y, toUnion.width, toUnion.height)
    return res
  elif toUnion.width == 0 or toUnion.height == 0:
    res.setTo(self.x, self.y, self.width, self.height)
    return res

  let x0 = if self.x > toUnion.x: toUnion.x else: self.x
  let x1 = if self.right < toUnion.right: toUnion.right else: self.right
  let y0 = if self.y > toUnion.y: toUnion.y else: self.y
  let y1 = if self.bottom < toUnion.bottom: toUnion.bottom else: self.bottom

  res.setTo(x0, y0, x1 - x0, y1 - y0)
  res

proc contract*(self: Rectangle, x: float64, y: float64, width: float64, height: float64) =
  if self.width == 0 and self.height == 0:
    return

  var offsetX = 0.0
  var offsetY = 0.0
  var offsetRight = 0.0
  var offsetBottom = 0.0

  if self.x < x: offsetX = x - self.x
  if self.y < y: offsetY = y - self.y
  if self.right > x + width: offsetRight = (x + width) - self.right
  if self.bottom > y + height: offsetBottom = (y + height) - self.bottom

  self.x += offsetX
  self.y += offsetY
  self.width += offsetRight - offsetX
  self.height += offsetBottom - offsetY

proc expand*(self: Rectangle, x: float64, y: float64, width: float64, height: float64) =
  if self.width == 0 and self.height == 0:
    self.x = x
    self.y = y
    self.width = width
    self.height = height
    return

  let cacheRight = self.right
  let cacheBottom = self.bottom

  if self.x > x:
    self.x = x
    self.width = cacheRight - x

  if self.y > y:
    self.y = y
    self.height = cacheBottom - y

  if cacheRight < x + width: self.width = x + width - self.x
  if cacheBottom < y + height: self.height = y + height - self.y

proc transform*(self: Rectangle, rect: Rectangle, m: Matrix) =
  let tx0_initial = m.a * self.x + m.c * self.y
  var tx0 = tx0_initial
  var tx1 = tx0_initial
  let ty0_initial = m.b * self.x + m.d * self.y
  var ty0 = ty0_initial
  var ty1 = ty0_initial

  var tx = m.a * (self.x + self.width) + m.c * self.y
  var ty = m.b * (self.x + self.width) + m.d * self.y

  if tx < tx0: tx0 = tx
  if ty < ty0: ty0 = ty
  if tx > tx1: tx1 = tx
  if ty > ty1: ty1 = ty

  tx = m.a * (self.x + self.width) + m.c * (self.y + self.height)
  ty = m.b * (self.x + self.width) + m.d * (self.y + self.height)

  if tx < tx0: tx0 = tx
  if ty < ty0: ty0 = ty
  if tx > tx1: tx1 = tx
  if ty > ty1: ty1 = ty

  tx = m.a * self.x + m.c * (self.y + self.height)
  ty = m.b * self.x + m.d * (self.y + self.height)

  if tx < tx0: tx0 = tx
  if ty < ty0: ty0 = ty
  if tx > tx1: tx1 = tx
  if ty > ty1: ty1 = ty

  rect.setTo(tx0 + m.tx, ty0 + m.ty, tx1 - tx0, ty1 - ty0)
