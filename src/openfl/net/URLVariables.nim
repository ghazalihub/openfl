import std/tables
import std/strutils
import std/uri

type
  URLVariables* = ref object
    variables*: TableRef[string, string]

proc newURLVariables*(source: string = ""): URLVariables =
  let self = URLVariables(variables: newTableRef[string, string]())
  if source != "":
    self.decode(source)
  return self

proc decode*(self: URLVariables, source: string) =
  self.variables.clear()
  let pairs = source.split('&')
  for pair in pairs:
    let kv = pair.split('=')
    if kv.len == 2:
      self.variables[decodeUrl(kv[0])] = decodeUrl(kv[1])
    elif kv.len == 1 and kv[0] != "":
      self.variables[decodeUrl(kv[0])] = ""

proc toString*(self: URLVariables): string =
  var result = ""
  for k, v in self.variables:
    if result != "": result &= "&"
    result &= encodeUrl(k) & "=" & encodeUrl(v)
  return result

proc `$`*(self: URLVariables): string =
  return self.toString()
