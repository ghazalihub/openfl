import ./Error

type
  TypeError* = ref object of Error

proc newTypeError*(message: string = ""): TypeError =
  let self = new(TypeError)
  self.msg = message
  self.kindName = "TypeError"
  return self
