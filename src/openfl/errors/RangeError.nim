import ./Error

type
  RangeError* = ref object of Error

proc newRangeError*(message: string = ""): RangeError =
  let self = new(RangeError)
  self.msg = message
  self.kindName = "RangeError"
  return self
