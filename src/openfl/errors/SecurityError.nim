import ./Error

type
  SecurityError* = ref object of Error

proc newSecurityError*(message: string = ""): SecurityError =
  let self = new(SecurityError)
  self.msg = message
  self.kindName = "SecurityError"
  return self
