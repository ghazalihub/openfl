import std/[tables, os, json, streams]
import ../events/EventDispatcher
import ./ObjectEncoding

type
  StringWrapper* = ref object of RootObj
    val*: string

type
  SharedObject* = ref object of EventDispatcher
    data*: TableRef[string, RootRef]
    objectEncoding*: ObjectEncoding

proc newSharedObject*(): SharedObject =
  let self = SharedObject(
    data: new(TableRef[string, RootRef]),
    objectEncoding: ObjectEncoding.AMF3
  )
  self.initEventDispatcher()
  return self

var defaultObjectEncoding* = ObjectEncoding.AMF3

proc getLocal*(name: string, localPath: string = "", secure: bool = false): SharedObject =
  let self = newSharedObject()
  # Simplified persistence: save to current directory for now
  let filename = name & ".sol"
  if fileExists(filename):
    try:
      let content = readFile(filename)
      let jsonNode = parseJson(content)
      for key, value in jsonNode.pairs:
        # This is a very limited implementation, only supporting string values for now
        if value.kind == JString:
          self.data[key] = StringWrapper(val: value.getStr())
    except:
      discard
  return self

proc flush*(self: SharedObject, name: string = "test", minDiskSpace: int = 0) =
  let filename = name & ".sol"
  var jsonNode = newJObject()
  for key, value in self.data.pairs:
    # Very limited implementation
    try:
      if value of StringWrapper:
        jsonNode[key] = %*(StringWrapper(value).val)
    except:
      discard

  try:
    writeFile(filename, jsonNode.pretty())
  except:
    discard

proc clear*(self: SharedObject) =
  self.data.clear()
