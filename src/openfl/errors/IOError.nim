import ./Error

type
  IOError* = ref object of Error

proc newIOError*(message: string = ""): IOError =
  let self = new(IOError)
  self.msg = message
  self.kindName = "IOError"
  return self
