import ./Error

type
  IllegalOperationError* = ref object of Error

proc newIllegalOperationError*(message: string = ""): IllegalOperationError =
  let self = new(IllegalOperationError)
  self.msg = message
  self.kindName = "IllegalOperationError"
  return self
