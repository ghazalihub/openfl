import ./Error

type
  EOFError* = ref object of Error

proc newEOFError*(message: string = "End of file was encountered", id: int = 2030): EOFError =
  let self = new(EOFError)
  self.msg = message
  self.errorID = id
  self.kindName = "EOFError"
  return self
