import ./Object

type
  Function* = ref object of Object
    # In Haxe/AS3, Function is a class. In Nim, it's more like a proc.
    # We can use a wrapper for dynamic calling if needed.
    methodName*: string
    target*: RootRef

proc newFunction*(target: RootRef = nil, methodName: string = ""): Function =
  return Function(target: target, methodName: methodName)

proc toString*(self: Function): string =
  return "[object Function]"
