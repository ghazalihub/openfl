import ./Error

type
  PermissionError* = ref object of Error

proc newPermissionError*(message: string = ""): PermissionError =
  let self = new(PermissionError)
  self.msg = message
  self.kindName = "PermissionError"
  return self
