type
  Vector*[T] = ref object
    data: seq[T]
    fixed*: bool

proc newVector*[T](length: int = 0, fixed: bool = false): Vector[T] =
  var data = newSeq[T](length)
  return Vector[T](data: data, fixed: fixed)

proc length*[T](self: Vector[T]): int =
  return self.data.len

proc `length=`*[T](self: Vector[T], value: int) =
  if not self.fixed:
    self.data.setLen(value)

proc `[]`*[T](self: Vector[T], index: int): T =
  return self.data[index]

proc `[]=`*[T](self: Vector[T], index: int, value: T) =
  self.data[index] = value

proc push*[T](self: Vector[T], x: T): int =
  if not self.fixed:
    self.data.add(x)
  return self.data.len

proc pop*[T](self: Vector[T]): T =
  if not self.fixed and self.data.len > 0:
    return self.data.pop()
  # Default value for T
  var res: T
  return res

proc shift*[T](self: Vector[T]): T =
  if not self.fixed and self.data.len > 0:
    let res = self.data[0]
    self.data.delete(0)
    return res
  var res: T
  return res

proc unshift*[T](self: Vector[T], x: T) =
  if not self.fixed:
    self.data.insert(x, 0)

proc indexOf*[T](self: Vector[T], x: T, fromIndex: int = 0): int =
  for i in fromIndex ..< self.data.len:
    if self.data[i] == x:
      return i
  return -1

proc lastIndexOf*[T](self: Vector[T], x: T, fromIndex: int = -1): int =
  let start = if fromIndex == -1: self.data.len - 1 else: fromIndex
  for i in countdown(start, 0):
    if self.data[i] == x:
      return i
  return -1

iterator items*[T](self: Vector[T]): T =
  for item in self.data:
    yield item

iterator pairs*[T](self: Vector[T]): (int, T) =
  for i, item in self.data:
    yield (i, item)
