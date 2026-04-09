type
  Object* = ref object of RootObj

proc newObject*(): Object =
  return Object()

proc toString*(self: Object): string =
  return "[object Object]"
