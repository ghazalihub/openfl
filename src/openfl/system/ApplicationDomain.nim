type
  ApplicationDomain* = ref object
    parentDomain*: ApplicationDomain

proc newApplicationDomain*(parentDomain: ApplicationDomain = nil): ApplicationDomain =
  return ApplicationDomain(parentDomain: parentDomain)

var currentDomain* = newApplicationDomain()
