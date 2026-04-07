type
  Error* = ref object of CatchableError
    errorID*: int
    kindName*: string

proc newError*(message: string = "", id: int = 0): Error =
  let self = new(Error)
  self.msg = message
  self.errorID = id
  self.kindName = "Error"
  return self

method toString*(self: Error): string {.base.} =
  if self.msg != "":
    return self.msg
  else:
    return "Error"

proc `$`*(self: Error): string =
  return self.toString()
