import ./Error

type
  ArgumentError* = ref object of Error

proc newArgumentError*(message: string = ""): ArgumentError =
  let self = new(ArgumentError)
  self.msg = message
  self.kindName = "ArgumentError"
  return self
